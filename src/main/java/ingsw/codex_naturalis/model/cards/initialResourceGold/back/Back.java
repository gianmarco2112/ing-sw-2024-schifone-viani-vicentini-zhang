package ingsw.codex_naturalis.model.cards.initialResourceGold.back;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.DefaultValue;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.enumerations.Symbol;

import java.util.List;

public class Back extends PlayableSide {
    
    private final List<Symbol> permanentResources;
    
    //----------------------------------------------------------------------------------
    @JsonCreator
    public Back(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("permanentResources") List<Symbol> permanentResources){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
        this.permanentResources = permanentResources;
    }

    //----------------------------------------------------------------------------------

    @Override
    public String handCardToString(Symbol kingdom) {
        String outString = kingdom.getColor() +
        "╭──┬─────┬──╮\n│  │     │  │\n├──╯  " + kingdom.getColoredChar() + kingdom.getColor() +
                "  ╰──┤\n├──╮     ╭──┤\n│  │     │  │\n╰──┴─────┴──╯" + DefaultValue.ANSI_RESET;
        return outString;
    }

    @Override
    protected void gainSymbols(PlayerArea playerArea){
        for (Symbol sb : permanentResources) {
            playerArea.incrNumOfSymbol(sb);
        }
    }
    @Override
    public String playerAreaCardToString(Symbol kingdom){
        return null;
    }

}
