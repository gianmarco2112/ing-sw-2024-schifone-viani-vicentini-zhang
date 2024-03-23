package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;

/**
 * ObjectiveCard's class
 * An objective card gives points for each objective achieved
 * The objective can be: to have a certain number of symbols on player's area or to compose a certain pattern with cards
 * The patterns can be two type: an L or a diagonal.
 * It is not necessary to have an attribute to represent the color of the PlayableSide, because we can know it from
 * the kingdom in which it belongs to.
 * Finally, ObjectiveCard has an interface that call the right algorithms at runtime.
 */
public abstract class ObjectiveCard {

    /**
     * attribute points represents the points that objective card gives for each objective achieved
     */
    private final int points;

    /**
     * attribute patternstrategy is used to call at runtime the right strategy to count extra points
     */
    protected CalcExtraPointsStrategy calcExtraPointsStrategy;


    /**
     * Constructor
     * @param points points of ObjectiveCard
     */
    public ObjectiveCard(int points){
        this.points = points;
    }


    public abstract void setCalcExtraPointsStrategy(PlayerArea playerArea);

    /**
     * Invoke at runtime the right algorithm depending on the type of card the player has.
     */
    public void execute(){
        calcExtraPointsStrategy.run();
    }

    /**
     * Getter
     * @return how many points the card gives for one objective achieved
     */
    public int getPoints() {
        return points;
    }
}
