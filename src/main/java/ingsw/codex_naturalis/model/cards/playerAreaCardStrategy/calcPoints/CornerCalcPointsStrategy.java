package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.HandCardPlayerAreaCard;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

import java.util.List;

public class CornerCalcPointsStrategy implements CalcPointsStrategy{

    private PlayerArea playerArea;
    private HandCardPlayerAreaCard handCardPlayerAreaCard;

    private final int points;


    public CornerCalcPointsStrategy(PlayerArea playerArea, HandCardPlayerAreaCard handCardPlayerAreaCard, int points){
        this.playerArea = playerArea;
        this.handCardPlayerAreaCard = handCardPlayerAreaCard;
        this.points = points;
    }


    @Override
    public void run(){

        List<Integer> coordinates = playerArea.getCoordinatesOfCard(handCardPlayerAreaCard);
        int x = coordinates.getFirst();
        int y = coordinates.getLast();
        int cornersCovered = 0;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayerAreaCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (!topLeftCard.getBottomRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayerAreaCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (!topRightCard.getBottomLeftCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayerAreaCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (!bottomLeftCard.getTopRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayerAreaCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            if (!bottomRightCard.getTopLeftCorner().isCovered()){
                cornersCovered++;
            }
        }

        playerArea.setPoints(playerArea.getPoints() + points * cornersCovered);
    }
}
