package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies.Strategy;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.HashMap;
/**
 * PointsGiverAndPointsGiverForCorner's class:
 * to calculate the points given by a gold card, based on the corners covered
 */
public class PointsGiverAndPointsGiverForCorner extends Needy {

    private final Strategy strategy;

    //-------------------------------------------------------------------------------------------
    /**
     * PointsGiverAndPointsGiverForCorner's constructor
     * @param points given by the cards
     * @param topRightCorner of the card
     * @param topLeftCorner of the card
     * @param bottomLeftCorner of the card
     * @param bottomRightCorner of the card
     * @param requirements needed to play the card
     * @param strategy: indicates that the gold card gives points based on the corners covered
     */
    @JsonCreator
    public PointsGiverAndPointsGiverForCorner(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points,
            @JsonProperty("requirements") HashMap<Symbol, Integer> requirements,
            @JsonProperty("pointsStrategy") Strategy strategy
            ){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.strategy = strategy;
    }

    //-------------------------------------------------------------------------------------------
    /**
     * This method prints to the user's screen the visual representation of the card
     * @param kingdom of the card
     * @return outString: the visual representation (implemented as a string) of the card
     */
    @Override
    public String handCardToString(Symbol kingdom) {
        return strategy.handCardToString(kingdom, this);
    }
    /**
     * To calculate the points given by the card
     * @param playerArea: playerArea where to add the points given by the card
     */
    @Override
    protected void gainPoints(PlayerArea playerArea){
        strategy.gainPoints(playerArea, this);
    }
}
