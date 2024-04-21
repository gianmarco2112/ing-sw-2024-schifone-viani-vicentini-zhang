package ingsw.codex_naturalis.distributed.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.distributed.ServerImpl;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.view.UI;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ServerStub implements Server {

    Client client;

    private final Map<MessageFromServer, Runnable> messageProtocol = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();

    private final String ip;
    private final int port;

    private BufferedReader reader;
    private PrintWriter writer;

    private Socket socket;

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;

        initMessageProtocol();
    }

    private void initMessageProtocol() {
        messageProtocol.put(MessageFromServer.LOBBY_UI_GAMES_SPECS_UPDATE, this::receiveUpdateLobbyUIGamesSpecs);
        messageProtocol.put(MessageFromServer.LOBBY_UI_ERROR_REPORT, this::receiveReportLobbyUIError);
        messageProtocol.put(MessageFromServer.UI_UPDATE, this::receiveUpdateUI);
        messageProtocol.put(MessageFromServer.GAME_STARTING_UI_GAME_ID_UPDATE, this::receiveUpdateGameStartingGameID);
        messageProtocol.put(MessageFromServer.SETUP_1_UPDATE, this::receiveUpdateSetup1);
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
            writer.println(objectMapper.writeValueAsString(MessageFromClient.GAME_TO_ACCESS_UPDATE));
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
            writer.println(objectMapper.writeValueAsString(MessageFromClient.NEW_GAME_UPDATE));
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
            writer.println(objectMapper.writeValueAsString(MessageFromClient.READY_UPDATE));
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

        String read;

        try {
            read = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader");
        }

        MessageFromServer message = objectMapper.readValue(read, MessageFromServer.class);

        Runnable runnable = messageProtocol.get(message);
        runnable.run();

    }

    private void receiveUpdateLobbyUIGamesSpecs() {
        try {
            String jsonGamesSpecs = reader.readLine();
            client.updateLobbyUIGameSpecs(jsonGamesSpecs);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }

    private void receiveReportLobbyUIError() {
        try {
            String error = reader.readLine();
            client.reportLobbyUIError(error);
        } catch (IOException e) {
        System.err.println("Error while receiving from server");
    }
    }

    private void receiveUpdateUI() {
        try {
            String jsonUpdateUI = reader.readLine();
            UI updateUI = objectMapper.readValue(jsonUpdateUI, UI.class);
            client.updateUI(updateUI);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }

    private void receiveUpdateGameStartingGameID() {
        try {
            int gameID = parseInt(reader.readLine());
            client.updateGameStartingUIGameID(gameID);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }

    private void receiveUpdateSetup1() {
        try {
            String jsonInitialCard = reader.readLine();
            PlayableCard.Immutable initialCard = objectMapper.readValue(jsonInitialCard, PlayableCard.Immutable.class);

            String jsonTopResourceCard = reader.readLine();
            PlayableCard.Immutable topResourceCard = objectMapper.readValue(jsonTopResourceCard, PlayableCard.Immutable.class);

            String jsonTopGoldCard = reader.readLine();
            PlayableCard.Immutable topGoldCard = objectMapper.readValue(jsonTopGoldCard, PlayableCard.Immutable.class);

            String jsonRevealedResourceCard = reader.readLine();
            List<PlayableCard.Immutable> revealedResourceCards = objectMapper.readValue(jsonRevealedResourceCard, new TypeReference<List<PlayableCard.Immutable>>(){});

            String jsonRevealedGoldCard = reader.readLine();
            List<PlayableCard.Immutable> revealedGoldCards = objectMapper.readValue(jsonRevealedGoldCard, new TypeReference<List<PlayableCard.Immutable>>(){});

            client.updateSetup1(initialCard, topResourceCard, topGoldCard, revealedResourceCards, revealedGoldCards);

        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }







    public void close() throws RemoteException {

        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }

    }
}
