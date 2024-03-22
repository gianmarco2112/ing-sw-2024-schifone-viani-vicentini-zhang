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
     * pattern max row
     */
    private final int maxRow;

    /**
     * pattern max column
     */
    private final int maxColumn;

    /**
     * pattern min row
     */
    private final int minRow;

    /**
     * pattern max column
     */
    private final int minColumn;

    /**
     * constructor
     * @param points points that
     * @param pattern reference to the HashMap that contains the pattern
     * @param maxRow max row of the pattern
     * @param maxColumn max column of the pattern
     */
    public PatternObjectiveCard(int points, HashMap<List<Integer>,Symbol> pattern, int maxRow, int maxColumn, int minRow, int minColumn){
        super(points);
        this.pattern = pattern;
        this.maxRow = maxRow;
        this.maxColumn = maxColumn;
        this.minRow = minRow;
        this.minColumn = minColumn;
    }


    /**
     * Max row getter
     * @return Max row
     */
    public int getMaxRow() {
        return maxRow;
    }

    /**
     * Max column getter
     * @return
     */
    public int getMaxColumn() {
        return maxColumn;
    }

    /**
     * Min row getter
     * @return
     */
    public int getMinRow() {
        return minRow;
    }

    /**
     * Min column getter
     * @return
     */
    public int getMinColumn() {
        return minColumn;
    }

    /**
     * getter
     * @param coordinates from which I get the symbol from the pattern
     * @return the symbol in the coordinates in the HashMap
     */
    public Symbol getSymbolAt(List<Integer> coordinates){
        return pattern.get(coordinates);
    }
}
