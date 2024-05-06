package ingsw.codex_naturalis.server.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.DefaultValue;

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
     * HashMap that contains the pattern for the Objective
     */
    private final Map<List<Integer>, Symbol> pattern;
    /**
     * constructor
     * @param points points given by the pattern
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
    /**
     * Getter of the extreme coordinates
     */
    private int getExtremeCoordinate(ExtremeCoordinate extremeCoordinate) {
        return extremeCoordinates.get(extremeCoordinate);
    }
    /**
     * Setter of the extreme coordinates
     */
    private void setExtremeCoordinate(ExtremeCoordinate extremeCoordinate, Integer value) {
        extremeCoordinates.put(extremeCoordinate, value);
    }

    /**
     * verifies the pattern for each player associated to the card and gives the related points
     */
    public void gainPoints(List<PlayerArea> playerAreas){
        for(int i=0; i< playerAreas.size(); i++){
            checkPatternOnPlayer(playerAreas, i);
        }
    }
    /**
     * Method used by cardToString to print the cards
     * This method creates a copy of the pattern on the card that would be later manipulated
     * @return patternCopy
     */
    private Map<List<Integer>, Symbol> patternMutableAsCopy(){
        Map<List<Integer>, Symbol> patternCopy = new HashMap<List<Integer>, Symbol>();
        List<Integer> list = new ArrayList();
        for (Map.Entry<List<Integer>, Symbol> set: pattern.entrySet()) {
            list = new ArrayList<>();
            list.add(set.getKey().getFirst());
            list.add(set.getKey().getLast());
            patternCopy.put(list, set.getValue());
        }
        return patternCopy;
    }
    /**
     * This method prints to the user's screen the visual representation of the cards
     * (the pattern is always represented with a 3x3 matrix)
     * @return outString: the visual representation (implemented as a string) of the card
     *
     */
    @Override
    public String cardToString() {
        // bc is "border color"
        String bc = DefaultValue.GoldColor;
        Map<List<Integer>, Symbol> mappedPattern = patternMutableAsCopy();
        int unusedLine = getUnusedLineInPattern();
        String outString = bc + "╭───────────╮\n";
        outString = outString + "│     "  + getPoints() + "p    │\n" + DefaultValue.ANSI_RESET;

        if (unusedLine != -1){
            mappedPattern = removeLineFromPattern(mappedPattern, unusedLine);
        }
        if (patternIs3x2(mappedPattern)){
            mappedPattern = addEmptyColumn(mappedPattern);
        }
        if (patternIsMappableIn3x3(mappedPattern)) {
            mappedPattern = normalizeCoordinates(mappedPattern);
            for (int i = 2; i >= 0; i--)
            {
                outString = outString + bc + "│ " + DefaultValue.ANSI_RESET;
                for (int j = 0; j <= 2; j++) {
                    if (mappedPattern.get(List.of(j, i)) != Symbol.EMPTY) {
                        outString = outString + mappedPattern.get(List.of(j, i)).getColor();
                        if (mappedPattern.get(List.of(j, i + 1)) == Symbol.EMPTY ||
                                !mappedPattern.containsKey(List.of(j, i + 1)))
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
    /**
     * This method is invoked just for the L-type patterns
     * In the player's area this type of pattern requires a 3x4 matrix of cards
     * One of these lines is not part of the pattern but is necessary to link the
     * two cards of the same color. Depending on which specific pattern the card
     * represents, the unused line could be the second or the third
     * @return unusedLineCoordinate: an integer representing the unused line's number
     */
    private int getUnusedLineInPattern(){
        int unusedLineCoordinate = -1;
        boolean isLineUsed;

        for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_Y); i <= extremeCoordinates.get(ExtremeCoordinate.MAX_Y); i++) {
            isLineUsed = false;
            for (int j = extremeCoordinates.get(ExtremeCoordinate.MIN_X); j <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); j++) {
                if (pattern.get(List.of(j, i)) != Symbol.EMPTY){
                    isLineUsed = true;
                }
            }
            if (!isLineUsed){
                unusedLineCoordinate = i;
            }
        }
        return unusedLineCoordinate;
    }
    /**
     * This method checks whether the pattern is diagonal-type
     * (which means it is already mappable in a 3x3 matrix)
     * @return True if the pattern is diagonal-type, False otherwise
     */
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
                max_y = set.getKey().getLast();
            else if (set.getKey().getLast() < min_y)
                min_y = set.getKey().getLast();
        }
        if (max_y - min_y == 2 && max_x - min_x == 2)
            isMappalbe = true;
        return isMappalbe;
    }
    /**
     * This method checks whether the pattern is L-type
     * (which means it is mappable in a 3x2 matrix once eliminated the unused line)
     * @return True if the pattern is L-type, False otherwise
     */
    private boolean patternIs3x2(Map<List<Integer>, Symbol> patt){
        boolean is3x2 = false;
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
                max_y = set.getKey().getLast();
            else if (set.getKey().getLast() < min_y)
                min_y = set.getKey().getLast();
        }
        if (max_y - min_y == 2 && max_x - min_x == 1)
            is3x2 = true;
        return is3x2;
    }
    
    /**
     * This method is invoked just for the L-type patterns.
     * It removes the unused line, reducing the matrix from a 4x2 to a 3x2
     * @param unmappedPattern copy of this pattern
     * @param lineToRemove line not used in pattern [given by getUnusedLineInPattern()]
     * @return mappedPattern: the 4x2 L-type pattern converted into a 3x2 matrix
     */
    private Map<List<Integer>, Symbol> removeLineFromPattern(Map<List<Integer>, Symbol> unmappedPattern, int lineToRemove){
        Symbol symbolToCopy = Symbol.EMPTY;
        Map<List<Integer>, Symbol> mappedPattern = unmappedPattern;
        for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_Y); i <= extremeCoordinates.get(ExtremeCoordinate.MAX_Y); i++) {
            for (int j = extremeCoordinates.get(ExtremeCoordinate.MIN_X); j <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); j++) {
                if (i == lineToRemove) {
                    mappedPattern.remove(List.of(j, i));
                }
            }
        }
        for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_Y); i <= extremeCoordinates.get(ExtremeCoordinate.MAX_Y); i++) {
            for (int j = extremeCoordinates.get(ExtremeCoordinate.MIN_X); j <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); j++) {
                if (mappedPattern.containsKey(List.of(j, i)) && i > lineToRemove){
                    symbolToCopy = mappedPattern.get(List.of(j, i));
                    mappedPattern.remove(List.of(j, i));
                    mappedPattern.put(List.of(j, i-1), symbolToCopy);
                }
            }
        }
        return mappedPattern;
    }
    /**
     * This method is invoked just for the L-type patterns.
     * It would normalize the coordinates of the mappedPattern (the 3x2 copy of the L-type pattern):
     * after removing the unusedLine the coordinates of the pattern would not respect the numerical order
     * because they skip the number representing the removed line.
     * @return mappedPattern: the 3x2 pattern with the coordinates normalized
     */
    private Map<List<Integer>, Symbol> normalizeCoordinates(Map<List<Integer>, Symbol> unmappedPattern) {
        Map<List<Integer>, Symbol> mappedPattern = new HashMap<List<Integer>, Symbol>();
        int max_x = Integer.MIN_VALUE;
        int min_x = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        for (Map.Entry<List<Integer>, Symbol> set: unmappedPattern.entrySet()) {
            if (set.getKey().getFirst() > max_x)
                max_x = set.getKey().getFirst();
            else if (set.getKey().getFirst() < min_x)
                min_x = set.getKey().getFirst();

            if (set.getKey().getLast() > max_y)
                max_y = set.getKey().getLast();
            else if (set.getKey().getLast() < min_y)
                min_y = set.getKey().getLast();
        }

        for (int i = min_y; i <= max_y; i++) {
            for (int j = min_x; j <= max_x ; j++) {
                mappedPattern.put(List.of(j - min_x, i - min_y), unmappedPattern.get(List.of(j, i)));
            }
        }
        return mappedPattern;
    }
    /**
     * This method is invoked just for the L-type patterns.
     * It would add an empty column in order to create a 3x3 matrix from the 3x2
     * mappedPattern representing the L-type pattern
     * @return : a 3x3 version of the mappedPattern
     */
    private Map<List<Integer>, Symbol> addEmptyColumn(Map<List<Integer>, Symbol> unmappedPattern) {
        Map<List<Integer>, Symbol> mappedPattern = unmappedPattern;
        int cardsInColumn0 = 0;
        int cardsInColumn1 = 0;
        for (Map.Entry<List<Integer>, Symbol> set : mappedPattern.entrySet()) {
            if(set.getValue() != Symbol.EMPTY){
                if (set.getKey().getFirst() == extremeCoordinates.get(ExtremeCoordinate.MAX_X)){
                    cardsInColumn1++;
                }
                else if (set.getKey().getFirst() == extremeCoordinates.get(ExtremeCoordinate.MIN_X)){
                    cardsInColumn0++;
                }
            }
        }
        if (cardsInColumn1 > cardsInColumn0){
            mappedPattern.put(List.of(2, 0), Symbol.EMPTY);
            mappedPattern.put(List.of(2, 1), Symbol.EMPTY);
            mappedPattern.put(List.of(2, 2), Symbol.EMPTY);
        }
        else{
            mappedPattern.put(List.of(-1, 0), Symbol.EMPTY);
            mappedPattern.put(List.of(-1, 1), Symbol.EMPTY);
            mappedPattern.put(List.of(-1, 2), Symbol.EMPTY);
        }
        return mappedPattern;
    }

    /**
     * Method to check if (and how many times) the objective card's specific pattern is present
     * in the current player's area and to give to the current player the relative points
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
     * Method to check for a pattern on the current area coordinates of the current player's area
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
     * in the next controls (in order to avoid counting the same cards as part of different patterns)
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
     * Getter of the pattern's width
     * @return : a number representing the pattern's width
     */
    private int getPatternWidth(){
        return getExtremeCoordinate(ExtremeCoordinate.MAX_X) + getExtremeCoordinate(ExtremeCoordinate.MIN_X)+1;
    }

    /**
     * Getter of the pattern's height
     * @return : a number representing the pattern's height
     */
    private int getPatternHeight(){
        return getExtremeCoordinate(ExtremeCoordinate.MAX_Y) + getExtremeCoordinate(ExtremeCoordinate.MIN_Y)+1;
    }
    /**
     * Getter of the central symbol (that represents the card's color)
     * of the card at the specified coordinates
     * @return : card's central symbol
     */
    private Symbol getSymbolAt(int x, int y){
        return pattern.get(new ArrayList<>(List.of(x,y)));
    }

}
