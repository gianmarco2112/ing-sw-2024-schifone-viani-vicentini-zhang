package ingsw.codex_naturalis.distributed.socket;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.ClientImpl;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.view.UI;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

import static java.lang.Integer.parseInt;

public class ClientSkeleton implements Client {




    private BufferedReader reader;
    private PrintWriter writer;

    public ClientSkeleton(Socket socket) throws RemoteException{

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

        writer.println("GamesSpecs");
        writer.println(jsonGamesSpecs);
        writer.flush();

    }

    @Override
    public void reportLobbyUIError(String error) throws RemoteException {

        writer.println("Error");
        writer.println(error);
        writer.flush();

    }

    @Override
    public void updateGameStartingUIGameID(int gameID) throws RemoteException {

        writer.println("GameID");
        writer.println(gameID);

    }


    @Override
    public void updateUI(UI ui) throws RemoteException {

        switch (ui) {
            case GAME_STARTING -> writer.println("GameStarting");
            case SETUP -> writer.println("Setup");
        }

        writer.flush();

    }


    public void receive(Server server) throws IOException {

        String read;
        try {
            read = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader");
        }

        switch (read) {
            case "ExistingGame" -> {
                int gameID = parseInt(reader.readLine());
                String nickname = reader.readLine();
                server.updateGameToAccess(this, gameID, nickname);
            }
            case "NewGame" -> {
                int numOfPlayers = parseInt(reader.readLine());
                String nickname = reader.readLine();
                server.updateNewGame(this, numOfPlayers, nickname);
            }
        }

    }
}
