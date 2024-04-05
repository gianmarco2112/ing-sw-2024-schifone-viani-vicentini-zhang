package ingsw.codex_naturalis.model.cards.initialResourceGold.front;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.PointsStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.Map;

public abstract class Needy extends PointsGiver{

    private final Map<Symbol, Integer> requirements;

    //-------------------------------------------------------------------------------------------
    public Needy(Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, Map<Symbol, Integer> requirements){
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
}
