package ingsw.codex_naturalis.model.cards.initial;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.NoCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerareacardstrategy.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class InitialCardFront extends PlayerAreaCard {

    public InitialCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }


    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new CornerResourcesGetSymbolsStrategy(playerArea, getTopLeftCorner(), getTopRightCorner(), getBottomLeftCorner(), getBottomRightCorner()));
        setCalcPointsStrategy(new NoCalcPointsStrategy());
    }
}
