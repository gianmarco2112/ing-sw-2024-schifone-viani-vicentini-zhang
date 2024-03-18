package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;
import java.util.List;

public class PatternObjectiveCard extends ObjectiveCard{
    public final HashMap<List<Integer>, Symbol> pattern;
    public final int maxPatternDim;
    public PatternObjectiveCard(int points, PatternStrategy patternStrategy, HashMap<List<Integer>,Symbol> pattern, int maxPatternDim){
        super(points,patternStrategy);
        this.pattern = new HashMap<>(pattern);
        this.maxPatternDim =maxPatternDim;
    }

    public int getPatternDim(){return maxPatternDim;}
    public Symbol getSymbolAt(List<Integer> coordinates){return pattern.get(coordinates);}

}
