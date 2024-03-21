package ingsw.codex_naturalis.model.cards.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.HandCard;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * ResourceCard's class
 * This kind of card is, together with the GoldCard, the main kind of PlayableCard in the game
 * At the first turn of the game each player draws 2 ResourceCards and 1 GoldCard
 * During the game the Player would have in his hand 3 cards (the number of ResourceCards and
 * GoldCard is not important, as long as the total is 3) that he would play into his PlayerArea
 * Each card (both ResourceCard or GoldCard) could give points to the Player and
 * could contain Resources or Objects into his corners
 *
 */
public class ResourceCard extends HandCard {

    private ResourceCardFront front;
    private ResourceCardBack back;

    @JsonCreator
    public ResourceCard(
            @JsonProperty("front") ResourceCardFront front,
            @JsonProperty("back") ResourceCardBack back){
        this.front = front;
        this.back = back;
    }

    public ResourceCardFront getFront() {
        return front;
    }

    public ResourceCardBack getBack() {
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
