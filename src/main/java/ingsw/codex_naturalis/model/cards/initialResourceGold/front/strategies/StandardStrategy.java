package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.DefaultValue;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

public class StandardStrategy implements Strategy {

    @Override
    public void gainPoints(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard){
        playerArea.setPoints(playerArea.getPoints() + playedCard.getPoints());
    }

    @Override
    public String handCardToString(Symbol kingdom, PointsGiverAndPointsGiverForCorner cardSide) {
        StringBuilder outString = new StringBuilder(DefaultValue.getTUIHandCardSideTemplate(cardSide, kingdom));
        if (cardSide.getTopLeftCorner().getSymbol() != Symbol.EMPTY){
            outString.replace(39, 40, DefaultValue.ANSI_RESET + cardSide.getPoints() + kingdom.getColor());
        }
        else{
            outString.replace(25, 26, DefaultValue.ANSI_RESET + cardSide.getPoints() + kingdom.getColor());
        }
        return cardSide.addRequirementsToHandCardString(outString.toString(), kingdom);
    }

}
