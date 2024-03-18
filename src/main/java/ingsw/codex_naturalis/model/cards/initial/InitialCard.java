package ingsw.codex_naturalis.model.cards.initial;

import ingsw.codex_naturalis.model.cards.HandCard;
/**
 * InitialCard's class
 * This class represents the Initial Card: each player randomly takes one card at
 * the beginning of the game and chooses which side of the card to play, placing
 * the chosen side of the card at the middle of his PlayerArea. At this point the
 * player could start to link at the corners of the InitialCard the ResourceCards
 * and GoldCards. After the first turn, the player can't take anymore the InitialCards.
 */
public class InitialCard extends HandCard {

    public InitialCard(InitialCardFront front, InitialCardBack back){
        super(front, back);
    }

}
