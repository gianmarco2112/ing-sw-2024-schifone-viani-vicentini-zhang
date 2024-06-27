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

/**
 * This class is used for socket communication, it receives the updates from the server through the client
 * skeleton and forwards them to the client implementation.
 */
public class ServerStub implements Server, GameController {

    ClientImpl client;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String ip;
    private final int port;

    private BufferedReader reader;
    private PrintWriter writer;

    private Socket socket;

    /**
     * Server stub's creator
     * @param ip of the server
     * @param port of the server
     */

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * ClientImpl's getter
     * @return client
     */
    public ClientImpl getClient() {
        return client;
    }

    /**
     * BufferedReader's getter
     * @return reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * To register a client to the server
     * @param client : the client to register
     */
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
            throw new RemoteException("Error while registering to server\n" + e.getMessage());
        }
    }
    /**
     * To choose the nickname
     */
    @Override
    public void chooseNickname(Client client, String nickname) throws RemoteException {
        try {
            MessageToServer message = new CTSChooseNickname(nickname);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To access an existing game
     * @param gameID : of the game to access
     * @param client : the client who wants to access the existing game
     */
    @Override
    public void accessExistingGame(Client client, int gameID) throws RemoteException {
        try {
            MessageToServer message = new CTSAccessExistingGame(gameID);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To access a new game
     * @param numOfPlayers : num of players of the new game
     * @param client : the client who wants to access the new game
     */
    @Override
    public void accessNewGame(Client client, int numOfPlayers) throws RemoteException {
        try {
            MessageToServer message = new CTSAccessNewGame(numOfPlayers);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * GameController's getter
     * @return the game controller of the specified client
     */
    @Override
    public GameController getGameController(Client client) throws RemoteException {
        try {
            MessageToServer message = new CTSGetGameController();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
        return this;
    }
    /**
     * To leave the game
     * @param client who leaves the game
     */
    @Override
    public void leaveGame(Client client) throws RemoteException {
        try {
            MessageToServer message = new CTSLeaveGame();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To update the client's status as connected to server
     * @param client: who is sending the update
     */
    @Override
    public void imAlive(Client client) throws RemoteException {
        try {
            MessageToServer message = new CTSImAlive();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * To update the player's status as "ready to play"
     * @param nickname: of the player who is ready
     */
    @Override
    public void readyToPlay(String nickname) throws RemoteException {
        try {
            MessageToServer message = new CTSReadyToPlay();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To update the player's initialcard
     * @param nickname: of the player
     */
    @Override
    public void updateInitialCard(String nickname, String jsonInitialCardEvent) throws RemoteException {
        try {
            MessageToServer message = new CTSUpdateInitialCard(jsonInitialCardEvent);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To allow the player to choose a color
     * @param nickname: of the player
     * @param jsonColor : the chosen color
     */
    @Override
    public void chooseColor(String nickname, String jsonColor) throws RemoteException {
        try {
            MessageToServer message = new CTSChooseColor(jsonColor);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To allow the player to choose a secret objective card
     * @param nickname: of the player
     * @param index : the chosen secret objective card
     */
    @Override
    public void chooseSecretObjectiveCard(String nickname, int index) throws RemoteException {
        try {
            MessageToServer message = new CTSObjectiveCardChoiceUpdate(index);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * To allow the player to flip a card
     * @param nickname: of the player who wants to flip the card
     * @param index : of the card to flip
     */
    @Override
    public void flipCard(String nickname, int index) throws RemoteException {
        try {
            MessageToServer message = new CTSFlipCard(index);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To allow the player to play a card on the specified coordinates
     * @param nickname: of the player who wants to play a card
     * @param index : the card to play
     * @param x : x coordinate where to play the card
     * @param y :y coordinate where to play the card
     */
    @Override
    public void playCard(String nickname, int index, int x, int y) throws NotYourTurnException, RemoteException {
        try {
            MessageToServer message = new CTSPlayCard(index, x, y);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To allow the player to draw a card
     * @param nickname: of the player who wants to draw the card
     */
    @Override
    public void drawCard(String nickname, String jsonDrawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException, RemoteException {
        try {
            MessageToServer message = new CTSDrawCard(jsonDrawCardEvent);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * To allow the player to send a message
     * @param nickname: of the player who wants to send the message
     * @param receiver : receivers of the message
     * @param content : the content of the message
     */
    @Override
    public void sendMessage(String nickname, String receiver, String content) throws RemoteException {
        try {
            MessageToServer message = new CTSSendMessage(receiver, content);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * To receive messages from the server
     */
    public void receive() throws IOException {
        String jsonMessage;
        try {
            jsonMessage = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader\n"+e.getMessage());
        }
        MessageToClient message = objectMapper.readValue(jsonMessage, MessageToClient.class);
        message.run(this);
    }
    /**
     * To close the socket
     */
    public void close() throws RemoteException {

        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }

    }
}
