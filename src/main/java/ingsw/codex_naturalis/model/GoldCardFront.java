package ingsw.codex_naturalis.model;

import java.util.*;

public class GoldCardFront extends PlayerAreaCard{

    private HashMap<Symbol, Integer> requirements;

    public GoldCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points);
        this.requirements = new HashMap<>(requirements);
    }


    @Override
    public Boolean isPlayable(HashMap<int[], PlayerAreaCard> area, int x, int y, HashMap<Symbol, Integer> numOfSymbols){
        if (area.containsKey(new int[]{x, y})){
            return false;
        }

        for(Symbol sb : requirements.keySet()){
            if(requirements.get(sb) > numOfSymbols.get(sb)){
                return false;
            }
        }

        int[] topLeft = {x-1, y+1};
        int[] topRight = {x+1, y+1};
        int[] bottomLeft = {x-1, y-1};
        int[] bottomRight = {x+1, y-1};

        int atLeastACard = 4;

        if (area.containsKey(topLeft)){
            PlayerAreaCard topLeftCard = area.get(topLeft);
            if (topLeftCard.getBottomRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (area.containsKey(topRight)){
            PlayerAreaCard topRightCard = area.get(topRight);
            if (topRightCard.getBottomLeftCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (area.containsKey(bottomLeft)){
            PlayerAreaCard bottomLeftCard = area.get(bottomLeft);
            if (bottomLeftCard.getTopRightCorner().isCovered()){
                return false;
            }
        }
        else{
            atLeastACard--;
        }
        if (area.containsKey(bottomRight)){
            PlayerAreaCard bottomRightCard = area.get(bottomRight);
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
