package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;

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
        assertEquals(0,model.getRevealedGoldCards().size());

        gameplayController.readyToPlay();
        gameplayController.readyToPlay();

        while(model.getPlayerOrder().getFirst().getInitialCard() == null || model.getPlayerOrder().getLast().getInitialCard() == null){
            //System.out.println("aspetto che il model si aggiorni");
        }

        assertNotNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertNotNull(model.getPlayerOrder().getLast().getInitialCard());
        assertEquals(2,model.getRevealedResourceCards().size());
        assertEquals(2,model.getRevealedGoldCards().size());
    }

    @Test
    void updateInitialCard() throws JsonProcessingException {
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
    void chooseColor() {
    }

    @Test
    void chooseSecretObjectiveCard() {
    }

    @Test
    void flipCard() {
    }

    @Test
    void playCard() {
    }

    @Test
    void drawCard() {
    }

    @Test
    void sendMessage() {
    }
}