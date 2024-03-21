package ingsw.codex_naturalis.model.cards.initial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.NoCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.PermanentResourcesGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

/**
 * Back side of the InitialCard
 * Each card could contain (or not) Resources in the corners
 * In the center there is a list of Permanent Resources
 */
public class InitialCardBack extends PlayerAreaCard {

    /**
     * The permanents resources
     */
    private List<Symbol> resources;

    /**
     * Constructor
     * @param kingdom (in this case is unused)
     * @param topLeftCorner (could contain 1 Resource or be empty)
     * @param topRightCorner (could contain 1 Resource or be empty)
     * @param bottomLeftCorner (could contain 1 Resource or be empty)
     * @param bottomRightCorner (could contain 1 Resource or be empty)
     * @param resources (the list of the central Resources of the card)
     * */
    @JsonCreator
    public InitialCardBack(
            @JsonProperty("kingdom") Symbol kingdom,
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("resources") List<Symbol> resources){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.resources = resources;
    }


    /**
     * Permanent resources getter
     * This method would return the list of the central Resources of the card
     * @return resources
     */
    public List<Symbol> getResources(){
        return resources;
    }

    /**
     * The back side of the initial card has been played
     * @param playerArea The player area that now has the initial card
     * @param x Coordinate x on the area
     * @param y Coordinate y on the area
     */
    @Override
    public void played(PlayerArea playerArea, int x, int y){
        setGetSymbolsStrategy(new PermanentResourcesGetSymbolsStrategy(playerArea, resources));
    }
}
