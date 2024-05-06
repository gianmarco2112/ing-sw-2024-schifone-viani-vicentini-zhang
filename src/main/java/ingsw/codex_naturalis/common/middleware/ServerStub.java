package ingsw.codex_naturalis.common.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.common.middleware.MessageFromClient.*;
import ingsw.codex_naturalis.common.middleware.MessageFromServer.MessageFromServer;
import ingsw.codex_naturalis.common.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.common.exceptions.NotYourTurnException;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server, GameController {

    Client client;

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



    @Override
    public void register(Client client) throws RemoteException {

        this.client = client;

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
    public void ctsUpdateGameToAccess(Client client, int gameID, String nickname) throws RemoteException {
        try {
            MessageFromClient message = new CTSGameToAccessUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(gameID);
            writer.println(nickname);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void ctsUpdateNewGame(Client client, int numOfPlayers, String nickname) throws RemoteException {
        try {
            MessageFromClient message = new CTSNewGameUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(numOfPlayers);
            writer.println(nickname);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public GameController getGameController(Client client) throws RemoteException {
        try {
            MessageFromClient message = new CTSGetGameController();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
        return this;
    }



    @Override
    public void updateReady() throws RemoteException {

        try {
            MessageFromClient message = new CTSReadyUpdate();
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
            MessageFromClient message = new CTSInitialCardUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonInitialCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updateColor(String nickname, String jsonColor) throws RemoteException {
        try {
            MessageFromClient message = new CTSColorUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonColor);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updateObjectiveCard(String nickname, String jsonObjectiveCardChoice) throws RemoteException {
        try {
            MessageFromClient message = new CTSObjectiveCardChoiceUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonObjectiveCardChoice);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void updateFlipCard(String nickname, String jsonFlipCardEvent) throws RemoteException {
        try {
            MessageFromClient message = new CTSFlipCardUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonFlipCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updatePlayCard(String nickname, String jsonPlayCardEvent, int x, int y) throws NotYourTurnException, RemoteException {
        try {
            MessageFromClient message = new CTSPlayCardUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonPlayCardEvent);
            writer.println(x);
            writer.println(y);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updateDrawCard(String nickname, String jsonDrawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException, RemoteException {
        try {
            MessageFromClient message = new CTSDrawCardUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonDrawCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updateSendMessage(String nickname, String receiver, String content) throws RemoteException {
        try {
            MessageFromClient message = new CTSSendMessageUpdate();
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

        MessageFromServer message = objectMapper.readValue(jsonMessage, MessageFromServer.class);
        message.run(client, reader);

    }



    public void close() throws RemoteException {

        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }

    }
}
