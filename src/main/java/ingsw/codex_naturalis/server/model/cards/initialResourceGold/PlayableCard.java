package ingsw.codex_naturalis.server.model.cards.initialResourceGold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.cards.Card;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;

/**
 * PlayableCard's class
 */
public class PlayableCard extends Card {

    /**
     * Card type, can be INITIAL, RESOURCE or GOLD
     */
    private final PlayableCardType playableCardType;
    /**
     * Kingdom of the card
     */
    private final Symbol kingdom;
    /**
     * Front side of the card
     */
    private final PlayableSide front;
    /**
     * Back side of the card
     */
    private final PlayableSide back;
    /**
     * The side that the card is currently showing
     */
    private PlayableSide currentPlayableSide;
    /**
     * True if the card is showing the front side, False otherwise
     */
    private boolean showingFront;

    /**
     * Constructor of the card
     * @param cardID: the ID of the card
     * @param playableCardType : the cardType of the card
     * @param kingdom: the kingdom of the card
     * @param front: the front side of the card
     * @param back: the back side of the card
     */

    //---------------------------------------------------------------------------------
    @JsonCreator
    public PlayableCard(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("playableCardType") PlayableCardType playableCardType,
            @JsonProperty("kingdom") Symbol kingdom,
            @JsonProperty("front") PlayableSide front,
            @JsonProperty("back") PlayableSide back){
        super(cardID);
        this.playableCardType = playableCardType;
        this.kingdom = kingdom;
        this.front = front;
        this.back = back;
        currentPlayableSide = back;
        showingFront = false;
    }

    //---------------------------------------------------------------------------------

    /**
     * Getter of the type of the card
     * @return playableCardTYpe: the type (INITIAL, GOLD, RESOURCE) of the card
     */
    public PlayableCardType getPlayableCardType() {
        return playableCardType;
    }
    /**
     * Getter of the kingdom of the card
     * @return kingdom: the kingdom of the card
     */
    public Symbol getKingdom() {
        return kingdom;
    }
    /**
     * Getter of the side currently showed by the card
     * @return currentPlayableSide: the side that the card is currently showing
     */
    public PlayableSide getCurrentPlayableSide() {
        return currentPlayableSide;
    }
    /**
     * Setter of the side currently showed by the card
     */
    public void setCurrentPlayableSide(PlayableSide currentPlayableSide) {
        this.currentPlayableSide = currentPlayableSide;
    }

    /**
     * Method to flip the card:
     * if the card is currently showing front, it would be flipped to the back side
     * if the card is currently showing back, it would be flipped to the front side
     */
    @Override
    public void flip(){
        if(!showingFront){
            showFront();
        }
        else{
            showBack();
        }
    }
    /**
     * Set to "front" the side that the card is showing
     */
    private void showFront() {
        showingFront = true;
        currentPlayableSide = front;
    }
    /**
     * Set to "back" the side that the card is showing
     */
    private void showBack() {
        showingFront = false;
        currentPlayableSide = back;
    }
    /**
     * Getter of the side currently showed by the card
     * @return showingFront: true is the card is showing the front side,
     *                       false otherwise
     */
    public boolean isShowingFront() {
        return showingFront;
    }
    /**
     * Front side's getter
     * @return front: the front side of the card
     */
    public PlayableSide getFront() {
        return front;
    }
    /**
     * Back side's getter
     * @return back: the back side of the card
     */
    public PlayableSide getBack() {
        return back;
    }
    /**
     * To draw the cards in TUI (with the view used to draw the cards in player's hand)
     * @param kingdom : the central symbol of the card (used to know the color to use to draw the card)
     */
    public String handCardToString(Symbol kingdom) {
        return currentPlayableSide.handCardToString(kingdom);
    }
    /**
     * To draw the cards in TUI (with the view used to draw the cards into the player's area)
     * @param kingdom : the central symbol of the card (used to know the color to use to draw the card)
     */
    public String playerAreaCardToString(Symbol kingdom){
        return currentPlayableSide.playerAreaCardToString(kingdom);
    }
    /**
     * Top left corner's getter
     * @return TopLeftCorner
     */
    public Corner getTopLeftCorner(){ return currentPlayableSide.getTopLeftCorner(); }
    /**
     * Top right corner's getter
     * @return TopRightCorner
     */
    public Corner getTopRightCorner() { return currentPlayableSide.getTopRightCorner(); }
    /**
     * Bottom left corner's getter
     * @return BottomLeftCorner
     */
    public Corner getBottomLeftCorner() { return currentPlayableSide.getBottomLeftCorner(); }
    /**
     * Bottom right corner's getter
     * @return BottomRightCorner
     */
    public Corner getBottomRightCorner() { return currentPlayableSide.getBottomRightCorner(); }

    /**
     * Method to increment the counters of each Symbol related to the played card
     * @param playerArea: of the player whose counters are incremented
     */
    public void play(PlayerArea playerArea){
        currentPlayableSide.play(playerArea);
    }
    /**
     * This method verifies that the card is playable at the specified coordinates
     * @param playerArea: of the Player that wish to play the card
     * @return True: if the indicated position is free of other cards and the
     *               requirements of the card I want to play are fulfilled
     *         False: otherwise
     */
    public boolean isPlayable(PlayerArea playerArea, int x, int y){
        return currentPlayableSide.isPlayable(playerArea, x, y);
    }
    /**
     * This method plays the card, placing it on the specified coordinates
     * @param playerArea: the PlayerArea of the Player that is playing the card
     * @param x: the x coordinate where to play the card
     * @param y: the y coordinate where to play the card
     */
    public void play(PlayerArea playerArea, int x, int y){
        currentPlayableSide.play(playerArea, x, y);
    }
}
