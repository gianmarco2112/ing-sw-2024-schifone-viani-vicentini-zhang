package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.RequirementsIsPlayableStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.SimpleCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

/**
 * Front side of the GoldCard
 * Each card could contain Resources or Objects in the Corners
 * There isn't any central resource
 * Each GoldCard has a list of Symbols that the Player has to
 * have into his PlayerArea in order to be able to place the card
 */
public class GoldCardFront extends PlayerAreaCard {

    /**
     * The resource requirements
     */
    private HashMap<Symbol, Integer> requirements;
    /**
     * The points
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
     * @param requirements (HashMap that contains all the Resources that
     *                     the Player needs to already have into its PlayerArea
     *                     in order to be able to place the GoldCard into the
     *                     PlayerArea)
     */
    public GoldCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.requirements = requirements;
        this.points = points;
    }


    /**
     * Requirements getter
     * @return Requirements
     */
    public HashMap<Symbol, Integer> getRequirements() {
        return requirements;
    }

    /**
     * points getter
     * @return Points
     */
    public int getPoints() {
        return points;
    }

    /**
     * The gold card has been drawn
     * @param playerArea The player area that will eventually have the gold card
     */
    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new RequirementsIsPlayableStrategy(playerArea, requirements));
    }

    /**
     * The front side of the gold card has been played
     * @param playerArea The player area that now has the gold card
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
