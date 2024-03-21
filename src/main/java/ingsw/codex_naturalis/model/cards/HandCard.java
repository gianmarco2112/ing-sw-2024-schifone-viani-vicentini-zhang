package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.CenterOfTable;
import ingsw.codex_naturalis.model.Player;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * HandCard's class
 * This class represents one single card that a player could have in his hand at any game's turn.
 * A card has a front and a back and the player can see both in order to choose which side to play.
 * ( Once the player has chosen a side and plays it, it can't change it anymore.)
 * -> DA VERIFICARE COME IMPLEMENTARE QUESTA PARTE TRA PARENTESI
 */
public abstract class HandCard {

    private Boolean showingFront;


    /**
     * Constructor of a HandCard
     */
    public HandCard(){
        this.showingFront = true;
    }


    /**
     * Method aimed to know if the card has been played on the front or on the back side
     */
    public Boolean isShowingFront(){ return showingFront; }

    /**
     * This method sets the side of the card that the player is playing as Front
     */
    public void showFront(){
        showingFront = true;
    }

    /**
     * This method sets the side of the card that the player is playing as Back
     */
    public void showBack(){
        showingFront = false;
    }

    /**
     * Front getter
     * @return
     */
    public abstract PlayerAreaCard getPlayerAreaCardFront();

    /**
     * Back getter
     * @return
     */
    public abstract PlayerAreaCard getPlayerAreaCardBack();

    /**
     * The card has been drawn
     * @param playerArea The player that has drawn the card
     */
    public abstract void drawn(PlayerArea playerArea);
}
