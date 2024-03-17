package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;
import java.util.Set;

public class LPatternStrategy implements PatternStrategy{

    /**
     * this algorithm checks the L pattern depending on the central symbol
     *
     * @param area player area
     * @param card objective card
     * @param numOfSymbols counter of the symbols on the player's area
     * @return total extra points
     */
    @Override
    public int run(HashMap<int[], PlayerAreaCard> area, ObjectiveCard card, HashMap<Symbol, Integer> numOfSymbols) {
        int totalExtraPoints = 0;
        HashMap<int[],Boolean> alreadyCountedPositions= new HashMap<>();
        Set<int[]> keyset = area.keySet();
        for (int[] position : keyset){
            alreadyCountedPositions.put(position,false);
        }
        boolean stop=false;
        PlayerAreaCard c;

        if(card.getCentralSymbol()== Symbol.PLANT){
            for (int[] position : area.keySet()){
                c = area.get(position);
                if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                    stop=true;
                }
                if(!stop){
                    int [] bottomLeft = {position[0]-1, position[1]-1};
                    if (area.containsKey(bottomLeft)){
                        c = area.get(bottomLeft);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    int [] above = {position[0], position[1]+1};
                    if (area.containsKey(above)){
                        c = area.get(above);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    totalExtraPoints=totalExtraPoints+ card.getPoints();
                    int[]center = {position[0],position[1]};
                    alreadyCountedPositions.put(center,true);
                    int[]bottomRight = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(bottomRight,true);
                    int[]topLeft = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(topLeft,true);
                }
            }
        }

        if(card.getCentralSymbol()==Symbol.ANIMAL){
            for (int[] position : area.keySet()){
                c = area.get(position);
                if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                    stop=true;
                }
                if(!stop){
                    int [] topRight = {position[0]+1, position[1]+1};
                    if (area.containsKey(topRight)){
                        c = area.get(topRight);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    int [] under = {position[0], position[1]-1};
                    if (area.containsKey(under)){
                        c = area.get(under);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    totalExtraPoints=totalExtraPoints+ card.getPoints();
                    int[]center = {position[0],position[1]};
                    alreadyCountedPositions.put(center,true);
                    int[]bottomRight = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(bottomRight,true);
                    int[]topLeft = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(topLeft,true);
                }
            }
        }

        if(card.getCentralSymbol()==Symbol.INSECT){
            for (int[] position : area.keySet()){
                c = area.get(position);
                if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                    stop=true;
                }
                if(!stop){
                    int [] topLeft = {position[0]-1, position[1]+1};
                    if (area.containsKey(topLeft)){
                        c = area.get(topLeft);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    int [] under = {position[0], position[1]-1};
                    if (area.containsKey(under)){
                        c = area.get(under);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    totalExtraPoints=totalExtraPoints+ card.getPoints();
                    int[]center = {position[0],position[1]};
                    alreadyCountedPositions.put(center,true);
                    int[]bottomRight = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(bottomRight,true);
                    int[]topLeft = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(topLeft,true);
                }
            }
        }

        if(card.getCentralSymbol()==Symbol.FUNGI){
            for (int[] position : area.keySet()){
                c = area.get(position);
                if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                    stop=true;
                }
                if(!stop){
                    int [] bottomRight = {position[0]+1, position[1]-1};
                    if (area.containsKey(bottomRight)){
                        c = area.get(bottomRight);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    int [] above = {position[0], position[1]+1};
                    if (area.containsKey(above)){
                        c = area.get(above);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }
                if(!stop){
                    totalExtraPoints=totalExtraPoints+ card.getPoints();
                    int[]center = {position[0],position[1]};
                    alreadyCountedPositions.put(center,true);
                    int[]bottomRight = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(bottomRight,true);
                    int[]topLeft = {position[0]+1, position[1]-1};
                    alreadyCountedPositions.put(topLeft,true);
                }
            }
        }
        return totalExtraPoints;
    }
}
