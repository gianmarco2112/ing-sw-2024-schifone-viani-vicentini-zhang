package ingsw.codex_naturalis.model;

import java.util.*;

public class InitialCardBack extends PlayerAreaCard{

    private List<Symbol> resources;


    public InitialCardBack(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, List<Symbol> resources){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points);
        this.resources = new ArrayList<>(resources);
    }


    public List<Symbol> getResources(){
        return resources;
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
        for(Symbol sb : resources){
            symbolsToAdd.add(sb);
        }
        return symbolsToAdd;
    }
}
