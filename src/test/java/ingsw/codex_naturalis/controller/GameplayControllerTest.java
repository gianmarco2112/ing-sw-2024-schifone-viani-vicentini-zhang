package ingsw.codex_naturalis.controller;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.enumerations.*;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiver;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverForObject;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.StandardPointsStrategy;
import ingsw.codex_naturalis.model.enumerations.*;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameplayControllerTest {
    GameplayController gameplayController;
    Game model;
    GameplayUI view;
    Player player;
    Player player2;
    @BeforeEach
    void setUp(){
        model = new Game(0,2);
        player = new Player("Test");
        player.setColor(Color.RED);
        player2 = new Player("Test2");
        player2.setColor(Color.BLUE);
        model.addPlayer(player);
        model.addPlayer(player2);
        model.setPlayerOrder(List.of(player,player2));
        model.setCurrentPlayer(player,"Test");
        gameplayController = new GameplayController(model,view);
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
                        new StandardPointsStrategy()
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
        model.setRevealedResourceCards(List.of(insectResourceCard(),insectResourceCard()),"Test");
        model.setRevealedGoldCards(List.of(insectGoldCard(),insectGoldCard()),"Test");
    }
    //------------------------------------------------------------------
    //--------------------TESTS-----------------------------------------
    @Test
    void updateFlipCard() {
        PlayableCard resourceCard = insectResourceCard(); //showing back initially
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);

        gameplayController.updateFlipCard("Test", FlipCard.FLIP_CARD_1);

        assertTrue(player.getHand().getFirst().showingFront);
    }

    @Test
    void updatePlayCard() {
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);

        player.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");

        gameplayController.updatePlayCard("Test", PlayCard.PLAY_CARD_1,1,1);

        assertEquals(resourceCard,player.getPlayerArea().getCardOnCoordinates(1,1));
    }

    @Test
    void notYourTurnException(){
        assertThrows(NotYourTurnException.class,()->{gameplayController.updatePlayCard("Test2", PlayCard.PLAY_CARD_1,1,1);});
    }

    @Test
    void updateDrawCard() {
        updatePlayCard();
        gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("R01",card.getCardID());

        assertThrows(NotYourTurnException.class,()->{gameplayController.updateDrawCard("Test1", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);});

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");
        player2.setHand(hand);

        gameplayController.updatePlayCard("Test2", PlayCard.PLAY_CARD_1,1,1);
        //----------------
        gameplayController.updateDrawCard("Test2", DrawCard.DRAW_FROM_GOLD_CARDS_DECK);//remove first
        card = player2.getHand().getFirst();
        assertEquals("G01",card.getCardID());

        assertThrows(NotYourDrawTurnStatusException.class,()->{gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);});
    }

    @Test
    void updateDrawRevealedResourceCard() {
        setRevealedCards();
        updatePlayCard();
        gameplayController.updateDrawCard("Test", DrawCard.DRAW_REVEALED_RESOURCE_CARD_1);//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("RTest",card.getCardID());

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");
        player2.setHand(hand);

        gameplayController.updatePlayCard("Test2", PlayCard.PLAY_CARD_1,1,1);
        //----------------
        gameplayController.updateDrawCard("Test2", DrawCard.DRAW_REVEALED_RESOURCE_CARD_2);//remove first
        card = player.getHand().getFirst();
        assertEquals("RTest",card.getCardID());
    }

    @Test
    void updateDrawRevealedGoldCard() {
        setRevealedCards();
        updatePlayCard();
        gameplayController.updateDrawCard("Test", DrawCard.DRAW_REVEALED_GOLD_CARD_1);//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("GTest",card.getCardID());

        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");
        player2.setHand(hand);

        gameplayController.updatePlayCard("Test2", PlayCard.PLAY_CARD_1,1,1);
        //----------------
        gameplayController.updateDrawCard("Test2", DrawCard.DRAW_REVEALED_GOLD_CARD_2);//remove first
        card = player.getHand().getFirst();
        assertEquals("GTest",card.getCardID());
    }

    @Test
    void updateText() {
        Player player1 = new Player("Test1");

        gameplayController.updateText("Test", Message.TEXT_A_PLAYER,"Prova",List.of("Test1"));

        assertEquals("Prova",model.getChat().getFirst().getContent());
        assertEquals("Test",model.getChat().getFirst().getSender());
        assertEquals("Test1",model.getChat().getFirst().getReceivers().getFirst());
    }

    @Test
    void noSuchNicknameException(){
        assertThrows(NoSuchNicknameException.class,()->{gameplayController.updateFlipCard("Test1", FlipCard.FLIP_CARD_1);});
    }
    @Test
    void notPlayableException(){
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);
        assertThrows(NotPlayableException.class,()->{gameplayController.updatePlayCard("Test", PlayCard.PLAY_CARD_1,1,1);});
    }

    @Test
    void lastRoundGameStatusTest(){
        int i = 1;
        Player player = model.getPlayerOrder().getFirst();
        player.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");
        model.setGameStatus(GameStatus.GAMEPLAY,"Test");
        while(i<=21){
            PlayableCard resourceCard = insectResourceCardWithPoint();
            resourceCard.flip("Test");
            List<PlayableCard> hand = new ArrayList<>();
            hand.add(resourceCard);

            player.setHand(hand);

            gameplayController.updatePlayCard("Test", PlayCard.PLAY_CARD_1,i,i);
            i++;
        }
        assertEquals(GameStatus.LAST_ROUND_20_POINTS,model.getGameStatus());

        gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);
        //play a card-----
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player2 = model.getCurrentPlayer();
        player2.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");
        player2.setHand(hand);

        gameplayController.updatePlayCard("Test2", PlayCard.PLAY_CARD_1,1,1);

        gameplayController.updateDrawCard("Test2", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);

        player.setHand(hand);
        gameplayController.updatePlayCard("Test", PlayCard.PLAY_CARD_1,-1,-1);

        player2.setHand(hand);
        gameplayController.updatePlayCard("Test2", PlayCard.PLAY_CARD_1,-1,-1);

        assertEquals(GameStatus.ENDGAME,model.getGameStatus());
    }

    @Test
    void GoldCardPlayTest(){
        PlayableCard goldStandardCard = goldStandardCard();
        goldStandardCard.flip("Test");
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(goldStandardCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);

        player.getPlayerArea().setCardOnCoordinates(initialCard(),0,0,"Test");

        //gameplayController.updatePlayCard("Test", PlayCard.PLAY_CARD_1,1,1);

        assertThrows(NotPlayableException.class,()->{gameplayController.updatePlayCard("Test", PlayCard.PLAY_CARD_1,1,1);});

    }

    @Test
    void emptyDecks(){
        int i = 1;
        while (i<=40){
            model.setCurrentPlayer(player,"Test");
            player.setTurnStatus(TurnStatus.DRAW);
            gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);
            i++;
        }
        model.setCurrentPlayer(player,"Test");
        player.setTurnStatus(TurnStatus.DRAW);
        assertThrows(EmptyDeckException.class,()->{gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_RESOURCE_CARDS_DECK);});

        i = 1;
        while (i<=40){
            model.setCurrentPlayer(player,"Test");
            player.setTurnStatus(TurnStatus.DRAW);
            gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_GOLD_CARDS_DECK);
            i++;
        }
        model.setCurrentPlayer(player,"Test");
        player.setTurnStatus(TurnStatus.DRAW);
        assertThrows(EmptyDeckException.class,()->{gameplayController.updateDrawCard("Test", DrawCard.DRAW_FROM_GOLD_CARDS_DECK);});

        assertEquals(GameStatus.LAST_ROUND_DECKS_EMPTY,model.getGameStatus());
    }

    @Test
    void emptyDecksForRevealedCards(){
        List<PlayableCard>  list = new ArrayList<>();
        list.add(null);
        list.add(null);
        model.setRevealedResourceCards(list,"Test");
        model.setRevealedGoldCards(list,"Test");

        int i = 1;
        while (i<=40){
            model.setCurrentPlayer(player,"Test");
            player.setTurnStatus(TurnStatus.DRAW);
            gameplayController.updateDrawCard("Test", DrawCard.DRAW_REVEALED_RESOURCE_CARD_2);
            i++;
        }

        model.setCurrentPlayer(player,"Test");
        player.setTurnStatus(TurnStatus.DRAW);
        assertThrows(NoMoreRevealedCardHereException.class,()->{gameplayController.updateDrawCard("Test", DrawCard.DRAW_REVEALED_RESOURCE_CARD_1);});
    }
}