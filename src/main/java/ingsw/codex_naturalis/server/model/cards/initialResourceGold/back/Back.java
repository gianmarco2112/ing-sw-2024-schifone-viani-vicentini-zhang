package ingsw.codex_naturalis.server.model.cards.initialResourceGold.back;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.List;
/**
 * Back side of the INITIAL,GOLD or RESOURCE card
 */
public class Back extends PlayableSide {
    /**
     * List of the central Symbols (called permanent Symbols) of the card
     */
    private final List<Symbol> permanentResources;
    /**
     * Back side's constructor
     * @param bottomRightCorner of the back side of the card
     * @param bottomLeftCorner of the back side of the card
     * @param topRightCorner of the back side of the card
     * @param topLeftCorner of the back side of the card
     * @param permanentResources : central symbol of the card
     */

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
    /**
     * This method updates the Symbol's counters of the Player with the Permanent Symbols of the card
     * @param playerArea of the player
     */
    @Override
    protected void gainSymbols(PlayerArea playerArea){
        for (Symbol sb : permanentResources) {
            playerArea.incrNumOfSymbol(sb);
        }
        if(getTopLeftCorner().getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(getTopLeftCorner().getSymbol());
        }
        if(getTopRightCorner().getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(getTopRightCorner().getSymbol());
        }
        if(getBottomLeftCorner().getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(getBottomLeftCorner().getSymbol());
        }
        if(getTopRightCorner().getSymbol() != Symbol.EMPTY){
            playerArea.incrNumOfSymbol(getBottomRightCorner().getSymbol());
        }
    }
    /**
     * To draw the cards in TUI (with the view used to draw the cards in player's hand)
     * @param kingdom : the central symbol of the card (used to know the color to use to draw the card)
     */
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
                if (getTopRightCorner().getSymbol() == Symbol.EMPTY && getTopLeftCorner().getSymbol() == Symbol.EMPTY){
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

    /**
     * To draw the cards in TUI (with the view used to draw the cards into the player's area)
     * @param kingdom : the central symbol of the card (used to know the color to use to draw the card)
     */
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
                    && (getBottomLeftCorner().getSymbol() == Symbol.EMPTY || getBottomLeftCorner().getSymbol() == Symbol.COVERED)
                    && (getBottomRightCorner().getSymbol() == Symbol.EMPTY || getBottomRightCorner().getSymbol() == Symbol.COVERED )){
                    outString.replace(52, 53, permanentResources.getLast().getColoredChar() + kingdom.getColor());
                    outString.replace(37, 38, permanentResources.get(1).getColoredChar() + kingdom.getColor());
                    outString.replace(22, 23, permanentResources.getFirst().getColoredChar() + kingdom.getColor());
                }
            }
        }
        else{
            outString.replace(37, 38, kingdom.getColoredChar() + kingdom.getColor());
        }
        return outString.toString();
    }
}
