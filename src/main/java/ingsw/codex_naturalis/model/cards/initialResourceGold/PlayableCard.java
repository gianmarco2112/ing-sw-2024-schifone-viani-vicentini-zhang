package ingsw.codex_naturalis.model.cards.initialResourceGold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class PlayableCard {

    /**
     * Card type, can be INITIAL, RESOURCE or GOLD
     */
    private final PlayableCardType playableCardType;
    /**
     * Kingdom
     */
    private final Symbol kingdom;
    /**
     * Front
     */
    private final PlayableSide front;
    /**
     * Back
     */
    private final PlayableSide back;
    /**
     * Current side showing
     */
    private PlayableSide currentPlayableSide;
    /**
     * True if showing the front side
     */
    private boolean showingFront;

    //---------------------------------------------------------------------------------
    public PlayableCard(PlayableCardType playableCardType, Symbol kingdom, PlayableSide front, PlayableSide back){
        this.playableCardType = playableCardType;
        this.kingdom = kingdom;
        this.front = front;
        this.back = back;
        currentPlayableSide = front;
        showingFront = true;
    }

    //---------------------------------------------------------------------------------
    public PlayableCardType getPlayableCardType() {
        return playableCardType;
    }

    public Symbol getKingdom() {
        return kingdom;
    }

    public PlayableSide getCurrentPlayableSide() {
        return currentPlayableSide;
    }




    public void flip(){
        if(showingFront){
            showFront();
        }
        else{
            showBack();
        }
    }

    private void showFront() {
        showingFront = true;
        currentPlayableSide = front;
    }

    private void showBack() {
        showingFront = false;
        currentPlayableSide = back;
    }

    public String getDescription(){
        return currentPlayableSide.getDescription();
    }

    public Corner getTopLeftCorner(){ return currentPlayableSide.getTopLeftCorner(); }
    public Corner getTopRightCorner() { return currentPlayableSide.getTopRightCorner(); }
    public Corner getBottomLeftCorner() { return currentPlayableSide.getBottomLeftCorner(); }
    public Corner getBottomRightCorner() { return currentPlayableSide.getBottomRightCorner(); }

    public void play(PlayerArea playerArea){
        currentPlayableSide.play(playerArea);
    }

    public boolean isPlayable(PlayerArea playerArea, int x, int y){
        return currentPlayableSide.isPlayable(playerArea, x, y);
    }

    public void play(PlayerArea playerArea, int x, int y){
        currentPlayableSide.play(playerArea, x, y);
    }
}
