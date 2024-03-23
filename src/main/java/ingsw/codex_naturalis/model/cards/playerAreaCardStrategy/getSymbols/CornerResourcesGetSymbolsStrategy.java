package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;


public class CornerResourcesGetSymbolsStrategy implements GetSymbolsStrategy{

    private final PlayerArea playerArea;

    private final Corner topLeftCorner;
    private final Corner topRightCorner;
    private final Corner bottomLeftCorner;
    private final Corner bottomRightCorner;


    public CornerResourcesGetSymbolsStrategy(PlayerArea playerArea, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner){
        this.playerArea = playerArea;
        this.topLeftCorner = topLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }


    @Override
    public void run(){
        if(topLeftCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(topLeftCorner.getSymbol());
        }
        if(topRightCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(topRightCorner.getSymbol());
        }
        if(bottomLeftCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(bottomLeftCorner.getSymbol());
        }
        if(bottomRightCorner.getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(bottomRightCorner.getSymbol());
        }
    }
}
