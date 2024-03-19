package ingsw.codex_naturalis.model.cards.resource;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.NoCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.PermanentResourceGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class ResourceCardBack extends PlayerAreaCard {

    private final Symbol resource;


    public ResourceCardBack(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, Symbol resource){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.resource = resource;
    }


    public Symbol getResource(){
        return resource;
    }

    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new PermanentResourceGetSymbolsStrategy(playerArea, resource));
        setCalcPointsStrategy(new NoCalcPointsStrategy());
    }
}
