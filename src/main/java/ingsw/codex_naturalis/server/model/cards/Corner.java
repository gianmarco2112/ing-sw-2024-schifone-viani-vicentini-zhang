package ingsw.codex_naturalis.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.common.enumerations.Symbol;

/**
 * Corner's class: each card would have 4 corners
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
     * Corner's constructor
     * @param symbol Symbol: the symbol on the corner
     * @param covered Covered: boolean to know if the corner is hidden/visible or not
     */
    @JsonCreator
    public Corner(
            @JsonProperty("symbol") Symbol symbol,
            @JsonProperty("covered") Boolean covered){
        this.symbol = symbol;
        this.covered = covered;
    }

    /**
     * Getter of the Symbol on the corner
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
     * Covers the corner, setting the boolean Covered to True
     */
    public void cover(){
        covered = true;
    }
}
