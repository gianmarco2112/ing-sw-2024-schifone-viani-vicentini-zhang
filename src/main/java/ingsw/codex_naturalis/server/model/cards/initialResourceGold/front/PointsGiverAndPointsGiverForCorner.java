package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies.Strategy;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.HashMap;

public class PointsGiverAndPointsGiverForCorner extends Needy {

    private final Strategy strategy;

    //-------------------------------------------------------------------------------------------
    @JsonCreator
    public PointsGiverAndPointsGiverForCorner(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points,
            @JsonProperty("requirements") HashMap<Symbol, Integer> requirements,
            @JsonProperty("pointsStrategy") Strategy strategy
            ){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.strategy = strategy;
    }

    //-------------------------------------------------------------------------------------------

    @Override
    public String handCardToString(Symbol kingdom) {
        return strategy.handCardToString(kingdom, this);
    }

    @Override
    protected void gainPoints(PlayerArea playerArea){
        strategy.gainPoints(playerArea, this);
    }
}
