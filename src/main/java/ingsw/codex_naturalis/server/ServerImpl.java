package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.common.exceptions.NicknameAlreadyExistsException;
import ingsw.codex_naturalis.common.exceptions.NotReachableGameException;
import ingsw.codex_naturalis.server.model.GameSpecs;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.Server;

import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl implements Server {

    private static class StartingGame {

        private final Game model;
        private final List<Client> clients = new ArrayList<>();

        private StartingGame(Game model, Client view){
            this.model = model;
            this.clients.add(view);
        }

    }


    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Client> inLobbyClients = new ArrayList<>();

    private final List<StartingGame> startingGames = new ArrayList<>();

    private final List<GameControllerImpl> gameControllerImpls = new ArrayList<>();

    private final Map<Client, GameControllerImpl> clientToGame = new HashMap<>();

    private final Map<GameControllerImpl, GameController> gameToExportedGame = new HashMap<>();






    @Override
    public void register(Client client) {

        synchronized (inLobbyClients) {
            inLobbyClients.add(client);
        }

        try {
            client.setViewAsLobby(objectMapper.writeValueAsString(getGamesSpecs()));
        } catch (JsonProcessingException | RemoteException e) {
            System.err.println("Error while updating client:\n" + e.getMessage());
        }

    }


    public Map<Client,GameControllerImpl> getClientToGame(){
        return clientToGame;
    }


    private void updateClientLobbyUIGameSpecs(List<Client> clients){

        for (Client client : clients) {
            try {
                client.stcUpdateLobbyUIGameSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while trying to update client's lobby UI");
            }
        }

    }
    private List<GameSpecs> getGamesSpecs() {

        synchronized (startingGames) {
            List<GameSpecs> gamesSpecs = new ArrayList<>();
            for (StartingGame gameManagement : startingGames) {
                int gameID = gameManagement.model.getGameID();
                int numOfPlayers = gameManagement.model.getNumOfPlayers();
                int currentNumOfPlayers = gameManagement.model.getPlayerOrder().size();
                GameSpecs gameSpecs = new GameSpecs(gameID, currentNumOfPlayers, numOfPlayers);
                gamesSpecs.add(gameSpecs);
            }
            return gamesSpecs;
        }

    }



    private StartingGame getStartingGameFromGameID(int gameID) throws NotReachableGameException {
        for (StartingGame game : startingGames) {
            if (gameID == game.model.getGameID())
                return game;
        }
        throw new NotReachableGameException();
    }


    private void reportLobbyUIErrorToClient(Client client, String error){
        try {
            client.reportLobbyUIError(error);
        } catch (RemoteException e) {
            System.err.println("Error while updating client");
        }
    }


    @Override
    public void ctsUpdateGameToAccess(Client client, int gameID, String nickname) {

        synchronized (startingGames) {
            try {

                StartingGame startingGame = getStartingGameFromGameID(gameID);
                startingGame.model.addPlayer(new Player(nickname));
                startingGame.clients.add(client);
                client.setNickname(nickname);

                synchronized (inLobbyClients) {
                    inLobbyClients.remove(client);
                }

                if (startingGame.model.getNumOfPlayers() > startingGame.model.getPlayerOrder().size())
                    try {
                        client.setViewAsGameStarting(gameID);
                    } catch (RemoteException e) {
                        System.err.println("Error while trying to update client's UI to Game Starting");
                    }

                else {
                    startingGames.remove(startingGame);
                    GameControllerImpl gameControllerImpl = new GameControllerImpl(startingGame.model, startingGame.clients);
                    synchronized (clientToGame) {
                        for (Client c : startingGame.clients)
                            clientToGame.put(c, gameControllerImpl);
                    }
                    setClientsViewAsSetup(startingGame.clients);
                    synchronized (gameControllerImpls) {
                        gameControllerImpls.add(gameControllerImpl);
                    }
                }

            } catch (NotReachableGameException | NicknameAlreadyExistsException | MaxNumOfPlayersInException e) {
                reportLobbyUIErrorToClient(client, e.getMessage());
            } catch (RemoteException ex) {
                System.err.println("Error while updating client: \n" + ex.getMessage());
            }
        }

    }

    private void setClientsViewAsSetup(List<Client> clients) {

        try {
            for (Client client : clients)
                client.setViewAsSetup();
        } catch (RemoteException e) {
            System.err.println("Error while trying to update client's UI to Setup:\n" + e.getMessage());
        }

    }

    private int createGameID(){
        int gameID;
        List<Integer> gameIDs = new ArrayList<>();
        for (StartingGame gameManagement : startingGames)
            gameIDs.add(gameManagement.model.getGameID());
        do {
            gameID = new Random().nextInt(100) + 1;
        } while (gameIDs.contains(gameID));
        return gameID;
    }

    @Override
    public void ctsUpdateNewGame(Client client, int numOfPlayers, String nickname) {

        int gameID = createGameID();
        Game game = new Game(gameID, numOfPlayers);
        Player player = new Player(nickname);
        game.addPlayer(player);

        StartingGame startingGame = new StartingGame(game, client);

        synchronized (startingGames) {
            startingGames.add(startingGame);
        }

        try {
            client.setNickname(nickname);
            client.setViewAsGameStarting(gameID);
        } catch (RemoteException e) {
            System.err.println("Error while trying to update client's UI to Game Starting:\n" + e.getMessage());
        }

        synchronized (inLobbyClients) {
            inLobbyClients.remove(client);
            updateClientLobbyUIGameSpecs(inLobbyClients);
        }

    }




    @Override
    public GameController getGameController(Client client) throws RemoteException {

        GameControllerImpl gameController = clientToGame.get(client);
        if (!gameToExportedGame.containsKey(gameController))
            gameToExportedGame.put(gameController, (GameController) UnicastRemoteObject.exportObject(gameController, 0));

        return gameToExportedGame.get(gameController);

    }

}
