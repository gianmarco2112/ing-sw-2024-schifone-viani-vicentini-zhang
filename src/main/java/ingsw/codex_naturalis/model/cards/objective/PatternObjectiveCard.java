package ingsw.codex_naturalis.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * class PatternObjectiveCard
 */
public class PatternObjectiveCard extends ObjectiveCard {
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
     * HashMap contains the pattern for the Objective
     */
    private final HashMap<List<Integer>, Symbol> pattern;
    /**
     * constructor
     * @param points points that
     * @param positions positions of the pattern
     * @param kingdoms kingdom in the pattern's positions
     * @param maxX max row of the pattern
     * @param maxY max column of the pattern
     */
    @JsonCreator
    public PatternObjectiveCard(@JsonProperty("points") int points,
                                @JsonProperty("positions") List<List<Integer>> positions,
                                @JsonProperty("kingdoms") List<Symbol> kingdoms,
                                @JsonProperty("maxX") int maxX,
                                @JsonProperty("maxY") int maxY,
                                @JsonProperty("minX") int minX,
                                @JsonProperty("minY") int minY) {
        super(points);
        this.pattern=new HashMap<>();
        for(int i = minX; i <= maxX; i++){
            for(int j = minY; j <= maxY; j++){
                pattern.put(List.of(i,j),Symbol.EMPTY);
            }
        }
        for(int i=0; i<kingdoms.size();i++){
            this.pattern.put(positions.get(i),kingdoms.get(i));
        }
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
    }



    /**
     * verifies the pattern for each player associated to the card
     */
    public void gainPoints(List<PlayerArea> playerAreas){
        for(int i=0; i< playerAreas.size(); i++){
            checkPatternOnPlayer(playerAreas, i);
        }
    }

    /**
     * Method to check for patterns in the current player area
     * @param i player index
     */
    private void checkPatternOnPlayer(List<PlayerArea> playerAreas, int i){

        HashMap<List<Integer>,Boolean> markedArea = playerAreas.get(i).getAreaToMark();
        int successes = 0;
        boolean success;

        for(int yArea=playerAreas.get(i).getMaxY(); yArea>=playerAreas.get(i).getMinY()+getPatternHeight()-1; yArea--){
            for(int xArea=playerAreas.get(i).getMinX(); xArea<=playerAreas.get(i).getMaxX()-getPatternWidth()+1; xArea++){
                success = checkPatternOnCoordinates(playerAreas, xArea, yArea, i, markedArea);
                if(success){
                    successes += 1;
                    markArea(markedArea, xArea, yArea);
                }
            }
        }
        playerAreas.get(i).setExtraPoints(playerAreas.get(i).getExtraPoints() + getPoints() * successes);
    }

    /**
     * Method to check for a pattern on the current area coordinates of the current player area
     * @param xArea x
     * @param yArea y
     * @param i player index
     * @param markedArea marked area
     * @return true if the pattern is overlapping on the area, false if it isn't
     */
    private boolean checkPatternOnCoordinates(List<PlayerArea> playerAreas, int xArea, int yArea, int i, HashMap<List<Integer>,Boolean> markedArea){
        for(int yPattern=getMaxY(),y=yArea; yPattern>=getMinY(); yPattern--, y--){
            for(int xPattern=getMinX(),x=xArea; xPattern<=getMaxX(); xPattern++, x++){
                if(getSymbolAt(xPattern, yPattern) != Symbol.EMPTY){
                    if(!playerAreas.get(i).containsCardOnCoordinates(x, y)){
                        return false;
                    }
                    else{
                        if(markedArea.get(new ArrayList<>(List.of(x, y)))  ||  getSymbolAt(xPattern, yPattern) != playerAreas.get(i).getCardOnCoordinates(x, y).getKingdom()){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method that marks the found spots of the pattern in the player area in order to ignore them
     * in the next controls
     * @param areaToMark area to mark
     * @param xArea x
     * @param yArea y
     */
    private void markArea(HashMap<List<Integer>,Boolean> areaToMark, int xArea, int yArea){
        for(int yPattern=getMaxY(), y=yArea; yPattern>=getMinY(); yPattern--, y--){
            for(int xPattern=getMinX(), x=xArea; xPattern<=getMaxX(); xPattern++, x++){
                if(getSymbolAt(xPattern, yPattern) != Symbol.EMPTY){
                    areaToMark.replace(new ArrayList<>(List.of(x, y)),true);
                }
            }
        }
    }

    /**
     * Method to get the pattern width
     * @return Pattern width
     */
    private int getPatternWidth(){
        return getMaxX()+getMinX()+1;
    }

    /**
     * Method to get the pattern height
     * @return Pattern height
     */
    private int getPatternHeight(){
        return getMaxY()+getMinY()+1;
    }

    /**
     * Max row getter
     * @return Max row
     */
    private int getMaxX() {
        return maxX;
    }

    /**
     * Max column getter
     * @return max y
     */
    private int getMaxY() {
        return maxY;
    }

    /**
     * Min row getter
     * @return min x
     */
    private int getMinX() {
        return minX;
    }

    /**
     * Min column getter
     * @return min y
     */
    private int getMinY() {
        return minY;
    }
    private Symbol getSymbolAt(int x, int y){
        return pattern.get(new ArrayList<>(List.of(x,y)));
    }
}
