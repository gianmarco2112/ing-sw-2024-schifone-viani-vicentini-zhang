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

/**
 * This class is the controller of a specific game.
 */
public class GameControllerImpl implements GameController {

    /**
     * Model
     */
    private final Game model;

    /**
     * Virtual views connected to the game that observe the model and update the controller.
     */
    private final List<VirtualView> virtualViews = new ArrayList<>();

    /**
     * Updates queue, used to make rmi async and to ensure that every message received from a client
     * gets processed in the correct order.
     */
    private final BlockingQueue<Runnable> updatesQueue = new LinkedBlockingQueue<>();

    /**
     * Main server
     */
    private final ServerImpl server;

    /**
     * Ready players count, used for some setup aspects of the game to make sure all players
     * are going together.
     */
    private int readyPlayers = 0;

    /**
     * Jackson object mapper.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();



    public GameControllerImpl(ServerImpl server, int gameID, int numOfPlayers, Client client, String nickname) {
        this.server = server;
        this.model = new Game(gameID, numOfPlayers);
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

    /**
     * Method to add a player to the game, it also creates his virtual view.
     * @param client client to add
     * @param nickname client's nickname
     * @return true if the game is full, false if it isn't
     */
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


    /**
     * Method called by a client to notify he is ready to play.
     */
    @Override
    public synchronized void readyToPlay(String nickname){
        try {
            updatesQueue.put( () -> {
                readyPlayers++;
                model.getPlayerByNickname(nickname).setReady(true);
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

    /**
     * Method called by a client to flip or play his initial card.
     * @param nickname client's nickname
     * @param jsonInitialCardEvent flip or play
     */
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

    /**
     * Method called by a client to choose his color.
     * @param nickname client's nickname
     * @param jsonColor color
     */
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

    /**
     * Method called by a client to choose his secret objective card.
     * @param nickname client's nickname
     * @param index objective card choice (index)
     */
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


    /**
     * Method called by a client to flip his hand card.
     * @param nickname client's nickname
     * @param index index of the hand card
     */
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

    /**
     * Method called by a client to play his hand card.
     * @param nickname client's nickname
     * @param index index of the hand card
     * @param x coordinate x of his play area
     * @param y coordinate y of his play area
     */
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

    /**
     * Method called after a card has been played, it checks if the player reached 20 points,
     * if the game ended and changes the turn.
     * @param player player that has just played his card
     */
    private void checkGameStatus(Player player) {
        switch (model.getGameStatus()) {
            case GAMEPLAY -> {
                if (player.getPlayerArea().getPoints() >= 20)
                    model.setGameStatus(GameStatus.SECOND_TO_LAST_ROUND_20_POINTS);
                player.setTurnStatus(TurnStatus.DRAW);
            }
            case SECOND_TO_LAST_ROUND_20_POINTS -> player.setTurnStatus(TurnStatus.DRAW);
            case SECOND_TO_LAST_ROUND_DECKS_EMPTY -> {
                boolean newRound = model.nextPlayer();
                if (newRound)
                    model.setGameStatus(GameStatus.LAST_ROUND);
            }
            case LAST_ROUND -> {
                boolean newRound = model.nextPlayer();
                if (newRound)
                    prepareEndGame();
            }
        }
    }

    private void prepareEndGame() {
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

    /**
     * Method called by a client to draw a card.
     * @param nickname client's nickname
     * @param jsonDrawCardEvent card to draw
     * @throws NotYourTurnException when it's not his turn
     * @throws NotYourDrawTurnStatusException when he has to play a card first
     */
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
                    boolean newRound = model.nextPlayer();
                    checkTurnsLeftInSecondToLastRound(player, newRound);
                } catch (JsonProcessingException e) {
                    System.err.println("Error while processing json:\n" + e.getMessage());
                } catch (EmptyDeckException ex) {
                    model.exceptionThrown(player, ex.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    model.exceptionThrown(player, "No more revealed card here!");
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draw from deck method.
     * @param player player who wants to draw
     * @param drawCardEvent card to draw
     */
    private void drawFromDeck(Player player, DrawCardEvent drawCardEvent) throws EmptyDeckException {
        PlayableCard drawnCard = null;
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
    }

    /**
     * Draw revealed card method.
     * @param player player who wants to draw
     * @param drawCardEvent card to draw
     */
    private void drawRevealedCard(Player player, DrawCardEvent drawCardEvent) throws IndexOutOfBoundsException, EmptyDeckException {
        PlayableCard drawnCard = null;
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
    }

    /**
     * Method called after a player has drawn a card, it is used for turns managing.
     * @param player player that has drawn a card
     */
    private void checkTurnsLeftInSecondToLastRound(Player player, boolean newRound) {
        if (model.getResourceCardsDeck().isEmpty() &&
                model.getGoldCardsDeck().isEmpty() &&
                model.getRevealedResourceCards().isEmpty() &&
                model.getRevealedGoldCards().isEmpty() &&
                model.getGameStatus() != GameStatus.SECOND_TO_LAST_ROUND_DECKS_EMPTY &&
                model.getGameStatus() != GameStatus.LAST_ROUND) {
            model.setGameStatus(GameStatus.SECOND_TO_LAST_ROUND_DECKS_EMPTY);
        }
        if ((model.getGameStatus() == GameStatus.SECOND_TO_LAST_ROUND_20_POINTS ||
                model.getGameStatus() == GameStatus.SECOND_TO_LAST_ROUND_DECKS_EMPTY) && newRound) {
            model.setGameStatus(GameStatus.LAST_ROUND);
        }
        player.setTurnStatus(TurnStatus.PLAY);
    }

    /**
     * Method called by a client to send a chat message
     * @param nickname client's nickname sender
     * @param receiver receiver (null if it's a global message)
     * @param content message content
     */
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


    /**
     * Method called by the main server to remove a player after he left the game.
     * @param nickname player's nickname
     */
    public synchronized void removePlayer(String nickname) {
        model.removePlayer(model.getPlayerByNickname(nickname));
        if (model.getGameStatus() == GameStatus.GAMEPLAY ||
                model.getGameStatus() == GameStatus.SECOND_TO_LAST_ROUND_20_POINTS ||
                model.getGameStatus() == GameStatus.SECOND_TO_LAST_ROUND_DECKS_EMPTY ||
                model.getGameStatus() == GameStatus.LAST_ROUND) {
            if (getPlayersConnectionStatus() == GameRunningStatus.RUNNING)
                skipTurn();
        }
        removeView(nickname);
    }

    /**
     * Method used to manage the turns after the current player has disconnected.
     * @param nickname disconnected player
     */
    private void checkPlayerTurn(String nickname) {
        if (model.getCurrentPlayer().getNickname().equals(nickname)) {
            Player player = model.getPlayerByNickname(nickname);
            switch (player.getTurnStatus()) {
                case DRAW -> drawRandomCard(player);
                case PLAY -> skipTurn();
            }
        }
    }

    /**
     * Method used to skip the current turn when a player leaves or disconnects before playing a card.
     */
    private void skipTurn() {
        boolean newRound = model.nextPlayer();
        if (newRound) {
            switch (model.getGameStatus()) {
                case SECOND_TO_LAST_ROUND_20_POINTS, SECOND_TO_LAST_ROUND_DECKS_EMPTY ->
                        model.setGameStatus(GameStatus.LAST_ROUND);
                case LAST_ROUND -> prepareEndGame();
            }
        }
    }

    /**
     * Method used to draw a random card when a player disconnects after playing a card, so he won't have
     * only 2 cards in case he reconnects.
     * @param player disconnected player
     */
    private void drawRandomCard(Player player) {
        if (!model.getResourceCardsDeck().isEmpty())
            drawFromDeck(player, DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK);
        else if (!model.getGoldCardsDeck().isEmpty())
            drawFromDeck(player, DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK);
        else if (!model.getRevealedResourceCards().isEmpty())
            drawFromDeck(player, DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
        else if (!model.getRevealedGoldCards().isEmpty())
            drawFromDeck(player, DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);

        boolean newRound = model.nextPlayer();
        checkTurnsLeftInSecondToLastRound(player, newRound);
    }

    /**
     * Method called by the main server after a player has disconnected.
     * @param nickname player's nickname
     */
    public synchronized void disconnectPlayer(String nickname) {
        removeView(nickname);
        switch (model.getGameStatus()) {
            case GAMEPLAY, SECOND_TO_LAST_ROUND_20_POINTS,
                    SECOND_TO_LAST_ROUND_DECKS_EMPTY, LAST_ROUND -> {
                model.getPlayerByNickname(nickname).setInGame(false);
                if (getPlayersConnectionStatus() == GameRunningStatus.RUNNING)
                    checkPlayerTurn(nickname);
            }
            case WAITING_FOR_PLAYERS -> model.silentlyRemovePlayer(model.getPlayerByNickname(nickname));
            default -> model.removePlayer(model.getPlayerByNickname(nickname));
        }
    }

    /**
     * Method called to get the game running status based on the players who left
     * and have disconnected.
     * @return the
     */
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

    /**
     * Method used to remove a view after the client has left or disconnected.
     * @param nickname client's nickname
     */
    private void removeView(String nickname) {
        VirtualView viewToRemove = null;
        for (VirtualView view : virtualViews)
            if (view.getNickname().equals(nickname))
                viewToRemove = view;
        virtualViews.remove(viewToRemove);
        model.deleteObserver(viewToRemove);
    }

    /**
     * Method called to reconnect a client to the game.
     * @param client client to reconnect
     * @param nickname client's nickname
     */
    public synchronized void reconnect(Client client, String nickname) {
        VirtualView view = new VirtualView(client, nickname);
        virtualViews.add(view);
        model.addObserver(view);
        model.getPlayerByNickname(nickname).setInGame(true);
    }

    /**
     * Method called to end the game.
     */
    private void endGame() {
        server.endGame(this);
    }
}
