package ingsw.codex_naturalis.distributed.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.distributed.socket.MessageFromClient.MessageFromClient;
import ingsw.codex_naturalis.distributed.socket.MessageFromServer.*;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

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
    public void stcUpdateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException {

        try {
            MessageFromServer message = new STCLobbyUIGamesSpecsUpdate();
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
            MessageFromServer message = new STCLobbyUIErrorReport();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(error);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void stcUpdateGameStartingUIGameID(int gameID) throws RemoteException {

        try {
            MessageFromServer message = new STCGameStartingUIGameIDUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(gameID);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void stcUpdateSetupUIInitialCard(String jsonImmGame, String jsonInitialCardEvent) {
        try {
            MessageFromServer message = new STCInitialCardUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(jsonInitialCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateSetupUIColor(String jsonColor) throws RemoteException {
        try {
            MessageFromServer message = new STCColorUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonColor);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void reportSetupUIError(String error) {
        try {
            MessageFromServer message = new STCSetupUIErrorReport();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(error);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateSetupUI(String jsonImmGame, String jsonGameEvent) {
        try {
            MessageFromServer message = new STCSetupUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(jsonGameEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateSetupUIObjectiveCardChoice(String jsonImmGame) throws RemoteException {
        try {
            MessageFromServer message = new STCObjectiveCardChoiceUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateGameplayUIPlayerOrder(String jsonImmGame) throws RemoteException {
        try {
            MessageFromServer message = new STCGameplayUIPlayerOrderUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateGameplayUI(String jsonImmGame) throws RemoteException {
        try {
            MessageFromServer message = new STCGameplayUIUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void reportGameplayUIError(String error) throws RemoteException {
        try {
            MessageFromServer message = new STCGameplayUIErrorReport();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(error);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void stcUpdateUI(String jsonUI) throws RemoteException {

        try {
            MessageFromServer message = new STCUIUpdate();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonUI);
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