package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.enumerations.Symbol;

public class Corner {
    private final Symbol symbol;
    private Boolean covered;

    public Corner(Symbol symbol, Boolean covered){
        this.symbol = symbol;
        this.covered = covered;
    }
    public Symbol getSymbol(){
        return symbol;
    }
    public Boolean isCovered(){
        return covered;
    }
    public void cover(){
        covered = true;
    }
}
