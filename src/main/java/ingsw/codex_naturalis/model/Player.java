package ingsw.codex_naturalis.model;

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

    /**
     * This HashMap represents the player area with all the cards he has placed.
     * The key represents the coordinates, the value the side of the card played.
     */
    private HashMap<int[], PlayerAreaCard> area;

    private HashMap<Symbol, Integer> numOfSymbols;

    private int points;


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
            ;
        }
    }

    /**
     * This method is called when the player wants to play a card from his hand.
     * Operates on the front or back of the card, based on what side the card is currently showing.
     * Checks that the card is actually playable in that spot.
     * Covers the corners and eventually decrease the number of symbols of the player.
     * Increases the number of symbols and the points (can be 0) of the player
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
        if (sideCard.isPlayable(area, x, y, numOfSymbols)) {
            area.put(new int[]{x, y}, sideCard);

            List<Symbol> symbolsToRemove = sideCard.coverCorners(area, x, y);
            for (Symbol sb : symbolsToRemove) {
                Integer numsb = numOfSymbols.get(sb);
                numsb--;
                numOfSymbols.replace(sb, numsb);
            }

            List<Symbol> symbolsToAdd = sideCard.getSymbols();
            for (Symbol sb : symbolsToAdd) {
                Integer numsb = numOfSymbols.get(sb);
                numsb++;
                numOfSymbols.replace(sb, numsb);
            }

            int p = sideCard.getPoints(area, x, y, numOfSymbols);
            points += p;

            hand.remove(card);
        }
    }

    public void draw(HandCard card) {
        centerOfTable.remove(card);
        hand.add(card);
    }
}