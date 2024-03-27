package ingsw.codex_naturalis.model.cards.objective;

/**
 * This interface is implemented by the three concrete algorithms: LPatternStrategy, NoPatterStrategu and DiagonalPatternStrategy
 */
public interface CalcExtraPointsStrategy {
    /**
     * This method calculates the total extra points won by the player
     */
    void run();
}
