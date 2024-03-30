package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.model.cards.HandPlayableCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCardBack;
import ingsw.codex_naturalis.model.cards.initial.InitialCardFront;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    CenterOfTable centerOfTable;
    InitialCard initialCard;
    @BeforeEach
    void setUp(){
        player = new Player("Test",Color.RED,1);
        centerOfTable = new CenterOfTable();
        player.setCenterOfTable(centerOfTable);
        initialCard = new InitialCard(new InitialCardFront(Symbol.EMPTY,new Corner(Symbol.EMPTY,false),
                                                                                    new Corner(Symbol.EMPTY,false),
                                                                                    new Corner(Symbol.EMPTY,false),
                                                                                    new Corner(Symbol.EMPTY,false)),
                                                  new InitialCardBack(Symbol.EMPTY, new Corner(Symbol.EMPTY,false),
                                                                                    new Corner(Symbol.EMPTY,false),
                                                                                    new Corner(Symbol.EMPTY,false),
                                                                                    new Corner(Symbol.EMPTY,false),
                                                                                    List.of(Symbol.EMPTY,Symbol.EMPTY,Symbol.EMPTY)));
        player.setInitialCard(initialCard);
        player.drawFromResourceCardsDeck();
        player.drawFromResourceCardsDeck();
        player.drawFromGoldCardsDeck();
    }

    @Test
    void testPlayerProperties(){
        assertEquals("Test",player.getNickname());
        assertEquals(Color.RED,player.getColor());
        assertEquals(centerOfTable,player.getCenterOfTable());
        assertEquals(initialCard,player.getInitialCard());
        assertEquals(1,player.getPlayerID());
        assertEquals(0,player.getSentMessages().size());
    }
    @Test
    void playInitialCard(){
        player.playInitialCard();
        PlayerArea playerArea = player.getPlayerArea();

        assertEquals(initialCard.getBack(),playerArea.getCardOnCoordinates(0,0));
    }
    @Test
    void testNotPlayableException() {
        playInitialCard();
        Exception exception;
        Exception exception1;
        //coordinate non valide
        exception = assertThrows(NotPlayableException.class,() -> player.playCard(player.getHand().getFirst(),10,10 ));
        assertEquals("This card is not playable here!",exception.getMessage());
        //non risorse sufficienti per poter piazzare la carta
        exception1 = assertThrows(NotPlayableException.class,() -> player.playCard(player.getHand().get(2),1,1 ));
        assertEquals("This card is not playable here!",exception1.getMessage());

    }

    @Test
    void testPlayCard(){
        playInitialCard();
        HandPlayableCard playableSide = player.getHand().getFirst();
        player.playCard(player.getHand().getFirst(),1,1);//attenzione, dopo aver giocato la carta essa viene rimossa dalla mano
        assertEquals(playableSide.getHandPlayableFront(),
                     player.getPlayerArea().getCardOnCoordinates(1,1));
    }

    /*@Test
    void drawFromResourceCardsDeck() {
        Player player = new Player("Bob", Color.RED,1);
        Player player1 = new Player("Bob",Color.RED,2);
        CenterOfTable centerOfTable = new CenterOfTable();
        player.setCenterOfTable(centerOfTable);
        Game game = new Game(1);
        game.addPlayer(player);
        game.dealInitialCard();
        *//*List<HandPlayableCard> hand = player.getList();
        assertTrue(hand.isEmpty());
        player.playInitialCard();



        player.drawFromResourceCardsDeck();
        assertFalse(hand.isEmpty());
        player.playCard(hand.getFirst(), 0, 0);
        assertTrue(hand.isEmpty());*//*
    }*/
}