package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.CalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.CoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.IsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public abstract class HandPlayableSide extends PlayableSide {

    private IsPlayableStrategy isPlayableStrategy;
    private CoverCornersStrategy coverCornersStrategy;
    private CalcPointsStrategy calcPointsStrategy;


    public HandPlayableSide(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }


    public void setIsPlayableStrategy(IsPlayableStrategy isPlayableStrategy) { this.isPlayableStrategy = isPlayableStrategy; }
    public void setCoverCornersStrategy(CoverCornersStrategy coverCornersStrategy) { this.coverCornersStrategy = coverCornersStrategy; }
    public void setCalcPointsStrategy(CalcPointsStrategy calcPointsStrategy) { this.calcPointsStrategy = calcPointsStrategy; }


    /**
     * This method is called when a player wants to play a card in a certain position into his PlayerArea.
     * It verifies if the pointed PlayableSide is placeable in the area on coordinates x and y.
     * It checks that there isn't a card already placed in those coordinates and that there's at least
     * one card on which the pointed card will be placed.
     * It also checks that every corner the PlayableAreaCard is going to cover, is a visible corner.
     * @param x (cartesian coordinate x of the position where the player wants to place the card)
     * @param y (cartesian coordinate y of the position where the player wants to place the card)
     * @return true (if the card is playable in that position),
     * false (if the card isn't playable in that position)
     */
    public Boolean isPlayable(int x, int y){
        return isPlayableStrategy.run(x,y);
    }

    /**
     * This method is called when a player wants to play a card into his PlayerArea.
     * It changes the attributes of the corners of the card is going to cover in order to
     * make them actually covered and to remove the symbols from the symbol count of the player.
     * @param x (cartesian coordinate x of the position where the player wants to place the card)
     * @param y (cartesian coordinate y of the position where the player wants to place the card)
     */
    public void coverCorners(int x, int y){
        coverCornersStrategy.run(x,y);
    }

    /**
     * This method calculates the number of points given by the card
     * */
    public void calcPoints(){
        calcPointsStrategy.run();
    }

    public abstract void drawn(PlayerArea playerArea);
}
