package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.PlayerArea;

public abstract class PlayableCard {

    private Boolean showingFront;


    public PlayableCard(){
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
}
