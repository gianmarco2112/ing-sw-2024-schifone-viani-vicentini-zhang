package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.List;

public class SymbolObjectiveCard extends ObjectiveCard{
    private final List<Symbol> symbolForPoints;
    public SymbolObjectiveCard(List<Symbol> symbolForPoints, int points, PatternStrategy patternStrategy){
        super(points, patternStrategy);
        this.symbolForPoints=symbolForPoints;
    }
    public Symbol getSymbolAt(int index){
        return symbolForPoints.get(index);
    }
    public int getNumOfSymbols(){
        return symbolForPoints.size();
    }
}
