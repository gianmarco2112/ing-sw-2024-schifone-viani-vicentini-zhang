package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

import java.util.List;

public class CornerPointsStrategy implements PointsStrategy{
    @Override
    public void run(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard) {

        List<Integer> coordinates = playerArea.getCoordinatesOfCard(playedCard);
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

        playerArea.setPoints(playerArea.getPoints() + playedCard.getPoints() * cornersCovered);
    }
}
