package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.PlayerArea;

/**
 * HandPlayableCard's class
 * This class represents one single card that a player could have in his hand at any game's turn.
 * A card has a front and a back and the player can see both in order to choose which side to play.
 */
public abstract class HandPlayableCard extends PlayableCard{

    /**
     * The card has been drawn
     * @param playerArea The player that has drawn the card
     */
    public abstract void drawn(PlayerArea playerArea);

    /**
     * Front getter
     * @return
     */
    public abstract HandPlayableSide getHandCardPlayerAreaCardFront();

    /**
     * Back getter
     * @return
     */
    public abstract HandPlayableSide getHandCardPlayerAreaCardBack();
}
