package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandCard;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

/**
 * Player class
 */
public class Player {

    private String nickname;

    private Color color;

    private CenterOfTable centerOfTable;

    /**
     * This list contains the card/cards the player currently has in his hands.
     * It will contain an initial card at the start, then two resource cards and a gold card.
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
     *
     * @param card
     */
    public void flip(HandCard card) {
        if (card.isShowingFront()) {
            card.showBack();
        } else {
            card.showFront();
        }
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
            sideCard = card.getFront();
        } else {
            sideCard = card.getBack();
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

    public void draw(HandCard card) {
        centerOfTable.remove(card);
        hand.add(card);
        card.drawn(playerArea);
    }

    public void writeMessage(String content, List<Player> receivers){
        messages.add(new Message(content, receivers));
    }
}