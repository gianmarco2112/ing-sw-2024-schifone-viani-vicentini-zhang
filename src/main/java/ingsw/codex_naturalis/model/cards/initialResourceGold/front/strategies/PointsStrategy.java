package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CornerPointsStrategy.class, name = "cornerPointsStrategy"),
        @JsonSubTypes.Type(value = StandardPointsStrategy.class, name = "standardPointsStrategy")
})
public interface PointsStrategy {

    void run(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard);

}
