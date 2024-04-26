package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.DefaultValue;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;

public class PointsGiver extends PlayableSide {

    private final int points;

    //---------------------------------------------------------------------------------------------------
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
    public int getPoints(){
        return points;
    }

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


    @Override
    public void play(PlayerArea playerArea, int x, int y) {
        coverCorners(playerArea, x, y);
        gainSymbols(playerArea);
        gainPoints(playerArea);
    }

    protected void gainPoints(PlayerArea playerArea){
        playerArea.setPoints(playerArea.getPoints() + points);
    }
}
