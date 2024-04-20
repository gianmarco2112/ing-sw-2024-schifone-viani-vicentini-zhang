package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.DefaultValue;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

import java.util.List;

public class CornerStrategy implements Strategy {

    @Override
    public void gainPoints(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard) {

        List<Integer> coordinates = playerArea.getCoordinatesOfCard(playedCard);
        int x = coordinates.getFirst();
        int y = coordinates.getLast();
        int cornersCovered = 0;

        if (playerArea.containsCardOnCoordinates(x-1,y+1)){
            PlayableCard topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            if (topLeftCard.getBottomRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1,y+1)){
            PlayableCard topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            if (topRightCard.getBottomLeftCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x-1,y-1)){
            PlayableCard bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            if (bottomLeftCard.getTopRightCorner().isCovered()){
                cornersCovered++;
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1,y-1)){
            PlayableCard bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            if (bottomRightCard.getTopLeftCorner().isCovered()){
                cornersCovered++;
            }
        }

        playerArea.setPoints(playerArea.getPoints() + playedCard.getPoints() * cornersCovered);
    }
    @Override
    public String handCardToString(Symbol kingdom, PointsGiverAndPointsGiverForCorner cardSide) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(cardSide, kingdom));
        if (cardSide.getTopLeftCorner().getSymbol() != Symbol.EMPTY){
            outString.replace(47, 50, DefaultValue.ANSI_RESET + cardSide.getPoints() + kingdom.getColor() + "│" + DefaultValue.ANSI_RESET + "C" + kingdom.getColor());
        }
        else{
            outString.replace(33, 36, DefaultValue.ANSI_RESET + cardSide.getPoints() + kingdom.getColor() + "│" + DefaultValue.ANSI_RESET + "C" + kingdom.getColor());
        }
        return cardSide.addRequirementsToHandCardString(outString.toString(), kingdom);
    }
}
