package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.enumerations.Symbol;

public class Corner {
    private final Symbol symbol;
    private Boolean covered;

    @JsonCreator
    public Corner(
            @JsonProperty("symbol") Symbol symbol,
            @JsonProperty("covered") Boolean covered){
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
