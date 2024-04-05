package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.Map;

public class PointsGiverForObject extends Needy {

    private final Symbol object;

    //-------------------------------------------------------------------------------------------
    public PointsGiverForObject(Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, Map<Symbol, Integer> requirements, Symbol object){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.object = object;
    }

    //-------------------------------------------------------------------------------------------
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    protected void gainPoints(PlayerArea playerArea){
        playerArea.setPoints(playerArea.getPoints() + getPoints() * playerArea.getNumOfSymbol(object));
    }
}
