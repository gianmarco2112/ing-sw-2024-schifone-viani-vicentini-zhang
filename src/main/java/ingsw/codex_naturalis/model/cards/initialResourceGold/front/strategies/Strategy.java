package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CornerStrategy.class, name = "cornerPointsStrategy"),
        @JsonSubTypes.Type(value = StandardStrategy.class, name = "standardPointsStrategy")
})
public interface Strategy {

    String handCardToString(Symbol kingdom, PointsGiverAndPointsGiverForCorner cardSide);
    void gainPoints(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard);

}
