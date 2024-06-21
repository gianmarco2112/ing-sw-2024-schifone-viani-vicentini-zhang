package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.client.view.tui.TextualUI;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ServerImplTest {

    ServerImpl server = new ServerImpl();

    ClientImpl client1;
    ClientImpl client2;
    ClientImpl client3;
    ClientImpl client4;
    ClientImpl client5;
    ClientImpl client6;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws RemoteException, InterruptedException {

        client1 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client2 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client3 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client4 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client5 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());
        client6 = new ClientImpl(server, NetworkProtocol.RMI, new TextualUI());

        server.register(client1);
        server.register(client2);
        server.register(client3);
        server.register(client4);
        server.register(client5);
        server.register(client6);

        server.chooseNickname(client1, "Test");
        TimeUnit.MILLISECONDS.sleep(200);
        server.chooseNickname(client2, "Test2");
        TimeUnit.MILLISECONDS.sleep(200);
        server.chooseNickname(client3, "Test3");
        TimeUnit.MILLISECONDS.sleep(200);
        server.chooseNickname(client4, "Test4");
        TimeUnit.MILLISECONDS.sleep(200);
        server.chooseNickname(client5, "Test5");
        TimeUnit.MILLISECONDS.sleep(200);
        server.chooseNickname(client6, "Test6");
        TimeUnit.MILLISECONDS.sleep(200);

        //nickname already exists
        server.chooseNickname(new ClientImpl(server, NetworkProtocol.RMI, new TextualUI()), "Test");
        TimeUnit.MILLISECONDS.sleep(200);

        //invalid num of plauers
        server.accessNewGame(client1, 5);
        TimeUnit.MILLISECONDS.sleep(200);

        server.accessNewGame(client1, 2);
        TimeUnit.MILLISECONDS.sleep(200);

//        //GameRunningStatus.TO_CANCEL_NOW
//        server.leaveGame(client1);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.accessNewGame(client1, 2);
//        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void accessingExistingGame() throws InterruptedException {
        server.accessExistingGame(client2, server.getGameControllerImpl(client1).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void accessingNoExistingGame() throws InterruptedException {//line 175
        server.accessExistingGame(client2, server.getGameControllerImpl(client1).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);

        server.accessExistingGame(client3, server.getGameControllerImpl(client1).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void nicknameAlreadyExists() throws RemoteException, InterruptedException {// lines 139-140
        server.chooseNickname(new ClientImpl(server, NetworkProtocol.RMI, new TextualUI()), "Test");
        TimeUnit.MILLISECONDS.sleep(200);
    }

//    @Test
//    void leaveGame() throws InterruptedException {
//        server.leaveGame(client1);
//        TimeUnit.MILLISECONDS.sleep(200);
//    }

    @Test
    void checkGameRunningStatus() throws InterruptedException {//line 266
        server.accessNewGame(client3, 3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.accessExistingGame(client4, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);
        server.accessExistingGame(client5, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);

        server.leaveGame(client3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.leaveGame(client4);
        TimeUnit.MILLISECONDS.sleep(200);
    }

//    @Test
//    void disconnection() throws InterruptedException, JsonProcessingException {
//        server.accessNewGame(client3, 4);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.accessExistingGame(client4, server.getGameControllerImpl(client3).getModel().getGameID());
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.accessExistingGame(client5, server.getGameControllerImpl(client3).getModel().getGameID());
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.accessExistingGame(client6, server.getGameControllerImpl(client3).getModel().getGameID());
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).readyToPlay("Test3");
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).readyToPlay("Test4");
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).readyToPlay("Test5");
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).readyToPlay("Test6");
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).updateInitialCard("Test3", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).updateInitialCard("Test4", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).updateInitialCard("Test5", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).updateInitialCard("Test6", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).chooseColor("Test3", objectMapper.writeValueAsString(Color.RED));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseColor("Test4", objectMapper.writeValueAsString(Color.BLUE));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseColor("Test5", objectMapper.writeValueAsString(Color.YELLOW));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseColor("Test6", objectMapper.writeValueAsString(Color.GREEN));
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test3", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test4", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test5", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test6", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).disconnectPlayer("Test3");
//
//        server.leaveGame(client4);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//    }

//    @Test
//    void reconnect() throws InterruptedException, JsonProcessingException {
//
//        server.accessNewGame(client3, 3);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.accessExistingGame(client4, server.getGameControllerImpl(client3).getModel().getGameID());
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.accessExistingGame(client5, server.getGameControllerImpl(client3).getModel().getGameID());
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).readyToPlay("Test3");
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).readyToPlay("Test4");
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).readyToPlay("Test5");
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).updateInitialCard("Test3", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).updateInitialCard("Test4", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).updateInitialCard("Test5", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).chooseColor("Test3", objectMapper.writeValueAsString(Color.RED));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseColor("Test4", objectMapper.writeValueAsString(Color.BLUE));
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseColor("Test5", objectMapper.writeValueAsString(Color.YELLOW));
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test3", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test4", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test5", 1);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        /*for(int i = 0; i <3; i++) {
//            if(server.getGameControllerImpl(client3).getModel().getPlayerOrder().get(i).getNickname().equals("Test3")){
//                server.getGameControllerImpl(client3).getModel().getPlayerOrder().get(i).setInGame(false);
//                TimeUnit.MILLISECONDS.sleep(200);
//            }
//            if(server.getGameControllerImpl(client3).getModel().getPlayerOrder().get(i).getNickname().equals("Test5")){
//                server.getGameControllerImpl(client3).getModel().getPlayerOrder().get(i).setInGame(false);
//                TimeUnit.MILLISECONDS.sleep(200);
//            }
//
//        }*/
//        server.getGameControllerImpl(client3).disconnectPlayer("Test3");
//        //server.getGameControllerImpl(client3).disconnectPlayer("Test5");
//
//        server.leaveGame(client4);
//        TimeUnit.MILLISECONDS.sleep(200);
//
//        server.chooseNickname(client3, "Test3");
//
//        //TimeUnit.SECONDS.sleep(10);
//
//    }

    @Test
    void iamAlive() throws InterruptedException, JsonProcessingException {
        server.accessNewGame(client3, 3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.accessExistingGame(client4, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);
        server.accessExistingGame(client5, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).readyToPlay("Test3");
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).readyToPlay("Test4");
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).readyToPlay("Test5");
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).updateInitialCard("Test3", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).updateInitialCard("Test4", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).updateInitialCard("Test5", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).chooseColor("Test3", objectMapper.writeValueAsString(Color.RED));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseColor("Test4", objectMapper.writeValueAsString(Color.BLUE));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseColor("Test5", objectMapper.writeValueAsString(Color.YELLOW));
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test3", 1);
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test4", 1);
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test5", 1);
        TimeUnit.MILLISECONDS.sleep(200);

        server.disconnect(client3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.leaveGame(client4);
        TimeUnit.MILLISECONDS.sleep(200);


    }

    @Test
    void endGameRunning() throws InterruptedException, JsonProcessingException {//line 356
        setupFor4Players();
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).getModel().setGameRunningStatus(GameRunningStatus.TO_CANCEL_LATER);
        TimeUnit.MILLISECONDS.sleep(200);
        server.endGame(server.getGameControllerImpl(client3));
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void reconnect() throws InterruptedException, JsonProcessingException {
        setupFor4Players();
        TimeUnit.MILLISECONDS.sleep(200);

        server.disconnect(client3);
        TimeUnit.MILLISECONDS.sleep(200);

        //server.leaveGame(client4);
        //TimeUnit.MILLISECONDS.sleep(400);

        server.chooseNickname(client3, "Test3");
        TimeUnit.MILLISECONDS.sleep(500);

        //TimeUnit.SECONDS.sleep(10);

    }

    @Test
    void getter() throws InterruptedException, JsonProcessingException, RemoteException {
        setupFor4Players();
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameController(client3);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void leaveInWaiting() throws InterruptedException { //line 259
        server.leaveGame(client1);
        TimeUnit.MILLISECONDS.sleep(200);

    }

    @Test
    void disconnectInLobby() throws InterruptedException {
        server.disconnect(client3);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void disconnetWaitingForPlayers() throws InterruptedException {
        server.accessNewGame(client3, 3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.accessExistingGame(client4, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);

        server.disconnect(client3);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void disconnectInReady() throws InterruptedException {
        accessingExistingGame();
        TimeUnit.MILLISECONDS.sleep(200);
        server.disconnect(client1);
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @Test
    void disconnectionAndEndGame() throws InterruptedException, JsonProcessingException {
        setupFor4Players();
        TimeUnit.MILLISECONDS.sleep(200);

        server.disconnect(client3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.leaveGame(client4);
        TimeUnit.MILLISECONDS.sleep(200);
        server.leaveGame(client5);
        TimeUnit.MILLISECONDS.sleep(200);

        TimeUnit.SECONDS.sleep(10);

    }

    @Test
    void disconnectionTwoTimesAndEndGame() throws InterruptedException, JsonProcessingException {
        setupFor4Players();
        TimeUnit.MILLISECONDS.sleep(200);

        server.disconnect(client3);
        TimeUnit.MILLISECONDS.sleep(200);

        server.disconnect(client4);
        TimeUnit.MILLISECONDS.sleep(200);

        server.leaveGame(client5);
        TimeUnit.MILLISECONDS.sleep(200);

        TimeUnit.SECONDS.sleep(10);


    }

    private void setupFor4Players() throws InterruptedException, JsonProcessingException {
        server.accessNewGame(client3, 4);
        TimeUnit.MILLISECONDS.sleep(200);

        server.accessExistingGame(client4, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);
        server.accessExistingGame(client5, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);
        server.accessExistingGame(client6, server.getGameControllerImpl(client3).getModel().getGameID());
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).readyToPlay("Test3");
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).readyToPlay("Test4");
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).readyToPlay("Test5");
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).readyToPlay("Test6");
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).updateInitialCard("Test3", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).updateInitialCard("Test4", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).updateInitialCard("Test5", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).updateInitialCard("Test6", objectMapper.writeValueAsString(InitialCardEvent.PLAY));
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).chooseColor("Test3", objectMapper.writeValueAsString(Color.RED));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseColor("Test4", objectMapper.writeValueAsString(Color.BLUE));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseColor("Test5", objectMapper.writeValueAsString(Color.YELLOW));
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseColor("Test6", objectMapper.writeValueAsString(Color.GREEN));
        TimeUnit.MILLISECONDS.sleep(200);

        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test3", 1);
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test4", 1);
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test5", 1);
        TimeUnit.MILLISECONDS.sleep(200);
        server.getGameControllerImpl(client3).chooseSecretObjectiveCard("Test6", 1);
        TimeUnit.MILLISECONDS.sleep(200);
    }
}