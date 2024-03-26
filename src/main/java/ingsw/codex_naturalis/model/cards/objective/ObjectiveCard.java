package ingsw.codex_naturalis.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.SymbolsObjectiveCard;

/**
 * ObjectiveCard's class
 * An objective card gives points for each objective achieved
 * The objective can be: to have a certain number of symbols on player's area or to compose a certain pattern with cards
 * The patterns can be two type: an L or a diagonal.
 * It is not necessary to have an attribute to represent the color of the PlayableSide, because we can know it from
 * the kingdom in which it belongs to.
 * Finally, ObjectiveCard has an interface that call the right algorithms at runtime.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PatternObjectiveCard.class, name = "pattern"),
        @JsonSubTypes.Type(value = SymbolsObjectiveCard.class, name = "symbols")
})
public abstract class ObjectiveCard {

    /**
     * attribute points represents the points that objective card gives for each objective achieved
     */
    private final int points;

    /**
     * attribute patternstrategy is used to call at runtime the right strategy to count extra points
     */
    protected CalcExtraPointsStrategy calcExtraPointsStrategy;


    /**
     * Constructor
     * @param points points of ObjectiveCard
     */
    @JsonCreator
    public ObjectiveCard(@JsonProperty("points") int points){
        this.points = points;
    }


    public void setCalcExtraPointsStrategy(CalcExtraPointsStrategy calcExtraPointsStrategy){
        this.calcExtraPointsStrategy = calcExtraPointsStrategy;
    }

    /**
     * Invoke at runtime the right algorithm depending on the type of card the player has.
     */
    public void execute(){
        calcExtraPointsStrategy.run();
    }

    /**
     * Getter
     * @return how many points the card gives for one objective achieved
     */
    public int getPoints() {
        return points;
    }

    public abstract void chosen(PlayerArea playerArea);
}
