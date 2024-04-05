package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.PointsStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.Map;

public class PointsGiverAndPointsGiverForCorner extends Needy {

    private final PointsStrategy pointsStrategy;

    //-------------------------------------------------------------------------------------------
    public PointsGiverAndPointsGiverForCorner(Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, Map<Symbol, Integer> requirements, PointsStrategy pointsStrategy){
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
