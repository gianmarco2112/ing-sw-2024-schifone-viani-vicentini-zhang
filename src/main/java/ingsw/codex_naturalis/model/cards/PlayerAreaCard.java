package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public abstract class PlayerAreaCard {

    private Symbol kingdom;

    private Corner topLeftCorner;

    private Corner topRightCorner;

    private Corner bottomLeftCorner;

    private Corner bottomRightCorner;

    private final int points;

    public int getPoints() {
        return points;
    }

    public PlayerAreaCard(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points){
        this.kingdom = kingdom;
        this.topLeftCorner = topLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
        this.points = points;
    }


    public Corner getTopLeftCorner(){ return topLeftCorner; }

    public Corner getTopRightCorner() { return topRightCorner; }

    public Corner getBottomLeftCorner() { return bottomLeftCorner; }

    public Corner getBottomRightCorner() { return bottomRightCorner; }

    public int calcPoints(HashMap<int[], PlayerAreaCard> area, int x, int y, HashMap<Symbol, Integer> numOfSymbols){
        return points;
    }

    /**
     * This method is called when a player calls his method to play a card.
     * It verifies if the pointed PlayerAreaCard is placeable in the area on coordinates x and y.
     * It checks that there isn't a card already placed in those coordinates and that there's at least...
     * ...one card on which the pointed card will be placed.
     * It also checks that every corner the PlayableAreaCard is going to cover, is a visible corner.
     * @param area
     * @param x
     * @param y
     * @return
     */
    //da ridefinire per GoldCardFront
    public Boolean isPlayable(HashMap<int[], PlayerAreaCard> area, int x, int y, HashMap<Symbol, Integer> numOfSymbols){
        if (area.containsKey(new int[]{x, y})){
            return false;
        }

        int[] topLeft = {x-1, y+1};
        int[] topRight = {x+1, y+1};
        int[] bottomLeft = {x-1, y-1};
        int[] bottomRight = {x+1, y-1};

        int atLeastACard = 4;

        if (area.containsKey(topLeft)){
            PlayerAreaCard topLeftCard = area.get(topLeft);
            if (topLeftCard.getBottomRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (area.containsKey(topRight)){
            PlayerAreaCard topRightCard = area.get(topRight);
            if (topRightCard.getBottomLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (area.containsKey(bottomLeft)){
            PlayerAreaCard bottomLeftCard = area.get(bottomLeft);
            if (bottomLeftCard.getTopRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (area.containsKey(bottomRight)){
            PlayerAreaCard bottomRightCard = area.get(bottomRight);
            if (bottomRightCard.getTopLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if(atLeastACard < 1){
            return false;
        }
        return true;
    }

    /**
     * This method is called when a player calls his method to play a card.
     * It changes the attributes of the corners the card is going to cover in order to...
     * ...make them actually covered and to remove the symbols from the symbol count of the player.
     * @param area
     * @param x
     * @param y
     */
    public List<Symbol> coverCorners(HashMap<int[], PlayerAreaCard> area, int x, int y){

        int[] topLeft = {x-1, y+1};
        int[] topRight = {x+1, y+1};
        int[] bottomLeft = {x-1, y-1};
        int[] bottomRight = {x+1, y-1};

        List<Symbol> symbolsToRemove = new ArrayList<>();

        if (area.containsKey(topLeft)){
            PlayerAreaCard topLeftCard = area.get(topLeft);
            Corner bottomRightCorner = topLeftCard.getBottomRightCorner();
            bottomRightCorner.cover();
            if (bottomRightCorner.getSymbol() != Symbol.EMPTY){
                symbolsToRemove.add(bottomRightCorner.getSymbol());
            }
        }
        if (area.containsKey(topRight)){
            PlayerAreaCard topRightCard = area.get(topRight);
            Corner bottomLeftCorner = topRightCard.getBottomLeftCorner();
            bottomLeftCorner.cover();
            if (bottomLeftCorner.getSymbol() != Symbol.EMPTY){
                symbolsToRemove.add(bottomLeftCorner.getSymbol());
            }
        }
        if (area.containsKey(bottomLeft)){
            PlayerAreaCard bottomLeftCard = area.get(bottomLeft);
            Corner topRightCorner = bottomLeftCard.getTopRightCorner();
            topRightCorner.cover();
            if (topRightCorner.getSymbol() != Symbol.EMPTY){
                symbolsToRemove.add(topRightCorner.getSymbol());
            }
        }
        if (area.containsKey(bottomRight)){
            PlayerAreaCard bottomRightCard = area.get(bottomRight);
            Corner topLeftCorner = bottomRightCard.getTopLeftCorner();
            topLeftCorner.cover();
            if (topLeftCorner.getSymbol() != Symbol.EMPTY){
                symbolsToRemove.add(topLeftCorner.getSymbol());
            }
        }
        return symbolsToRemove;
    }

    /**
     * This method is called when a player calls his method to play a card.
     * It updates the symbol count of the player adding the symbols of the pointed PlayerAreaCard.
     * @return
     */
    //da ridefinire in GoldCardFront, ResourceCardFront
    public List<Symbol> getSymbols(){

        List<Symbol> symbolsToAdd = new ArrayList<>();

        if(getTopLeftCorner().getSymbol() != Symbol.EMPTY){
            symbolsToAdd.add(getTopLeftCorner().getSymbol());
        }
        if(getTopRightCorner().getSymbol() != Symbol.EMPTY){
            symbolsToAdd.add(getTopRightCorner().getSymbol());
        }
        if(getBottomLeftCorner().getSymbol() != Symbol.EMPTY){
            symbolsToAdd.add(getBottomLeftCorner().getSymbol());
        }
        if(getBottomRightCorner().getSymbol() != Symbol.EMPTY){
            symbolsToAdd.add(getBottomRightCorner().getSymbol());
        }
        return symbolsToAdd;
    }

    public Symbol getKingdom() {
        return kingdom;
    }
}
