package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class GoldCardFrontObject extends GoldCardFront {

    private Symbol object;


    public GoldCardFrontObject(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements, Symbol object){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
        this.object = object;
    }


    @Override
    public int getPoints(HashMap<int[], PlayerAreaCard> area, int x, int y, HashMap<Symbol, Integer> numOfSymbols){
        return getPoints() * numOfSymbols.get(object);
    }
}