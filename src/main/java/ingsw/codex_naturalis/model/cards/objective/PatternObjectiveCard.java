package ingsw.codex_naturalis.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.DefaultValue;

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

    @Override
    public String cardToString() {
        // bc is "border color"
        String bc = DefaultValue.GoldColor;
        Map<List<Integer>, Symbol> mappedPattern = pattern;
        int unusedLine = getUnusedLineInPattern();
        String outString = bc + "╭───────────╮\n";
        outString = outString + "│     "  + getPoints() + "p    │\n" + DefaultValue.ANSI_RESET;

        if (unusedLine != -1){
            mappedPattern = removeLineFromPattern(pattern, unusedLine);
        }
        if (patternIsMappableIn3x3(mappedPattern)) {
            mappedPattern = normalizeCoordinates(mappedPattern);
            for (int i = 0; i < 3; i++)
            {
                outString = outString + bc + "│ " + DefaultValue.ANSI_RESET;
                for (int j = 0; j < 3; j++) {
                    if (mappedPattern.containsKey(List.of(j, i))) {
                        outString = outString + mappedPattern.get(List.of(j, i)).getColor();
                        if (mappedPattern.containsKey(List.of(j, i + 1)))
                            outString = outString + "███";
                        else
                            outString = outString + "▇▇▇";
                        outString = outString + DefaultValue.ANSI_RESET;
                    }
                    else
                        outString = outString + "   ";
                }
                outString = outString + bc + " │\n" + DefaultValue.ANSI_RESET;
            }
        }
        else{
            outString = outString + bc + "│           │\n│    Err    │\n│           │\n" + DefaultValue.ANSI_RESET;
        }

        outString = outString + bc + "╰───────────╯" + DefaultValue.ANSI_RESET;

        return outString;
    }

    private int getUnusedLineInPattern(){
        int unusedLineCoordinate = -1;
        boolean isLineUsed;
        int i = extremeCoordinates.get(ExtremeCoordinate.MIN_Y);

        while (i != extremeCoordinates.get(ExtremeCoordinate.MAX_Y)){
            isLineUsed = false;
            for (Map.Entry<List<Integer>, Symbol> set: pattern.entrySet()) {
                if (set.getKey().getLast() == i) {
                    isLineUsed = true;
                }
            }
            if (!isLineUsed){
                unusedLineCoordinate = i;
            }
            i++;
        }
        return unusedLineCoordinate;
    }

    private boolean patternIsMappableIn3x3(Map<List<Integer>, Symbol> patt){
        boolean isMappalbe = false;
        int max_x = Integer.MIN_VALUE;
        int min_x = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        for (Map.Entry<List<Integer>, Symbol> set: patt.entrySet()) {
            if (set.getKey().getFirst() > max_x)
                max_x = set.getKey().getFirst();
            else if (set.getKey().getFirst() < min_x)
                min_x = set.getKey().getFirst();
            if (set.getKey().getLast() > max_y)
                max_y = set.getKey().getFirst();
            else if (set.getKey().getLast() < min_y)
                min_y = set.getKey().getFirst();
        }
        if (max_y - min_y == 2 && max_x - min_x== 2)
            isMappalbe = true;
        return isMappalbe;
    }
    
    /**
     * Method to map the pattern. Call this only if patterIsMappableIn3x3() is true
     * @param unmappedPattern copy of this.pattern
     * @param lineToRemove line not used in pattern
     */
    private Map<List<Integer>, Symbol> removeLineFromPattern(Map<List<Integer>, Symbol> unmappedPattern, int lineToRemove){
        Map<List<Integer>, Symbol> mappedPattern = unmappedPattern;

        int i = extremeCoordinates.get(ExtremeCoordinate.MAX_Y);
        while (i >= lineToRemove){
            for (Map.Entry<List<Integer>, Symbol> set: mappedPattern.entrySet()) {
                set.getKey().set(1, set.getKey().getLast() - 1);
            }
            i--;

        }
        return mappedPattern;
    }

    private Map<List<Integer>, Symbol> normalizeCoordinates(Map<List<Integer>, Symbol> unmappedPattern) {
        Map<List<Integer>, Symbol> mappedPattern = unmappedPattern;
        for (Map.Entry<List<Integer>, Symbol> set : mappedPattern.entrySet()) {
            set.getKey().set(0, set.getKey().getFirst() - extremeCoordinates.get(ExtremeCoordinate.MIN_X));
            set.getKey().set(1, set.getKey().getLast() - extremeCoordinates.get(ExtremeCoordinate.MIN_Y));
        }
        return mappedPattern;
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

}
