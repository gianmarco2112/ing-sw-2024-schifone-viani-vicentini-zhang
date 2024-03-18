package ingsw.codex_naturalis.model.cards.playerareacardstrategy;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.*;
import ingsw.codex_naturalis.model.enumerations.*;

import java.util.*;

public class RequirementsIsPlayableStrategy implements IsPlayableStrategy{

    private PlayerArea playerArea;

    private HashMap<Symbol, Integer> requirements;


    public RequirementsIsPlayableStrategy(PlayerArea playerArea, HashMap<Symbol,Integer> requirements){
        this.playerArea = playerArea;
        this.requirements = requirements;
    }


    @Override
    public Boolean run(int x, int y){

        if (playerArea.containsCardOnCoordinates(x,y)){
            return false;
        }

        for(Symbol sb : requirements.keySet()){
            if(requirements.get(sb) > playerArea.getNumOfSymbol(sb)){
                return false;
            }
        }

        int atLeastACard = 4;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayerAreaCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (topLeftCard.getBottomRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayerAreaCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (topRightCard.getBottomLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayerAreaCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (bottomLeftCard.getTopRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayerAreaCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
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
}
