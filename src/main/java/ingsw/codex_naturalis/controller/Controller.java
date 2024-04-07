package ingsw.codex_naturalis.controller;

import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.view.playing.events.MessageEvent;
import ingsw.codex_naturalis.view.playing.events.PlayCardEvent;
import ingsw.codex_naturalis.view.playing.TextualUI;
import ingsw.codex_naturalis.view.playing.events.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.playing.events.commands.FlipCardCommand;
import ingsw.codex_naturalis.view.playing.events.commands.PlayCardCommand;

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
    @Override
    public void update(FlipCardCommand arg) {
        flip(arg);
    }

    /**
     * Method called to flip a resource or gold card from the hand
     * @param flipCardCommand Index of the card
     */
    public void flip(FlipCardCommand flipCardCommand) {

        Map<FlipCardCommand, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(FlipCardCommand.FLIP_CARD_1, 0);
        eventToHandIndex.put(FlipCardCommand.FLIP_CARD_2, 1);
        eventToHandIndex.put(FlipCardCommand.FLIP_CARD_3, 2);

        PlayableCard cardToFlip = model.getCurrentPlayer().getHand().get(eventToHandIndex.get(flipCardCommand));
        cardToFlip.flip();
    }


    @Override
    public void update(PlayCardEvent arg) {
        playCard(arg.playCardCommand(), arg.x(), arg.y());
    }

    /**
     * Method called to play a resource or gold card from the hand
     * @param playCardCommand Index of the hand card
     * @param x Coordinate x on the player area
     * @param y Coordinate y on the player area
     * @throws NotPlayableException When the card can't be placed in that spot
     */
    public void playCard(PlayCardCommand playCardCommand, int x, int y) throws NotPlayableException {

        Map<PlayCardCommand, Integer> eventToHandIndex = new HashMap<>();
        eventToHandIndex.put(PlayCardCommand.PLAY_CARD_1, 0);
        eventToHandIndex.put(PlayCardCommand.PLAY_CARD_2, 1);
        eventToHandIndex.put(PlayCardCommand.PLAY_CARD_3, 2);

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();
        PlayableCard cardToPlay = hand.get(eventToHandIndex.get(playCardCommand));
        PlayerArea playerArea = model.getCurrentPlayer().getPlayerArea();

        if(!cardToPlay.isPlayable(playerArea, x, y))
            throw new NotPlayableException();

        playerArea.setCardOnCoordinates(cardToPlay, x, y);
        cardToPlay.play(playerArea, x, y);
        hand.remove(cardToPlay);
    }


    @Override
    public void update(DrawCardCommand arg) {
        switch (arg) {
            case DRAW_FROM_RESOURCE_CARDS_DECK ->
                drawFromResourceCardsDeck();
            case DRAW_FROM_GOLD_CARDS_DECK ->
                drawFromGoldCardsDeck();
            case DRAW_REVEALED_RESOURCE_CARD_1 ->
                drawFirstFromRevealedResourceCards();
            case DRAW_REVEALED_RESOURCE_CARD_2 ->
                drawLastFromRevealedResourceCards();
            case DRAW_REVEALED_GOLD_CARD_1 ->
                drawFirstFromRevealedGoldCards();
            case DRAW_REVEALED_GOLD_CARD_2 ->
                drawLastFromRevealedGoldCards();
        }
        nextPlayer();
    }

    /**
     * Draws from the resource cards deck
     */
    public void drawFromResourceCardsDeck() {

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();

        PlayableCard cardToDraw = model.getCenterOfTable().removeFromResourceCardsDeck();

        cardToDraw.flip();
        hand.add(cardToDraw);
    }

    /**
     * Draws from the gold cards deck
     */
    public void drawFromGoldCardsDeck() {

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();

        PlayableCard cardToDraw = model.getCenterOfTable().removeFromGoldCardsDeck();

        cardToDraw.flip();
        hand.add(cardToDraw);
    }

    /**
     * Draws the first revealed resource card on the table
     */
    public void drawFirstFromRevealedResourceCards() {

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();

        PlayableCard cardToDraw = model.getCenterOfTable().removeFirstFromRevealedResourceCards();

        hand.add(cardToDraw);
    }

    /**
     * Draws the second revealed resource card on the table
     */
    public void drawLastFromRevealedResourceCards() {

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();

        PlayableCard cardToDraw = model.getCenterOfTable().removeLastFromRevealedResourceCards();

        hand.add(cardToDraw);
    }

    /**
     * Draws the first revealed gold card on the table
     */
    public void drawFirstFromRevealedGoldCards() {

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();

        PlayableCard cardToDraw = model.getCenterOfTable().removeFirstFromRevealedGoldCards();

        hand.add(cardToDraw);
    }

    /**
     * Draws the last revealed resource card on the table
     */
    public void drawLastFromRevealedGoldCards() {

        List<PlayableCard> hand = model.getCurrentPlayer().getHand();

        PlayableCard cardToDraw = model.getCenterOfTable().removeLastFromRevealedGoldCards();

        hand.add(cardToDraw);
    }

    /**
     * Sets the new current player
     */
    private void nextPlayer() {
        model.setCurrentPlayer(getNextPlayer());
    }

    /**
     * Gets the new current player
     * @return New current player
     */
    private Player getNextPlayer() {
        int index = model.getPlayerOrder().indexOf(model.getCurrentPlayer());
        if(index < model.getPlayerOrder().size() -1 ){
            return model.getPlayerOrder().get(index+1);
        }else{
            return model.getPlayerOrder().getFirst();
        }
    }


    @Override
    public void update(MessageEvent arg) {
        // to do
        System.out.println("Choise: " + arg.content());
    }
}