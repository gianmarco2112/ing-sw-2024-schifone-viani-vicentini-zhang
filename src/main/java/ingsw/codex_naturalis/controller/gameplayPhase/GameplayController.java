package ingsw.codex_naturalis.controller.gameplayPhase;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.enumerations.TurnStatus;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameplayController {

    private final Game model;
    private final List<Client> views;

    //including the player who gets to 20 points
    private int turnsLeftInSecondToLastRound;
    private int turnsLeftInLastRound;

    //--------------------------------------------------------------------------------------
    public GameplayController(Game model, List<Client> views) {
        this.model = model;
        this.views = views;
        turnsLeftInLastRound = model.getNumOfPlayers();
        turnsLeftInSecondToLastRound = model.getNumOfPlayers();
    }

    //-----------------------------------------------------------------------------------------

    public void updateFlipCard(String nickname, FlipCardEvent flipCardEvent) {

        Player player = model.getPlayerByNickname(nickname);
        switch (flipCardEvent) {
            case FLIP_CARD_1 -> player.flip(player.getHand().get(0));
            case FLIP_CARD_2 -> player.flip(player.getHand().get(1));
            case FLIP_CARD_3 -> player.flip(player.getHand().get(2));
        }

    }


    public void updatePlayCard(String nickname, PlayCardEvent playCardEvent, int x, int y) throws NotYourTurnException, NotYourPlayTurnStatusException, NotPlayableException {

        Player player = model.getPlayerByNickname(nickname);

        if (!nickname.equals(model.getCurrentPlayer().getNickname()))
            throw new NotYourTurnException();

        if (!player.getTurnStatus().equals(TurnStatus.PLAY))
            throw new NotYourPlayTurnStatusException();

        PlayableCard cardToPlay;
        switch (playCardEvent) {
            case PLAY_CARD_1 -> cardToPlay = player.getHand().get(0);
            case PLAY_CARD_2 -> cardToPlay = player.getHand().get(1);
            case PLAY_CARD_3 -> cardToPlay = player.getHand().get(2);
            default -> { return; }
        }

        if(!cardToPlay.isPlayable(player.getPlayerArea(), x, y))
            throw new NotPlayableException();

        player.playCard(cardToPlay, x, y);

        checkGameStatus(player);
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



    public void updateDrawCard(Client client, DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {
/*
        try {
            if (!client.getNickname().equals(model.getCurrentPlayer().getNickname()))
                throw new NotYourTurnException();
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }

        if (!model.getCurrentPlayer().getTurnStatus().equals(TurnStatus.DRAW))
            throw new NotYourDrawTurnStatusException();

        Player player = null;
        try {
            player = getPlayerByNickname(client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }

        PlayableCard drawnCard = null;
        switch (drawCardEvent) {
            case DRAW_FROM_RESOURCE_CARDS_DECK ->
                drawnCard = drawFromResourceCardsDeck(client);
            case DRAW_FROM_GOLD_CARDS_DECK ->
                drawnCard = drawFromGoldCardsDeck(client);
            case DRAW_REVEALED_RESOURCE_CARD_1 ->
                drawnCard = drawFromRevealedResourceCards(client, DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
            case DRAW_REVEALED_RESOURCE_CARD_2 ->
                drawnCard = drawFromRevealedResourceCards(client, DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2);
            case DRAW_REVEALED_GOLD_CARD_1 ->
                drawnCard = drawFromRevealedGoldCards(client, DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);
            case DRAW_REVEALED_GOLD_CARD_2 ->
                drawnCard = drawFromRevealedGoldCards(client, DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2);
        }

        List<PlayableCard> hand = player.getHand();
        hand.add(drawnCard);
        player.setHand(hand);

        nextPlayer(client);
        player.setTurnStatus(TurnStatus.PLAY);

        if (model.getResourceCardsDeck().isEmpty() &&
        model.getGoldCardsDeck().isEmpty() &&
        model.getRevealedResourceCards().isEmpty() &&
        model.getRevealedGoldCards().isEmpty()) {
            turnsLeftInSecondToLastRound = model.getNumOfPlayers() - model.getPlayerOrder().indexOf(player) - 1;
            try {
                model.setGameStatus(GameStatus.LAST_ROUND_DECKS_EMPTY, client.getNickname());
            } catch (RemoteException e) {
                System.err.println("Error while getting nickname");
            }
        }

        if (model.getGameStatus() == GameStatus.LAST_ROUND_20_POINTS)
            turnsLeftInSecondToLastRound--;*/
    }

    /**
     * Draws from the resource cards deck
     */
    private PlayableCard drawFromResourceCardsDeck(Client client) throws EmptyResourceCardsDeckException, NoSuchNicknameException {

        if (model.getResourceCardsDeck().isEmpty())
            throw new EmptyResourceCardsDeckException();

        PlayableCard drawnCard = null;
        drawnCard = model.getResourceCardsDeck().drawACard();

        drawnCard.flip();

        return drawnCard;

    }

    /**
     * Draws from the gold cards deck
     */
    private PlayableCard drawFromGoldCardsDeck(Client client) throws EmptyResourceCardsDeckException {

        if (model.getGoldCardsDeck().isEmpty())
            throw new EmptyResourceCardsDeckException();

        PlayableCard drawnCard = null;
        drawnCard = model.getGoldCardsDeck().drawACard();
        drawnCard.flip();

        return drawnCard;
    }

    /**
     * Draws the first revealed resource card on the table
     */
    private PlayableCard drawFromRevealedResourceCards(Client client, DrawCardEvent drawCardEvent) throws NoMoreRevealedCardHereException {

        Map<DrawCardEvent, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1, 0);
        eventToHandIndex.put(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2, 1);

        List<PlayableCard> revealedResourceCards = model.getRevealedResourceCards();
        PlayableCard drawnCard = revealedResourceCards.get(eventToHandIndex.get(drawCardEvent));
        DrawCardEvent otherDrawCardEvent = DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1;
        if (drawCardEvent == DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1)
            otherDrawCardEvent = DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2;
        if (drawnCard == null && revealedResourceCards.get(eventToHandIndex.get(otherDrawCardEvent)) != null)
            throw new NoMoreRevealedCardHereException();

        PlayableCard cardToReveal = null;
        if (!model.getResourceCardsDeck().isEmpty()) {
            cardToReveal = model.getResourceCardsDeck().drawACard();
            cardToReveal.flip();
        }
        revealedResourceCards.set(eventToHandIndex.get(drawCardEvent), cardToReveal);
        model.setRevealedResourceCards(revealedResourceCards, "client.getNickname()");

        return drawnCard;
    }

    /**
     * Draws the first revealed gold card on the table
     */
    private PlayableCard drawFromRevealedGoldCards(Client client, DrawCardEvent drawCardEvent) {

        Map<DrawCardEvent, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1, 0);
        eventToHandIndex.put(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2, 1);

        List<PlayableCard> revealedGoldCards = model.getRevealedGoldCards();
        PlayableCard drawnCard = revealedGoldCards.get(eventToHandIndex.get(drawCardEvent));
        DrawCardEvent otherDrawCardEvent = DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1;
        if (drawCardEvent == DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1)
            otherDrawCardEvent = DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2;
        if (drawnCard == null && revealedGoldCards.get(eventToHandIndex.get(otherDrawCardEvent)) != null)
            throw new NoMoreRevealedCardHereException();

        PlayableCard cardToReveal = null;
        if (!model.getGoldCardsDeck().isEmpty()) {
            try {
                cardToReveal = model.getGoldCardsDeck().drawACard();
                cardToReveal.flip();
            } catch (EmptyResourceCardsDeckException e){
                System.err.println("The gold cards deck is now empty!");
            }
        }
        revealedGoldCards.set(eventToHandIndex.get(drawCardEvent), cardToReveal);
        model.setRevealedGoldCards(revealedGoldCards, "client.getNickname()");

        return drawnCard;
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



    public void updateText(Client client, Message arg, String content, List<String> receivers) {

        sendAMessage(client, arg, content, receivers);

    }
    private void sendAMessage(Client client, Message arg, String content, List<String> receivers){

        List<ingsw.codex_naturalis.model.Message> messages = model.getChat();
        ingsw.codex_naturalis.model.Message messageToSend = null;
        messageToSend = new ingsw.codex_naturalis.model.Message(content, "client.getNickname()", receivers);
        messages.add(messageToSend);
        model.setMessages(messages, "client.getNickname()");

    }

    public Game getModel() {
        return model;
    }

    public List<Client> getViews(){
        return views;
    }
}