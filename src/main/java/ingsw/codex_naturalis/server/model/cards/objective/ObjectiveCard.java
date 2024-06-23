package ingsw.codex_naturalis.server.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import ingsw.codex_naturalis.server.model.cards.Card;
import ingsw.codex_naturalis.server.model.player.PlayerArea;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * ObjectiveCard's class
 * An objective card gives points for each objective achieved
 * The objective can be: to have a certain number of symbols on player's area or to
 * compose a certain pattern with cards
 * The patterns can be two type: an L or a diagonal.
 * It is not necessary to have an attribute to represent the color of the
 * ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide,
 * because we can know it from the kingdom in which it belongs to.
 * Finally,to give the right amount of points to Players, ObjectiveCard has an interface
 * that, at runtime, calls the right algorithm.
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
     * True if the card is showing the front side, False if it's showing the back side
     */
    private boolean showingFront;

    /**
     * The points that the objective card gives to the Player each time the objective is achieved
     */
    private final int points;

    /**
     * Objective card's constructor
     * @param points points of ObjectiveCard
     * @param cardID the ID of the card
     */
    @JsonCreator
    public ObjectiveCard(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("points") int points){
        super(cardID);
        this.points = points;
        showingFront = false;
    }

    /**
     * Getter of the objective card's points
     * @return points: how many points the card gives each time the objective is achieved
     */
    public int getPoints() {
        return points;
    }
    /**
     * Method to flip the card:
     * if the card is currently showing front, it would be flipped to the back side
     * if the card is currently showing back, it would be flipped to the front side
     */
    public void flip(){
        showingFront = !showingFront;
    }
    /**
     * This method adds to the PlayerArea the points given by the Objective card
     */
    public abstract void gainPoints(List<PlayerArea> playerAreas);
    /**
     * To print the card on the screen (the visual representation of the card is a string)
     */
    public abstract String cardToString();
    /**
     * To know is the card is currently showing the front of back side
     * @return true if the card is showing front,
     *         false otherwise
     */
    public boolean isShowingFront() {
        return showingFront;
    }

}
