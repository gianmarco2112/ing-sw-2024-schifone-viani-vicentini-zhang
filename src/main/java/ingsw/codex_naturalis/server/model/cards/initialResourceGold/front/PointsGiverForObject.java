package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.HashMap;
/**
 * PointsGiverAndPointsGiverForCorner's class:
 * to calculate the points given by a gold card, based on the number of objects
 * of the specified type present in the PlayerArea when the gold card is played
 */
public class PointsGiverForObject extends Needy {
    /**
     * the object that gives points
     */
    private final Symbol object;

    //-------------------------------------------------------------------------------------------
    /**
     * PointsGiverForObject's constructor:
     * @param bottomLeftCorner of the card
     * @param bottomRightCorner of the card
     * @param topLeftCorner of the card
     * @param topRightCorner of the card
     * @param requirements needed to play the card
     * @param points given by the card
     * @param object: the object that gives point
     */
    @JsonCreator
    public PointsGiverForObject(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points,
            @JsonProperty("requirements") HashMap<Symbol, Integer> requirements,
            @JsonProperty("object") Symbol object){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.object = object;
    }

    //-------------------------------------------------------------------------------------------
    /**
     * Method to give the points of the card to the player
     * @param playerArea where to add the points
     */
    @Override
    protected void gainPoints(PlayerArea playerArea){
        playerArea.setPoints(playerArea.getPoints() + getPoints() * playerArea.getNumOfSymbol(object));
    }
    /**
     * This method prints to the user's screen the visual representation of the card
     * @param kingdom of the card
     * @return outString: the visual representation (implemented as a string) of the card
     */
    @Override
    public String handCardToString(Symbol kingdom) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(this, kingdom));
        if (this.getTopLeftCorner().getSymbol() != Symbol.EMPTY && this.getTopLeftCorner().getSymbol() != Symbol.COVERED){
            outString.replace(47, 50, DefaultValue.ANSI_RESET + this.getPoints() + kingdom.getColor() + "│" + object.getColoredChar() + kingdom.getColor());
        }
        else{
            outString.replace(33, 36, DefaultValue.ANSI_RESET + this.getPoints() + kingdom.getColor() + "│" + object.getColoredChar() + kingdom.getColor());
        }
        return this.addRequirementsToHandCardString(outString.toString(), kingdom);
    }
}
