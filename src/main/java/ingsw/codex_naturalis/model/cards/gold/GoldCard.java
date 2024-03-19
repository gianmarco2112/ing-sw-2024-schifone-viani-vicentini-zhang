package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.cards.HandCard;

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

    /**
     * Constructor
     * @param front Front side of the card
     * @param back Back side of the card
     */
    public GoldCard(GoldCardFront front, GoldCardBack back){
        super(front, back);
    }
}
