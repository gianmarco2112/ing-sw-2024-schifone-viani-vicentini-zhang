package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.RequirementsIsPlayableStrategy;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.SimpleCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class GoldCardFront extends PlayerAreaCard {

    private HashMap<Symbol, Integer> requirements;
    private final int points;

    public GoldCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.requirements = requirements;
        this.points = points;
    }


    public HashMap<Symbol, Integer> getRequirements() {
        return requirements;
    }
    public int getPoints() {
        return points;
    }

    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new RequirementsIsPlayableStrategy(playerArea, requirements));
    }

    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new CornerResourcesGetSymbolsStrategy(playerArea, getTopLeftCorner(), getTopRightCorner(), getBottomLeftCorner(), getBottomRightCorner()));
        setCalcPointsStrategy(new SimpleCalcPointsStrategy(playerArea, points));
    }
}
