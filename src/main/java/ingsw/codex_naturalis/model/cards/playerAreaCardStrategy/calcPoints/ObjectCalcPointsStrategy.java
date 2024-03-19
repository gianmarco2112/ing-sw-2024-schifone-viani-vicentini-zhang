package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints;

import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class ObjectCalcPointsStrategy implements CalcPointsStrategy{

    private PlayerArea playerArea;

    private final Symbol object;
    private final int points;


    public ObjectCalcPointsStrategy(PlayerArea playerArea, Symbol object, int points){
        this.playerArea = playerArea;
        this.object = object;
        this.points = points;
    }


    @Override
    public void run() {
        playerArea.setPoints(playerArea.getPoints() + points * playerArea.getNumOfSymbol(object));
    }
}
