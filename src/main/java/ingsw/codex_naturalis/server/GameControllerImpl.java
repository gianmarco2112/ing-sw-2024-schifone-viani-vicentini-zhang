package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.common.enumerations.TurnStatus;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.exceptions.*;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.Message;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.player.PlayerArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameControllerImpl implements GameController {

    private final Game model;

    private final List<VirtualView> virtualViews = new ArrayList<>();

    private final BlockingQueue<Runnable> updatesQueue = new LinkedBlockingQueue<>();

    private final ServerImpl server;

    private int readyPlayers = 0;

    //including the player who gets to 20 points
    private int turnsLeftInSecondToLastRound;

    private int turnsLeftInLastRound;

    private final ObjectMapper objectMapper = new ObjectMapper();



    public GameControllerImpl(ServerImpl server, int gameID, int numOfPlayers, Client client, String nickname) {
        this.server = server;
        this.model = new Game(gameID, numOfPlayers);
        turnsLeftInLastRound = model.getNumOfPlayers();
        turnsLeftInSecondToLastRound = model.getNumOfPlayers();
        new Thread(() -> {
            while (true) {
                try {
                    updatesQueue.take().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        addPlayer(client, nickname);
    }




    public Game getModel() {
        return model;
    }

    public List<VirtualView> getVirtualViews() {
        return virtualViews;
    }


    public synchronized boolean addPlayer(Client client, String nickname) {
        Player player = new Player(nickname);
        VirtualView virtualView = new VirtualView(client, nickname);
        virtualViews.add(virtualView);
        model.addObserver(virtualView);
        try {
            return model.addPlayer(player);
        } catch (MaxNumOfPlayersInException | InvalidNumOfPlayersException e) {
            model.deleteObserver(virtualView);
            virtualViews.remove(virtualView);
            throw e;
        }
    }


    @Override
    public synchronized void readyToPlay(){
        try {
            updatesQueue.put( () -> {
                readyPlayers++;
                if (readyPlayers >= model.getNumOfPlayers()) {
                    model.setupResourceAndGoldCards();
                    model.dealInitialCards();
                    readyPlayers = 0;
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void updateInitialCard(String nickname, String jsonInitialCardEvent) {
        try {
            updatesQueue.put( () -> {
                Player player = model.getPlayerByNickname(nickname);
                try {
                    InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
                    switch (initialCardEvent) {
                        case FLIP -> player.flipInitialCard();
                        case PLAY -> player.playInitialCard();
                    }
                } catch (JsonProcessingException e) {
                    System.err.println("Error while processing json");
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void chooseColor(String nickname, String jsonColor) {
        try {
            updatesQueue.put( () -> {
                Player player = model.getPlayerByNickname(nickname);
                try {
                    Color color = objectMapper.readValue(jsonColor, Color.class);
                    model.setPlayerColor(player, color);
                    readyPlayers++;
                    if (readyPlayers == model.getNumOfPlayers()) {
                        model.setupHands();
                        model.setupCommonObjectiveCards();
                        model.setupSecretObjectiveCards();
                        readyPlayers = 0;
                    }
                } catch (JsonProcessingException e) {
                    System.err.println("Error while processing json\n" + e.getMessage());
                } catch (ColorAlreadyChosenException ex) {
                    model.exceptionThrown(player, ex.getMessage());
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void chooseSecretObjectiveCard(String nickname, int index) {
        try {
            updatesQueue.put( () -> {
                Player player = model.getPlayerByNickname(nickname);
                try {
                    player.chooseObjectiveCard(index, model.getObjectiveCardsDeck());
                    readyPlayers++;
                    if (readyPlayers == model.getPlayerOrder().size()) {
                        model.shufflePlayerList();
                        model.setGameStatus(GameStatus.GAMEPLAY);
                    }
                } catch (IndexOutOfBoundsException e) {
                    model.exceptionThrown(player, e.getMessage());
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public synchronized void flipCard(String nickname, int index) {
        if (model.getGameRunningStatus() == GameRunningStatus.TO_CANCEL_LATER)
            return;
        try {
            updatesQueue.put( () -> {
                Player player = model.getPlayerByNickname(nickname);
                try {
                    player.flipCard(index);
                } catch (IndexOutOfBoundsException e) {
                    model.exceptionThrown(player, e.getMessage());
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void playCard(String nickname, int index, int x, int y) {
        if (model.getGameRunningStatus() == GameRunningStatus.TO_CANCEL_LATER)
            return;
        try {
            updatesQueue.put( () -> {
                Player player = model.getPlayerByNickname(nickname);
                if (player != model.getCurrentPlayer()) {
                    model.exceptionThrown(player, new NotYourTurnException().getMessage());
                    return;
                }
                try {
                    player.playCard(index, x, y);
                    checkGameStatus(player);
                } catch (IndexOutOfBoundsException | NotYourPlayTurnStatusException | NotPlayableException e) {
                    model.exceptionThrown(player, e.getMessage());
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkGameStatus(Player player) {
        if (player.getPlayerArea().getPoints() >= 20 && model.getGameStatus() == GameStatus.GAMEPLAY) {
            turnsLeftInSecondToLastRound = model.getNumOfPlayers() - model.getPlayerOrder().indexOf(player);
            model.setGameStatus(GameStatus.LAST_ROUND_20_POINTS);
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() != GameStatus.LAST_ROUND_DECKS_EMPTY)
            player.setTurnStatus(TurnStatus.DRAW);

        if (turnsLeftInSecondToLastRound == 0) {
            turnsLeftInLastRound--;
            model.nextPlayer();
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() == GameStatus.LAST_ROUND_DECKS_EMPTY) {
            turnsLeftInSecondToLastRound--;
            model.nextPlayer();
        }

        if (turnsLeftInLastRound == 0) {
            //secret obj cards
            for (Player p : model.getPlayerOrder())
                p.getPlayerArea().getObjectiveCard().gainPoints(new ArrayList<>(List.of(p.getPlayerArea())));
            //common obj cards
            for (ObjectiveCard card : model.getCommonObjectiveCards()) {
                List<PlayerArea> playerAreas = new ArrayList<>();
                for (Player p : model.getPlayerOrder())
                    playerAreas.add(p.getPlayerArea());
                card.gainPoints(playerAreas);
            }
            model.setGameStatus(GameStatus.ENDGAME);
            endGame();
        }
    }

    @Override
    public synchronized void drawCard(String nickname, String jsonDrawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {
        if (model.getGameRunningStatus() == GameRunningStatus.TO_CANCEL_LATER)
            return;
        try {
            updatesQueue.put( () -> {
                Player player = model.getPlayerByNickname(nickname);
                try {
                    DrawCardEvent drawCardEvent = objectMapper.readValue(jsonDrawCardEvent, DrawCardEvent.class);
                    if (!nickname.equals(model.getCurrentPlayer().getNickname())) {
                        model.exceptionThrown(player, new NotYourTurnException().getMessage());
                        return;
                    }
                    if (player.getTurnStatus() != TurnStatus.DRAW) {
                        model.exceptionThrown(player, new NotYourDrawTurnStatusException().getMessage());
                        return;
                    }
                    switch (drawCardEvent) {
                        case DRAW_FROM_RESOURCE_CARDS_DECK, DRAW_FROM_GOLD_CARDS_DECK -> drawFromDeck(player, drawCardEvent);
                        default -> drawRevealedCard(player, drawCardEvent);
                    }
                    model.nextPlayer();
                    checkTurnsLeftInSecondToLastRound(player);
                } catch (JsonProcessingException e) {
                    System.err.println("Error while processing json:\n" + e.getMessage());
                } catch (EmptyDeckException ex) {
                    model.exceptionThrown(player, ex.getMessage());
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawFromDeck(Player player, DrawCardEvent drawCardEvent) {
        PlayableCard drawnCard = null;
        try {
            switch (drawCardEvent) {
                case DRAW_FROM_RESOURCE_CARDS_DECK -> {
                    drawnCard = model.getResourceCardsDeck().drawACard();
                    drawnCard.flip();
                }
                case DRAW_FROM_GOLD_CARDS_DECK -> {
                    drawnCard = model.getGoldCardsDeck().drawACard();
                    drawnCard.flip();
                }
            }
            player.drawCard(drawnCard);
        } catch (EmptyDeckException e) {
            model.exceptionThrown(player, e.getMessage());
        }
    }

    private void drawRevealedCard(Player player, DrawCardEvent drawCardEvent) {
        PlayableCard drawnCard = null;
        try {
            switch (drawCardEvent) {
                case DRAW_REVEALED_RESOURCE_CARD_1 -> {
                    drawnCard = model.removeRevealedResourceCard(0);
                    if (!model.getResourceCardsDeck().isEmpty()) {
                        PlayableCard cardToReveal = model.getResourceCardsDeck().drawACard();
                        cardToReveal.flip();
                        model.addRevealedResourceCard(cardToReveal);
                    }
                }
                case DRAW_REVEALED_RESOURCE_CARD_2 -> {
                    drawnCard = model.removeRevealedResourceCard(1);
                    if (!model.getResourceCardsDeck().isEmpty()) {
                        PlayableCard cardToReveal = model.getResourceCardsDeck().drawACard();
                        cardToReveal.flip();
                        model.addRevealedResourceCard(cardToReveal);
                    }
                }
                case DRAW_REVEALED_GOLD_CARD_1 -> {
                    drawnCard = model.removeRevealedGoldCard(0);
                    if (!model.getGoldCardsDeck().isEmpty()) {
                        PlayableCard cardToReveal = model.getGoldCardsDeck().drawACard();
                        cardToReveal.flip();
                        model.addRevealedGoldCard(cardToReveal);
                    }
                }
                case DRAW_REVEALED_GOLD_CARD_2 -> {
                    drawnCard = model.removeRevealedGoldCard(1);
                    if (!model.getGoldCardsDeck().isEmpty()) {
                        PlayableCard cardToReveal = model.getGoldCardsDeck().drawACard();
                        cardToReveal.flip();
                        model.addRevealedGoldCard(cardToReveal);
                    }
                }
            }
            player.drawCard(drawnCard);
        } catch (IndexOutOfBoundsException e) {
            model.exceptionThrown(player, "No more revealed card here!");
        }
    }

    private void checkTurnsLeftInSecondToLastRound(Player player) {
        if (model.getResourceCardsDeck().isEmpty() &&
                model.getGoldCardsDeck().isEmpty() &&
                model.getRevealedResourceCards().isEmpty() &&
                model.getRevealedGoldCards().isEmpty()) {
            turnsLeftInSecondToLastRound = model.getNumOfPlayers() - model.getPlayerOrder().indexOf(player) - 1;
            model.setGameStatus(GameStatus.LAST_ROUND_DECKS_EMPTY);
        }
        if (model.getGameStatus() == GameStatus.LAST_ROUND_20_POINTS)
            turnsLeftInSecondToLastRound--;
        player.setTurnStatus(TurnStatus.PLAY);
    }

    @Override
    public synchronized void sendMessage(String nickname, String receiver, String content) {
        if (model.getGameRunningStatus() == GameRunningStatus.TO_CANCEL_LATER)
            return;
        try {
            updatesQueue.put( () -> {
                List<String> receivers = new ArrayList<>();
                if (receiver != null)
                    receivers.add(receiver);
                else {
                    for (Player player : model.getPlayerOrder())
                        if (!player.getNickname().equals(nickname))
                            receivers.add(player.getNickname());
                }
                model.addMessageToChat(new Message(content, nickname, receivers));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void removePlayer(String nickname) {
        model.removePlayer(model.getPlayerByNickname(nickname));
        removeView(nickname);
    }

    public synchronized void disconnectPlayer(String nickname) {
        removeView(nickname);
        if (model.getGameStatus() == GameStatus.GAMEPLAY ||
                model.getGameStatus() == GameStatus.LAST_ROUND_20_POINTS ||
                model.getGameStatus() == GameStatus.LAST_ROUND_DECKS_EMPTY)
            model.getPlayerByNickname(nickname).setInGame(false);
        else if (model.getGameStatus() == GameStatus.WAITING_FOR_PLAYERS) {
            model.silentlyRemovePlayer(model.getPlayerByNickname(nickname));
        } else model.removePlayer(model.getPlayerByNickname(nickname));
    }

    public GameRunningStatus getPlayersConnectionStatus() {
        if (model.getNumOfPlayers() == 1)
            return GameRunningStatus.TO_CANCEL_NOW;
        int count = 0;
        for (Player player : model.getPlayerOrder())
            if (player.isInGame())
                count++;
        if (count >= 2)
            return GameRunningStatus.RUNNING;
        return GameRunningStatus.TO_CANCEL_LATER;
    }

    private void removeView(String nickname) {
        VirtualView viewToRemove = null;
        for (VirtualView view : virtualViews)
            if (view.getNickname().equals(nickname))
                viewToRemove = view;
        virtualViews.remove(viewToRemove);
        model.deleteObserver(viewToRemove);
    }

    public synchronized void reconnect(Client client, String nickname) {
        VirtualView view = new VirtualView(client, nickname);
        virtualViews.add(view);
        model.addObserver(view);
        model.getPlayerByNickname(nickname).setInGame(true);
    }

    private void endGame() {
        server.endGame(this);
    }
}
