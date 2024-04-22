package ingsw.codex_naturalis.distributed.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.view.UI;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ClientSkeleton implements Client {

    private final Server server;

    private final Map<MessageFromClient, Runnable> messageProtocol = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();

    private final BufferedReader reader;
    private final PrintWriter writer;

    public ClientSkeleton(Socket socket, Server server) throws RemoteException{

        this.server = server;

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            this.writer = new PrintWriter(bufferedWriter);

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.reader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            throw new RemoteException("Error while creating the client skeleton");
        }

        initMessageProtocol();

    }

    private void initMessageProtocol() {

        messageProtocol.put(MessageFromClient.GAME_TO_ACCESS_UPDATE, this::receiveUpdateGameToAccess);
        messageProtocol.put(MessageFromClient.NEW_GAME_UPDATE, this::receiveUpdateNewGame);
        messageProtocol.put(MessageFromClient.READY_UPDATE, this::receiveUpdateReady);

    }


    @Override
    public void updateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException {

        try {
            writer.println(objectMapper.writeValueAsString(MessageFromServer.LOBBY_UI_GAMES_SPECS_UPDATE));
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
        writer.println(jsonGamesSpecs);
        writer.flush();

    }

    @Override
    public void reportLobbyUIError(String error) throws RemoteException {

        try {
            writer.println(objectMapper.writeValueAsString(MessageFromServer.LOBBY_UI_ERROR_REPORT));
            writer.println(error);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void updateGameStartingUIGameID(int gameID) throws RemoteException {

        try {
            writer.println(objectMapper.writeValueAsString(MessageFromServer.GAME_STARTING_UI_GAME_ID_UPDATE));
            writer.println(gameID);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards) {

        try {
            writer.println(objectMapper.writeValueAsString(MessageFromServer.SETUP_1_UPDATE));
            writer.println(objectMapper.writeValueAsString(initialCard));
            writer.println(objectMapper.writeValueAsString(resourceCards));
            writer.println(objectMapper.writeValueAsString(goldCards));
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }


    @Override
    public void updateUI(UI ui) throws RemoteException {

        try {
            writer.println(objectMapper.writeValueAsString(MessageFromServer.UI_UPDATE));
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
        switch (ui) {
            case GAME_STARTING -> {
                try {
                    writer.println(objectMapper.writeValueAsString(UI.GAME_STARTING));
                } catch (JsonProcessingException e) {
                    System.err.println("Error while processing json");
                }
            }
            case SETUP -> {
                try {
                    writer.println(objectMapper.writeValueAsString(UI.SETUP));
                } catch (JsonProcessingException e) {
                    System.err.println("Error while processing json");
                }
            }
        }

        writer.flush();

    }


    public void receive() throws IOException {

        String read;
        try {
            read = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader");
        }

        MessageFromClient message = objectMapper.readValue(read, MessageFromClient.class);

        Runnable runnable = messageProtocol.get(message);
        runnable.run();

    }


    private void receiveUpdateGameToAccess(){
        try {
            int gameID = parseInt(reader.readLine());
            String nickname = reader.readLine();
            server.updateGameToAccess(this, gameID, nickname);
        } catch (IOException e) {
            System.err.println("Error while receiving from client");
        }
    }

    private void receiveUpdateNewGame() {
        try {
        int numOfPlayers = parseInt(reader.readLine());
        String nickname = reader.readLine();
        server.updateNewGame(this, numOfPlayers, nickname);
        } catch (IOException e) {
            System.err.println("Error while receiving from client");
        }
    }

    private void receiveUpdateReady() {
        try {
            server.updateReady(this);
        } catch (RemoteException e) {
            System.err.println("Error while receiving from client");
        }
    }
}