package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

/**
 * The player area
 */
public class PlayerArea {

    /**
     * This HashMap represents the player area with all the cards he has placed.
     * The key represents the coordinates, the value the side of the card played.
     */
    private HashMap<List<Integer>, PlayerAreaCard> area;
    /**
     * area max row
     */
    private int maxRow;

    /**
     * area max column
     */
    private int maxColumn;

    /**
     * area min row
     */
    private int minRow;

    /**
     * area max column
     */
    private int minColumn;

    /**
     * The count of the symbols of the player
     */
    private HashMap<Symbol, Integer> numOfSymbols;

    /**
     * The player's objective card
     */
    private ObjectiveCard objectiveCard;

    /**
     * The points the player has
     */
    private int points;

    /**
     * The extra points from the objective cards
     */
    private int extraPoints;


    /**
     * Constructor
     */
    public PlayerArea(int maxRow, int maxColumn, int minRow, int minColumn){
        this.area = new HashMap<>();
        this.numOfSymbols = new HashMap<>();
        this.points = 0;
        this.maxRow = maxRow;
        this.maxColumn = maxColumn;
        this.minRow = minRow;
        this.minColumn = minColumn;
    }


    /**
     * Max row getter
     * @return Max row
     */
    public int getMaxRow() { return maxRow; }
    /**
     * Max column getter
     * @return
     */
    public int getMaxColumn() { return maxColumn; }
    /**
     * Min row getter
     * @return
     */
    public int getMinRow() { return minRow; }
    /**
     * Min column getter
     * @return
     */
    public int getMinColumn() { return minColumn; }

    /**
     * Max row setter
     * @param maxRow Max row
     */
    public void setMaxRow(int maxRow) { this.maxRow = maxRow; }
    /**
     * Max column setter
     * @param maxColumn Max column
     */
    public void setMaxColumn(int maxColumn) { this.maxColumn = maxColumn; }

    /**
     * Min row setter
     * @param minRow Min row
     */
    public void setMinRow(int minRow) { this.minRow = minRow; }

    /**
     * Min column setter
     * @param minColumn Min column
     */
    public void setMinColumn(int minColumn) { this.minColumn = minColumn; }

    /**
     * Points getter
     * @return Points
     */
    public int getPoints() { return points; }

    /**
     * Points setter
     * @param points to add to the player
     */
    public void setPoints(int points) { this.points = points; }

    /**
     * Objective card getter
     * @return Objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    /**
     * Objective card setter
     * @param objectiveCard Objective card
     */
    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     * Extra points getter
     * @return Extra points
     */
    public int getExtraPoints() {
        return extraPoints;
    }

    /**
     * Extra points setter
     * @param extraPoints Extra points to set
     */
    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    /**
     * Checks if there's a card on the coordinates given
     * @param x Coordinate x
     * @param y Coordinate y
     * @return true or false
     */
    public Boolean containsCardOnCoordinates(int x, int y){
        return area.containsKey(new ArrayList<>(List.of(x,y)));
    }

    /**
     * Returns the card placed on the given coordinates
     * @param x Coordinate x
     * @param y Coordinate y
     * @return the card
     */
    public PlayerAreaCard getCardOnCoordinates(int x, int y){
        return area.get(new ArrayList<>(List.of(x,y)));
    }

    /**
     * Returns the count of the given symbol
     * @param symbol symbol
     * @return the count
     */
    public Integer getNumOfSymbol(Symbol symbol){
        return numOfSymbols.get(symbol);
    }

    /**
     * Decreases by one the count of the given symbol
     * @param symbol
     */
    public void decrNumOfSymbol(Symbol symbol){
        numOfSymbols.replace(symbol, getNumOfSymbol(symbol)-1);
    }

    /**
     * Increases by one the count of the given symbol
     * @param symbol
     */
    public void incrNumOfSymbol(Symbol symbol){
        numOfSymbols.replace(symbol, getNumOfSymbol(symbol)+1);
    }

    /**
     * Places the given card on the given coordinates
     * @param card the card to play
     * @param x coordinate x
     * @param y coordinate y
     */
    public void setCardOnCoordinates(PlayerAreaCard card, int x, int y){
        area.put(new ArrayList<>(List.of(x,y)), card);
    }
}
