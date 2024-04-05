package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;

public class PointsGiver extends PlayableSide {

    private final int points;

    //---------------------------------------------------------------------------------------------------
    public PointsGiver(Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.points = points;
    }

    //---------------------------------------------------------------------------------------------------
    public int getPoints(){
        return points;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void play(PlayerArea playerArea, int x, int y) {
        coverCorners(playerArea, x, y);
        gainSymbols(playerArea);
        gainPoints(playerArea);
    }

    protected void gainPoints(PlayerArea playerArea){
        playerArea.setPoints(playerArea.getPoints() + points);
    }
}
