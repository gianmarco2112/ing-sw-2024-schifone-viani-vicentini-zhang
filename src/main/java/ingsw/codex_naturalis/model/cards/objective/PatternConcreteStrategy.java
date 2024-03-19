package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;

/**
 * Class PatternConcreteStrategy verifies if the player has the pattern on his play area and gives him the corresponding total extra points
 */
public class PatternConcreteStrategy implements PatternStrategy{
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
     * @param c the specific Pattern ObjectiveCard
     * @param area reference to playerArea
     */
    public PatternConcreteStrategy(PatternObjectiveCard c, PlayerArea area){
        patternObjectiveCard=c;
        playerArea=area;
    }

    /**
     * verify the pattern
     * @return the total extra points accumulated
     */
    //da implementare ancora
    @Override
    public int run() {
        return 0;
    }
}
