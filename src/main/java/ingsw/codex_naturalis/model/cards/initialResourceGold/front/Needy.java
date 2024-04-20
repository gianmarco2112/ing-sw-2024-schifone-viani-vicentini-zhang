package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.DefaultValue;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.enumerations.Symbol;

import java.util.*;

public abstract class Needy extends PointsGiver{

    private final Map<Symbol, Integer> requirements;

    //-------------------------------------------------------------------------------------------
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
    public Map<Symbol, Integer> getRequirements(){
        return requirements;
    }

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

    // Requires a string formatted as in DefaultValue
    public String addRequirementsToHandCardString(String cardString, Symbol kingdom){
        StringBuilder lineBuilder = new StringBuilder();
        List<String> lines = Arrays.asList(cardString.split("\n"));
        if (this.getRequirements().size() == 1){
            // For the symbol
            lineBuilder = new StringBuilder(lines.get(3));
            lineBuilder.replace(6, 7, this.getRequirements().keySet().iterator().next().getColoredChar() + kingdom.getColor());
            lines.set(3, lineBuilder.toString());
            // For the value
            lineBuilder = new StringBuilder(lines.get(4));
            if (this.getBottomLeftCorner().isCovered() || this.getBottomLeftCorner().getSymbol() == Symbol.EMPTY){
                lineBuilder.replace(6, 7,  this.getRequirements().keySet().iterator().next().getColor() +
                        this.getRequirements().get(this.getRequirements().keySet().iterator().next()) + kingdom.getColor());
            }
            else{
                lineBuilder.replace(20, 21,  this.getRequirements().keySet().iterator().next().getColor() +
                        this.getRequirements().get(this.getRequirements().keySet().iterator().next()) + kingdom.getColor());
            }
            lines.set(4, lineBuilder.toString());
        }else if(this.getRequirements().size() == 2){
            // For the symbols
            lineBuilder = new StringBuilder(lines.get(3));
            Iterator<Symbol> iterator = this.getRequirements().keySet().iterator();
            lineBuilder.replace(5, 8,
                    iterator.next().getColoredChar() + kingdom.getColor() + "│" +
                        iterator.next().getColoredChar() + kingdom.getColor());
            lines.set(3, lineBuilder.toString());

            // For the values
            lineBuilder = new StringBuilder(lines.get(4));
            iterator = this.getRequirements().keySet().iterator();
            Symbol keyValue = iterator.next();

            //this.getRequirements().entrySet().get(0);
            if (this.getBottomLeftCorner().isCovered() || this.getBottomLeftCorner().getSymbol() == Symbol.EMPTY){
                lineBuilder.replace(5, 7,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor() + "│");
                keyValue = iterator.next();
                lineBuilder.replace(17, 18,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor());
            }
            else{
                lineBuilder.replace(19, 21,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor() + "│");
                keyValue = iterator.next();
                lineBuilder.replace(31, 32,  keyValue.getColor() +
                        this.getRequirements().get(keyValue) + kingdom.getColor());
            }
            lines.set(4, lineBuilder.toString());
        }
        return String.join("\n", lines);
    }
}
