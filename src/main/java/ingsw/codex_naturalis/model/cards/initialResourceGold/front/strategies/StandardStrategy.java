package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

public class StandardStrategy implements Strategy {

    @Override
    public String handCardToString(Symbol kingdom) {
        return null;
    }

    @Override
    public void gainPoints(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard){
        playerArea.setPoints(playerArea.getPoints() + playedCard.getPoints());
    }

}
