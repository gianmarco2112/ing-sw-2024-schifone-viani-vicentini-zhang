package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

/**
 * This class represents one single card in each player's hand.
 * A card have a front and a back and player can see both in order to choose which side to play
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
    public Boolean isShowingFront(){ return showingFront; }
    public void showFront(){
        showingFront = true;
    }
    public void showBack(){
        showingFront = false;
    }
    public PlayerAreaCard getFront(){
        return front;
    }
    public PlayerAreaCard getBack(){
        return back;
    }
}
