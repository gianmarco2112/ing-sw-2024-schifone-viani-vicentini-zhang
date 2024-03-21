package ingsw.codex_naturalis.model.cards.initial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.HandCard;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * InitialCard's class
 * This class represents the Initial Card: each player randomly takes one card at
 * the beginning of the game and chooses which side of the card to play, placing
 * the chosen side of the card at the middle of his PlayerArea. At this point the
 * player could start to link at the corners of the InitialCard the ResourceCards
 * and GoldCards. After the first turn, the player can't take anymore the InitialCards.
 */
public class InitialCard extends HandCard {

    private InitialCardFront front;
    private InitialCardBack back;


    /**
     * Constructor
     * @param front represents the front side of the card
     * @param back represents the back side of the card
     */
    @JsonCreator
    public InitialCard(
            @JsonProperty("front") InitialCardFront front,
            @JsonProperty("back") InitialCardBack back){
        this.front = front;
        this.back = back;
    }


    public InitialCardFront getFront() {
        return front;
    }

    public InitialCardBack getBack() {
        return back;
    }

    @Override
    public PlayerAreaCard getPlayerAreaCardFront() {
        return front;
    }

    @Override
    public PlayerAreaCard getPlayerAreaCardBack() {
        return back;
    }

    @Override
    public void drawn(PlayerArea playerArea) {
        front.drawn(playerArea);
        back.drawn(playerArea);
    }
}
