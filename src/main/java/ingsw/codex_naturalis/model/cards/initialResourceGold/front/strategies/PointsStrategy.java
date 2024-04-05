package ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;

public interface PointsStrategy {

    void run(PlayerArea playerArea, PointsGiverAndPointsGiverForCorner playedCard);

}
