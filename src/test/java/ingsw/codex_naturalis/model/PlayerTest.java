package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.CardNotInHandException;
import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiver;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.StandardPointsStrategy;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * it tests player's properties (nickname, color, id, initial card ect) and actions (draw, play a card)
 */
class PlayerTest {
    Player player;
    CenterOfTable centerOfTable;
    PlayableCard initialCard;
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
        player.drawFromResourceCardsDeck();
        player.drawFromResourceCardsDeck();
        player.drawFromGoldCardsDeck();
    }

    /**
     * it Tests Player's properties
     */
    @Test
    void testPlayerProperties(){
        assertEquals("Test",player.getNickname());
        assertEquals(Color.RED,player.getColor());
        assertEquals(centerOfTable,player.getCenterOfTable());
        assertEquals(initialCard,player.getInitialCard());
        assertEquals(1,player.getPlayerID());
        assertEquals(0,player.getSentMessages().size());
    }

    /**
     * it Tests playInitialCard
     */
    @Test
    void playInitialCard(){
        player.playInitialCard();
        PlayerArea playerArea = player.getPlayerArea();

        assertEquals(initialCard,playerArea.getCardOnCoordinates(0,0));
    }

    /**
     * it Tests the moments when the card is not playable (not available coordinates or not enough symbols on playerArea)
     */
    @Test
    void testNotPlayableException() {
        playInitialCard();
        Exception exception;
        Exception exception1;
        //coordinate non valide
        exception = assertThrows(NotPlayableException.class,() -> player.playCard(player.getHand().getFirst(),0,1 ));
        assertEquals("This card is not playable here!",exception.getMessage());
        //non risorse sufficienti per poter piazzare la carta
        exception1 = assertThrows(NotPlayableException.class,() -> player.playCard(player.getHand().get(2),1,1 ));
        assertEquals("This card is not playable here!",exception1.getMessage());

    }

    /**
     * it Tests if the card is played correctly
     */
    @Test
    void testPlayCard(){
        playInitialCard();
        PlayableCard playableSide = player.getHand().getFirst();
        player.playCard(player.getHand().getFirst(),1,1);//attenzione, dopo aver giocato la carta essa viene rimossa dalla mano
        assertEquals(playableSide,
                     player.getPlayerArea().getCardOnCoordinates(1,1));
    }

    /**
     * @return a resource card that gives 1 point when it is played
     */
    private PlayableCard resourceCardWithPoint(){
        return new PlayableCard(
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
                new PointsGiver(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT)));
    }

    /**
     * it Tests that the points earned by player are added correctly
     */
    @Test
    void testPoints(){
        PlayableCard handPlayableCard = resourceCardWithPoint();
        playInitialCard();
        handPlayableCard.flip();
        player.playCard(handPlayableCard,1,1);
        assertEquals(1,player.getPlayerArea().getPoints());
    }

    /**
     * @return a gold card example: to place it the player need to have an INSECT symbol on his playerArea
     */
    private PlayableCard goldCardExample(){
        return new PlayableCard(
                PlayableCardType.GOLD,
                Symbol.INSECT,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        new HashMap<>(Map.of(Symbol.INSECT,1)),
                        new StandardPointsStrategy()
                        )
                ,
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT)
                )
        );
    }
    /**
     * it tests the placement of a card that has a requirement
     */
    @Test
    void testRequirementsPlayCard(){
        PlayableCard resourceCard = resourceCardWithPoint();
        PlayableCard goldCard = goldCardExample();
        goldCard.flip();

        playInitialCard();
        player.playCard(resourceCard,1,1);
        player.playCard(goldCard,-1,-1);

        assertEquals(2,player.getPlayerArea().getPoints());
        assertEquals(1,player.getPlayerArea().getNumOfSymbol(Symbol.INSECT));
    }

    /*@Test
    void testFlipInitialCard(){
        PlayableCard playerInitialCard = player.getInitialCard();
        if (playerInitialCard.isShowingFront()){
            player.flip(playerInitialCard);
            assertEquals(Boolean.FALSE, initialCard.isShowingFront());
        }
        else{
            player.flip(playerInitialCard);
            assertEquals(Boolean.TRUE, initialCard.isShowingFront());
        }
    }*/

    @Test
    void testFlipPlayedInitialCard(){
        player.playInitialCard();
        PlayableCard playerInitialCard = player.getInitialCard();
        assertThrows(CardNotInHandException.class, () -> {
            player.flip(player.getInitialCard());
        });
    }

    @Test
    void testPlayCardWithRequirements(){

        player.playInitialCard();

        // Play first two resource card on back (getting 1 Insect resource)
        player.getHand().getFirst().flip();
        player.playCard(player.getHand().getFirst(), 1, 1);
        player.getHand().getFirst().flip();
        player.playCard(player.getHand().getFirst(), 2, 2);

        // Draw two more resource card from deck and play them on the back
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.playCard(player.getHand().getLast(), 3, 3);
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().flip();
        player.playCard(player.getHand().getLast(), 4, 4);
        player.drawFromResourceCardsDeck();

        assertThrows(NotPlayableException.class, () -> {
            player.playCard(player.getHand().getFirst(), 5, 5);
        });
        player.getHand().getLast().flip();
        player.playCard(player.getHand().getLast(), 5, 5);
        player.playCard(player.getHand().getFirst(), 6, 6);
    }
}