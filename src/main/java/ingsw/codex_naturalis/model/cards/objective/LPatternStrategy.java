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
        boolean stop;
        PlayerAreaCard c;

        if(card.getCentralSymbol()== Symbol.PLANT){

            for (int[] position : area.keySet()){
                stop=false;
                c = area.get(position);
                if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                    stop=true;
                }

                //scendo in fondo all'ultima carta utile che ho per contare il pattern

                int x = position[0];
                int y = position[1];
                while(!stop){
                    y = y-1;
                    int [] under = {x, y};
                    if (area.containsKey(under)){
                        c = area.get(under);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }

                stop = false;
                while(!stop){
                    int [] center = {x, y};
                    if (area.containsKey(center)){
                        c = area.get(center);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }

                    int [] bottomLeft = {x-1, y};
                    if (area.containsKey(bottomLeft)){
                        c = area.get(bottomLeft);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                    y = y+1;
                    int [] up = {x,y};
                    if (area.containsKey(up)){
                        c = area.get(up);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                    y = y+1;
                    if(!stop){
                        totalExtraPoints= totalExtraPoints + card.getPoints();
                    }
                }
            }
        }

        if(card.getCentralSymbol()== Symbol.ANIMAL){

            for (int[] position : area.keySet()){
                stop=false;
                c = area.get(position);
                if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                    stop=true;
                }

                //scendo in fondo all'ultima carta utile che ho per contare il pattern

                int x = position[0];
                int y = position[1];
                while(!stop){
                    y = y-1;
                    int [] under = {x, y};
                    if (area.containsKey(under)){
                        c = area.get(under);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }

                stop = false;
                while(!stop){
                    int [] center = {x, y};
                    if (area.containsKey(center)){
                        c = area.get(center);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }

                    y=y+1;
                    int [] up = {x,y};
                    if (area.containsKey(up)){
                        c = area.get(up);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }

                    int [] topRight = {x+1, y};
                    if (area.containsKey(topRight)){
                        c = area.get(topRight);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                    y = y+1;

                    if(!stop){
                        totalExtraPoints= totalExtraPoints + card.getPoints();
                    }
                }
            }
        }

        if(card.getCentralSymbol()== Symbol.INSECT){

            for (int[] position : area.keySet()){
                stop=false;
                c = area.get(position);
                if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                    stop=true;
                }

                //scendo in fondo all'ultima carta utile che ho per contare il pattern

                int x = position[0];
                int y = position[1];
                while(!stop){
                    y = y-1;
                    int [] under = {x, y};
                    if (area.containsKey(under)){
                        c = area.get(under);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }

                stop = false;
                while(!stop){
                    int [] center = {x, y};
                    if (area.containsKey(center)){
                        c = area.get(center);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }

                    y=y+1;
                    int [] up = {x,y};
                    if (area.containsKey(up)){
                        c = area.get(up);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }

                    int [] topLeft = {x-1, y};
                    if (area.containsKey(topLeft)){
                        c = area.get(topLeft);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                    y = y+1;

                    if(!stop){
                        totalExtraPoints= totalExtraPoints + card.getPoints();
                    }
                }
            }
        }

        if(card.getCentralSymbol()== Symbol.FUNGI){

            for (int[] position : area.keySet()){
                stop=false;
                c = area.get(position);
                if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                    stop=true;
                }

                //scendo in fondo all'ultima carta utile che ho per contare il pattern

                int x = position[0];
                int y = position[1];
                while(!stop){
                    y = y-1;
                    int [] under = {x, y};
                    if (area.containsKey(under)){
                        c = area.get(under);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                }

                stop = false;
                while(!stop){
                    int [] center = {x, y};
                    if (area.containsKey(center)){
                        c = area.get(center);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }

                    int [] bottomRight = {x+1, y};
                    if (area.containsKey(bottomRight)){
                        c = area.get(bottomRight);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                    y = y+1;
                    int [] up = {x,y};
                    if (area.containsKey(up)){
                        c = area.get(up);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }
                    }
                    y = y+1;

                    if(!stop){
                        totalExtraPoints= totalExtraPoints + card.getPoints();
                    }
                }
            }
        }
        return totalExtraPoints;
    }
}
