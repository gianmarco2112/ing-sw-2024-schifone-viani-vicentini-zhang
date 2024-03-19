package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.List;

/**
 * class SymbolObjectiveCard
 */
public class SymbolObjectiveCard extends ObjectiveCard{
    /**
     * contains the List of Symbol
     */
    private final List<Symbol> symbolForPoints;

    /**
     * Constructor
     * @param symbolForPoints List of the Symbol for extra Points
     * @param points extra points giver by the card
     * @param patternStrategy invokes the right algorithms at runtime
     */
    public SymbolObjectiveCard(List<Symbol> symbolForPoints, int points, PatternStrategy patternStrategy){
        super(points, patternStrategy);
        this.symbolForPoints=symbolForPoints;
    }

    /**
     * getter
     * @param index index of the List of symbols for extra points
     * @return return the Symbol contained in the list of Symbol for extra points
     */
    public Symbol getSymbolAt(int index){
        return symbolForPoints.get(index);
    }

    /**
     * getter
     * @return the size of the list
     */
    public int getNumOfSymbols(){
        return symbolForPoints.size();
    }
}
