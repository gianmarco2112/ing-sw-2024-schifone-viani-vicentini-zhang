package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;

public class PointsGiver extends PlayableSide {

    private final int points;

    //---------------------------------------------------------------------------------------------------
    @JsonCreator
    public PointsGiver(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.points = points;
    }

    //---------------------------------------------------------------------------------------------------
    public int getPoints(){
        return points;
    }

    @Override
    public String getDescription() {
        return super.getDescription();
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
