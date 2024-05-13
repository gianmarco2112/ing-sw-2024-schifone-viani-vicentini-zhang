package ingsw.codex_naturalis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.common.middleware.MessageToServer.*;
import ingsw.codex_naturalis.common.middleware.MessageToClient.MessageToClient;
import ingsw.codex_naturalis.server.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.server.exceptions.NotYourTurnException;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server, GameController {

    ClientImpl client;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String ip;
    private final int port;

    private BufferedReader reader;
    private PrintWriter writer;

    private Socket socket;



    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public ClientImpl getClient() {
        return client;
    }

    public BufferedReader getReader() {
        return reader;
    }


    @Override
    public void register(Client client) throws RemoteException {
        this.client = (ClientImpl) client;
        try {
            this.socket = new Socket(ip, port);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            this.writer = new PrintWriter(bufferedWriter);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.reader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            throw new RemoteException("Error while registering to server");
        }
    }

    @Override
    public void chooseNickname(Client client, String nickname) throws RemoteException {
        try {
            MessageToServer message = new CTSChooseNickname();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(nickname);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void viewIsReady(Client client) throws RemoteException {
        try {
            MessageToServer message = new CTSViewIsReady();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void accessExistingGame(Client client, int gameID) throws RemoteException {
        try {
            MessageToServer message = new CTSAccessExistingGame();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(gameID);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void accessNewGame(Client client, int numOfPlayers) throws RemoteException {
        try {
            MessageToServer message = new CTSAccessNewGame();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(numOfPlayers);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public GameController getGameController(Client client) throws RemoteException {
        try {
            MessageToServer message = new CTSGetGameController();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
        return this;
    }

    @Override
    public void leaveGame(Client client, boolean hasDisconnected) throws RemoteException {
        try {
            MessageToServer message = new CTSLeaveGame();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void imAlive(Client client) throws RemoteException {
        try {
            MessageToServer message = new CTSImAlive();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void readyToPlay() throws RemoteException {
        try {
            MessageToServer message = new CTSReadyToPlay();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updateInitialCard(String nickname, String jsonInitialCardEvent) throws RemoteException {
        try {
            MessageToServer message = new CTSUpdateInitialCard();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonInitialCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void chooseColor(String nickname, String jsonColor) throws RemoteException {
        try {
            MessageToServer message = new CTSChooseColor();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonColor);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void chooseSecretObjectiveCard(String nickname, int index) throws RemoteException {
        try {
            MessageToServer message = new CTSObjectiveCardChoiceUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(index);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void flipCard(String nickname, int index) throws RemoteException {
        try {
            MessageToServer message = new CTSFlipCard();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(index);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void playCard(String nickname, int index, int x, int y) throws NotYourTurnException, RemoteException {
        try {
            MessageToServer message = new CTSPlayCard();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(index);
            writer.println(x);
            writer.println(y);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void drawCard(String nickname, String jsonDrawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException, RemoteException {
        try {
            MessageToServer message = new CTSDrawCard();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonDrawCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void sendMessage(String nickname, String receiver, String content) throws RemoteException {
        try {
            MessageToServer message = new CTSSendMessage();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(receiver);
            writer.println(content);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }





    public void receive() throws IOException {
        String jsonMessage;
        try {
            jsonMessage = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader");
        }
        MessageToClient message = objectMapper.readValue(jsonMessage, MessageToClient.class);
        message.run(this);
    }



    public void close() throws RemoteException {

        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }

    }
}
