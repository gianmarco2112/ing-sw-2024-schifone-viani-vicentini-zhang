package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class SymbolsCalcExtraPointsStrategy implements CalcExtraPointsStrategy {

    /**
     * Attribute SymbolObjectiveCard contains the reference to the specific ObjectiveCard that count symbols for extra points
     */
    private SymbolsObjectiveCard symbolsObjectiveCard;

    /**
     * Attribute PlayerArea: the algorithm access the Player's area and count Symbols required for extra points
     */
    private PlayerArea playerArea;


    /**
     * Constructor
     * @param symbolsObjectiveCard SymbolObjectCard
     * @param playerArea PlayerArea
     */
    public SymbolsCalcExtraPointsStrategy(SymbolsObjectiveCard symbolsObjectiveCard, PlayerArea playerArea){
        this.symbolsObjectiveCard = symbolsObjectiveCard;
        this.playerArea = playerArea;
    }


    /**
     * this algorithm counts the extra points to assign to the player
     */
    @Override
    public void run() {
        List<Integer> count = new ArrayList<>();
        Set<Symbol> symbols = symbolsObjectiveCard.getKeySet();
        for (Symbol sb : symbols){
            if (symbolsObjectiveCard.getNumOfSymbol(sb) <= playerArea.getNumOfSymbol(sb)){
                count.add(playerArea.getNumOfSymbol(sb) / symbolsObjectiveCard.getNumOfSymbol(sb));
            }
        }
        playerArea.setExtraPoints(playerArea.getExtraPoints() + symbolsObjectiveCard.getPoints() * Collections.min(count));
    }
}
