package ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
/**
 * StandardStrategy's class
 */
public class StandardStrategy implements Strategy {
    /**
     * This method counts the points given by the card
     * @param playerArea where to add the points
     * @param playedCard: the gold card
     */
    @Override
    public void gainPoints(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard){
        playerArea.setPoints(playerArea.getPoints() + playedCard.getPoints());
    }
    /**
     * This method prints to the user's screen the visual representation of the card
     * @param kingdom of the card
     * @param cardSide: the gold card
     * @return outString: the visual representation (implemented as a string) of the card
     */
    @Override
    public String handCardToString(Symbol kingdom, PointsGiverAndPointsGiverForCorner cardSide) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(cardSide, kingdom));
        if (cardSide.getTopLeftCorner().getSymbol() != Symbol.EMPTY && cardSide.getTopLeftCorner().getSymbol() != Symbol.COVERED){
            outString.replace(48, 49, DefaultValue.ANSI_RESET + cardSide.getPoints() + kingdom.getColor());
        }
        else{
            outString.replace(34, 35, DefaultValue.ANSI_RESET + cardSide.getPoints() + kingdom.getColor());
        }
        return cardSide.addRequirementsToHandCardString(outString.toString(), kingdom);
    }

}
