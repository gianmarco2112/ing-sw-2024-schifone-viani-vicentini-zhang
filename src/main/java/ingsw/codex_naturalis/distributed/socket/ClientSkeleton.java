package ingsw.codex_naturalis.distributed.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.distributed.socket.MessageFromClient.MessageFromClient;
import ingsw.codex_naturalis.distributed.socket.MessageFromServer.*;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.view.UI;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class ClientSkeleton implements Client {

    private final Server server;

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

    }



    @Override
    public void updateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException {

        try {
            MessageFromServer message = new LobbyUIGamesSpecsUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonGamesSpecs);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void reportLobbyUIError(String error) throws RemoteException {

        try {
            MessageFromServer message = new LobbyUIErrorReport();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(error);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void updateGameStartingUIGameID(int gameID) throws RemoteException {

        try {
            MessageFromServer message = new GameStartingUIGameIDUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(gameID);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards) {

        try {
            MessageFromServer message = new Setup1Update();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
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
            MessageFromServer message = new UIUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            switch (ui) {
                case GAME_STARTING -> writer.println(objectMapper.writeValueAsString(UI.GAME_STARTING));
                case SETUP -> writer.println(objectMapper.writeValueAsString(UI.SETUP));
            }
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

        MessageFromClient message = objectMapper.readValue(jsonMessage, MessageFromClient.class);
        message.run(this, server, reader);

    }

}