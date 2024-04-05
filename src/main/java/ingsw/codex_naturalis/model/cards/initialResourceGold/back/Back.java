package ingsw.codex_naturalis.model.cards.initialResourceGold.back;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.List;

public class Back extends PlayableSide {
    
    private final List<Symbol> permanentResources;
    
    //----------------------------------------------------------------------------------
    public Back(Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, List<Symbol> permanentResources){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.permanentResources = permanentResources;
    }

    //----------------------------------------------------------------------------------

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    protected void gainSymbols(PlayerArea playerArea){
        for (Symbol sb : permanentResources) {
            playerArea.incrNumOfSymbol(sb);
        }
    }
}
