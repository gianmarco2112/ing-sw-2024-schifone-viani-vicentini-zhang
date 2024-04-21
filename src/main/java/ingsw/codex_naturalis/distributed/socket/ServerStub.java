package ingsw.codex_naturalis.distributed.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.view.UI;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ServerStub implements Server {

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
        writer.println("ExistingGame");
        writer.println(gameID);
        writer.println(nickname);
        writer.flush();
    }

    @Override
    public void updateNewGame(Client client, int numOfPlayers, String nickname) throws RemoteException {
        writer.println("NewGame");
        writer.println(numOfPlayers);
        writer.println(nickname);
        writer.flush();
    }





    @Override
    public void updateReady(Client client) throws RemoteException {

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





    public void receive(Client client) throws IOException {

        String read;

        try {
            read = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader");
        }

        switch (read) {
            case "GamesSpecs" -> {
                String jsonGamesSpecs = null;
                try {
                    jsonGamesSpecs = reader.readLine();
                } catch (IOException e) {
                    throw new RemoteException();
                }
                client.updateLobbyUIGameSpecs(jsonGamesSpecs);
            }
            case "Error" -> {
                String error = reader.readLine();
                client.reportLobbyUIError(error);
            }
            case "GameStarting" -> {
                client.updateUI(UI.GAME_STARTING);
            }
            case "GameID" -> {
                int gameID = 0;
                try {
                    gameID = parseInt(reader.readLine());
                } catch (IOException e) {
                    throw new RemoteException();
                }
                client.updateGameStartingUIGameID(gameID);
            }
            case "Setup" -> {
                client.updateUI(UI.SETUP);
            }
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
