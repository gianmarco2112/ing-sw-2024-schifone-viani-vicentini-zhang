package ingsw.codex_naturalis.server.model.cards.objective;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.*;

/**
 * class SymbolObjectiveCard
 */
public class SymbolsObjectiveCard extends ObjectiveCard{

    /**
     * contains the List of Symbol
     */
    private final Map<Symbol,Integer> symbolsForPoints;


    /**
     * Constructor
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
     * this algorithm counts the extra points to assign to the player
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
