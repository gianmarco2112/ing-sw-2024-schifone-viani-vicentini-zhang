package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.NoCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.PermanentResourceGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

/**
 * The back side of the gold card
 */
public class GoldCardBack extends PlayerAreaCard {

    /**
     * Permanent resource
     */
    private Symbol resource;


    /**
     * Constructor
     * @param kingdom Kingdom
     * @param topLeftCorner Top left corner
     * @param topRightCorner Top right corner
     * @param bottomLeftCorner Bottom left corner
     * @param bottomRightCorner Bottom right corner
     * @param resource Permanet resource
     */
    public GoldCardBack(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, Symbol resource){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.resource = resource;
    }


    /**
     * Permanent resource getter
     * @return
     */
    public Symbol getResource(){
        return resource;
    }

    /**
     * The gold card has been drawn
     * @param playerArea The player area that will eventually have the gold card
     */
    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    /**
     * The back side of the gold card has been played
     * @param playerArea The player area that now has the gold card
     * @param x Coordinate x on the area
     * @param y Coordinate y on the area
     */
    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new PermanentResourceGetSymbolsStrategy(playerArea, resource));
        setCalcPointsStrategy(new NoCalcPointsStrategy());
    }
}
