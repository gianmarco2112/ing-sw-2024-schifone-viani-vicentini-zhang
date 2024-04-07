package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

public class StandardPointsStrategy implements PointsStrategy{

    @Override
    public void run(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard){
        playerArea.setPoints(playerArea.getPoints() + playedCard.getPoints());
    }

}
