package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.*;
/**
 * Needy's class (to implements gold cards)
 */
public abstract class Needy extends PointsGiver{
    /**
     * List of the type and the number of symbols of that type that the player needs to play the card
     */
    private final Map<Symbol, Integer> requirements;

    //-------------------------------------------------------------------------------------------
    /**
     * Needy's constructor
     * @param topRightCorner of the card
     * @param topLeftCorner of the card
     * @param bottomLeftCorner of the card
     * @param bottomRightCorner of the card
     * @param requirements needed to play the card
     * @param points given by the card
     */
    @JsonCreator
    public Needy(
            @JsonProperty("topLeftCorner") Corner topLeftCorner,
            @JsonProperty("topRightCorner") Corner topRightCorner,
            @JsonProperty("bottomLeftCorner") Corner bottomLeftCorner,
            @JsonProperty("bottomRightCorner") Corner bottomRightCorner,
            @JsonProperty("points") int points,
            @JsonProperty("requirements") HashMap<Symbol, Integer> requirements){
        super(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points);
        this.requirements = requirements;
    }

    //----------------------------------------------------------------------------------------
    /**
     * Requirements' getter
     * @return requirements: list of symbol (each with his number) needed to play the card
     */
    public Map<Symbol, Integer> getRequirements(){
        return requirements;
    }
    /**
     * To know if the card is playable on the specified coordinates
     * @param playerArea of the player that wants to play the card
     * @param x the x coordinate where the player wants to play the card
     * @param y the y coordinate where the player wants to play the card
     * @return true is the card is playable on the specified coordinates, false otherwise
     */
    @Override
    public boolean isPlayable(PlayerArea playerArea, int x, int y){

        if (playerArea.containsCardOnCoordinates(x,y))
            return false;

        for(Symbol sb : requirements.keySet()){
            if(requirements.get(sb) > playerArea.getNumOfSymbol(sb)){
                return false;
            }
        }

        int atLeastACard = 4;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayableCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (topLeftCard.getBottomRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayableCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (topRightCard.getBottomLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayableCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (bottomLeftCard.getTopRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayableCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            if (bottomRightCard.getTopLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if(atLeastACard < 1){
            return false;
        }
        return true;
    }
    /**
     * This method adds the requirements to the user's screen the visual representation of the card
     * @param cardString the  visual representation of the card
     * @param kingdom of the card
     * @return outString: the visual representation (implemented as a string) of the card
     */
    // Requires a string formatted as in DefaultValue
    public String addRequirementsToHandCardString(String cardString, Symbol kingdom){
        StringBuilder lineBuilder = new StringBuilder();
        List<String> lines = Arrays.asList(cardString.split("\n"));
        if (this.getRequirements().size() == 1){
            // For the symbol
            lineBuilder = new StringBuilder(lines.get(3));
            lineBuilder.replace(11, 12, this.getRequirements().keySet().iterator().next().getColoredChar() + kingdom.getColor());
            lines.set(3, lineBuilder.toString());
            // For the value
            lineBuilder = new StringBuilder(lines.get(4));
            if (this.getBottomLeftCorner().isCovered() || this.getBottomLeftCorner().getSymbol() == Symbol.EMPTY){
                lineBuilder.replace(11, 12,  this.getRequirements().keySet().iterator().next().getColor() +
                        this.getRequirements().get(this.getRequirements().keySet().iterator().next()) + kingdom.getColor());
            }
            else{
                lineBuilder.replace(25, 26,  this.getRequirements().keySet().iterator().next().getColor() +
                        this.getRequirements().get(this.getRequirements().keySet().iterator().next()) + kingdom.getColor());
            }
            lines.set(4, lineBuilder.toString());

        }else if(this.getRequirements().size() == 2){
            // For the symbols
            lineBuilder = new StringBuilder(lines.get(3));
            Iterator<Symbol> iterator = this.getRequirements().keySet().iterator();
            lineBuilder.replace(10, 13,
                    iterator.next().getColoredChar() + kingdom.getColor() + "│" +
                        iterator.next().getColoredChar() + kingdom.getColor());
            lines.set(3, lineBuilder.toString());

            // For the values
            lineBuilder = new StringBuilder(lines.get(4));
            iterator = this.getRequirements().keySet().iterator();
            Symbol keyValue = iterator.next();

            if (this.getBottomLeftCorner().isCovered() || this.getBottomLeftCorner().getSymbol() == Symbol.EMPTY){
                lineBuilder.replace(10, 12,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor() + "│");
                keyValue = iterator.next();
                lineBuilder.replace(22, 23,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor());
            }
            else{
                lineBuilder.replace(24, 26,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor() + "│");
                keyValue = iterator.next();
                lineBuilder.replace(36, 37,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor());
            }
            lines.set(4, lineBuilder.toString());
        }
        return String.join("\n", lines);
    }
}
