package ingsw.codex_naturalis.model.cards.gold;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.*;

public class GoldCardFrontCorners extends GoldCardFront {

    public GoldCardFrontCorners(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points, HashMap<Symbol, Integer> requirements){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points, requirements);
    }


    @Override
    public int getPoints(HashMap<int[], PlayerAreaCard> area, int x, int y, HashMap<Symbol, Integer> numOfSymbols){

        int[] topLeft = {x-1, y+1};
        int[] topRight = {x+1, y+1};
        int[] bottomLeft = {x-1, y-1};
        int[] bottomRight = {x+1, y-1};

        int cornersCovered = 0;

        if (area.containsKey(topLeft)){
            PlayerAreaCard topLeftCard = area.get(topLeft);
            if (!topLeftCard.getBottomRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (area.containsKey(topRight)){
            PlayerAreaCard topRightCard = area.get(topRight);
            if (!topRightCard.getBottomLeftCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (area.containsKey(bottomLeft)){
            PlayerAreaCard bottomLeftCard = area.get(bottomLeft);
            if (!bottomLeftCard.getTopRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (area.containsKey(bottomRight)){
            PlayerAreaCard bottomRightCard = area.get(bottomRight);
            if (!bottomRightCard.getTopLeftCorner().isCovered()){
                cornersCovered++;
            }
        }
        return getPoints() * cornersCovered;
    }
}
