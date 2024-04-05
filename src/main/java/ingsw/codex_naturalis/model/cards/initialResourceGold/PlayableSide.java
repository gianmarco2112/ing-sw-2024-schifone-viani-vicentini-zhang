package ingsw.codex_naturalis.model.cards.initialResourceGold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class PlayableSide {

    /**
     * Top left corner
     */
    private final Corner topLeftCorner;
    /**
     * Top right corner
     */
    private final Corner topRightCorner;
    /**
     * Bottom left corner
     */
    private final Corner bottomLeftCorner;
    /**
     * Bottom right corner
     */
    private final Corner bottomRightCorner;

    //-------------------------------------------------------------------------------------------
    public PlayableSide(Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner){
        this.topLeftCorner = topLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }

    //----------------------------------------------------------------------------------------------
    public Corner getTopLeftCorner(){ return topLeftCorner; }
    public Corner getTopRightCorner() { return topRightCorner; }
    public Corner getBottomLeftCorner() { return bottomLeftCorner; }
    public Corner getBottomRightCorner() { return bottomRightCorner; }




    public String getDescription(){
        return null;
    }

    public void play(PlayerArea playerArea){
        gainSymbols(playerArea);
    }

    public boolean isPlayable(PlayerArea playerArea, int x, int y) {
        if (playerArea.containsCardOnCoordinates(x,y)){
            return false;
        }

        int atLeastACard = 4;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayableCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (topLeftCard.getBottomRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayableCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (topRightCard.getBottomLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayableCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (bottomLeftCard.getTopRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayableCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            if (bottomRightCard.getTopLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if(atLeastACard < 1){
            return false;
        }
        return true;
    }

    public void play(PlayerArea playerArea, int x, int y) {
        coverCorners(playerArea, x, y);
        gainSymbols(playerArea);
    }

    protected void coverCorners(PlayerArea playerArea, int x, int y){
        if (playerArea.containsCardOnCoordinates(x-1, y+1)){
            PlayableCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            Corner bottomRightCorner = topLeftCard.getBottomRightCorner();
            bottomRightCorner.cover();
            if (bottomRightCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(bottomRightCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1, y+1)){
            PlayableCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            Corner bottomLeftCorner = topRightCard.getBottomLeftCorner();
            bottomLeftCorner.cover();
            if (bottomLeftCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(bottomLeftCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x-1, y-1)){
            PlayableCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            Corner topRightCorner = bottomLeftCard.getTopRightCorner();
            topRightCorner.cover();
            if (topRightCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(topRightCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1, y-1)){
            PlayableCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            Corner topLeftCorner = bottomRightCard.getTopLeftCorner();
            topLeftCorner.cover();
            if (topLeftCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(topLeftCorner.getSymbol());
            }
        }
    }

    protected void gainSymbols(PlayerArea playerArea){
        if(topLeftCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(topLeftCorner.getSymbol());
        }
        if(topRightCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(topRightCorner.getSymbol());
        }
        if(bottomLeftCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(bottomLeftCorner.getSymbol());
        }
        if(bottomRightCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(bottomRightCorner.getSymbol());
        }
    }
}
