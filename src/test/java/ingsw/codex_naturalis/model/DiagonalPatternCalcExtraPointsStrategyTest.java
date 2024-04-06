package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalPatternCalcExtraPointsStrategyTest {
    Player player;
    CenterOfTable centerOfTable;
    PlayableCard initialCard;
    ObjectiveCard patternObjectiveCard;
    @BeforeEach
    void setUp(){
        player = new Player("Test",Color.RED,1);
        centerOfTable = new CenterOfTable();
        player.setCenterOfTable(centerOfTable);
        initialCard = new PlayableCard(
                PlayableCardType.INITIAL,
                Symbol.EMPTY,
                new PlayableSide(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false)),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.EMPTY,Symbol.EMPTY,Symbol.EMPTY)));
        player.setInitialCard(initialCard);
        player.playInitialCard();
    }
    private void diagonalBottomLeftTopRight(){
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();

        player.playCard(player.getHand().get(0),1,1 );
        player.playCard(player.getHand().get(0),2,2 );
        player.playCard(player.getHand().get(0),3,3 );
    }
    private ObjectiveCard diagonalBottomLeftTopRightCard(){
        return new PatternObjectiveCard(2,List.of(List.of(0,0),List.of(1,1),List.of(2,2)),List.of(Symbol.INSECT,Symbol.INSECT,Symbol.INSECT),2,2,0,0);
    }
    private void diagonalTopLeftBottomRight(){
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();

        player.playCard(player.getHand().get(0),-1,1 );
        player.playCard(player.getHand().get(0),-2,2 );
        player.playCard(player.getHand().get(0),-3,3 );
    }
    private void twoDiagonalTopLeftBottomRight(){
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();

        player.playCard(player.getHand().get(0),-1,1 );
        player.playCard(player.getHand().get(0),-2,2 );
        player.playCard(player.getHand().get(0),-3,3 );
        player.playCard(player.getHand().get(0),-4,4 );
        player.playCard(player.getHand().get(0),-5,5 );
        player.playCard(player.getHand().get(0),-6,6 );
    }
    private ObjectiveCard diagonalTopLeftBottomRightCard(){
        return new PatternObjectiveCard(2,List.of(List.of(0,2),List.of(1,1),List.of(2,0)),List.of(Symbol.INSECT,Symbol.INSECT,Symbol.INSECT),2,2,0,0);
    }
    @Test
    void diagonalTopLeftBottomRightPattern(){
        diagonalTopLeftBottomRight();
        patternObjectiveCard = diagonalTopLeftBottomRightCard();
        patternObjectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(2,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void diagonalBottomLeftTopRightPattern(){
        diagonalBottomLeftTopRight();
        patternObjectiveCard = diagonalBottomLeftTopRightCard();
        patternObjectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(2,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void twoDiagonalTopLeftBottomRightPattern(){
        twoDiagonalTopLeftBottomRight();
        patternObjectiveCard = diagonalTopLeftBottomRightCard();
        patternObjectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(4,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void mixedDiagonalPattern(){
        twoDiagonalTopLeftBottomRight();
        patternObjectiveCard = diagonalTopLeftBottomRightCard();
        patternObjectiveCard.gainPoints(List.of(player.getPlayerArea()));
        diagonalBottomLeftTopRight();
        patternObjectiveCard = diagonalBottomLeftTopRightCard();
        patternObjectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(6,player.getPlayerArea().getExtraPoints());
    }
}