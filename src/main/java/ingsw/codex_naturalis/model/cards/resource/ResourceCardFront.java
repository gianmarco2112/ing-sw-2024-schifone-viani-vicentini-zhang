package ingsw.codex_naturalis.model.cards.resource;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.SimpleCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

/**
 * Front side of the ResourceCard
 * Each card could contain Resources or Objects in the Corners
 * There isn't any central resource
 */
public class ResourceCardFront extends PlayerAreaCard {

    /**
     * The points given to the Player by the card when placed into the PlayerArea
     */
    private final int points;

    /**
     * Constructor
     * @param kingdom (represents the color of the card)
     * @param topLeftCorner (could contain Resources or Objects)
     * @param topRightCorner (could contain Resources or Objects)
     * @param bottomLeftCorner (could contain Resources or Objects)
     * @param bottomRightCorner (could contain Resources or Objects)
     * @param points (given by the card when placed in the PlayerArea)
     */
    public ResourceCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.points = points;
    }


    /**
     * Points getter
     * @return Points
     */
    public int getPoints() {
        return points;
    }

    /**
     * The resource card has been drawn
     * @param playerArea The player area that will have the resource card
     */
    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    /**
     * The front side of the resource card has been played
     * @param playerArea The player area that now has the resource card
     * @param x Coordinate x on the area
     * @param y Coordinate y on the area
     */
    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new CornerResourcesGetSymbolsStrategy(playerArea, getTopLeftCorner(), getTopRightCorner(), getBottomLeftCorner(), getBottomRightCorner()));
        setCalcPointsStrategy(new SimpleCalcPointsStrategy(playerArea, points));
    }

}
