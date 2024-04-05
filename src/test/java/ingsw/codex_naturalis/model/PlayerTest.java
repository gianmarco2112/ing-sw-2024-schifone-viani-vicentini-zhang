package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.CardNotInHandException;
import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCardBack;
import ingsw.codex_naturalis.model.cards.initial.InitialCardFront;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCardBack;
import ingsw.codex_naturalis.model.cards.resource.ResourceCardFront;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
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

        assertEquals(initialCard.getBack(),playerArea.getCardOnCoordinates(0,0));
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
        HandPlayableCard playableSide = player.getHand().getFirst();
        player.playCard(player.getHand().getFirst(),1,1);//attenzione, dopo aver giocato la carta essa viene rimossa dalla mano
        assertEquals(playableSide.getHandPlayableFront(),
                     player.getPlayerArea().getCardOnCoordinates(1,1));
    }

    /**
     * @return a resource card that gives 1 point when it is played
     */
    private HandPlayableCard resourceCardWithPoint(){
        return new ResourceCard(
                new ResourceCardFront(
                        Symbol.INSECT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1),
                new ResourceCardBack(
                        Symbol.INSECT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        Symbol.INSECT));
    }

    /**
     * it Tests that the points earned by player are added correctly
     */
    @Test
    void testPoints(){
        HandPlayableCard handPlayableCard = resourceCardWithPoint();
        handPlayableCard.drawn(player.getPlayerArea()); //activate card's behavior
        playInitialCard();
        handPlayableCard.showFront();
        player.playCard(handPlayableCard,1,1);
        assertEquals(1,player.getPlayerArea().getPoints());
    }

    /**
     * @return a gold card example: to place it the player need to have an INSECT symbol on his playerArea
     */
    private HandPlayableCard goldCardExample(){
        return new GoldCard(
                new GoldCardFront(
                        Symbol.INSECT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        new HashMap<>(Map.of(Symbol.INSECT,1))
                ),
                new GoldCardBack(
                        Symbol.INSECT,
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        Symbol.INSECT
                )
        );
    }

    /**
     * it tests the placement of a card that has a requirement
     */
    @Test
    void testRequirementsPlayCard(){
        HandPlayableCard resourceCard = resourceCardWithPoint();
        HandPlayableCard goldCard = goldCardExample();
        resourceCard.drawn(player.getPlayerArea());
        goldCard.drawn(player.getPlayerArea());
        resourceCard.showBack();
        goldCard.showFront();

        playInitialCard();
        player.playCard(resourceCard,1,1);
        player.playCard(goldCard,-1,-1);

        assertEquals(2,player.getPlayerArea().getPoints());
        assertEquals(1,player.getPlayerArea().getNumOfSymbol(Symbol.INSECT));
    }

    @Test
    void testFlipInitialCard(){
        InitialCard playerInitialCard = player.getInitialCard();
        if (playerInitialCard.isShowingFront()){
            player.flip(playerInitialCard);
            assertEquals(Boolean.FALSE, initialCard.isShowingFront());
        }
        else{
            player.flip(playerInitialCard);
            assertEquals(Boolean.TRUE, initialCard.isShowingFront());
        }
    }

    @Test
    void testFlipPlayedInitialCard(){
        player.playInitialCard();
        InitialCard playerInitialCard = player.getInitialCard();
        assertThrows(CardNotInHandException.class, () -> {
            player.flip(player.getInitialCard());
        });
    }

    @Test
    void testPlayCardWithRequirements(){

        player.playInitialCard();

        // Play first two resource card on back (getting 1 Insect resource)
        player.getHand().getFirst().showBack();
        player.playCard(player.getHand().getFirst(), 1, 1);
        player.getHand().getFirst().showBack();
        player.playCard(player.getHand().getFirst(), 2, 2);

        // Draw two more resource card from deck and play them on the back
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();
        player.playCard(player.getHand().getLast(), 3, 3);
        player.drawFromResourceCardsDeck();
        player.getHand().getLast().showBack();
        player.playCard(player.getHand().getLast(), 4, 4);
        player.drawFromResourceCardsDeck();

        assertThrows(NotPlayableException.class, () -> {
            player.playCard(player.getHand().getFirst(), 5, 5);
        });
        player.getHand().getLast().showBack();
        player.playCard(player.getHand().getLast(), 5, 5);
        player.playCard(player.getHand().getFirst(), 6, 6);
    }
}