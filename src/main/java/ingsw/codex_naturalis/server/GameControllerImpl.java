package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.common.enumerations.TurnStatus;
import ingsw.codex_naturalis.common.events.gameplayPhase.DrawCardEvent;
import ingsw.codex_naturalis.common.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.common.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.common.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.common.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.common.exceptions.*;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.Message;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.server.model.player.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameControllerImpl implements GameController {

    private final Game model;

    private final List<VirtualView> virtualViews = new ArrayList<>();

    private int readyPlayers = 0;

    //including the player who gets to 20 points
    private int turnsLeftInSecondToLastRound;

    private int turnsLeftInLastRound;

    private final ObjectMapper objectMapper = new ObjectMapper();



    public GameControllerImpl(Game model, List<Client> clients) {

        this.model = model;
        turnsLeftInLastRound = model.getNumOfPlayers();
        turnsLeftInSecondToLastRound = model.getNumOfPlayers();
        for (Client client : clients)
            virtualViews.add(new VirtualView(client));

        for (VirtualView virtualView : virtualViews)
            model.addObserver(virtualView);

    }




    public Game getModel() {
        return model;
    }

    public List<VirtualView> getVirtualViews() {
        return virtualViews;
    }


    private Client getClientByNickname(String nickname) throws RemoteException{

        for (VirtualView virtualView : virtualViews) {
            if (virtualView.getClient().getNickname().equals(nickname))
                return virtualView.getClient();
        }

        throw new RuntimeException("No client found");

    }


    @Override
    public synchronized void updateReady(){

        readyPlayers++;
        if (readyPlayers == model.getNumOfPlayers()) {
            model.setupResourceAndGoldCards();
            model.dealInitialCards();
            readyPlayers = 0;
        }

    }

    @Override
    public synchronized void updateInitialCard(String nickname, String jsonInitialCardEvent) {

        try {
            InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
            Player player = model.getPlayerByNickname(nickname);
            PlayableCard initialCard = player.getInitialCard();
            switch (initialCardEvent) {
                case FLIP -> player.flip(initialCard);
                case PLAY -> player.playInitialCard();
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public synchronized void updateColor(String nickname, String jsonColor) {

        try {
            Color color = objectMapper.readValue(jsonColor, Color.class);
            Player player = model.getPlayerByNickname(nickname);
            for (Player p : model.getPlayerOrder())
                if (p.getColor() == color) {
                    getClientByNickname(nickname).reportSetupUIError(new ColorAlreadyChosenException().getMessage());
                    return;
                }
            player.setColor(color);
            readyPlayers++;
            if (readyPlayers == model.getNumOfPlayers()) {
                model.setupHands();
                model.setupCommonObjectiveCards();
                model.setupSecretObjectiveCards();
                readyPlayers = 0;
            }
        } catch (JsonProcessingException | RemoteException e) {
            System.err.println("Error while updating client\n" + e.getMessage());
        }


    }

    @Override
    public synchronized void updateObjectiveCard(String nickname, String jsonObjectiveCardChoice) {

        try {
            ObjectiveCardChoice objectiveCardChoice = objectMapper.readValue(jsonObjectiveCardChoice, ObjectiveCardChoice.class);
            Player player = model.getPlayerByNickname(nickname);
            ObjectiveCard objectiveCard;
            switch (objectiveCardChoice) {
                case CHOICE_1 -> {
                    objectiveCard = player.getSecretObjectiveCards().removeFirst();
                    model.getObjectiveCardsDeck().discardACard(player.getSecretObjectiveCards().removeFirst());
                }
                case CHOICE_2 -> {
                    objectiveCard = player.getSecretObjectiveCards().removeLast();
                    model.getObjectiveCardsDeck().discardACard(player.getSecretObjectiveCards().removeFirst());
                }
                default -> {
                    return;
                }
            }
            player.chooseObjectiveCard(objectiveCard);
            readyPlayers++;
            if (readyPlayers == model.getPlayerOrder().size()) {
                model.shufflePlayerList();
                model.setGameStatus(GameStatus.GAMEPLAY);
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json:\n" + e.getMessage());
        }

    }





    @Override
    public synchronized void updateFlipCard(String nickname, String jsonFlipCardEvent) {

        try {
            FlipCardEvent flipCardEvent = objectMapper.readValue(jsonFlipCardEvent, FlipCardEvent.class);
            Player player = model.getPlayerByNickname(nickname);
            switch (flipCardEvent) {
                case FLIP_CARD_1 -> player.flip(player.getHand().get(0));
                case FLIP_CARD_2 -> player.flip(player.getHand().get(1));
                case FLIP_CARD_3 -> player.flip(player.getHand().get(2));
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json:\n" + e.getMessage());
        }

    }

    @Override
    public synchronized void updatePlayCard(String nickname, String jsonPlayCardEvent, int x, int y) throws NotYourTurnException, NotYourPlayTurnStatusException, NotPlayableException {

        try {
            PlayCardEvent playCardEvent = objectMapper.readValue(jsonPlayCardEvent, PlayCardEvent.class);

            Player player = model.getPlayerByNickname(nickname);

            if (!nickname.equals(model.getCurrentPlayer().getNickname())) {
                getClientByNickname(nickname).reportGameplayUIError(new NotYourTurnException().getMessage());
                return;
            }

            if (!player.getTurnStatus().equals(TurnStatus.PLAY)) {
                getClientByNickname(nickname).reportGameplayUIError(new NotYourPlayTurnStatusException().getMessage());
                return;
            }

            PlayableCard cardToPlay;
            switch (playCardEvent) {
                case PLAY_CARD_1 -> cardToPlay = player.getHand().get(0);
                case PLAY_CARD_2 -> cardToPlay = player.getHand().get(1);
                case PLAY_CARD_3 -> cardToPlay = player.getHand().get(2);
                default -> {
                    return;
                }
            }

            if (!cardToPlay.isPlayable(player.getPlayerArea(), x, y)) {
                getClientByNickname(nickname).reportGameplayUIError(new NotPlayableException().getMessage());
                return;
            }

            player.playCard(cardToPlay, x, y);

            checkGameStatus(player);
        } catch (JsonProcessingException | RemoteException e) {
            System.err.println("Error while updating client:\n" + e.getMessage());
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
            nextPlayer();
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() == GameStatus.LAST_ROUND_DECKS_EMPTY) {
            turnsLeftInSecondToLastRound--;
            nextPlayer();
        }

        if (turnsLeftInLastRound == 0) {
            model.setGameStatus(GameStatus.ENDGAME);
        }

    }

    @Override
    public synchronized void updateDrawCard(String nickname, String jsonDrawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {

        try {
            DrawCardEvent drawCardEvent = objectMapper.readValue(jsonDrawCardEvent, DrawCardEvent.class);

            Player player = model.getPlayerByNickname(nickname);

            if (!nickname.equals(model.getCurrentPlayer().getNickname())) {
                getClientByNickname(nickname).reportGameplayUIError(new NotYourTurnException().getMessage());
                return;
            }

            if (!player.getTurnStatus().equals(TurnStatus.DRAW)) {
                getClientByNickname(nickname).reportGameplayUIError(new NotYourDrawTurnStatusException().getMessage());
                return;
            }

            PlayableCard drawnCard;
            switch (drawCardEvent) {
                case DRAW_FROM_RESOURCE_CARDS_DECK -> {
                    drawnCard = model.getResourceCardsDeck().drawACard();
                    drawnCard.flip();
                }
                case DRAW_FROM_GOLD_CARDS_DECK -> {
                    drawnCard = model.getGoldCardsDeck().drawACard();
                    drawnCard.flip();
                }
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
                default -> {
                    return;
                }
            }

            nextPlayer();

            player.drawCard(drawnCard);

            checkTurnsLeftInSecondToLastRound(player);
        } catch (JsonProcessingException | RemoteException e) {
            System.err.println("Error while updating client:\n" + e.getMessage());
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
    }
    /**
     * Sets the new current player
     */
    private void nextPlayer() {
        Player nextPlayer;
        int index = model.getPlayerOrder().indexOf(model.getCurrentPlayer());
        if (index < model.getPlayerOrder().size() -1 ) {
            nextPlayer = model.getPlayerOrder().get(index+1);
        } else {
            nextPlayer = model.getPlayerOrder().getFirst();
        }
        model.setCurrentPlayer(nextPlayer);
    }

    @Override
    public synchronized void updateSendMessage(String nickname, String receiver, String content) {

        List<String> receivers = new ArrayList<>();
        if (receiver != null)
            receivers.add(receiver);
        else {
            for (Player player : model.getPlayerOrder())
                if (!player.getNickname().equals(nickname))
                    receivers.add(player.getNickname());
        }
        model.addMessageToChat(new Message(content, nickname, receivers));

    }

}
