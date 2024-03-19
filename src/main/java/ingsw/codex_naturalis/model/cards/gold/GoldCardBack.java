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
 * Each card contains 1 Resource in the center and has all the 4 corners empty
 */
public class GoldCardBack extends PlayerAreaCard {

    /**
     * Permanent resource (in the centre of the card)
     */
    private Symbol resource;


    /**
     * Constructor
     * @param kingdom (represents the color of the card)
     * @param topLeftCorner (it would be empty)
     * @param topRightCorner (it would be empty)
     * @param bottomLeftCorner (it would be empty)
     * @param bottomRightCorner (it would be empty)
     * @param resource (central resource of the card)
     */
    public GoldCardBack(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, Symbol resource){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.resource = resource;
    }

    /**
     * Permanent resource getter
     * This method returns the central Resource of the card, which is the only Resource
     * present in the card (all the corners are empty)
     * @return resource
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
