package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;

/**
 * Class PatternCalcExtraPointsStrategy verifies if the player has the pattern on his play area and gives him the corresponding total extra points
 */
public class PatternCalcExtraPointsStrategy implements CalcExtraPointsStrategy {

    /**
     * patternObjectiveCard contains the reference to the specific patternObjectiveCard
     */
    PatternObjectiveCard patternObjectiveCard;

    /**
     * playerArea contains the reference to the playerArea in order to verify pattern
     */
    PlayerArea playerArea;

    /**
     * Constructor
     * @param patternObjectiveCard the specific Pattern ObjectiveCard
     * @param playerArea reference to playerArea
     */
    public PatternCalcExtraPointsStrategy(PatternObjectiveCard patternObjectiveCard, PlayerArea playerArea){
        this.patternObjectiveCard = patternObjectiveCard;
        this.playerArea = playerArea;
    }

    /**
     * verify the pattern
     */
    @Override
    public void run() {
    }
}
