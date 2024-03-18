package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;

/**
 * This interface is implemented by the three concrete algorithms: LPatternStrategy, NoPatterStrategu and DiagonalPatternStrategy
 */
public interface PatternStrategy {
    /**
     * This method calculates the total extra points won by the player
     * @param area PlayerArea
     * @param card in order to obtain the information of the card
     * @param numOfSymbols helps us to count the number of Symbol on the area
     * @return total extra points
     */
    int run(HashMap<int[], PlayerAreaCard> area, ObjectiveCard card, HashMap<Symbol, Integer> numOfSymbols);
}
