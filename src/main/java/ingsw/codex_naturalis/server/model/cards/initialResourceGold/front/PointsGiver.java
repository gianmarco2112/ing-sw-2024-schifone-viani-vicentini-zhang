package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
/**
 * PointsGiver's class
 */
public class PointsGiver extends PlayableSide {
    /**
     * Points given by the card
     */
    private final int points;

    //---------------------------------------------------------------------------------------------------
    /**
     * PointsGiver's constructor
     * @param points given by the card
     * @param bottomRightCorner of the card
     * @param bottomLeftCorner of the card
     * @param topLeftCorner of the card
     * @param topRightCorner of the card
     */
    @JsonCreator
    public PointsGiver(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.points = points;
    }

    //---------------------------------------------------------------------------------------------------
    /**
     * Getter of the points of the card
     * @return points
     */
    public int getPoints(){
        return points;
    }
    /**
     * This method prints to the user's screen the visual representation of the card
     * @return outString: the visual representation (implemented as a string) of the card
     */
    @Override
    public String handCardToString(Symbol kingdom) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(this, kingdom));
        if (getTopLeftCorner().getSymbol() != Symbol.EMPTY && getTopLeftCorner().getSymbol() != Symbol.COVERED){
            outString.replace(48, 49, DefaultValue.ANSI_RESET + getPoints() + kingdom.getColor());
        }
        else{
            outString.replace(34, 35, DefaultValue.ANSI_RESET + getPoints() + kingdom.getColor());
        }
        return outString.toString();
    }

    /**
     * To play the card on the specified coordinates
     * @param playerArea where to place the card
     * @param x : x coordinates where to place the card
     * @param y : y coordinates where to place the card
     */
    @Override
    public void play(PlayerArea playerArea, int x, int y) {
        coverCorners(playerArea, x, y);
        gainSymbols(playerArea);
        gainPoints(playerArea);
    }
    /**
     * To add the point given by the card to the player's area
     * @param playerArea of the player that has played the card
     */
    protected void gainPoints(PlayerArea playerArea){
        playerArea.setPoints(playerArea.getPoints() + points);
    }
}
