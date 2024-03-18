package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * HandCard's class
 * This class represents one single card that a player could have in his hand at any game's turn.
 * A card have a front and a back and player can see both in order to choose which side to play.
 * Once the player has chosen a side and plays it, it can't change it anymore.
 */
public abstract class HandCard {
    private PlayerAreaCard front;
    private PlayerAreaCard back;
    private Boolean showingFront;

    /**
     * Constructor of a HandCard
     * @param front Gold or Resource or InitialCardFront
     * @param back Gold or Resource or InitialCardBack
     */
    public HandCard(PlayerAreaCard front, PlayerAreaCard back){
        this.front = front;
        this.back = back;
    }
    /**
     * Method aimed to know if the card has been played on the front or on the back side
     */
    public Boolean isShowingFront(){ return showingFront; }
    /**
     * This method would set the side of the card that the player is playing as Front
     */
    public void showFront(){
        showingFront = true;
    }
    /**
     * This method would set the side of the card that the player is playing as Back
     */
    public void showBack(){
        showingFront = false;
    }
    /**
     * This method would show the Front side of the card
     */
    public PlayerAreaCard getFront(){
        return front;
    }
    /**
     * This method would show the Back side of the card
     */
    public PlayerAreaCard getBack(){
        return back;
    }
}
