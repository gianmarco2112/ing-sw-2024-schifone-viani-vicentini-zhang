package ingsw.codex_naturalis.model.cards.playerareacardstrategy;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;

public class CornerCalcPointsStrategy implements CalcPointsStrategy{

    private PlayerArea playerArea;
    private int x;
    private int y;

    private final int points;


    public CornerCalcPointsStrategy(PlayerArea playerArea, int x, int y, int points){
        this.playerArea = playerArea;
        this.x = x;
        this.y = y;
        this.points = points;
    }


    @Override
    public void run(){

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
