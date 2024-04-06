package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.PointsStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.Map;

public class PointsGiverAndPointsGiverForCorner extends Needy {

    private final PointsStrategy pointsStrategy;

    //-------------------------------------------------------------------------------------------
    @JsonCreator
    public PointsGiverAndPointsGiverForCorner(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points,
            @JsonProperty("requirements") Map<Symbol, Integer> requirements,
            @JsonProperty("pointsStrategy") PointsStrategy pointsStrategy
            ){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.pointsStrategy = pointsStrategy;
    }

    //-------------------------------------------------------------------------------------------
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    protected void gainPoints(PlayerArea playerArea){
        pointsStrategy.run(playerArea, this);
    }
}
