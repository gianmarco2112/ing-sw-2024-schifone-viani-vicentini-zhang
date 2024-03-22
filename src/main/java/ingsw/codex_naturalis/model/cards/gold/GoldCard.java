package ingsw.codex_naturalis.model.cards.gold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.HandCard;
import ingsw.codex_naturalis.model.cards.HandCardPlayerAreaCard;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * GoldCard's class
 * This kind of card is, together with the ResourceCard, the main kind of PlayableCard in the game
 * At the first turn of the game each player draws 2 ResourceCards and 1 GoldCard
 * During the game the Player would have in his hand 3 cards (the number of ResourceCards and
 * GoldCard is not important, as long as the total is 3) that he would play into his PlayerArea
 * Each card (both ResourceCard or GoldCard) could give points to the Player and
 * could contain Resources or Objects into his corners
 * The GoldCards could be of 2 types: GoldCardCorners (which give points depending on the number
 * of corners of the cards already into the PlayerArea covered by the card when placed into the PlayerArea)
 * or GoldCardObjects (which give points depending on the number of Resources already present into the
 * PlayerArea when the GOldCard is placed)
 *
 */
public class GoldCard extends HandCard {

    private GoldCardFront front;
    private GoldCardBack back;

    /**
     * Constructor
     * @param front Front side of the card
     * @param back Back side of the card
     */
    @JsonCreator
    public GoldCard(
            @JsonProperty("front") GoldCardFront front,
            @JsonProperty("back") GoldCardBack back){
        this.front = front;
        this.back = back;
    }


    public GoldCardFront getFront() {
        return front;
    }

    public GoldCardBack getBack() {
        return back;
    }

    @Override
    public HandCardPlayerAreaCard getHandCardPlayerAreaCardFront() {
        return front;
    }

    @Override
    public HandCardPlayerAreaCard getHandCardPlayerAreaCardBack() {
        return back;
    }

    @Override
    public void drawn(PlayerArea playerArea) {
        front.drawn(playerArea);
        back.drawn(playerArea);
    }
}
