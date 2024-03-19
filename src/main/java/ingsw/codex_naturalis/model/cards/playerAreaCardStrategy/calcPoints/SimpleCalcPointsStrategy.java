package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints;

import ingsw.codex_naturalis.model.PlayerArea;

public class SimpleCalcPointsStrategy implements CalcPointsStrategy{

    private PlayerArea playerArea;

    private final int points;


    public SimpleCalcPointsStrategy(PlayerArea playerArea, int points){
        this.playerArea = playerArea;
        this.points = points;
    }


    @Override
    public void run(){
        playerArea.setPoints(playerArea.getPoints()+points);
    }
}
