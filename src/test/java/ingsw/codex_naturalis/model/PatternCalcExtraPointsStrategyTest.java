package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.CenterOfTable;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Player;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCardBack;
import ingsw.codex_naturalis.model.cards.initial.InitialCardFront;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternCalcExtraPointsStrategyTest {
    Player player;
    CenterOfTable centerOfTable;
    InitialCard initialCard;
    ObjectiveCard patternObjectiveCard;
    @BeforeEach
    void setUp(){
        player = new Player("Test",Color.RED,1);
        centerOfTable = new CenterOfTable();
        player.setCenterOfTable(centerOfTable);
        initialCard = new InitialCard(
                new InitialCardFront(
                        Symbol.EMPTY,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false)),
                new InitialCardBack(
                        Symbol.EMPTY,
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
        player.getHand().getLast().showBack();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();

        player.playCard(player.getHand().get(0),1,1 );
        player.playCard(player.getHand().get(0),2,2 );
        player.playCard(player.getHand().get(0),3,3 );
    }
    private ObjectiveCard diagonalBottomLeftTopRightCard(){
        return new PatternObjectiveCard(2,List.of(List.of(0,0),List.of(1,1),List.of(2,2)),List.of(Symbol.INSECT,Symbol.INSECT,Symbol.INSECT),2,2,0,0);
    }
    private void diagonalTopLeftBottomRight(){
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();

        player.playCard(player.getHand().get(0),-1,1 );
        player.playCard(player.getHand().get(0),-2,2 );
        player.playCard(player.getHand().get(0),-3,3 );
    }
    private ObjectiveCard diagonalTopLeftBottomRightCard(){
        return new PatternObjectiveCard(2,List.of(List.of(0,2),List.of(1,1),List.of(2,0)),List.of(Symbol.INSECT,Symbol.INSECT,Symbol.INSECT),2,2,0,0);
    }
    @Test
    void diagonalTopLeftBottomRightPattern(){
        diagonalTopLeftBottomRight();
        patternObjectiveCard = diagonalTopLeftBottomRightCard();
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        assertEquals(2,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void diagonalBottomLeftTopRightPattern(){
        diagonalBottomLeftTopRight();
        patternObjectiveCard = diagonalBottomLeftTopRightCard();
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        assertEquals(2,player.getPlayerArea().getExtraPoints());
    }
    /*@Test
    void patternCalcExtraPointsStrategy(){
        Player player = new Player("Andrea", Color.BLUE,1);
        CenterOfTable centerOfTable = new CenterOfTable();
        Game game = new Game(1);

        game.addPlayer(player);

        game.dealInitialCard();
        player.playInitialCard();

        player.setCenterOfTable(centerOfTable);

        ResourceCard c1 = centerOfTable.removeFromResourceCardsDeck();
        ResourceCard c2 = centerOfTable.removeFromResourceCardsDeck();
        ResourceCard c3 = centerOfTable.removeFromResourceCardsDeck();

        c1.drawn(player.getPlayerArea());
        c2.drawn(player.getPlayerArea());
        c3.drawn(player.getPlayerArea());

        //player.playCard(c1, 1,-1);
        //player.playCard(c2,2,-2);
        //player.playCard(c3,3,-3);

        ObjectiveCard c = centerOfTable.removeFromObjectiveCardsDeck();
        //PatternObjectiveCard cx = (PatternObjectiveCard) c;
        //assertEquals(Symbol.FUNGI,cx.getSymbolAt(0,0));

        SymbolsObjectiveCard cx = (SymbolsObjectiveCard) c;
        //assertNull(c);
        assertEquals(2,cx.getNumOfSymbol(Symbol.MANUSCRIPT));
        assertEquals(2,cx.getPoints());

    }*/


}