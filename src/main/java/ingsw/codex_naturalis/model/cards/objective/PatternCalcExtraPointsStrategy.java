package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;

import java.util.List;

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
    List<PlayerArea> playerAreas;

    /**
     * Constructor
     * @param patternObjectiveCard the specific Pattern ObjectiveCard
     */
    public PatternCalcExtraPointsStrategy(PatternObjectiveCard patternObjectiveCard, List<PlayerArea> playerAreas){
        this.patternObjectiveCard = patternObjectiveCard;
        this.playerAreas = playerAreas;
    }

    /**
     * verify the pattern
     */
    @Override
    public void run() {
    }
}
