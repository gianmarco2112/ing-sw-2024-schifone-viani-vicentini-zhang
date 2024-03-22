package ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.PlayableSide;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class SimpleCoverCornersStrategy implements CoverCornersStrategy{

    private PlayerArea playerArea;


    public SimpleCoverCornersStrategy(PlayerArea playerArea){
        this.playerArea = playerArea;
    }

    @Override
    public void run(int x, int y){

        if (playerArea.containsCardOnCoordinates(x-1, y+1)){
            PlayableSide topLeftCard = playerArea.getCardOnCoordinates(x-1,y+1);
            Corner bottomRightCorner = topLeftCard.getBottomRightCorner();
            bottomRightCorner.cover();
            if (bottomRightCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(bottomRightCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1, y+1)){
            PlayableSide topRightCard = playerArea.getCardOnCoordinates(x+1,y+1);
            Corner bottomLeftCorner = topRightCard.getBottomLeftCorner();
            bottomLeftCorner.cover();
            if (bottomLeftCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(bottomLeftCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x-1, y-1)){
            PlayableSide bottomLeftCard = playerArea.getCardOnCoordinates(x-1,y-1);
            Corner topRightCorner = bottomLeftCard.getTopRightCorner();
            topRightCorner.cover();
            if (topRightCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(topRightCorner.getSymbol());
            }
        }
        if (playerArea.containsCardOnCoordinates(x+1, y-1)){
            PlayableSide bottomRightCard = playerArea.getCardOnCoordinates(x+1,y-1);
            Corner topLeftCorner = bottomRightCard.getTopLeftCorner();
            topLeftCorner.cover();
            if (topLeftCorner.getSymbol() != Symbol.EMPTY){
                playerArea.decrNumOfSymbol(topLeftCorner.getSymbol());
            }
        }
    }
}
