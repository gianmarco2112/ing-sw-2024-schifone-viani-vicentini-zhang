package ingsw.codex_naturalis.model.cards.resource;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class ResourceCardBack extends PlayerAreaCard {

    private Symbol resource;


    public ResourceCardBack(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, Symbol resource){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points);
        this.resource = resource;
    }


    public Symbol getResource(){
        return resource;
    }

    @Override
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
        symbolsToAdd.add(getResource());
        return symbolsToAdd;
    }
}
