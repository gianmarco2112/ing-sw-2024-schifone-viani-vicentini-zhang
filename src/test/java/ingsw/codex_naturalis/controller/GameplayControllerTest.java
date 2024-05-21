package ingsw.codex_naturalis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.server.ServerImpl;
import ingsw.codex_naturalis.server.exceptions.NotPlayableException;
import ingsw.codex_naturalis.server.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.server.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiver;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverForObject;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies.StandardStrategy;
import ingsw.codex_naturalis.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameplayControllerTest {
    GameControllerImpl gameplayController;
    Game model;
    Player player;
    Player player2;

    ClientImpl client1;
    ClientImpl client2;
    ServerImpl server;
    //--------------SETUP-----------------------------------------------------
    @BeforeEach
    void setUp() throws RemoteException {
        //model = new Game(0,2);

        //player = new Player("Test");
        //player.setColor(Color.RED);
        //model.addPlayer(player);

        //player2 = new Player("Test2");
        //player2.setColor(Color.BLUE);
        //model.addPlayer(player2);

        //model.setCurrentPlayer(player);

        server = new ServerImpl();

        client1 = new ClientImpl(server, NetworkProtocol.RMI);
        client1.setViewTest();
        client2 = new ClientImpl(server, NetworkProtocol.RMI);
        client2.setViewTest();

        gameplayController = new GameControllerImpl(server,100,2,client1,"Test");
        gameplayController.addPlayer(client2, "Test2");
        model = gameplayController.getModel();
        //model.addPlayer(player);
        //model.addPlayer(player2);
        model.setCurrentPlayer(model.getPlayerByNickname("Test"));

    }
    //--------------CARDS-----------------------------------------------------
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
    private PlayableCard insectResourceCardWithPoint(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
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
                        List.of(Symbol.INSECT))
        );
        return(resourceCard);
    }
    private PlayableCard insectGoldCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "GTest",
                PlayableCardType.GOLD,
                Symbol.INSECT,
                new PointsGiverForObject(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1,
                        new HashMap<>(),
                        Symbol.QUILL
                        ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT))
        );
        return(resourceCard);
    }
    private PlayableCard initialCard(){
        PlayableCard initialCard;
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
        return(initialCard);
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
    //------------------------------------------------------------------
    //--------------PRIVATE METHODS-------------------------------------
    private void setRevealedCards(){
        model.addRevealedResourceCard(insectResourceCard());
        model.addRevealedResourceCard(insectResourceCard());
        model.addRevealedGoldCard(insectGoldCard());
        model.addRevealedGoldCard(insectGoldCard());
    }
    //------------------------------------------------------------------
    //--------------------TESTS-----------------------------------------
    @Test
    void updateFlipCard() {
        PlayableCard resourceCard = insectResourceCard(); //showing back initially
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getCurrentPlayer();
        player.setHand(hand);

        gameplayController.flipCard("Test", 0);


        while(!player.getHand().getFirst().getImmutablePlayableCard().showingFront()){

        }

        assertTrue(player.getHand().getFirst().getImmutablePlayableCard().showingFront());
    }

    @Test
    void updatePlayCard() {
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getCurrentPlayer();
        player.setHand(hand);

        player.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);

        gameplayController.playCard("Test", 0,1,1);

        while(player.getPlayerArea().getCardOnCoordinates(1,1)==null){

        }

        assertEquals(resourceCard,player.getPlayerArea().getCardOnCoordinates(1,1));
    }

    @Test
    void notYourTurnException(){
        assertThrows(NotYourTurnException.class,()->{gameplayController.playCard("Test2", 1,1,1);});
    }

    @Test
    void updateDrawCard() throws JsonProcessingException {
        updatePlayCard();
        ObjectMapper objectMapper = new ObjectMapper();;
        gameplayController.drawCard("Test", objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK));//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("R01",card.getCardID());

        assertThrows(NotYourTurnException.class,()->{gameplayController.drawCard("Test1", objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK));});

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);
        player2.setHand(hand);

        gameplayController.playCard("Test2", 1,1,1);
        //----------------
        gameplayController.drawCard("Test2", objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK));//remove first
        card = player2.getHand().getFirst();
        assertEquals("G01",card.getCardID());

        assertThrows(NotYourDrawTurnStatusException.class,()->{gameplayController.drawCard("Test", objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK));});
    }

    @Test
    void updateDrawRevealedResourceCard() throws JsonProcessingException {
        setRevealedCards();
        updatePlayCard();
        ObjectMapper objectMapper = new ObjectMapper();
        gameplayController.drawCard("Test", objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1));//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("RTest",card.getCardID());

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);
        player2.setHand(hand);

        gameplayController.playCard("Test2", 1,1,1);
        //----------------
        gameplayController.drawCard("Test2", objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2));//remove first
        card = player.getHand().getFirst();
        assertEquals("RTest",card.getCardID());
    }

    @Test
    void updateDrawRevealedGoldCard() throws JsonProcessingException {
        setRevealedCards();
        updatePlayCard();
        ObjectMapper objectMapper = new ObjectMapper();
        gameplayController.drawCard("Test", objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1));//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("GTest",card.getCardID());

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);
        player2.setHand(hand);

        gameplayController.playCard("Test2", 1,1,1);
        //----------------
        gameplayController.drawCard("Test2", objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2));//remove first
        card = player.getHand().getFirst();
        assertEquals("GTest",card.getCardID());
    }

    @Test
    void updateText() {
        gameplayController.sendMessage("Test", "Test1","Prova");

        assertEquals("Prova",model.getChat().getFirst().getContent());
        assertEquals("Test",model.getChat().getFirst().getSender());
        assertEquals("Test1",model.getChat().getFirst().getReceivers().getFirst());
    }

    @Test
    void notPlayableException(){
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getCurrentPlayer();
        player.setHand(hand);
        assertThrows(NotPlayableException.class,()->{gameplayController.playCard("Test", 1,1,1);});
    }

    @Test
    void lastRoundGameStatusTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int i = 1;
        Player player = model.getCurrentPlayer();
        player.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);
        model.setGameStatus(GameStatus.GAMEPLAY);
        while(i<21){
            PlayableCard resourceCard = insectResourceCardWithPoint();
            resourceCard.flip();
            List<PlayableCard> hand = new ArrayList<>();
            hand.add(resourceCard);

            player.setHand(hand);

            model.setCurrentPlayer(player);

            gameplayController.playCard("Test", 1,i,i);

            gameplayController.drawCard("Test", objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK));
            i++;
        }
        assertEquals(GameStatus.LAST_ROUND_20_POINTS,model.getGameStatus());

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);
        player2.setHand(hand);

        gameplayController.playCard("Test2", 1,1,1);
        //draw a card-----
        gameplayController.drawCard("Test2", objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK));

        //now is the last round
        hand.add(resourceCard);
        player.setHand(hand);
        //play a card without drawing a card because last round
        gameplayController.playCard("Test", 1,-1,-1);

        hand.add(resourceCard);
        player2.setHand(hand);
        //play a card without drawing a card because last round
        gameplayController.playCard("Test2", 1,-1,-1);

        assertEquals(GameStatus.ENDGAME,model.getGameStatus());
    }

    @Test
    void GoldCardPlayTest(){
        PlayableCard goldStandardCard = goldStandardCard();
        goldStandardCard.flip();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(goldStandardCard);

        Player player = model.getCurrentPlayer();
        player.setHand(hand);

        player.getPlayerArea().setCardOnCoordinates(initialCard(),0,0);

        assertThrows(NotPlayableException.class,()->{gameplayController.playCard("Test", 1,1,1);});
    }
}
