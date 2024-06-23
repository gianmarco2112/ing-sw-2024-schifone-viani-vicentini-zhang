package ingsw.codex_naturalis.server.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.*;

/**
 * SymbolObjectiveCard's class
 */
public class SymbolsObjectiveCard extends ObjectiveCard{

    /**
     * Contains the List of Symbol
     */
    private final Map<Symbol,Integer> symbolsForPoints;


    /**
     * Symbol objective card's constructor
     * @param cardID : the ID of the card
     * @param symbolsForPoints List of the Symbol for extra Points
     * @param points extra points giver by the card
     */
    @JsonCreator
    public SymbolsObjectiveCard(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("points") int points,
            @JsonProperty("symbolsForPoints") HashMap<Symbol,Integer> symbolsForPoints
            ){
        super(cardID, points);
        this.symbolsForPoints = symbolsForPoints;
    }


    /**
     * This method counts the extra points to give to the players
     * @param playerAreas : list of the playerAreas of all the players in the game
     */
    public void gainPoints(List<PlayerArea> playerAreas){
        for (PlayerArea playerArea : playerAreas) {
            List<Integer> count = new ArrayList<>();
            Set<Symbol> symbols = getKeySet();
            for (Symbol sb : symbols) {
                if (getNumOfSymbol(sb) <= playerArea.getNumOfSymbol(sb)) {
                    count.add(playerArea.getNumOfSymbol(sb) / getNumOfSymbol(sb));
                }
            }
            if(count.isEmpty()){
                count.add(0);
            }
            playerArea.setExtraPoints(playerArea.getExtraPoints() + getPoints() * Collections.min(count));
        }
    }
    /**
     * This method prints to the user's screen the visual representation of the card
     * @return outString: the visual representation (implemented as a string) of the card
     */
    @Override
    public String cardToString() {
        // bc is "border color"
        String bc = DefaultValue.GoldColor;
        int numOfSymbolsToWrite = getNumOfSymbols();
        String outString = bc + "╭───────────╮\n";
        outString = outString + "│     "  + getPoints()
                + "p    │\n" + "│           │\n" + "│   " + DefaultValue.ANSI_RESET;

        if (numOfSymbolsToWrite == 2){
            outString = outString + " ";
        }
        for (Map.Entry<Symbol, Integer> set: symbolsForPoints.entrySet()) {
            for (int i = 0; i < set.getValue(); i++) {
                    outString = outString + set.getKey().getColoredChar() + " ";
            }
        }
        if (numOfSymbolsToWrite == 2){
            outString = outString + " ";
        }

        outString = outString + bc + "  │\n│           │\n" + bc + "╰───────────╯" + DefaultValue.ANSI_RESET;

        return outString;
    }

    /**
     * Method to get the number of symbols to write for cardToString()
     * @return sum
     */
    private int getNumOfSymbols(){
        int sum = 0;

        for (Map.Entry<Symbol, Integer> set: symbolsForPoints.entrySet()) {
            sum = sum + set.getValue();
        }
        return sum;
    }

    /**
     * Method to get the count of a symbol from the HashMap
     * @param symbol the symbol that needs to be counted
     * @return the count of the symbol
     */
    private Integer getNumOfSymbol(Symbol symbol){
        return symbolsForPoints.get(symbol);
    }

    /**
     * Returns a copy of the key set of the HashMap
     * @return the set
     */
    private Set<Symbol> getKeySet() {
        return symbolsForPoints.keySet();
    }


}
