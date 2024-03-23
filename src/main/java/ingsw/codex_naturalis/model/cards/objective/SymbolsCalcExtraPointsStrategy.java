package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class SymbolsCalcExtraPointsStrategy implements CalcExtraPointsStrategy {

    /**
     * Attribute SymbolObjectiveCard contains the reference to the specific ObjectiveCard that count symbols for extra points
     */
    private final SymbolsObjectiveCard symbolsObjectiveCard;

    /**
     * Attribute PlayerArea: the algorithm access the Player's area and count Symbols required for extra points
     */
    private final List<PlayerArea> playerAreas;


    /**
     * Constructor
     * @param symbolsObjectiveCard SymbolObjectCard
     * @param playerAreas PlayerArea
     */
    public SymbolsCalcExtraPointsStrategy(SymbolsObjectiveCard symbolsObjectiveCard, List<PlayerArea> playerAreas){
        this.symbolsObjectiveCard = symbolsObjectiveCard;
        this.playerAreas = playerAreas;
    }


    /**
     * this algorithm counts the extra points to assign to the player
     */
    @Override
    public void run() {
        for (PlayerArea playerArea : playerAreas) {
            List<Integer> count = new ArrayList<>();
            Set<Symbol> symbols = symbolsObjectiveCard.getKeySet();
            for (Symbol sb : symbols) {
                if (symbolsObjectiveCard.getNumOfSymbol(sb) <= playerArea.getNumOfSymbol(sb)) {
                    count.add(playerArea.getNumOfSymbol(sb) / symbolsObjectiveCard.getNumOfSymbol(sb));
                }
            }
            playerArea.setExtraPoints(playerArea.getExtraPoints() + symbolsObjectiveCard.getPoints() * Collections.min(count));
        }
    }
}
