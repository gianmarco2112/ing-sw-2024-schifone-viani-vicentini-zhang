package ingsw.codex_naturalis.model.objectiveCards;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalPatternCalcExtraPointsStrategyTest {
    Player player;
    Game game;
    PlayableCard initialCard;
    ObjectiveCard patternObjectiveCard;
    @BeforeEach
    void setUp(){
        player = new Player("Test");
        game = new Game(0,4);
        game.addPlayer(player);
        initialCard = new PlayableCard(
                "I00",
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
    //----------CARD----------------------------------------------------
    private PlayableCard insectResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
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
                        List.of(Symbol.INSECT))
        );
        return(resourceCard);
    }
    //--------------------------------------------------------------------------
    //---------------OBJECTIVE CARDS--------------------------------------------
    private ObjectiveCard diagonalBottomLeftTopRightCard(){
        return new PatternObjectiveCard("OTest",2,List.of(List.of(0,0),List.of(1,1),List.of(2,2)),List.of(Symbol.INSECT,Symbol.INSECT,Symbol.INSECT),2,2,0,0);
    }
    private ObjectiveCard diagonalTopLeftBottomRightCard(){
        return new PatternObjectiveCard("OTest",2,List.of(List.of(0,2),List.of(1,1),List.of(2,0)),List.of(Symbol.INSECT,Symbol.INSECT,Symbol.INSECT),2,2,0,0);
    }
    //--------------------------------------------------------------------------
    //---------------COMPOSE PLAYER AREA----------------------------------------
    private void diagonalBottomLeftTopRight(){
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),1,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),2,2,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),3,3,"Test");
    }

    private void diagonalTopLeftBottomRight(){
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-1,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-2,2,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-3,3,"Test");
    }
    private void twoDiagonalTopLeftBottomRight(){
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-1,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-2,2,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-3,3,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-4,4,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-5,5,"Test");
        player.getPlayerArea().setCardOnCoordinates(insectResourceCard(),-6,6,"Test");
    }
    //--------------------------------------------------------------------------
    //------------------TEST ALGORITHM------------------------------------------
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