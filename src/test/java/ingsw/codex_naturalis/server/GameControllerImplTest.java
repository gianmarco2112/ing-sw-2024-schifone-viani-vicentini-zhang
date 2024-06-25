package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.client.view.tui.TextualUI;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.enumerations.*;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.server.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.server.model.Deck;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerImplTest {
    GameControllerImpl gameplayController;
    Game model;
    ClientImpl client1;
    ClientImpl client2;
    ClientImpl client3;
    ServerImpl server;
    ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp() throws RemoteException {
        server = new ServerImpl();

        client1 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client2 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client3 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());

        gameplayController = new GameControllerImpl(server,100,2,client1,"Test");
        gameplayController.addPlayer(client2, "Test2");
        model = gameplayController.getModel();
        model.setCurrentPlayer(model.getPlayerByNickname("Test"));

    }

    @Test
    void readyToPlay() {
        assertNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertNull(model.getPlayerOrder().getLast().getInitialCard());
        assertEquals(0,model.getRevealedResourceCards().size());
        gameplayController.readyToPlay("Test");
        gameplayController.readyToPlay("Test2");

        while(model.getPlayerOrder().getFirst().getInitialCard() == null || model.getPlayerOrder().getLast().getInitialCard() == null){
            //System.out.println("waiting for model update");
        }
        assertNotNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertNotNull(model.getPlayerOrder().getLast().getInitialCard());
    }

    @Test
    void updateInitialCard() throws JsonProcessingException, InterruptedException {
        readyToPlay();
        assertFalse(model.getPlayerOrder().getFirst().getInitialCard().isShowingFront());
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        while(!model.getPlayerOrder().getFirst().getInitialCard().isShowingFront()){
            //System.out.println("waiting for model update");
        }
        assertTrue(model.getPlayerOrder().getFirst().getInitialCard().isShowingFront());

        assertFalse(model.getPlayerOrder().getFirst().getPlayerArea().getArea().containsKey(List.of(0,0)));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        while(!model.getPlayerOrder().getFirst().getPlayerArea().getArea().containsKey(List.of(0,0))){
            //System.out.println("waiting for model update");
        }

        assertTrue(model.getPlayerOrder().getFirst().getPlayerArea().getArea().containsKey(List.of(0,0)));
    }

    @Test
    void chooseColor() throws JsonProcessingException {
        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        while(model.getPlayerOrder().getFirst().getColor() == null){
            //System.out.println("waiting for model update");
        }
        assertEquals(model.getPlayerOrder().getFirst().getColor(), Color.RED);
        assertThrows(ColorAlreadyChosenException.class, () -> {model.setPlayerColor(model.getPlayerOrder().getFirst(), Color.RED);});
    }

    @Test
    void chooseSecretObjectiveCard() throws JsonProcessingException {
        readyToPlay();
        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        gameplayController.chooseColor("Test2", objectMapper.writeValueAsString(Color.BLUE));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        assertTrue(model.getPlayerOrder().getFirst().getSecretObjectiveCards().isEmpty());
        gameplayController.chooseSecretObjectiveCard("Test", 1);
        gameplayController.chooseSecretObjectiveCard("Test2", 1);
        while(model.getPlayerOrder().getFirst().getPlayerArea().getObjectiveCard() == null){
            //System.out.println("waiting for model update");
        }
        assertNotNull(model.getPlayerOrder().getFirst().getPlayerArea().getObjectiveCard());
    }

    @Test
    void flipCard() throws JsonProcessingException, InterruptedException {
        chooseSecretObjectiveCard();
        assertEquals(3, model.getPlayerOrder().getFirst().getHand().size());
        boolean initialIsShowingFront = model.getPlayerOrder().getFirst().getHand().getFirst().isShowingFront();
        gameplayController.flipCard("Test", 0);
        gameplayController.flipCard("Test2", 0);
        while(model.getPlayerOrder().getFirst().getHand().getFirst().isShowingFront() == initialIsShowingFront){
            //System.out.println("waiting for model update");
        }
        assertEquals(!initialIsShowingFront, model.getPlayerOrder().getFirst().getHand().getFirst().isShowingFront());

        gameplayController.flipCard("Test", 4);//line 244
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void playCard() throws JsonProcessingException, InterruptedException {
        chooseSecretObjectiveCard();
        TimeUnit.MILLISECONDS.sleep(200);
        assertEquals(1, model.getPlayerOrder().getFirst().getPlayerArea().getArea().size());

        gameplayController.drawCard(model.getPlayerOrder().getFirst().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1));//line 343
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 1, 1, 1);
        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 1, 1, 1);
        while(model.getPlayerOrder().getFirst().getPlayerArea().getArea().size() == 1){
            //System.out.println("waiting for model update");
        }
        assertEquals(1, model.getPlayerOrder().getLast().getPlayerArea().getArea().size());
        assertEquals(2, model.getPlayerOrder().getFirst().getPlayerArea().getArea().size());

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 1, 1, 1); //line 274
        TimeUnit.MILLISECONDS.sleep(200);
        gameplayController.drawCard(model.getPlayerOrder().getLast().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1));//line 339
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void drawCard() throws JsonProcessingException, InterruptedException {
        playCard();
        assertEquals(1, model.getPlayerOrder().getLast().getPlayerArea().getArea().size());
        assertEquals(2, model.getPlayerOrder().getFirst().getPlayerArea().getArea().size());
        while(model.getPlayerOrder().getFirst().getHand().size() == 3){
            //System.out.println("waiting for model update");
        }
        assertEquals(2, model.getPlayerOrder().getFirst().getHand().size());
        gameplayController.drawCard(model.getPlayerOrder().getFirst().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1));
        while(model.getPlayerOrder().getFirst().getHand().size() == 2){
            //System.out.println("waiting for model update");
        }
        assertEquals(3, model.getPlayerOrder().getFirst().getHand().size());
    }

    @Test
    void sendMessage() throws JsonProcessingException {
        chooseSecretObjectiveCard();
        assertEquals(0 ,model.getChat().size());
        gameplayController.sendMessage("Test", "Test2", "test");
        gameplayController.sendMessage("Test", null, "test");
        while (model.getChat().size() < 2){
            //System.out.println("waiting for model update");
        }
        assertEquals(2 ,model.getChat().size());
    }

    @Test
    void endGame() throws JsonProcessingException, InterruptedException {
        chooseSecretObjectiveCard();
        TimeUnit.MILLISECONDS.sleep(200);

        model.getPlayerOrder().getFirst().getPlayerArea().setPoints(20);

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(GameStatus.SECOND_TO_LAST_ROUND_20_POINTS, model.getGameStatus());

        gameplayController.drawCard(model.getPlayerOrder().getFirst().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1));
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.drawCard(model.getPlayerOrder().getLast().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2));
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 2, -1, -1);
        TimeUnit.MILLISECONDS.sleep(200);


        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, -1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, 1, -1);
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(GameStatus.ENDGAME, model.getGameStatus());
    }

    @Test
    void deckEmptyAndEndGame() throws JsonProcessingException, InterruptedException {
        chooseSecretObjectiveCard();
        TimeUnit.MILLISECONDS.sleep(200);

        Deck<PlayableCard> resourceCardsDeck = model.getResourceCardsDeck();
        Deck<PlayableCard> goldCradsDeck = model.getGoldCardsDeck();

        model.getPlayerOrder().getFirst().getPlayerArea().setPoints(20);
        model.getPlayerOrder().getLast().getPlayerArea().setPoints(21);

        for(int i = 0; i < 34; i++) {
            resourceCardsDeck.drawACard();
        }

        for(int i = 0; i < 36; i ++) {
            goldCradsDeck.drawACard();
        }

        for(int i = 0; i < 2; i ++) {
            PlayableCard p1 = model.removeRevealedGoldCard(0);
        }
        PlayableCard p2 = model.removeRevealedResourceCard(0);

        //lascio un'ultima carta da pescare per fare scatenare fine dei mazzi
        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);
        gameplayController.drawCard(model.getPlayerOrder().getFirst().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1));
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(GameStatus.SECOND_TO_LAST_ROUND_DECKS_EMPTY, model.getGameStatus());

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, -1, -1);
        TimeUnit.MILLISECONDS.sleep(200);
        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, -1, -1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, -1);
        TimeUnit.MILLISECONDS.sleep(200);
        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, 1, -1);
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(GameStatus.ENDGAME, model.getGameStatus());
    }

    @Test
    void drawFromDecks() throws JsonProcessingException, InterruptedException {
        chooseSecretObjectiveCard();
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.drawCard(model.getPlayerOrder().getFirst().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK));
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(PlayableCardType.RESOURCE, model.getPlayerOrder().getFirst().getHand().getLast().getPlayableCardType());


        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.drawCard(model.getPlayerOrder().getLast().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK));
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(PlayableCardType.GOLD, model.getPlayerOrder().getLast().getHand().getLast().getPlayableCardType());

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 2, -1, -1);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void drawFromRevealedGold() throws JsonProcessingException, InterruptedException {
        chooseSecretObjectiveCard();
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, 1);
        //gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.drawCard(model.getPlayerOrder().getFirst().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1));
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(PlayableCardType.GOLD, model.getPlayerOrder().getFirst().getHand().getLast().getPlayableCardType());

        //gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, 1, 1);
        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.drawCard(model.getPlayerOrder().getLast().getNickname(), objectMapper.writeValueAsString(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2));
        TimeUnit.MILLISECONDS.sleep(200);

        assertEquals(PlayableCardType.GOLD, model.getPlayerOrder().getLast().getHand().getLast().getPlayableCardType());

        //gameplayController.playCard(model.getPlayerOrder().getFirst().getNickname(), 0, -1, -1);
        gameplayController.playCard(model.getPlayerOrder().getLast().getNickname(), 0, -1, -1);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void maxNumOfPlayer() {
        assertThrows(MaxNumOfPlayersInException.class,()->{gameplayController.addPlayer(new ClientImpl(new ServerImpl(), NetworkProtocol.RMI, new TextualUI()), "Test3");});
    }

    @Test
    void colorAlreadyChoosed() throws JsonProcessingException, InterruptedException {
        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        TimeUnit.MILLISECONDS.sleep(200);
        gameplayController.chooseColor("Test2", objectMapper.writeValueAsString(Color.RED));
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void chooseSecretOutOfBound() throws JsonProcessingException, InterruptedException {//line 219
        readyToPlay();
        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        gameplayController.chooseColor("Test2", objectMapper.writeValueAsString(Color.BLUE));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        assertTrue(model.getPlayerOrder().getFirst().getSecretObjectiveCards().isEmpty());
        gameplayController.chooseSecretObjectiveCard("Test", 2);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void drawCardRandomTest() throws JsonProcessingException, InterruptedException {
        //ho bisogno di 3 giocatori almeno
        setupForThreePlayers();

        model.setCurrentPlayer(model.getPlayerByNickname("Test"));
        gameplayController.playCard(model.getCurrentPlayer().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);
        assertEquals(2, model.getCurrentPlayer().getHand().size());
        gameplayController.disconnectPlayer("Test");
        assertEquals(3, model.getPlayerByNickname("Test").getHand().size());

        setupForThreePlayers();
        Deck<PlayableCard> resourceCardsDeck = model.getResourceCardsDeck();

        for(int i = 0; i < 32; i++) {
            resourceCardsDeck.drawACard();
        }
        model.setCurrentPlayer(model.getPlayerByNickname("Test"));
        gameplayController.playCard(model.getCurrentPlayer().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);
        assertEquals(2, model.getCurrentPlayer().getHand().size());
        gameplayController.disconnectPlayer("Test");
        assertEquals(3, model.getPlayerByNickname("Test").getHand().size());

        setupForThreePlayers();
        resourceCardsDeck = model.getResourceCardsDeck();
        Deck<PlayableCard> goldCradsDeck = model.getGoldCardsDeck();

        for(int i = 0; i < 32; i++) {
            resourceCardsDeck.drawACard();
        }
        for(int i = 0; i < 35; i ++) {
            goldCradsDeck.drawACard();
        }
        model.setCurrentPlayer(model.getPlayerByNickname("Test"));
        gameplayController.playCard(model.getCurrentPlayer().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);
        assertEquals(2, model.getCurrentPlayer().getHand().size());
        gameplayController.disconnectPlayer("Test");
        assertEquals(3, model.getPlayerByNickname("Test").getHand().size());


        setupForThreePlayers();
        resourceCardsDeck = model.getResourceCardsDeck();
        goldCradsDeck = model.getGoldCardsDeck();

        for(int i = 0; i < 32; i++) {
            resourceCardsDeck.drawACard();
        }
        for(int i = 0; i < 35; i ++) {
            goldCradsDeck.drawACard();
        }
        for(int i = 0; i < 2; i ++) {
            model.removeRevealedResourceCard(0);
        }
        model.setCurrentPlayer(model.getPlayerByNickname("Test"));
        gameplayController.playCard(model.getCurrentPlayer().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);
        assertEquals(2, model.getCurrentPlayer().getHand().size());
        gameplayController.disconnectPlayer("Test");
        assertEquals(3, model.getPlayerByNickname("Test").getHand().size());

    }

    private void setupForThreePlayers() throws InterruptedException, JsonProcessingException {
        gameplayController = new GameControllerImpl(server,100,3,client1,"Test");
        gameplayController.addPlayer(client2, "Test2");
        model = gameplayController.getModel();
        model.setCurrentPlayer(model.getPlayerByNickname("Test"));
        gameplayController.addPlayer(client3, "Test3");

        gameplayController.readyToPlay("Test");
        gameplayController.readyToPlay("Test2");
        gameplayController.readyToPlay("Test3");
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        gameplayController.chooseColor("Test2", objectMapper.writeValueAsString(Color.BLUE));
        gameplayController.chooseColor("Test3", objectMapper.writeValueAsString(Color.YELLOW));
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test3", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        gameplayController.updateInitialCard("Test3", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);

        gameplayController.chooseSecretObjectiveCard("Test", 1);
        gameplayController.chooseSecretObjectiveCard("Test2", 1);
        gameplayController.chooseSecretObjectiveCard("Test3", 1);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void skipTurn() throws InterruptedException, JsonProcessingException {
        setupForThreePlayers();

        model.setGameStatus(GameStatus.SECOND_TO_LAST_ROUND_20_POINTS);
        model.setCurrentPlayer(model.getPlayerOrder().getLast());
        gameplayController.disconnectPlayer(model.getPlayerOrder().getLast().getNickname());
        assertEquals(model.getPlayerOrder().getFirst(), model.getCurrentPlayer());
        assertEquals(GameStatus.LAST_ROUND, model.getGameStatus());
        gameplayController.playCard(model.getCurrentPlayer().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);

        setupForThreePlayers();

        model.setGameStatus(GameStatus.LAST_ROUND);
        model.setCurrentPlayer(model.getPlayerOrder().getLast());
        gameplayController.disconnectPlayer(model.getPlayerOrder().getLast().getNickname());
        assertEquals(model.getPlayerOrder().getFirst(), model.getCurrentPlayer());
        assertEquals(GameStatus.ENDGAME, model.getGameStatus());
        gameplayController.playCard(model.getCurrentPlayer().getNickname(), 0, 1, 1);
        TimeUnit.MILLISECONDS.sleep(200);
    }
}