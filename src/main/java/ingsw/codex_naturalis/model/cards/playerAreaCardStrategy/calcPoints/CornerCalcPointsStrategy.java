package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.HandPlayableSide;
import ingsw.codex_naturalis.model.cards.PlayableSide;

import java.util.List;

public class CornerCalcPointsStrategy implements CalcPointsStrategy{

    private PlayerArea playerArea;
    private HandPlayableSide handPlayableSide;

    private final int points;


    public CornerCalcPointsStrategy(PlayerArea playerArea, HandPlayableSide handPlayableSide, int points){
        this.playerArea = playerArea;
        this.handPlayableSide = handPlayableSide;
        this.points = points;
    }


    @Override
    public void run(){

        List<Integer> coordinates = playerArea.getCoordinatesOfCard(handPlayableSide);
        int x = coordinates.getFirst();
        int y = coordinates.getLast();
        int cornersCovered = 0;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayableSide topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (!topLeftCard.getBottomRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayableSide topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (!topRightCard.getBottomLeftCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayableSide bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (!bottomLeftCard.getTopRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayableSide bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            if (!bottomRightCard.getTopLeftCorner().isCovered()){
                cornersCovered++;
            }
        }

        playerArea.setPoints(playerArea.getPoints() + points * cornersCovered);
    }
}
