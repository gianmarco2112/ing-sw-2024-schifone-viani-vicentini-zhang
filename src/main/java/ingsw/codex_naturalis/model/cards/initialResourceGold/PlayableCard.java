package ingsw.codex_naturalis.model.cards.initialResourceGold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.cards.Card;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class PlayableCard extends Card {

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

    //---------------------------------------------------------------------------------
    @JsonCreator
    public PlayableCard(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("playableCardType") PlayableCardType playableCardType,
            @JsonProperty("kingdom") Symbol kingdom,
            @JsonProperty("front") PlayableSide front,
            @JsonProperty("back") PlayableSide back){
        super(cardID);
        this.playableCardType = playableCardType;
        this.kingdom = kingdom;
        this.front = front;
        this.back = back;
        currentPlayableSide = back;
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

    public void setCurrentPlayableSide(PlayableSide currentPlayableSide) {
        this.currentPlayableSide = currentPlayableSide;
    }


    @Override
    public void flip(String nickname){
        if(!showingFront){
            showFront();
        }
        else{
            showBack();
        }
        notifyObservers(Event.HAND_CHANGED, nickname);
    }

    private void showFront() {
        showingFront = true;
        currentPlayableSide = front;
    }

    private void showBack() {
        showingFront = false;
        currentPlayableSide = back;
    }

    public PlayableSide getFront() {
        return front;
    }

    public PlayableSide getBack() {
        return back;
    }

    @Override
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
