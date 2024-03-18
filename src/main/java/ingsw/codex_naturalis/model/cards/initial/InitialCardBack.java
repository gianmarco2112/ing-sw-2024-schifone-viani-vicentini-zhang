package ingsw.codex_naturalis.model.cards.initial;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.*;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class InitialCardBack extends PlayerAreaCard {

    private List<Symbol> resources;


    public InitialCardBack(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, List<Symbol> resources){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.resources = resources;
    }


    public List<Symbol> getResources(){
        return resources;
    }

    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new PermanentResourcesGetSymbolsStrategy(playerArea, resources));
        setCalcPointsStrategy(new NoCalcPointsStrategy());
    }
}
