package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.CenterOfTable;
import ingsw.codex_naturalis.model.Player;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * HandCard's class
 * This class represents one single card that a player could have in his hand at any game's turn.
 * A card has a front and a back and the player can see both in order to choose which side to play.
 */
public abstract class HandCard extends PlayableCard{

    /**
     * The card has been drawn
     * @param playerArea The player that has drawn the card
     */
    public abstract void drawn(PlayerArea playerArea);
}
