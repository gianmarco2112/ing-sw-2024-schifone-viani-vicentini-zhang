package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;
import java.util.List;

/**
 * class PatternObjectiveCard
 */
public class PatternObjectiveCard extends ObjectiveCard{
    /**
     * HashMap contains the pattern for the Objective
     */
    public HashMap<List<Integer>, Symbol> pattern;
    /**
     * pattern max dimension
     */
    public final int maxPatternDim;

    /**
     * constructor
     * @param points points that
     * @param patternStrategy invokes the right algorithms at runtime
     * @param pattern reference to the HashMap that contains the pattern
     * @param maxPatternDim max coordinates of the pattern
     */
    public PatternObjectiveCard(int points, PatternStrategy patternStrategy, HashMap<List<Integer>,Symbol> pattern, int maxPatternDim){
        super(points,patternStrategy);
        this.pattern = new HashMap<>(pattern);
        this.maxPatternDim =maxPatternDim;
    }

    /**
     * getter
     * @return pattern dimension
     */
    public int getPatternDim(){return maxPatternDim;}

    /**
     * getter
     * @param coordinates from which I get the symbol from the pattern
     * @return the symbol in the coordinates in the HashMap
     */
    public Symbol getSymbolAt(List<Integer> coordinates){return pattern.get(coordinates);}

}
