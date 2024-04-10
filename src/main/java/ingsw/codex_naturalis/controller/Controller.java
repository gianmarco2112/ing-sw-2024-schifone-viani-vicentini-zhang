package ingsw.codex_naturalis.controller;

import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Message;
import ingsw.codex_naturalis.model.enumerations.TurnStatus;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.view.playing.TextualUI;
import ingsw.codex_naturalis.view.playing.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.playing.commands.FlipCardCommand;
import ingsw.codex_naturalis.view.playing.commands.PlayCardCommand;
import ingsw.codex_naturalis.view.playing.commands.TextCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Controller implements ObserverController {

    private final Game model;
    private final TextualUI view;

    //--------------------------------------------------------------------------------------
    public Controller(Game model, TextualUI view) {
        this.model = model;
        this.view = view;
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


    @Override
    public void updateFlipCard(String nickname, FlipCardCommand flipCardCommand) {

        flip(nickname, flipCardCommand);

    }

    /**
     * Method called to flip a resource or gold card from the hand
     * @param flipCardCommand Index of the card
     */
    private void flip(String nickname, FlipCardCommand flipCardCommand) {

        Map<FlipCardCommand, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(FlipCardCommand.FLIP_CARD_1, 0);
        eventToHandIndex.put(FlipCardCommand.FLIP_CARD_2, 1);
        eventToHandIndex.put(FlipCardCommand.FLIP_CARD_3, 2);

        Player player = getPlayerByNickname(nickname);

        PlayableCard cardToFlip = player.getHand().get(eventToHandIndex.get(flipCardCommand));
        cardToFlip.flip(nickname);

    }



    @Override
    public void updatePlayCard(String nickname, PlayCardCommand playCardCommand, int x, int y) throws NotYourTurnException {

        if (!nickname.equals(model.getCurrentPlayer().getNickname()))
            throw new NotYourTurnException();

        playCard(nickname, playCardCommand, x, y);

    }

    /**
     * Method called to play a resource or gold card from the hand
     * @param playCardCommand Index of the hand card
     * @param x Coordinate x on the player area
     * @param y Coordinate y on the player area
     * @throws NotPlayableException When the card can't be placed in that spot
     */
    private void playCard(String nickname, PlayCardCommand playCardCommand, int x, int y) throws NotPlayableException {

        Map<PlayCardCommand, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(PlayCardCommand.PLAY_CARD_1, 0);
        eventToHandIndex.put(PlayCardCommand.PLAY_CARD_2, 1);
        eventToHandIndex.put(PlayCardCommand.PLAY_CARD_3, 2);

        Player player = getPlayerByNickname(nickname);

        PlayableCard cardToPlay = player.getHand().get(eventToHandIndex.get(playCardCommand));
        PlayerArea playerArea = model.getCurrentPlayer().getPlayerArea();
        if(!cardToPlay.isPlayable(playerArea, x, y))
            throw new NotPlayableException();
        playerArea.setCardOnCoordinates(cardToPlay, x, y, nickname);

        List<PlayableCard> hand = player.getHand();
        hand.remove(cardToPlay);
        player.setHand(hand);

        player.setTurnStatus(TurnStatus.DRAW);

    }


    @Override
    public void updateDrawCard(String nickname, DrawCardCommand drawCardCommand) throws NotYourTurnException, NotYourTurnStatusException{

        if (!nickname.equals(model.getCurrentPlayer().getNickname()))
            throw new NotYourTurnException();

        if (!model.getCurrentPlayer().getTurnStatus().equals(TurnStatus.DRAW))
            throw new NotYourTurnStatusException();

        Player player = getPlayerByNickname(nickname);

        PlayableCard drawnCard = null;
        switch (drawCardCommand) {
            case DRAW_FROM_RESOURCE_CARDS_DECK ->
                drawnCard = drawFromResourceCardsDeck(nickname);
            case DRAW_FROM_GOLD_CARDS_DECK ->
                drawnCard = drawFromGoldCardsDeck(nickname);
            case DRAW_REVEALED_RESOURCE_CARD_1 ->
                drawnCard = drawFromRevealedResourceCards(nickname, DrawCardCommand.DRAW_REVEALED_RESOURCE_CARD_1);
            case DRAW_REVEALED_RESOURCE_CARD_2 ->
                drawnCard = drawFromRevealedResourceCards(nickname, DrawCardCommand.DRAW_REVEALED_RESOURCE_CARD_2);
            case DRAW_REVEALED_GOLD_CARD_1 ->
                drawnCard = drawFromRevealedGoldCards(nickname, DrawCardCommand.DRAW_REVEALED_GOLD_CARD_1);
            case DRAW_REVEALED_GOLD_CARD_2 ->
                drawnCard = drawFromRevealedGoldCards(nickname, DrawCardCommand.DRAW_REVEALED_GOLD_CARD_2);
        }

        List<PlayableCard> hand = player.getHand();
        hand.add(drawnCard);
        player.setHand(hand);

        nextPlayer(nickname);
        player.setTurnStatus(TurnStatus.PLAY);

    }

    /**
     * Draws from the resource cards deck
     */
    private PlayableCard drawFromResourceCardsDeck(String nickname) throws EmptyDeckException, NoSuchNicknameException {

        if (model.getResourceCardsDeck().isEmpty())
            throw new EmptyDeckException();

        PlayableCard drawnCard = model.getResourceCardsDeck().drawACard(nickname);

        drawnCard.flip(nickname);

        return drawnCard;

    }

    /**
     * Draws from the gold cards deck
     */
    private PlayableCard drawFromGoldCardsDeck(String nickname) throws EmptyDeckException{

        if (model.getGoldCardsDeck().isEmpty())
            throw new EmptyDeckException();

        PlayableCard drawnCard = model.getGoldCardsDeck().drawACard(nickname);

        drawnCard.flip(nickname);

        return drawnCard;
    }

    /**
     * Draws the first revealed resource card on the table
     */
    private PlayableCard drawFromRevealedResourceCards(String nickname, DrawCardCommand drawCardCommand) throws NoMoreRevealedCardHereException {

        Map<DrawCardCommand, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(DrawCardCommand.DRAW_REVEALED_RESOURCE_CARD_1, 0);
        eventToHandIndex.put(DrawCardCommand.DRAW_REVEALED_RESOURCE_CARD_2, 1);

        List<PlayableCard> revealedResourceCards = model.getRevealedResourceCards();
        PlayableCard drawnCard = revealedResourceCards.get(eventToHandIndex.get(drawCardCommand));
        if (drawnCard == null)
            throw new NoMoreRevealedCardHereException();

        PlayableCard cardToReveal = null;
        if (!model.getResourceCardsDeck().isEmpty()) {
            try {
                cardToReveal = model.getResourceCardsDeck().drawACard(nickname);
                cardToReveal.flip(nickname);
            } catch (EmptyDeckException e){
                System.err.println("The resource cards deck is now empty!");
            }
        }
        revealedResourceCards.set(eventToHandIndex.get(drawCardCommand), cardToReveal);
        model.setRevealedResourceCards(revealedResourceCards, nickname);

        return drawnCard;
    }

    /**
     * Draws the first revealed gold card on the table
     */
    private PlayableCard drawFromRevealedGoldCards(String nickname, DrawCardCommand drawCardCommand) {

        Map<DrawCardCommand, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(DrawCardCommand.DRAW_REVEALED_GOLD_CARD_1, 0);
        eventToHandIndex.put(DrawCardCommand.DRAW_REVEALED_GOLD_CARD_2, 1);

        List<PlayableCard> revealedGoldCards = model.getRevealedGoldCards();
        PlayableCard drawnCard = revealedGoldCards.get(eventToHandIndex.get(drawCardCommand));
        if (drawnCard == null)
            throw new NoMoreRevealedCardHereException();

        PlayableCard cardToReveal = null;
        if (!model.getGoldCardsDeck().isEmpty()) {
            try {
                cardToReveal = model.getGoldCardsDeck().drawACard(nickname);
                cardToReveal.flip(nickname);
            } catch (EmptyDeckException e){
                System.err.println("The gold cards deck is now empty!");
            }
        }
        revealedGoldCards.set(eventToHandIndex.get(drawCardCommand), cardToReveal);
        model.setRevealedGoldCards(revealedGoldCards, nickname);

        return drawnCard;
    }

    /**
     * Sets the new current player
     */
    private void nextPlayer(String nickname) {
        Player nextPlayer;
        int index = model.getPlayerOrder().indexOf(model.getCurrentPlayer());
        if(index < model.getPlayerOrder().size() -1 ){
            nextPlayer = model.getPlayerOrder().get(index+1);
        }else{
            nextPlayer = model.getPlayerOrder().getFirst();
        }
        model.setCurrentPlayer(nextPlayer, nickname);
    }


    @Override
    public void updateText(String nickname, TextCommand arg, String content, List<String> receivers) {

        sendAMessage(nickname, arg, content, receivers);

    }
    private void sendAMessage(String nickname, TextCommand arg, String content, List<String> receivers){

        List<Message> messages = model.getMessages();
        Message messageToSend = new Message(content, nickname, receivers);
        messages.add(messageToSend);
        model.setMessages(messages, nickname);

    }

}