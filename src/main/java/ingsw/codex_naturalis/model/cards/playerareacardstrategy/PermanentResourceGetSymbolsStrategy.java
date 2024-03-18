package ingsw.codex_naturalis.model.cards.playerareacardstrategy;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;

public class PermanentResourceGetSymbolsStrategy implements GetSymbolsStrategy{

    private PlayerArea playerArea;

    private final Symbol resource;


    public PermanentResourceGetSymbolsStrategy(PlayerArea playerArea, Symbol resource){
        this.playerArea = playerArea;
        this.resource = resource;
    }


    @Override
    public void run(){
        playerArea.incrNumOfSymbol(resource);
    }
}
