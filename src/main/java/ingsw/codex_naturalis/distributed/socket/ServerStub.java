package ingsw.codex_naturalis.distributed.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.distributed.socket.MessageFromClient.GameToAccessUpdate;
import ingsw.codex_naturalis.distributed.socket.MessageFromClient.MessageFromClient;
import ingsw.codex_naturalis.distributed.socket.MessageFromClient.NewGameUpdate;
import ingsw.codex_naturalis.distributed.socket.MessageFromClient.ReadyUpdate;
import ingsw.codex_naturalis.distributed.socket.MessageFromServer.MessageFromServer;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class ServerStub implements Server {

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
    public void updateGameToAccess(Client client, int gameID, String nickname) throws RemoteException {
        try {
            MessageFromClient message = new GameToAccessUpdate();
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
    public void updateNewGame(Client client, int numOfPlayers, String nickname) throws RemoteException {
        try {
            MessageFromClient message = new NewGameUpdate();
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
    public void updateReady(Client client) throws RemoteException {

        try {
            MessageFromClient message = new ReadyUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void updateInitialCard(Client client, InitialCardEvent initialCardEvent) throws RemoteException {

    }

    @Override
    public void updateColor(Client client, Color color) throws RemoteException {

    }

    @Override
    public void updateFlipCard(Client client, FlipCard flipCard) throws RemoteException {

    }

    @Override
    public void updatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException, RemoteException {

    }

    @Override
    public void updateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException, RemoteException {

    }

    @Override
    public void updateText(Client client, Message message, String content, List<String> receivers) throws RemoteException {

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
