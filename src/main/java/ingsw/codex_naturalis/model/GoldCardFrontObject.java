package ingsw.codex_naturalis.model;

import java.util.*;

public class GoldCardFrontObject extends GoldCardFront{

    private Symbol object;


    public GoldCardFrontObject(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements, Symbol object){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.object = object;
    }


    @Override
    public int getPoints(HashMap<int[], PlayerAreaCard> area, int x, int y, HashMap<Symbol, Integer> numOfSymbols){
        return points * numOfSymbols.get(object);
    }
}