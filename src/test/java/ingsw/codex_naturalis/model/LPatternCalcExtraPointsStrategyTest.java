package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandPlayableCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCardBack;
import ingsw.codex_naturalis.model.cards.initial.InitialCardFront;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCardBack;
import ingsw.codex_naturalis.model.cards.resource.ResourceCardFront;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LPatternCalcExtraPointsStrategyTest {
    Player player;
    CenterOfTable centerOfTable;
    InitialCard initialCard;
    ObjectiveCard patternObjectiveCard;
    @BeforeEach
    void setUp(){
        player = new Player("Test", Color.RED,1);
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
    private HandPlayableCard fungiResourceCard(){
        ResourceCard resourceCard = new ResourceCard(
                new ResourceCardFront(
                        Symbol.FUNGI,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        0),
                new ResourceCardBack(
                        Symbol.FUNGI,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        Symbol.FUNGI)
        );
        resourceCard.drawn(player.getPlayerArea());
        return(resourceCard);
    }
    private HandPlayableCard insectResourceCard(){
        ResourceCard resourceCard = new ResourceCard(
                new ResourceCardFront(
                        Symbol.INSECT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        0),
                new ResourceCardBack(
                        Symbol.INSECT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        Symbol.INSECT)
        );
        resourceCard.drawn(player.getPlayerArea());
        return(resourceCard);
    }
    private HandPlayableCard plantResourceCard(){
        ResourceCard resourceCard = new ResourceCard(
                new ResourceCardFront(
                        Symbol.PLANT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        0),
                new ResourceCardBack(
                        Symbol.PLANT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        Symbol.PLANT)
        );
        resourceCard.drawn(player.getPlayerArea());
        return(resourceCard);
    }
    private HandPlayableCard animalResourceCard(){
        ResourceCard resourceCard = new ResourceCard(
                new ResourceCardFront(
                        Symbol.ANIMAL,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        0),
                new ResourceCardBack(
                        Symbol.ANIMAL,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        Symbol.ANIMAL)
        );
        resourceCard.drawn(player.getPlayerArea());
        return(resourceCard);
    }
    private ObjectiveCard bottomLeftLObjectiveCard(){
        return (new PatternObjectiveCard(
                3,
                List.of(List.of(0,0),List.of(1,1),List.of(1,3)),
                List.of(Symbol.INSECT,Symbol.PLANT,Symbol.PLANT),
                1,
                3,
                0,
                0
        ));
    }
    private ObjectiveCard bottomRightLObjectiveCard(){
        return (new PatternObjectiveCard(
                3,
                List.of(List.of(1,0),List.of(0,1),List.of(0,3)),
                List.of(Symbol.PLANT,Symbol.FUNGI,Symbol.FUNGI),
                1,
                3,
                0,
                0));
    }
    private ObjectiveCard topLeftLObjectiveCard(){
        return (new PatternObjectiveCard(
                3,
                List.of(List.of(0,3),List.of(1,2),List.of(1,0)),
                List.of(Symbol.ANIMAL,Symbol.INSECT,Symbol.INSECT),
                1,
                3,
                0,
                0));
    }
    private ObjectiveCard topRightLObjectiveCard(){
        return (new PatternObjectiveCard(
                3,
                List.of(List.of(0,0),List.of(0,2),List.of(1,3)),
                List.of(Symbol.ANIMAL,Symbol.ANIMAL,Symbol.FUNGI),
                1,
                3,
                0,
                0));
    }
    private void bottomLeftL(){
        player.testGethand().add(insectResourceCard());
        player.testGethand().add(plantResourceCard());
        player.testGethand().add(plantResourceCard());
        player.testGethand().add(plantResourceCard());

        player.playCard(player.testGethand().getFirst(),1,1 );
        player.playCard(player.testGethand().getFirst(),2,2 );
        player.playCard(player.testGethand().getFirst(),1,3 );
        player.playCard(player.testGethand().getFirst(),2,4 );
    }
    private void bottomRightL(){
        player.testGethand().add(plantResourceCard());
        player.testGethand().add(fungiResourceCard());
        player.testGethand().add(fungiResourceCard());
        player.testGethand().add(fungiResourceCard());

        player.playCard(player.testGethand().getFirst(),-1,1 );
        player.playCard(player.testGethand().getFirst(),-2,2 );
        player.playCard(player.testGethand().getFirst(),-3,3 );
        player.playCard(player.testGethand().getFirst(),-2,4 );
    }
    private void topLeftL(){
        player.testGethand().add(animalResourceCard());
        player.testGethand().add(insectResourceCard());
        player.testGethand().add(insectResourceCard());
        player.testGethand().add(insectResourceCard());

        player.playCard(player.testGethand().getFirst(),1,-1 );
        player.playCard(player.testGethand().getFirst(),2,-2 );
        player.playCard(player.testGethand().getFirst(),1,-3 );
        player.playCard(player.testGethand().getFirst(),2,-4 );
    }
    private void topRightL(){
        player.testGethand().add(fungiResourceCard());
        player.testGethand().add(animalResourceCard());
        player.testGethand().add(animalResourceCard());
        player.testGethand().add(animalResourceCard());

        player.playCard(player.testGethand().getFirst(),-1,-1 );
        player.playCard(player.testGethand().getFirst(),-2,-2 );
        player.playCard(player.testGethand().getFirst(),-1,-3 );
        player.playCard(player.testGethand().getFirst(),-2,-4 );
    }
    @Test
    void bottomLeftLTest(){
        bottomLeftL();
        patternObjectiveCard = bottomLeftLObjectiveCard();
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        assertEquals(3,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void bottomRightLTest(){
        bottomRightL();
        patternObjectiveCard = bottomRightLObjectiveCard();
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        assertEquals(3,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void topLeftLTest(){
        topLeftL();
        patternObjectiveCard = topLeftLObjectiveCard();
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        assertEquals(3,player.getPlayerArea().getExtraPoints());
    }
    @Test
    void topRightLTest(){
        topRightL();
        patternObjectiveCard = topRightLObjectiveCard();
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        assertEquals(3,player.getPlayerArea().getExtraPoints());
    }
}
