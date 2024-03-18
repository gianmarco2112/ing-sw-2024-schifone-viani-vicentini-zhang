package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class PlayerArea {

    /**
     * This HashMap represents the player area with all the cards he has placed.
     * The key represents the coordinates, the value the side of the card played.
     */
    private HashMap<List<Integer>, PlayerAreaCard> area;

    private HashMap<Symbol, Integer> numOfSymbols;

    private int points;


    public PlayerArea(){
        this.area = new HashMap<>();
        this.numOfSymbols = new HashMap<>();
        this.points = 0;
    }


    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Boolean containsCardOnCoordinates(int x, int y){
        return area.containsKey(new ArrayList<>(List.of(x,y)));
    }

    public PlayerAreaCard getCardOnCoordinates(int x, int y){
        return area.get(new ArrayList<>(List.of(x,y)));
    }

    public Integer getNumOfSymbol(Symbol symbol){
        return numOfSymbols.get(symbol);
    }

    public void decrNumOfSymbol(Symbol symbol){
        numOfSymbols.replace(symbol, getNumOfSymbol(symbol)-1);
    }

    public void incrNumOfSymbol(Symbol symbol){
        numOfSymbols.replace(symbol, getNumOfSymbol(symbol)+1);
    }

    public void setCardOnCoordinates(PlayerAreaCard card, int x, int y){
        area.put(new ArrayList<>(List.of(x,y)), card);
    }
}
