package ingsw.codex_naturalis.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.enumerations.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class PatternObjectiveCard
 */
public class PatternObjectiveCard extends ObjectiveCard {

    private final Map<ExtremeCoordinate, Integer> extremeCoordinates;
  
    /**
     * HashMap contains the pattern for the Objective
     */
    private final Map<List<Integer>, Symbol> pattern;
    /**
     * constructor
     * @param points points that
     * @param positions positions of the pattern
     * @param kingdoms kingdom in the pattern's positions
     * @param maxX max row of the pattern
     * @param maxY max column of the pattern
     */
    @JsonCreator
    public PatternObjectiveCard(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("points") int points,
            @JsonProperty("positions") List<List<Integer>> positions,
            @JsonProperty("kingdoms") List<Symbol> kingdoms,
            @JsonProperty("maxX") int maxX,
            @JsonProperty("maxY") int maxY,
            @JsonProperty("minX") int minX,
            @JsonProperty("minY") int minY) {
        super(cardID, points);
        this.pattern = new HashMap<>();
        for(int i = minX; i <= maxX; i++){
            for(int j = minY; j <= maxY; j++){
                pattern.put(List.of(i,j),Symbol.EMPTY);
            }
        }
        for(int i=0; i<kingdoms.size();i++){
            this.pattern.put(positions.get(i),kingdoms.get(i));
        }
        extremeCoordinates = new HashMap<>();
        setExtremeCoordinate(ExtremeCoordinate.MAX_X, maxX);
        setExtremeCoordinate(ExtremeCoordinate.MAX_Y, maxY);
        setExtremeCoordinate(ExtremeCoordinate.MIN_X, minX);
        setExtremeCoordinate(ExtremeCoordinate.MIN_Y, minY);
    }

    private int getExtremeCoordinate(ExtremeCoordinate extremeCoordinate) {
        return extremeCoordinates.get(extremeCoordinate);
    }
    private void setExtremeCoordinate(ExtremeCoordinate extremeCoordinate, Integer value) {
        extremeCoordinates.put(extremeCoordinate, value);
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

        Map<List<Integer>,Boolean> markedArea = playerAreas.get(i).getAreaToMark();
        int successes = 0;
        boolean success;

        int playerAreaMaxY = playerAreas.get(i).getExtremeCoordinate(ExtremeCoordinate.MAX_Y);
        int playerAreaMinY = playerAreas.get(i).getExtremeCoordinate(ExtremeCoordinate.MIN_Y);
        int playerAreaMinX = playerAreas.get(i).getExtremeCoordinate(ExtremeCoordinate.MIN_X);
        int playerAreaMaxX = playerAreas.get(i).getExtremeCoordinate(ExtremeCoordinate.MAX_X);
        for(int yArea = playerAreaMaxY; yArea >= playerAreaMinY + getPatternHeight()-1; yArea--){
            for(int xArea = playerAreaMinX; xArea <= playerAreaMaxX - getPatternWidth()+1; xArea++){
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
    private boolean checkPatternOnCoordinates(List<PlayerArea> playerAreas, int xArea, int yArea, int i, Map<List<Integer>,Boolean> markedArea){
        for(int yPattern = getExtremeCoordinate(ExtremeCoordinate.MAX_Y),y = yArea; yPattern >= getExtremeCoordinate(ExtremeCoordinate.MIN_Y); yPattern--, y--){
            for(int xPattern = getExtremeCoordinate(ExtremeCoordinate.MIN_X),x = xArea; xPattern <= getExtremeCoordinate(ExtremeCoordinate.MAX_X); xPattern++, x++){
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
    private void markArea(Map<List<Integer>,Boolean> areaToMark, int xArea, int yArea){
        for(int yPattern = getExtremeCoordinate(ExtremeCoordinate.MAX_Y), y = yArea; yPattern >= getExtremeCoordinate(ExtremeCoordinate.MIN_Y); yPattern--, y--){
            for(int xPattern = getExtremeCoordinate(ExtremeCoordinate.MIN_X), x = xArea; xPattern <= getExtremeCoordinate(ExtremeCoordinate.MAX_X); xPattern++, x++){
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
        return getExtremeCoordinate(ExtremeCoordinate.MAX_X) + getExtremeCoordinate(ExtremeCoordinate.MIN_X)+1;
    }

    /**
     * Method to get the pattern height
     * @return Pattern height
     */
    private int getPatternHeight(){
        return getExtremeCoordinate(ExtremeCoordinate.MAX_Y) + getExtremeCoordinate(ExtremeCoordinate.MIN_Y)+1;
    }
    
    private Symbol getSymbolAt(int x, int y){
        return pattern.get(new ArrayList<>(List.of(x,y)));
    }

    @Override
    public String getDescription() {
        return null;
    }
}
