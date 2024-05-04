package ingsw.codex_naturalis.server.model.cards.initialResourceGold;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.Needy;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiver;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverForObject;
import ingsw.codex_naturalis.common.enumerations.Symbol;

/**
 * Class that represents one side (Front or Back) of an INITIAL, GOLD or RESOURCE card
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Back.class, name = "back"),
        @JsonSubTypes.Type(value = PointsGiver.class, name = "pointsGiver"),
        @JsonSubTypes.Type(value = Needy.class, name = "needy"),
        @JsonSubTypes.Type(value = PointsGiverAndPointsGiverForCorner.class, name = "pointsGiverAndPointsGiverForCorner"),
        @JsonSubTypes.Type(value = PointsGiverForObject.class, name = "pointsGiverForObject"),
        @JsonSubTypes.Type(value = PlayableSide.class, name = "playableSide")
})
public class PlayableSide {

    /**
     * Top left corner
     */
    private final Corner topLeftCorner;
    /**
     * Top right corner
     */
    private final Corner topRightCorner;
    /**
     * Bottom left corner
     */
    private final Corner bottomLeftCorner;
    /**
     * Bottom right corner
     */
    private final Corner bottomRightCorner;

    /**
     * Side's constructor
     * @param topLeftCorner
     * @param topRightCorner
     * @param bottomLeftCorner
     * @param bottomRightCorner
     */

    //-------------------------------------------------------------------------------------------
    @JsonCreator
    public PlayableSide(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner){
        this.topLeftCorner = topLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Top left corner's getter
     * @return topLeftCorner
     */
    public Corner getTopLeftCorner(){ return topLeftCorner; }
    /**
     * Top right corner's getter
     * @return topRightCorner
     */
    public Corner getTopRightCorner() { return topRightCorner; }
    /**
     * Bottom left corner's getter
     * @return bottomLeftCorner
     */
    public Corner getBottomLeftCorner() { return bottomLeftCorner; }
    /**
     * Bottom right corner's getter
     * @return bottomRightCorner
     */
    public Corner getBottomRightCorner() { return bottomRightCorner; }
    /**
     * Method to increment the counters of each Symbol related to the played card.
     * @param playerArea: of the player whose counters are incremented
     */
    public void play(PlayerArea playerArea){
        gainSymbols(playerArea);
    }

    /**
     * This method verifies that the card is playable at the specified coordinates
     * @param playerArea: of the Player that wish to play the card
     * @return True: if the indicated position is free of other cards and the
     *               requirements of the card I want to play are fulfilled
     *         False: otherwise
     */
    public boolean isPlayable(PlayerArea playerArea, int x, int y) {

        if (playerArea.containsCardOnCoordinates(x,y))
            return false;

        int atLeastACard = 4;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayableCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (topLeftCard.getBottomRightCorner().isCovered())
                return false;
        }
        else
            atLeastACard--;
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayableCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (topRightCard.getBottomLeftCorner().isCovered())
                return false;
        }
        else
            atLeastACard--;
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayableCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (bottomLeftCard.getTopRightCorner().isCovered())
                return false;
        }
        else
            atLeastACard--;
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayableCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            if (bottomRightCard.getTopLeftCorner().isCovered())
                return false;
        }
        else
            atLeastACard--;

        if(atLeastACard < 1)
            return false;

        return true;
    }
    /**
     * This method plays the card, placing it on the specified coordinates
     * @param playerArea: the PlayerArea of the Player that is playing the card
     * @param x: the x coordinate where to play the card
     * @param y: the y coordinate where to play the card
     */
    public void play(PlayerArea playerArea, int x, int y) {
        coverCorners(playerArea, x, y);
        gainSymbols(playerArea);
    }
    /**
     * This method covers the corners of the corner-adjacent cards to the played card, if they are present.
     * @param playerArea: the PlayerArea of the Player that has played the card
     * @param x: the x coordinate where the card has been played
     * @param y: the y coordinate where the card has been played
     */
    protected void coverCorners(PlayerArea playerArea, int x, int y){
        if (playerArea.containsCardOnCoordinates(x-1, y+1)){
            PlayableCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            Corner bottomRightCorner = topLeftCard.getBottomRightCorner();
            bottomRightCorner.cover();
            if (bottomRightCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(bottomRightCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1, y+1)){
            PlayableCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            Corner bottomLeftCorner = topRightCard.getBottomLeftCorner();
            bottomLeftCorner.cover();
            if (bottomLeftCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(bottomLeftCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x-1, y-1)){
            PlayableCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            Corner topRightCorner = bottomLeftCard.getTopRightCorner();
            topRightCorner.cover();
            if (topRightCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(topRightCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1, y-1)){
            PlayableCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            Corner topLeftCorner = bottomRightCard.getTopLeftCorner();
            topLeftCorner.cover();
            if (topLeftCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(topLeftCorner.getSymbol());
            }
        }
    }

    /**
     * This method updates the Symbol's counters of the Player with the Symbols on the played card
     * @param playerArea
     */
    protected void gainSymbols(PlayerArea playerArea){
        if(topLeftCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(topLeftCorner.getSymbol());
        }
        if(topRightCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(topRightCorner.getSymbol());
        }
        if(bottomLeftCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(bottomLeftCorner.getSymbol());
        }
        if(bottomRightCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(bottomRightCorner.getSymbol());
        }
    }

    public String handCardToString(Symbol kingdom) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(this, kingdom));
        return outString.toString();
    }

    public String playerAreaCardToString(Symbol kingdom){
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIPlayerAreaCardSideTemplate(this, kingdom));
        return outString.toString();
    }
}
