package ingsw.codex_naturalis.controller.gameplayPhase;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.enumerations.TurnStatus;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameplayController {

    private final Game model;
    private final List<Client> views = new ArrayList<>();

    //including the player who gets to 20 points
    private int turnsLeftInSecondToLastRound;
    private int turnsLeftInLastRound;

    //--------------------------------------------------------------------------------------
    public GameplayController(Game model, Client view) {
        this.model = model;
        this.views.add(view);
        turnsLeftInLastRound = model.getNumOfPlayers();
        turnsLeftInSecondToLastRound = model.getNumOfPlayers();
    }

    //-----------------------------------------------------------------------------------------
    private Player getPlayerByNickname(String nickname) throws NoSuchNicknameException{

        List<Player> playerOrder = model.getPlayerOrder();
        for (Player player : playerOrder) {
            if (player.getNickname().equals(nickname))
                return player;
        }
        throw new NoSuchNicknameException();

    }



    public void updateFlipCard(Client client, FlipCard flipCard) {

        flip(client, flipCard);

    }

    /**
     * Method called to flip a resource or gold card from the hand
     * @param flipCard Index of the card
     */
    private void flip(Client client, FlipCard flipCard) {
/*
        Map<FlipCard, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(FlipCard.FLIP_CARD_1, 0);
        eventToHandIndex.put(FlipCard.FLIP_CARD_2, 1);
        eventToHandIndex.put(FlipCard.FLIP_CARD_3, 2);

        Player player = null;
        try {
            player = getPlayerByNickname(client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }

        PlayableCard cardToFlip = player.getHand().get(eventToHandIndex.get(flipCard));
        try {
            cardToFlip.flip(client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }
*/
    }



    public void updatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException {
/*
        try {
            if (!client.getNickname().equals(model.getCurrentPlayer().getNickname()))
                throw new NotYourTurnException();
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }

        if (!model.getCurrentPlayer().getTurnStatus().equals(TurnStatus.PLAY))
            throw new NotYourPlayTurnStatusException();

        playCard(client, playCard, x, y);
*/
    }

    /**
     * Method called to play a resource or gold card from the hand
     * @param playCard Index of the hand card
     * @param x Coordinate x on the player area
     * @param y Coordinate y on the player area
     * @throws NotPlayableException When the card can't be placed in that spot
     */
    private void playCard(Client client, PlayCard playCard, int x, int y) throws NotPlayableException {
/*
        Map<PlayCard, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(PlayCard.PLAY_CARD_1, 0);
        eventToHandIndex.put(PlayCard.PLAY_CARD_2, 1);
        eventToHandIndex.put(PlayCard.PLAY_CARD_3, 2);

        Player player = null;
        try {
            player = getPlayerByNickname(client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }

        PlayableCard cardToPlay = player.getHand().get(eventToHandIndex.get(playCard));
        PlayerArea playerArea = player.getPlayerArea();
        if(!cardToPlay.isPlayable(playerArea, x, y))
            throw new NotPlayableException();
        try {
            playerArea.setCardOnCoordinates(cardToPlay, x, y, client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }

        List<PlayableCard> hand = player.getHand();
        hand.remove(cardToPlay);
        player.setHand(hand);

        if (player.getPlayerArea().getPoints() >= 20 && model.getGameStatus() == GameStatus.GAMEPLAY) {
            turnsLeftInSecondToLastRound = model.getNumOfPlayers() - model.getPlayerOrder().indexOf(player);
            try {
                model.setGameStatus(GameStatus.LAST_ROUND_20_POINTS, client.getNickname());
            } catch (RemoteException e) {
                System.err.println("Error while getting nickname");
            }
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() != GameStatus.LAST_ROUND_DECKS_EMPTY)
            player.setTurnStatus(TurnStatus.DRAW);

        if (turnsLeftInSecondToLastRound == 0) {
            turnsLeftInLastRound--;
            nextPlayer(client);
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() == GameStatus.LAST_ROUND_DECKS_EMPTY) {
            turnsLeftInSecondToLastRound--;
            nextPlayer(client);
        }

        if (turnsLeftInLastRound == 0) {
            try {
                model.setGameStatus(GameStatus.ENDGAME, client.getNickname());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
*/
    }



    public void updateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {
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
        switch (drawCard) {
            case DRAW_FROM_RESOURCE_CARDS_DECK ->
                drawnCard = drawFromResourceCardsDeck(client);
            case DRAW_FROM_GOLD_CARDS_DECK ->
                drawnCard = drawFromGoldCardsDeck(client);
            case DRAW_REVEALED_RESOURCE_CARD_1 ->
                drawnCard = drawFromRevealedResourceCards(client, DrawCard.DRAW_REVEALED_RESOURCE_CARD_1);
            case DRAW_REVEALED_RESOURCE_CARD_2 ->
                drawnCard = drawFromRevealedResourceCards(client, DrawCard.DRAW_REVEALED_RESOURCE_CARD_2);
            case DRAW_REVEALED_GOLD_CARD_1 ->
                drawnCard = drawFromRevealedGoldCards(client, DrawCard.DRAW_REVEALED_GOLD_CARD_1);
            case DRAW_REVEALED_GOLD_CARD_2 ->
                drawnCard = drawFromRevealedGoldCards(client, DrawCard.DRAW_REVEALED_GOLD_CARD_2);
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
    private PlayableCard drawFromResourceCardsDeck(Client client) throws EmptyDeckException, NoSuchNicknameException {

        if (model.getResourceCardsDeck().isEmpty())
            throw new EmptyDeckException();

        PlayableCard drawnCard = null;
        drawnCard = model.getResourceCardsDeck().drawACard("client.getNickname()");

        drawnCard.flip("client.getNickname()");

        return drawnCard;

    }

    /**
     * Draws from the gold cards deck
     */
    private PlayableCard drawFromGoldCardsDeck(Client client) throws EmptyDeckException{

        if (model.getGoldCardsDeck().isEmpty())
            throw new EmptyDeckException();

        PlayableCard drawnCard = null;
        drawnCard = model.getGoldCardsDeck().drawACard("client.getNickname()");
        drawnCard.flip("client.getNickname()");

        return drawnCard;
    }

    /**
     * Draws the first revealed resource card on the table
     */
    private PlayableCard drawFromRevealedResourceCards(Client client, DrawCard drawCard) throws NoMoreRevealedCardHereException {

        Map<DrawCard, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(DrawCard.DRAW_REVEALED_RESOURCE_CARD_1, 0);
        eventToHandIndex.put(DrawCard.DRAW_REVEALED_RESOURCE_CARD_2, 1);

        List<PlayableCard> revealedResourceCards = model.getRevealedResourceCards();
        PlayableCard drawnCard = revealedResourceCards.get(eventToHandIndex.get(drawCard));
        DrawCard otherDrawCard = DrawCard.DRAW_REVEALED_RESOURCE_CARD_1;
        if (drawCard == DrawCard.DRAW_REVEALED_RESOURCE_CARD_1)
            otherDrawCard = DrawCard.DRAW_REVEALED_RESOURCE_CARD_2;
        if (drawnCard == null && revealedResourceCards.get(eventToHandIndex.get(otherDrawCard)) != null)
            throw new NoMoreRevealedCardHereException();

        PlayableCard cardToReveal = null;
        if (!model.getResourceCardsDeck().isEmpty()) {
            cardToReveal = model.getResourceCardsDeck().drawACard("client.getNickname()");
            cardToReveal.flip("client.getNickname()");
        }
        revealedResourceCards.set(eventToHandIndex.get(drawCard), cardToReveal);
        model.setRevealedResourceCards(revealedResourceCards, "client.getNickname()");

        return drawnCard;
    }

    /**
     * Draws the first revealed gold card on the table
     */
    private PlayableCard drawFromRevealedGoldCards(Client client, DrawCard drawCard) {

        Map<DrawCard, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(DrawCard.DRAW_REVEALED_GOLD_CARD_1, 0);
        eventToHandIndex.put(DrawCard.DRAW_REVEALED_GOLD_CARD_2, 1);

        List<PlayableCard> revealedGoldCards = model.getRevealedGoldCards();
        PlayableCard drawnCard = revealedGoldCards.get(eventToHandIndex.get(drawCard));
        DrawCard otherDrawCard = DrawCard.DRAW_REVEALED_GOLD_CARD_1;
        if (drawCard == DrawCard.DRAW_REVEALED_GOLD_CARD_1)
            otherDrawCard = DrawCard.DRAW_REVEALED_GOLD_CARD_2;
        if (drawnCard == null && revealedGoldCards.get(eventToHandIndex.get(otherDrawCard)) != null)
            throw new NoMoreRevealedCardHereException();

        PlayableCard cardToReveal = null;
        if (!model.getGoldCardsDeck().isEmpty()) {
            try {
                cardToReveal = model.getGoldCardsDeck().drawACard("client.getNickname()");
                cardToReveal.flip("client.getNickname()");
            } catch (EmptyDeckException e){
                System.err.println("The gold cards deck is now empty!");
            }
        }
        revealedGoldCards.set(eventToHandIndex.get(drawCard), cardToReveal);
        model.setRevealedGoldCards(revealedGoldCards, "client.getNickname()");

        return drawnCard;
    }

    /**
     * Sets the new current player
     */
    private void nextPlayer(Client client) {
        Player nextPlayer;
        int index = model.getPlayerOrder().indexOf(model.getCurrentPlayer());
        if (index < model.getPlayerOrder().size() -1 ) {
            nextPlayer = model.getPlayerOrder().get(index+1);
        } else {
            nextPlayer = model.getPlayerOrder().getFirst();
        }
        model.setCurrentPlayer(nextPlayer, "client.getNickname()");
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