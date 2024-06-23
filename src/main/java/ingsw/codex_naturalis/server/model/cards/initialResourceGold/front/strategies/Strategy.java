package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
/**
 * Strategy's class
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CornerStrategy.class, name = "cornerPointsStrategy"),
        @JsonSubTypes.Type(value = StandardStrategy.class, name = "standardPointsStrategy")
})
public interface Strategy {
    /**
     * This method prints to the user's screen the visual representation of the card
     * @param kingdom of the card
     * @param cardSide : which side (of the card) to draw
     * @return outString: the visual representation (implemented as a string) of the card
     */
    String handCardToString(Symbol kingdom, PointsGiverAndPointsGiverForCorner cardSide);
    /**
     * To count the points
     * @param playerArea: the playerArea of the player
     * @param playedCard: the gold card
     */
    void gainPoints(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard);

}
