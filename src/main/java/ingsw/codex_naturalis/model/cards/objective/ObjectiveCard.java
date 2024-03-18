package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ObjectiveCard's class
 * An objective card gives points for each objective achieved
 * The objective can be: to have a certain number of symbols on player's area or to compose a certain pattern with cards
 * The patterns can be two type: an L or a diagonal.If we image to represent the pattern ad a 3x3 matrix,
 * each type of pattern is uniquely identified by the centralSymbol([2][2]).
 * It is not necessary to have an attribute to represent the color of the PlayerAreaCard, because we can know it from
 * the kingdom in which it belongs to.
 * For the objective which counts the Symbol I introduced a List of Symbol, that could be EMPTY if it is a pattern card.
 * If it is not a pattern card, centralSymbol is EMPTY.
 * Finally, ObjectiveCard has an interface that call the right algorithms at runtime.
 */
public class ObjectiveCard {
    private final int points;
    private final Symbol centralSymbol; //could be EMPTY
    private final List<Symbol> objectForPoints; //could be EMPTY
    private final PatternStrategy patternStrategy;

    /**
     * Constructor
     * @param ps it describes which types of ObjectiveCard we have (L Pattern, Diagonal Patter or No Pattern)
     * @param points it is the value of the card, points givers for each objective
     * @param centralSymbol EMPTY if it is not a pattern card. It contains a Symbol if it represents a pattern
     * @param listOfObject EMPTY if it is a pattern card. Contains the Symbols that we need to have in order to obtain extra points
     */
    public ObjectiveCard(PatternStrategy ps, int points, Symbol centralSymbol, List<Symbol> listOfObject){
        patternStrategy = ps;
        this.points=points;
        this.centralSymbol=centralSymbol;
        objectForPoints = new ArrayList<>();
        objectForPoints.addAll(listOfObject);
    }

    /**
     * Invoke at runtime the right algorithm depending on the type of card the player has.
     * @param area player area, because we have to check pattern or count Symbol on the player's area.
     * @param numOfSymbols we use it for the NoPatternStrategy to see the number of Symbol for points
     * @return number of extraPoints
     */
    public int execute(HashMap<int[], PlayerAreaCard> area, HashMap<Symbol, Integer> numOfSymbols){
        return patternStrategy.run(area, this, numOfSymbols);
    }

    /**
     * Getter
     * @return the reference to the List of the ObjectForPoints
     */
    public List<Symbol> getObjectForPoints() {
        return objectForPoints;
    }

    /**
     * Getter
     * @return the central Symbol
     */
    public Symbol getCentralSymbol() {
        return centralSymbol;
    }

    /**
     * Getter
     * @return how many points the card gives for one objective achieved
     */
    public int getPoints() {
        return points;
    }
}
