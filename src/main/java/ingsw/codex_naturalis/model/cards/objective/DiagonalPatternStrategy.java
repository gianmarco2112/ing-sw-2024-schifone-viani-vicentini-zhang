package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.util.HashMap;
import java.util.Set;

public class DiagonalPatternStrategy implements PatternStrategy{

    @Override
    public int run(HashMap<int[], PlayerAreaCard> area, ObjectiveCard card, HashMap<Symbol, Integer> numOfSymbols) {
        HashMap<int[],Boolean> alreadyCountedPositions= new HashMap<>();
        Set<int[]> keyset = area.keySet();
        for (int[] position : keyset){
            alreadyCountedPositions.put(position,false);
        }
        boolean stop;
        int totalExtraPoints = 0;
        PlayerAreaCard c;

        int diagonalCardsCounter;

        if(card.getCentralSymbol()== Symbol.PLANT){
            for (int[] position : area.keySet()){
                diagonalCardsCounter=0;
                stop=false;
                //scorro tutta la diagonale e tengo un contatore di carte sulla diagonale
                //mentre li scorro segno le posizioni già visitate

                c = area.get(position);
                if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                    stop=true;
                }else{
                    diagonalCardsCounter++;
                    alreadyCountedPositions.put(position,true);
                }

                int x = position[0];
                int y = position[1];
                while(!stop){
                    x = x-1;
                    y = y+1;
                    int [] topLeft = {x, y};
                    if (area.containsKey(topLeft)){
                        c = area.get(topLeft);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                stop=false;
                x = position[0];
                y = position[1];
                while(!stop){
                    x = x+1;
                    y = y-1;
                    int [] bottomRight = {x, y};
                    if (area.containsKey(bottomRight)){
                        c = area.get(bottomRight);
                        if(c.getKingdom()!=Symbol.PLANT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                totalExtraPoints=(diagonalCardsCounter/3)*card.getPoints();
            }
        }

        if(card.getCentralSymbol()== Symbol.INSECT){
            for (int[] position : area.keySet()){
                diagonalCardsCounter=0;
                stop=false;

                c = area.get(position);
                if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                    stop=true;
                }else{
                    diagonalCardsCounter++;
                    alreadyCountedPositions.put(position,true);
                }

                int x = position[0];
                int y = position[1];
                while(!stop){
                    x = x-1;
                    y = y+1;
                    int [] topLeft = {x, y};
                    if (area.containsKey(topLeft)){
                        c = area.get(topLeft);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                stop=false;
                x = position[0];
                y = position[1];
                while(!stop){
                    x = x+1;
                    y = y-1;
                    int [] bottomRight = {x, y};
                    if (area.containsKey(bottomRight)){
                        c = area.get(bottomRight);
                        if(c.getKingdom()!=Symbol.INSECT||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                totalExtraPoints=(diagonalCardsCounter/3)*card.getPoints();
            }
        }

        if(card.getCentralSymbol()== Symbol.FUNGI){
            for (int[] position : area.keySet()){
                diagonalCardsCounter=0;
                stop=false;

                c = area.get(position);
                if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                    stop=true;
                }else{
                    diagonalCardsCounter++;
                    alreadyCountedPositions.put(position,true);
                }

                int x = position[0];
                int y = position[1];
                while(!stop){
                    x = x+1;
                    y = y+1;
                    int [] topRight = {x, y};
                    if (area.containsKey(topRight)){
                        c = area.get(topRight);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                stop=false;
                x = position[0];
                y = position[1];
                while(!stop){
                    x = x-1;
                    y = y-1;
                    int [] bottomLeft = {x, y};
                    if (area.containsKey(bottomLeft)){
                        c = area.get(bottomLeft);
                        if(c.getKingdom()!=Symbol.FUNGI||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                totalExtraPoints=(diagonalCardsCounter/3)*card.getPoints();
            }
        }

        if(card.getCentralSymbol()== Symbol.ANIMAL){
            for (int[] position : area.keySet()){
                diagonalCardsCounter=0;
                stop=false;

                c = area.get(position);
                if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                    stop=true;
                }else{
                    diagonalCardsCounter++;
                    alreadyCountedPositions.put(position,true);
                }

                int x = position[0];
                int y = position[1];
                while(!stop){
                    x = x+1;
                    y = y+1;
                    int [] topRight = {x, y};
                    if (area.containsKey(topRight)){
                        c = area.get(topRight);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                stop=false;
                x = position[0];
                y = position[1];
                while(!stop){
                    x = x-1;
                    y = y-1;
                    int [] bottomLeft = {x, y};
                    if (area.containsKey(bottomLeft)){
                        c = area.get(bottomLeft);
                        if(c.getKingdom()!=Symbol.ANIMAL||alreadyCountedPositions.get(position)){
                            stop=true;
                        }else{
                            diagonalCardsCounter++;
                            alreadyCountedPositions.put(position,true);
                        }
                    }
                }

                totalExtraPoints=(diagonalCardsCounter/3)*card.getPoints();
            }
        }
        return totalExtraPoints;
    }
}
