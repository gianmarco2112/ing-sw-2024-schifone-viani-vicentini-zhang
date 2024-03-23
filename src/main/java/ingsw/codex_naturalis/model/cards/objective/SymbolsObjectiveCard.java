package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * class SymbolObjectiveCard
 */
public class SymbolsObjectiveCard extends ObjectiveCard{

    /**
     * contains the List of Symbol
     */
    private final HashMap<Symbol,Integer> symbolsForPoints;


    /**
     * Constructor
     * @param symbolsForPoints List of the Symbol for extra Points
     * @param points extra points giver by the card
     * @param calcExtraPointsStrategy invokes the right algorithms at runtime
     */
    public SymbolsObjectiveCard(HashMap<Symbol,Integer> symbolsForPoints, int points, CalcExtraPointsStrategy calcExtraPointsStrategy){
        super(points);
        this.symbolsForPoints = symbolsForPoints;
    }


    /**
     * Method to get the count of a symbol from the HashMap
     * @param symbol the symbol that needs to be counted
     * @return the count of the symbol
     */
    public Integer getNumOfSymbol(Symbol symbol){
        return symbolsForPoints.get(symbol);
    }

    /**
     * Returns a copy of the key set of the HashMap
     * @return the set
     */
    public Set<Symbol> getKeySet() {
        return symbolsForPoints.keySet();
    }

    @Override
    public void setCalcExtraPointsStrategy(PlayerArea playerArea) {
        calcExtraPointsStrategy = new SymbolsCalcExtraPointsStrategy(this, new ArrayList<>(List.of(playerArea)));
    }
}
