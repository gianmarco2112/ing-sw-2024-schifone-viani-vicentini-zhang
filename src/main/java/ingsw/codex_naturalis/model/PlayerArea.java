package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.PlayableSide;
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
    private HashMap<List<Integer>, PlayableSide> area;
    /**
     * area max row
     */
    private int maxX;

    /**
     * area max column
     */
    private int maxY;

    /**
     * area min row
     */
    private int minX;

    /**
     * area max column
     */
    private int minY;

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
    public PlayerArea(){
        this.area = new HashMap<>();
        this.numOfSymbols = new HashMap<>();
        this.points = 0;
    }


    /**
     * Max row getter
     * @return Max row
     */
    public int getMaxX() { return maxX; }
    /**
     * Max column getter
     * @return
     */
    public int getMaxY() { return maxY; }
    /**
     * Min row getter
     * @return
     */
    public int getMinX() { return minX; }
    /**
     * Min column getter
     * @return
     */
    public int getMinY() { return minY; }

    /**
     * Max row setter
     * @param maxX Max row
     */
    public void setMaxX(int maxX) { this.maxX = maxX; }
    /**
     * Max column setter
     * @param maxY Max column
     */
    public void setMaxY(int maxY) { this.maxY = maxY; }

    /**
     * Min row setter
     * @param minX Min row
     */
    public void setMinX(int minX) { this.minX = minX; }

    /**
     * Min column setter
     * @param minY Min column
     */
    public void setMinY(int minY) { this.minY = minY; }

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
    public PlayableSide getCardOnCoordinates(int x, int y){
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
    public void setCardOnCoordinates(PlayableSide card, int x, int y){
        area.put(new ArrayList<>(List.of(x,y)), card);
    }

    public List<Integer> getCoordinatesOfCard(PlayableSide card){
        for (Map.Entry<List<Integer>, PlayableSide> entry : area.entrySet()) {
            if (entry.getValue() == card) {
                return entry.getKey();
            }
        }
        return new ArrayList<>();
    }

    public HashMap<List<Integer>,Boolean> getAreaToMark(){
        HashMap<List<Integer>, Boolean> areaToMark = new HashMap<>();
        for (List<Integer> key : area.keySet()) {
            areaToMark.put(key, false);
        }
        return areaToMark;
    }
}
