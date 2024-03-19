package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.ObjectCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.RequirementsIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class GoldCardFrontObject extends GoldCardFront {

    private Symbol object;


    public GoldCardFrontObject(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements, Symbol object){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.object = object;
    }


    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new RequirementsIsPlayableStrategy(playerArea, getRequirements()));
    }

    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new CornerResourcesGetSymbolsStrategy(playerArea, getTopLeftCorner(), getTopRightCorner(), getBottomLeftCorner(), getBottomRightCorner()));
        setCalcPointsStrategy(new ObjectCalcPointsStrategy(playerArea, object, getPoints()));
    }
}