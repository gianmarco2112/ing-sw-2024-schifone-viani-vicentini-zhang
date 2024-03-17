package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;

public class NoPatternStrategy implements PatternStrategy{
    /**
     * this algorithm counts the Symbol required for Objective
     *
     * @param area player area
     * @param card to obtain the information of the card
     * @param numOfSymbols counter for the Symbols
     * @return total extra points accumulated
     */
    @Override
    public int run(HashMap<int[], PlayerAreaCard> area, ObjectiveCard card, HashMap<Symbol, Integer> numOfSymbols) {
        int totalExtraPoints;
        int numOfSymbolRequired = card.getObjectForPoints().size();
        int numOfSymbolOnArea;
        Symbol symbolRequired;


        if((numOfSymbolRequired==2 && card.getPoints()==2)||(numOfSymbolRequired==3 && card.getPoints()==2)){
            symbolRequired = card.getObjectForPoints().get(1);

            numOfSymbolOnArea = numOfSymbols.get(symbolRequired);

            totalExtraPoints = (numOfSymbolOnArea/numOfSymbolRequired) * card.getPoints();
        }else{
            int min=Integer.MAX_VALUE;

            for(Symbol symbol : numOfSymbols.keySet()){
                if (symbol == Symbol.INKWELL || symbol == Symbol.QUILL || symbol == Symbol.MANUSCRIPT) {
                    if (numOfSymbols.get(symbol) < min) {
                        min = numOfSymbols.get(symbol);
                    }
                }
            }
            totalExtraPoints=min * card.getPoints();
        }
        return totalExtraPoints;
    }
}
