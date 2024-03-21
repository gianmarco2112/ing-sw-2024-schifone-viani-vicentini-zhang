package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandCard;
import ingsw.codex_naturalis.model.cards.PlayableCard;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

/**
 * Player class
 */
public class Player {

    private String nickname;

    private Color color;

    private InitialCard initialCard;

    private CenterOfTable centerOfTable;

    /**
     * This list contains the card/cards the player currently has in his hands.
     * It will contain two resource cards and a gold card at the start.
     * During the game, it will contain from two to three resource/golden cards.
     */
    private List<HandCard> hand;

    private PlayerArea playerArea;

    private List<Message> messages;

    private List<Player> players;


    public Player(String nickname, Color color) {
        this.nickname = nickname;
        this.color = color;
    }


    /**
     * This method is called when the player wants to see the other side of the card.
     * @param card Card to flip
     */
    public void flip(PlayableCard card) {
        if (card.isShowingFront()) {
            card.showBack();
        } else {
            card.showFront();
        }
    }

    /**
     * Method to play the initial card
     */
    public void playInitialCard(){
        PlayerAreaCard sideCard;
        if (initialCard.isShowingFront()) {
            sideCard = initialCard.getPlayerAreaCardFront();
        } else {
            sideCard = initialCard.getPlayerAreaCardBack();
        }
        playerArea.setCardOnCoordinates(sideCard, 0, 0);
        sideCard.played(playerArea, 0, 0);
        sideCard.getSymbols();
        initialCard = null;
    }

    /**
     * This method is called when the player wants to play a card from his hand.
     * Operates on the front or back of the card, based on what side the card is currently showing.
     * Checks that the card is actually playable in that spot.
     * Covers the corners and eventually decreases the number of symbols of the player.
     * Increases the number of symbols and the points, if necessary, of the player
     * @param card
     * @param x
     * @param y
     */
    public void playCard(HandCard card, int x, int y) {
        PlayerAreaCard sideCard;
        if (card.isShowingFront()) {
            sideCard = card.getPlayerAreaCardFront();
        } else {
            sideCard = card.getPlayerAreaCardBack();
        }
        if (sideCard.isPlayable(x,y)) {
            playerArea.setCardOnCoordinates(sideCard, x, y);
            sideCard.played(playerArea, x, y);
            sideCard.coverCorners(x,y);
            sideCard.getSymbols();
            sideCard.calcPoints();
            hand.remove(card);
        }
    }

    public void drawFromResourceCardsDeck() {
        HandCard card = centerOfTable.removeFromResourceCardsDeck();
        addCardToHand(card);
        card.drawn(playerArea);
    }
    public void drawFromGoldCardsDeck() {
        HandCard card = centerOfTable.removeFromGoldCardsDeck();
        addCardToHand(card);
        card.drawn(playerArea);
    }
    public void drawFirstFromRevealedResourceCards() {
        HandCard card = centerOfTable.removeFirstFromRevealedResourceCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }
    public void drawLastFromRevealedResourceCards() {
        HandCard card = centerOfTable.removeLastFromRevealedResourceCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }
    public void drawFirstFromRevealedGoldCards() {
        HandCard card = centerOfTable.removeFirstFromRevealedGoldCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }
    public void drawLastFromRevealedGoldCards() {
        HandCard card = centerOfTable.removeLastFromRevealedGoldCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }
    private void addCardToHand(HandCard card){
        hand.add(card);
    }

    public void writeMessage(String content, List<Player> receivers){
        messages.add(new Message(content, receivers));
    }
}