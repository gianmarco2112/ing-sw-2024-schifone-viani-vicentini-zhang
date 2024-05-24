package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.server.model.Game;
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
    ServerImpl server;
    ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp() throws RemoteException {
        server = new ServerImpl();

        client1 = new ClientImpl(server, NetworkProtocol.RMI);
        client1.setViewTest();
        client2 = new ClientImpl(server, NetworkProtocol.RMI);
        client2.setViewTest();

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
            //System.out.println("aspetto che il model si aggiorni");
        }
        assertNotNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertNotNull(model.getPlayerOrder().getLast().getInitialCard());
    }

    @Test
    void updateInitialCard() throws JsonProcessingException, InterruptedException {
        readyToPlay();
        assertFalse(model.getPlayerOrder().getFirst().getInitialCard().getImmutablePlayableCard().showingFront());
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.FLIP));
        while(!model.getPlayerOrder().getFirst().getInitialCard().getImmutablePlayableCard().showingFront()){
            //System.out.println("aspetto che il model si aggiorni");
        }
        assertTrue(model.getPlayerOrder().getFirst().getInitialCard().getImmutablePlayableCard().showingFront());

        assertFalse(model.getPlayerOrder().getFirst().getPlayerArea().getArea().containsKey(List.of(0,0)));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        while(!model.getPlayerOrder().getFirst().getPlayerArea().getArea().containsKey(List.of(0,0))){
            //System.out.println("aspetto che il model si aggiorni");
        }

        assertTrue(model.getPlayerOrder().getFirst().getPlayerArea().getArea().containsKey(List.of(0,0)));
    }

    @Test
    void chooseColor() throws JsonProcessingException {
        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        while(model.getPlayerOrder().getFirst().getColor() == null){
            //System.out.println("aspetto che il model si aggiorni");
        }
        assertEquals(model.getPlayerOrder().getFirst().getColor(), Color.RED);
        assertThrows(ColorAlreadyChosenException.class, () -> {model.setPlayerColor(model.getPlayerOrder().getFirst(), Color.RED);});
    }

    @Test
    void chooseSecretObjectiveCard() throws JsonProcessingException {
        readyToPlay();
        gameplayController.chooseColor("Test", objectMapper.writeValueAsString(Color.RED));
        gameplayController.chooseColor("Test2", objectMapper.writeValueAsString(Color.BLUE));
        gameplayController.updateInitialCard("Test", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        gameplayController.updateInitialCard("Test2", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        assertTrue(model.getPlayerOrder().getFirst().getSecretObjectiveCards().isEmpty());
        gameplayController.chooseSecretObjectiveCard("Test", 1);
        gameplayController.chooseSecretObjectiveCard("Test2", 1);
        while(model.getPlayerOrder().getFirst().getPlayerArea().getObjectiveCard() == null){
            //System.out.println("aspetto che il model si aggiorni");
        }
        assertNotNull(model.getPlayerOrder().getFirst().getPlayerArea().getObjectiveCard());
    }

    @Test
    void flipCard() throws JsonProcessingException {
        chooseSecretObjectiveCard();
        assertEquals(3, model.getPlayerOrder().getFirst().getHand().size());
        boolean initialIsShowingFront = model.getPlayerOrder().getFirst().getHand().getFirst().isShowingFront();
        gameplayController.flipCard("Test", 0);
        gameplayController.flipCard("Test2", 0);
        while(model.getPlayerOrder().getFirst().getHand().getFirst().isShowingFront() == initialIsShowingFront){
            //System.out.println("aspetto che il model si aggiorni");
        }
        assertEquals(!initialIsShowingFront, model.getPlayerOrder().getFirst().getHand().getFirst().isShowingFront());
    }

    @Test
    void playCard() throws JsonProcessingException {
        chooseSecretObjectiveCard();
        assertEquals(1, model.getPlayerOrder().getFirst().getPlayerArea().getArea().size());
        gameplayController.playCard("Test", 1, 1, 1);
        gameplayController.playCard("Test2", 1, 1, 1);
        while(model.getPlayerOrder().getFirst().getPlayerArea().getArea().size() == 1){
            //System.out.println("aspetto che il model si aggiorni");
        }
        assertEquals(2, model.getPlayerOrder().getFirst().getPlayerArea().getArea().size());
    }

    @Test
    void drawCard() {
    }

    @Test
    void sendMessage() {
    }
}