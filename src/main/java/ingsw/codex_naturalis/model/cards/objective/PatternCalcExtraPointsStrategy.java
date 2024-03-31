package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

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
     * verifies the pattern for each player associated to the card
     */
    @Override
    public void run() {
        for(int i=0; i< playerAreas.size(); i++){
            checkPatternOnPlayer(i);
        }
    }

    /**
     * Method to check for patterns in the current player area
     * @param i player index
     */
    private void checkPatternOnPlayer(int i){

        HashMap<List<Integer>,Boolean> markedArea = playerAreas.get(i).getAreaToMark();
        int successes = 0;
        boolean success;

        for(int yArea=playerAreas.get(i).getMaxY(); yArea>=playerAreas.get(i).getMinY()+getPatternHeight()-1; yArea--){
            for(int xArea=playerAreas.get(i).getMinX(); xArea<=playerAreas.get(i).getMaxX()-getPatternWidth()+1; xArea++){
                success = checkPatternOnCoordinates(xArea, yArea, i, markedArea);
                if(success){
                    successes += 1;
                    markArea(markedArea, xArea, yArea);
                }
            }
        }
        playerAreas.get(i).setExtraPoints(playerAreas.get(i).getExtraPoints() + patternObjectiveCard.getPoints() * successes);
    }

    /**
     * Method to check for a pattern on the current area coordinates of the current player area
     * @param xArea x
     * @param yArea y
     * @param i player index
     * @param markedArea marked area
     * @return true if the pattern is overlapping on the area, false if it isn't
     */
    private boolean checkPatternOnCoordinates(int xArea, int yArea, int i, HashMap<List<Integer>,Boolean> markedArea){
        for(int yPattern=patternObjectiveCard.getMaxY(),y=yArea; yPattern>=patternObjectiveCard.getMinY(); yPattern--, y--){
            for(int xPattern=patternObjectiveCard.getMinX(),x=xArea; xPattern<=patternObjectiveCard.getMaxX(); xPattern++, x++){
                if(patternObjectiveCard.getSymbolAt(xPattern, yPattern) != Symbol.EMPTY){
                    if(!playerAreas.get(i).containsCardOnCoordinates(x, y)){
                        return false;
                    }
                    else{
                        if(markedArea.get(new ArrayList<>(List.of(x, y)))  ||  patternObjectiveCard.getSymbolAt(xPattern, yPattern) != playerAreas.get(i).getCardOnCoordinates(x, y).getKingdom()){
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
        for(int yPattern=patternObjectiveCard.getMaxY(), y=yArea; yPattern>=patternObjectiveCard.getMinY(); yPattern--, y--){
            for(int xPattern=patternObjectiveCard.getMinX(), x=xArea; xPattern<=patternObjectiveCard.getMaxX(); xPattern++, x++){
                if(patternObjectiveCard.getSymbolAt(xPattern, yPattern) != Symbol.EMPTY){
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
        return patternObjectiveCard.getMaxX()+patternObjectiveCard.getMinX()+1;
    }

    /**
     * Method to get the pattern height
     * @return Pattern height
     */
    private int getPatternHeight(){
        return patternObjectiveCard.getMaxY()+patternObjectiveCard.getMinY()+1;
    }
}
