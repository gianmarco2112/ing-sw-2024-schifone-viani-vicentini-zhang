package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * class PatternObjectiveCard
 */
public class PatternObjectiveCard extends ObjectiveCard{

    /**
     * HashMap contains the pattern for the Objective
     */
    public HashMap<List<Integer>, Symbol> pattern;

    /**
     * pattern max row
     */
    private final int maxX;

    /**
     * pattern max column
     */
    private final int maxY;

    /**
     * pattern min row
     */
    private final int minX;

    /**
     * pattern max column
     */
    private final int minY;

    /**
     * constructor
     * @param points points that
     * @param pattern reference to the HashMap that contains the pattern
     * @param maxX max row of the pattern
     * @param maxY max column of the pattern
     */
    public PatternObjectiveCard(int points, List<List<Integer>> positions, List<Symbol> values, int maxX, int maxY, int minX, int minY){
        super(points);
        this.pattern =  new HashMap<>();
        for(int i = minX; i < maxX; i++){
            for(int j = minY; j < maxY; j++){
                pattern.put(List.of(i,j),Symbol.EMPTY);
            }
        }
        for(int i= 0; i< values.size();i++){
            this.pattern.put(positions.get(i),values.get(i));
        }
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
    }


    /**
     * Max row getter
     * @return Max row
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Max column getter
     * @return max y
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Min row getter
     * @return min x
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Min column getter
     * @return min y
     */
    public int getMinY() {
        return minY;
    }

    public Symbol getSymbolAt(int x, int y){
        return pattern.get(new ArrayList<>(List.of(x,y)));
    }

    @Override
    public void chosen(PlayerArea playerArea) {
        setCalcExtraPointsStrategy(new PatternCalcExtraPointsStrategy(this, new ArrayList<>(List.of(playerArea))));
    }
}
