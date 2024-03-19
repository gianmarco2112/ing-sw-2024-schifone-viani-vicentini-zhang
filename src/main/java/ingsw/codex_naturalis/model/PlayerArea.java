package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
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
     * The count of the symbols of the player
     */
    private HashMap<Symbol, Integer> numOfSymbols;

    /**
     * The points the player has
     */
    private int points;


    /**
     * Constructor
     */
    public PlayerArea(){
        this.area = new HashMap<>();
        this.numOfSymbols = new HashMap<>();
        this.points = 0;
    }


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
