package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class PermanentResourcesGetSymbolsStrategy implements GetSymbolsStrategy{

    private PlayerArea playerArea;
    private List<Symbol> resources;


    public PermanentResourcesGetSymbolsStrategy(PlayerArea playerArea, List<Symbol> resources){
        this.playerArea = playerArea;
        this.resources = new ArrayList<>(resources);
    }


    @Override
    public void run(){
        for (Symbol sb : resources) {
            playerArea.incrNumOfSymbol(sb);
        }
    }
}
