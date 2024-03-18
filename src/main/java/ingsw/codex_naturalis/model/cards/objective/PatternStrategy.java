package ingsw.codex_naturalis.model.cards.objective;

/**
 * This interface is implemented by the three concrete algorithms: LPatternStrategy, NoPatterStrategu and DiagonalPatternStrategy
 */
public interface PatternStrategy {
    /**
     * This method calculates the total extra points won by the player
     * @return total extra points
     */
    int run();
}
