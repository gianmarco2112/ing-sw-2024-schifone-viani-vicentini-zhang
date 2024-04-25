package ingsw.codex_naturalis.model;


import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverForObject;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.CornerStrategy;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.StandardStrategy;
import ingsw.codex_naturalis.enumerations.PlayableCardType;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayableCardTest {

    Player player;
    Game game;
    PlayableCard initialCard;
    //GameplayController controller;
    //GameplayUI view;

    @BeforeEach
    void setup(){
        player = new Player("P1");
        game = new Game(0,4);
        game.addPlayer(player);
        //controller = new GameplayController(game,view);
        initialCard = new PlayableCard(
                "ITest",
                PlayableCardType.INITIAL,
                Symbol.EMPTY,
                new PlayableSide(
                        new Corner(Symbol.QUILL,false),
                        new Corner(Symbol.QUILL,false),
                        new Corner(Symbol.QUILL,false),
                        new Corner(Symbol.QUILL,false)),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.QUILL,Symbol.QUILL,Symbol.QUILL)));
    }
    private void setInitialCard(){
        player.getPlayerArea().setCardOnCoordinates(initialCard,0,0,"Test");
    }
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

    private PlayableCard plantResourceCard(){
        PlayableCard resourceCard;
        resourceCard = new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.PLANT,
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
                        List.of(Symbol.PLANT))
        );
        return(resourceCard);
    }
    private PlayableCard animalResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.ANIMAL,
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
                        List.of(Symbol.ANIMAL))
        );
        return(resourceCard);
    }

    private PlayableCard fungiResourceCard(){
        PlayableCard resourceCard;
        resourceCard = new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.FUNGI,
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
                        List.of(Symbol.FUNGI))
        );
        return(resourceCard);
    }

    private PlayableCard goldObjectCard(){
        PlayableCard goldCard;
        HashMap<Symbol,Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI,2);
        requirements.put(Symbol.ANIMAL,1);
        goldCard= new PlayableCard(
                "GTest",
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
                new PointsGiverForObject(
                        new Corner(Symbol.MANUSCRIPT,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1,
                        requirements,
                        Symbol.MANUSCRIPT
                        ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT))
        );
        return(goldCard);
    }

    private PlayableCard goldCornerCard(){
        PlayableCard goldCard;
        HashMap<Symbol,Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI,2);
        requirements.put(Symbol.ANIMAL,1);
        goldCard= new PlayableCard(
                "GTest",
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.MANUSCRIPT,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        requirements,
                        new CornerStrategy()
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT))
        );
        return(goldCard);
    }

    private PlayableCard goldStandardCard(){
        PlayableCard goldCard;
        HashMap<Symbol,Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI,2);
        requirements.put(Symbol.ANIMAL,1);
        goldCard= new PlayableCard(
                "GTest",
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.MANUSCRIPT,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        requirements,
                        new StandardStrategy()
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT))
        );
        return(goldCard);
    }

    @Test
    void testIsPlayable(){
        setInitialCard();
        PlayableCard card = insectResourceCard();
        assertTrue(card.isPlayable(player.getPlayerArea(),1,1));
        assertFalse(card.isPlayable(player.getPlayerArea(),3,3));
        assertFalse(card.isPlayable(player.getPlayerArea(),0,0));
    }
    @Test
    void testPlayWithoutCoordinates(){
        initialCard.play(player.getPlayerArea());
        assertEquals(3,player.getPlayerArea().getNumOfSymbol(Symbol.QUILL));
        for(int i = 0; i < 3; i++){
            player.getPlayerArea().decrNumOfSymbol(Symbol.QUILL);
        }
        initialCard.flip();
        initialCard.play(player.getPlayerArea());
        assertEquals(4,player.getPlayerArea().getNumOfSymbol(Symbol.QUILL));
    }
    @Test
    void testPlayWithCoordinates(){
        initialCard.flip();
        setInitialCard();

        PlayableCard card = insectResourceCard();
        card.play(player.getPlayerArea(),1,1);
        assertEquals(3,player.getPlayerArea().getNumOfSymbol(Symbol.QUILL));
        assertEquals(1,player.getPlayerArea().getNumOfSymbol(Symbol.INSECT));
    }
    @Test
    void simplePlayableCardTest(){
        PlayableCard card = insectResourceCard();
        assertEquals(PlayableCardType.RESOURCE,card.getPlayableCardType());
        assertEquals(Symbol.INSECT,card.getKingdom());
        assertEquals(card.getBack(),card.getCurrentPlayableSide());
        card.flip();
        assertEquals(card.getFront(),card.getCurrentPlayableSide());
        card.setCurrentPlayableSide(card.getBack());
        assertEquals(card.getBack(),card.getCurrentPlayableSide());
        card.flip();
        assertEquals(Symbol.EMPTY,card.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY,card.getBottomRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY,card.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY,card.getTopRightCorner().getSymbol());
        assertFalse(card.getBottomLeftCorner().isCovered());
        assertFalse(card.getBottomRightCorner().isCovered());
        assertFalse(card.getTopLeftCorner().isCovered());
        assertFalse(card.getTopRightCorner().isCovered());
    }

    @Test
    void goldObjectCardTest(){
        setInitialCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(fungiResourceCard());
        hand.add(fungiResourceCard());
        hand.add(animalResourceCard());
        hand.add(goldObjectCard());

        hand.getLast().flip();

        player.getPlayerArea().setCardOnCoordinates(hand.getFirst(),1,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(1),2,2,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(2),3,3,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.getLast(),4,4,"Test");

        assertEquals(1,player.getPlayerArea().getPoints());
    }

    @Test
    void goldCornerCardTest(){
        setInitialCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(fungiResourceCard());
        hand.add(fungiResourceCard());
        hand.add(animalResourceCard());

        hand.add(insectResourceCard());
        hand.add(insectResourceCard());
        hand.add(insectResourceCard());

        hand.add(goldCornerCard());

        hand.getLast().flip();

        player.getPlayerArea().setCardOnCoordinates(hand.getFirst(),1,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(1),2,0,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(2),3,-1,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(3),2,-2,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(4),-1,-1,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(5),0,-2,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.getLast(),1,-1,"Test");

        assertEquals(8,player.getPlayerArea().getPoints());
    }

    @Test
    void goldStandardCardTest(){
        setInitialCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(fungiResourceCard());
        hand.add(fungiResourceCard());
        hand.add(animalResourceCard());
        hand.add(goldStandardCard());

        hand.getLast().flip();

        player.getPlayerArea().setCardOnCoordinates(hand.getFirst(),1,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(1),2,0,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.get(2),3,1,"Test");
        player.getPlayerArea().setCardOnCoordinates(hand.getLast(),1,-1,"Test");

        assertEquals(2,player.getPlayerArea().getPoints());
    }

}
