package ingsw.codex_naturalis.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import ingsw.codex_naturalis.model.cards.Card;
import ingsw.codex_naturalis.model.player.PlayerArea;

import java.util.List;

/**
 * ObjectiveCard's class
 * An objective card gives points for each objective achieved
 * The objective can be: to have a certain number of symbols on player's area or to compose a certain pattern with cards
 * The patterns can be two type: an L or a diagonal.
 * It is not necessary to have an attribute to represent the color of the ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide, because we can know it from
 * the kingdom in which it belongs to.
 * Finally, ObjectiveCard has an interface that call the right algorithms at runtime.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = SymbolsObjectiveCard.class, name = "symbols"),
        @Type(value = PatternObjectiveCard.class, name = "pattern")
})
public abstract class ObjectiveCard extends Card {

    /**
     * attribute points represents the points that objective card gives for each objective achieved
     */
    private final int points;


    /**
     * Constructor
     * @param points points of ObjectiveCard
     */
    @JsonCreator
    public ObjectiveCard(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("points") int points){
        super(cardID);
        this.points = points;
    }



    /**
     * Getter
     * @return how many points the card gives for one objective achieved
     */
    public int getPoints() {
        return points;
    }

    public void flip(String nickname){
        showingFront = !showingFront;
    }

    public abstract void gainPoints(List<PlayerArea> playerAreas);

    public abstract String getDescription();
}
