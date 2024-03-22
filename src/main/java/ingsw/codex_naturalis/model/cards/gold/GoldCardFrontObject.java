package ingsw.codex_naturalis.model.cards.gold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.ObjectCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.CornerResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.RequirementsIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

/**
 * The front side of the gold card that gives points based on the number of object the player area has
 */
public class GoldCardFrontObject extends GoldCardFront {

    /**
     * The object for the points
     */
    private Symbol object;


    /**
     * Constructor
     * @param kingdom Kingdom
     * @param topLeftCorner Top left corner
     * @param topRightCorner Top right corner
     * @param bottomLeftCorner Bottom left corner
     * @param bottomRightCorner Bottom right corner
     * @param points Points
     * @param object Object
     */
    @JsonCreator
    public GoldCardFrontObject(
            @JsonProperty("kingdom") Symbol kingdom,
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points,
            @JsonProperty("requirements") HashMap<Symbol, Integer> requirements,
            @JsonProperty("object") Symbol object){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.object = object;
    }


    /**
     * Object getter
     * @return Object
     */
    public Symbol getObject() {
        return object;
    }

    /**
     * The gold card has been drawn
     * @param playerArea The player area that will eventually have the gold card
     */
    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new RequirementsIsPlayableStrategy(playerArea, getRequirements()));
    }

    /**
     * The front side of the gold card has been played
     * @param playerArea The player area that now has the gold card
     */
    @Override
    public void played(PlayerArea playerArea){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new CornerResourcesGetSymbolsStrategy(playerArea, getTopLeftCorner(), getTopRightCorner(), getBottomLeftCorner(), getBottomRightCorner()));
        setCalcPointsStrategy(new ObjectCalcPointsStrategy(playerArea, object, getPoints()));
    }
}