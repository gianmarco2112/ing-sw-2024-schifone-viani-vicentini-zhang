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
    protected void gainSymbols(PlayerArea playerArea){
        for (Symbol sb : permanentResources) {
            playerArea.incrNumOfSymbol(sb);
        }
    }

    @Override
    public String handCardToString(Symbol kingdom) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(this, kingdom));
        if (kingdom == Symbol.EMPTY) {
            if(permanentResources.size() == 1){
                if (getTopLeftCorner().getSymbol() == Symbol.EMPTY ^ getTopRightCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(71, 72, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
                else if (getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(57, 58, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            } else if (permanentResources.size() == 2) {
                if(getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(57, 58, permanentResources.getLast().getColoredChar() + kingdom.getColor());
                    outString.replace(34, 35, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            } else if (permanentResources.size() == 3){
                if (getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY
                        && getBottomLeftCorner().getSymbol() == Symbol.EMPTY && getBottomRightCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(80, 81, permanentResources.getLast().getColoredChar() + kingdom.getColor());
                    outString.replace(57, 58, permanentResources.get(1).getColoredChar() + kingdom.getColor());
                    outString.replace(34, 35, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            }
        }
        else{
            outString.replace(57, 58, kingdom.getColoredChar() + kingdom.getColor());
        }
        outString.insert(0, kingdom.getColor());
        outString.append(DefaultValue.ANSI_RESET);
        return outString.toString();
    }


    @Override
    public String playerAreaCardToString(Symbol kingdom){
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIPlayerAreaCardSideTemplate(this, kingdom));
        if (kingdom == Symbol.EMPTY) {
            if(permanentResources.size() == 1){
                if (getTopLeftCorner().getSymbol() == Symbol.EMPTY ^ getTopRightCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(51, 52, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
                else if (getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(37, 38, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            } else if (permanentResources.size() == 2) {
                if(getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(37, 38, permanentResources.getLast().getColoredChar() + kingdom.getColor());
                    outString.replace(22, 23, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            } else if (permanentResources.size() == 3){
                if (getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY
                    && getBottomLeftCorner().getSymbol() == Symbol.EMPTY && getBottomRightCorner().getSymbol() == Symbol.EMPTY){
                    outString.replace(52, 53, permanentResources.getLast().getColoredChar() + kingdom.getColor());
                    outString.replace(37, 38, permanentResources.get(1).getColoredChar() + kingdom.getColor());
                    outString.replace(22, 23, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            }
        }
        else{
            outString.replace(37, 38, kingdom.getColoredChar() + kingdom.getColor());
        }
        outString.insert(0, kingdom.getColor());
        outString.append(DefaultValue.ANSI_RESET);
        return outString.toString();
    }
}
