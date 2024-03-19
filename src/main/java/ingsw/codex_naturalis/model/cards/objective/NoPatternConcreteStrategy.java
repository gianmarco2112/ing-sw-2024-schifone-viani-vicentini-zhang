package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class NoPatternConcreteStrategy implements PatternStrategy{
    /**
     * Attribute SymbolObjectiveCard contains the reference to the specific ObjectiveCard that count symbols for extra points
     */
    private SymbolObjectiveCard symbolObjectiveCard;
    /**
     * Attribute PlayerArea: the algorithm access the Player's area and count Symbols required for extra points
     */
    private PlayerArea area;

    /**
     * Constructor
     * @param c SymbolObjectCard
     * @param a PlayerArea
     */
    public NoPatternConcreteStrategy(SymbolObjectiveCard c, PlayerArea a){
        symbolObjectiveCard=c;
        area=a;
    }
    /**
     * this algorithm counts the Symbol required for Objective
     * @return total extra points accumulated
     */
    @Override
    public int run() {
        int totalExtraPoints;
        int numOfSymbolRequired = symbolObjectiveCard.getNumOfSymbols();
        int numOfSymbolOnArea;
        Symbol symbolRequired;


        if((numOfSymbolRequired==2 && symbolObjectiveCard.getPoints()==2)||(numOfSymbolRequired==3 && symbolObjectiveCard.getPoints()==2)){
            symbolRequired = symbolObjectiveCard.getSymbolAt(1);

            numOfSymbolOnArea = area.getNumOfSymbol(symbolRequired);

            totalExtraPoints = (numOfSymbolOnArea/numOfSymbolRequired) * symbolObjectiveCard.getPoints();
        }else{
            int min=Integer.MAX_VALUE;

            if(area.getNumOfSymbol(Symbol.INKWELL) < min){
                min = area.getNumOfSymbol(Symbol.INKWELL);
            }

            if(area.getNumOfSymbol(Symbol.QUILL) < min){
                min = area.getNumOfSymbol(Symbol.QUILL);
            }

            if(area.getNumOfSymbol(Symbol.MANUSCRIPT) < min){
                min = area.getNumOfSymbol(Symbol.INKWELL);
            }

            totalExtraPoints=min * symbolObjectiveCard.getPoints();
        }
        return totalExtraPoints;
    }
}
