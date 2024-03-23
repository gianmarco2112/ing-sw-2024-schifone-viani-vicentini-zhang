package ingsw.codex_naturalis.model.cards.resource;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.HandPlayableSide;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.NoCalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.PermanentResourceGetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.SimpleCoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.SimpleIsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;


/**
 * Back side of the ResourceCard
 * Each card contains 1 Resource in the center and has all the 4 corners empty
 */
public class ResourceCardBack extends HandPlayableSide {

    /**
     * The permanent resource
     */
    private final Symbol resource;


    /**
     * Constructor
     * @param kingdom (represents the color of the card)
     * @param topLeftCorner (it would be empty)
     * @param topRightCorner (it would be empty)
     * @param bottomLeftCorner (it would be empty)
     * @param bottomRightCorner (it would be empty)
     * @param resource (central resource of the card)
     * */
    @JsonCreator
    public ResourceCardBack(
            @JsonProperty("kingdom") Symbol kingdom,
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("resource") Symbol resource){
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
     * The resource card has been drawn
     * @param playerArea The player area that will have the resource card
     */
    @Override
    public void drawn(PlayerArea playerArea){
        setIsPlayableStrategy(new SimpleIsPlayableStrategy(playerArea));
    }

    /**
     * The back side of the resource card has been played
     * @param playerArea The player area that now has the resource card
     */
    @Override
    public void played(PlayerArea playerArea){
        setCoverCornersStrategy(new SimpleCoverCornersStrategy(playerArea));
        setGetSymbolsStrategy(new PermanentResourceGetSymbolsStrategy(playerArea, resource));
        setCalcPointsStrategy(new NoCalcPointsStrategy());
    }
}
