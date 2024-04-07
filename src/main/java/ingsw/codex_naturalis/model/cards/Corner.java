package ingsw.codex_naturalis.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.model.enumerations.Symbol;

/**
 * Corner class
 */
public class Corner {
    /**
     * Symbol on the corner
     */
    private final Symbol symbol;
    /**
     * True if it's a hidden corner or if it's a visible corner covered by another card
     */
    private Boolean covered;

    /**
     * Contructor
     * @param symbol Symbol
     * @param covered Covered
     */
    @JsonCreator
    public Corner(
            @JsonProperty("symbol") Symbol symbol,
            @JsonProperty("covered") Boolean covered){
        this.symbol = symbol;
        this.covered = covered;
    }


    /**
     * Symbol getter
     * @return Symbol
     */
    public Symbol getSymbol(){
        return symbol;
    }

    /**
     * Checks if the corner is covered
     * @return true if it is covered, false if it isn't
     */
    public Boolean isCovered(){
        return covered;
    }

    /**
     * Covers the corner
     */
    public void cover(){
        covered = true;
    }
}
