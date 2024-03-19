package ingsw.codex_naturalis.model.cards.initial;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.NoCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

/**
 * Front side of the InitialCard
 * Each card contains all the 4 Resources (one per corner)
 * There isn't any central resource
 */
public class InitialCardFront extends PlayerAreaCard {

    /**
     * Constructor
     * @param kingdom (this attribute is inherited from PlayerAreaCard but not used)
     * @param topLeftCorner (could contain Resources or Objects)
     * @param topRightCorner (could contain Resources or Objects)
     * @param bottomLeftCorner (could contain Resources or Objects)
     * @param bottomRightCorner (could contain Resources or Objects)
     * */
    public InitialCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }


    /**
     * The initial card has been drawn
     * @param playerArea The player area that will have the initial card
     */
    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    /**
     * The front side of the initial card has been played
     * @param playerArea The player area that now has the initial card
     * @param x Coordinate x on the area
     * @param y Coordinate y on the area
     */
    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new CornerResourcesGetSymbolsStrategy(playerArea, getTopLeftCorner(), getTopRightCorner(), getBottomLeftCorner(), getBottomRightCorner()));
        setCalcPointsStrategy(new NoCalcPointsStrategy());
    }
}
