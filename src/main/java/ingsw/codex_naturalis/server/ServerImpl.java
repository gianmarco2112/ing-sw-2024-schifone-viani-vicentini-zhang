package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.server.exceptions.InvalidNumOfPlayersException;
import ingsw.codex_naturalis.server.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.Server;
import com.google.common.collect.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

/**
 * Main server, used to manage the lobby and the starting games, it also takes trace of all
 * the clients and started games.
 */
public class ServerImpl implements Server {

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * List of clients connected to the server that haven't logged in yet.
     */
    private final List<Client> loggedOutClients = new ArrayList<>();

    /**
     * Logged-in users that are still in lobby.
     */
    private final Set<String> inLobbyUsers = new HashSet<>();

    /**
     * Maps every playing user to the joined game.
     */
    private final Map<String, GameControllerImpl> userToGame = new HashMap<>();

    /**
     * BiMap used to get the nickname from a client and vice versa.
     */
    private final BiMap<Client, String> clientNicknameBiMap = HashBiMap.create();

    /**
     * Used to remove the association between disconnected users
     * and the game after the game ended.
     */
    private final Map<GameControllerImpl, Set<String>> gameToDisconnectedUsers = new HashMap<>();


    /**
     * List of not started games, where the current players number in is less
     * than the number of the players set for the game.
     */
    private final List<GameControllerImpl> notStartedGames = new ArrayList<>();

    /**
     * List of started games, where players are already playing.
     */
    private final List<GameControllerImpl> startedGames = new ArrayList<>();

    /**
     * Maps the game ID to the game, used when a player wants to join a game.
     */
    private final Map<Integer, GameControllerImpl> gameIDToGame = new HashMap<>();

    /**
     * Used, for the RMI protocol, to map a GameControllerImpl to his exported stub, in order
     * to export it only once.
     */
    private final Map<GameControllerImpl, GameController> gameToExportedGame = new HashMap<>();

    /**
     * Updates queue, used to make rmi async and to ensure that every message received from a client
     * gets processed in the correct order.
     */
    private final BlockingQueue<Runnable> updatesQueue = new LinkedBlockingQueue<>();

    /**
     * Maps every client to his timeout, used for the resilience implementation.
     */
    private final Map<Client, ScheduledFuture<?>> clientToTimeout = new ConcurrentHashMap<>();

    /**
     * Scheduled executor service
     */
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    /**
     * ServerImpl's constructor
     */
    public ServerImpl() {
        new Thread(() -> {
            while (true) {
                try {
                    updatesQueue.take().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
     * First method called by a client connected to the server, it adds the client to the loggedOutClients list
     * @param client client that has connected
     */
    @Override
    public void register(Client client) {
        synchronized (loggedOutClients) {
            loggedOutClients.add(client);
        }
    }

    /**
     * Called by a client to set his nickname, it checks that the name is unique. It also checks
     * if there is a disconnected player with that nickname associated to a game, in this case, it
     * reconnects the client to the game.
     * @param client client caller
     * @param nickname nickname chosen
     */
    @Override
    public synchronized void chooseNickname(Client client, String nickname) {
        try {
            updatesQueue.put( () -> {
                try {
                    //if there is a user with that nickname in lobby
                    if (inLobbyUsers.contains(nickname)) {
                        client.reportException("This nickname is already in use, please choose an other one.");
                        return;
                    }
                    if (userToGame.containsKey(nickname)) {
                        //if there is a user with that nickname in game and he is connected
                        if (userToGame.get(nickname).getModel().getPlayerByNickname(nickname).isInGame())
                            client.reportException("This nickname is already in use, please choose an other one.");
                            //if there is a user with that nickname in game and he is NOT connected
                        else
                            reconnect(client, nickname, userToGame.get(nickname));
                        return;
                    }

                    //otherwise, set the client's nickname
                    inLobbyUsers.add(nickname);
                    loggedOutClients.remove(client);
                    clientNicknameBiMap.put(client, nickname);
                    client.setNickname(nickname);
                    client.updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while updating client\n"+e.getMessage());
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called from the client when he wants to join an existing game that hasn't started yet.
     * @param client caller
     * @param gameID game id of the game to join
     */
    @Override
    public synchronized void accessExistingGame(Client client, int gameID) {
        try {
            updatesQueue.put(() -> {
                GameControllerImpl gameController;
                gameController = gameIDToGame.get(gameID);
                if (!notStartedGames.contains(gameController)) {
                    try {
                        client.reportException("No existing game found!");
                        return;
                    } catch (RemoteException ex) {
                        System.err.println("Error while updating client\n" + ex.getMessage());
                    }
                }
                boolean isGameStarted = gameController.addPlayer(client, clientNicknameBiMap.get(client));
                if (isGameStarted) {
                    startedGames.add(gameController);
                    notStartedGames.remove(gameController);
                    gameIDToGame.remove(gameID);
                }
                inLobbyUsers.remove(clientNicknameBiMap.get(client));
                userToGame.put(clientNicknameBiMap.get(client), gameController);
                updateInLobbyUsersGamesSpecs();

            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called by a client when he wants to create a new game
     * @param client client caller
     * @param numOfPlayers number of players to set for the game
     */
    @Override
    public synchronized void accessNewGame(Client client, int numOfPlayers) {
        try {
            updatesQueue.put(() -> {
                int gameID = createGameID();
                try {
                    GameControllerImpl gameController = new GameControllerImpl(this, gameID, numOfPlayers, client, clientNicknameBiMap.get(client));
                    notStartedGames.add(gameController);
                    userToGame.put(clientNicknameBiMap.get(client), gameController);
                    inLobbyUsers.remove(clientNicknameBiMap.get(client));
                    gameIDToGame.put(gameID, gameController);
                    updateInLobbyUsersGamesSpecs();
                } catch (InvalidNumOfPlayersException e) {
                    try {
                        client.reportException(e.getMessage());
                    } catch (RemoteException ex) {
                        System.err.println("Error while updating client\n" + ex.getMessage());
                    }
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called by an RMI client to get his game controller, it makes sure to export the
     * stub only once.
     * @param client client caller
     * @return the game controller stub
     * @throws RemoteException remote exception
     */
    @Override
    public synchronized GameController getGameController(Client client) throws RemoteException {
        GameControllerImpl gameController = userToGame.get(clientNicknameBiMap.get(client));
        if (!gameToExportedGame.containsKey(gameController))
            gameToExportedGame.put(gameController, (GameController) UnicastRemoteObject.exportObject(gameController, 0));
        return gameToExportedGame.get(gameController);
    }

    /**
     * Method called by a client when he wants to leave a game, he won't be able to rejoin.
     * @param client client caller
     */
    @Override
    public synchronized void leaveGame(Client client) {
        try {
            updatesQueue.put(() -> {
                GameControllerImpl gameController = userToGame.get(clientNicknameBiMap.get(client));
                gameController.removePlayer(clientNicknameBiMap.get(client));
                userToGame.remove(clientNicknameBiMap.get(client));
                inLobbyUsers.add(clientNicknameBiMap.get(client));
                try {
                    client.updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while updating client\n" + e.getMessage());
                }
                if (gameController.getModel().getGameStatus() == GameStatus.WAITING_FOR_PLAYERS) {
                    if (gameController.getModel().getGameRunningStatus() == GameRunningStatus.TO_CANCEL_NOW) {
                        notStartedGames.remove(gameController);
                        updateInLobbyUsersGamesSpecs();
                    }
                    return;
                }
                checkGameRunningStatus(gameController);
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Part of the resilience, method called every 5 seconds from the client.
     * In every call, it sets a new timeout of 10 seconds on that client.
     * @param client client caller
     */
    @Override
    public synchronized void imAlive(Client client) {
        ScheduledFuture<?> timeoutTaskToCancel = clientToTimeout.remove(client);
        if (timeoutTaskToCancel != null)
            timeoutTaskToCancel.cancel(false);
        ScheduledFuture<?> timeoutTask = executorService.schedule(() -> {
            disconnect(client);
        }, 20, TimeUnit.SECONDS);
        clientToTimeout.put(client, timeoutTask);
    }

    /**
     * Method called when a client's timeout expires, it removes the client from the game.
     * @param client disconnected client
     */
    public synchronized void disconnect(Client client) {
        //don't care about his heartbeat
        clientToTimeout.remove(client);
        //don't care if he disconnected before logging in
        if (loggedOutClients.contains(client))
            return;

        String nickname = clientNicknameBiMap.get(client);
        clientNicknameBiMap.remove(client);
        //don't care if he crashed while in lobby
        if (inLobbyUsers.contains(nickname)) {
            inLobbyUsers.remove(nickname);
            return;
        }

        GameControllerImpl gameController = userToGame.get(nickname);
        gameController.disconnectPlayer(nickname);

        switch (gameController.getModel().getGameStatus()) {
            case WAITING_FOR_PLAYERS -> {
                userToGame.remove(nickname);
                return;
            }
            case READY, SETUP_1, SETUP_2 -> userToGame.remove(nickname);
            default -> {
                Set<String> disconnectedUsers = new HashSet<>();
                if (gameToDisconnectedUsers.containsKey(gameController))
                    disconnectedUsers.addAll(gameToDisconnectedUsers.get(gameController));
                disconnectedUsers.add(nickname);
                gameToDisconnectedUsers.put(gameController, disconnectedUsers);
            }
        }
        checkGameRunningStatus(gameController);
    }

    /**
     * Method called after a client has disconnected or left a game, it checks if the game has to
     * be canceled.
     * @param gameController game to check
     */
    private void checkGameRunningStatus(GameControllerImpl gameController) {
        GameRunningStatus gameRunningStatus = gameController.getPlayersConnectionStatus();
        switch (gameRunningStatus) {
            case TO_CANCEL_NOW -> {
                endGame(gameController);
            }
            case TO_CANCEL_LATER -> {
                gameController.getModel().setGameRunningStatus(gameRunningStatus);
                executorService.schedule(() -> {
                    endGame(gameController);
                }, 10, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * Method called when a game has to be canceled because of players leaving, or called
     * from the game controller when the game ends.
     * @param gameController game to end
     */
    public void endGame(GameControllerImpl gameController) {

        if (gameController.getVirtualViews().size() >= 2 && gameController.getModel().getGameRunningStatus() != GameRunningStatus.RUNNING) {
            gameController.getModel().setGameRunningStatus(GameRunningStatus.RUNNING);
            return;
        }

        gameController.getModel().setGameRunningStatus(GameRunningStatus.TO_CANCEL_NOW);

        if (gameToDisconnectedUsers.containsKey(gameController)){
            Set<String> disconnectedUsers = gameToDisconnectedUsers.remove(gameController);
            for (String user : disconnectedUsers)
                userToGame.remove(user);
        }
        startedGames.remove(gameController);
        if (gameController.getVirtualViews().isEmpty())
            return;

        List<VirtualView> virtualViews = gameController.getVirtualViews();
        for (VirtualView view : virtualViews){
            gameController.getModel().deleteObserver(view);
            userToGame.remove(clientNicknameBiMap.get(view.getClient()));
            inLobbyUsers.add(clientNicknameBiMap.get(view.getClient()));
            try {
                view.getClient().updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while updating client\n" + e.getMessage());
            }
        }
    }

    /**
     * Method called when a user wants to reconnect to a game he left.
     * @param client client to reconnect
     * @param nickname user's nickname
     * @param gameController game
     */
    private void reconnect(Client client, String nickname, GameControllerImpl gameController) {
        if (gameToDisconnectedUsers.containsKey(gameController)){
            Set<String> disconnectedUsers = gameToDisconnectedUsers.get(gameController);
            disconnectedUsers.remove(nickname);
        }
        clientNicknameBiMap.put(client, nickname);
        loggedOutClients.remove(client);
        gameController.reconnect(client, nickname);
    }

    /**
     * Method used to get the not started games specs to send to the lobby users.
     * @return list of games specs
     */
    private List<GameSpecs> getGamesSpecs() {
        synchronized (notStartedGames) {
            List<GameSpecs> gamesSpecs = new ArrayList<>();
            for (GameControllerImpl gameController : notStartedGames) {
                int gameID = gameController.getModel().getGameID();
                int numOfPlayers = gameController.getModel().getNumOfPlayers();
                int currentNumOfPlayers = gameController.getModel().getPlayerOrder().size();
                GameSpecs gameSpecs = new GameSpecs(gameID, currentNumOfPlayers, numOfPlayers);
                gamesSpecs.add(gameSpecs);
            }
            return gamesSpecs;
        }
    }

    /**
     * Method used to create a game id when creating a new game,
     * it makes sure the id created is unique.
     * @return the game id
     */
    private int createGameID() {
        int gameID;
        do {
            gameID = new Random().nextInt(100) + 1;
        } while (gameIDToGame.containsKey(gameID));
        return gameID;
    }

    /**
     * Method called by the client skeleton (socket) to get the game controller.
     * @param client client skeleton caller
     * @return game controller
     */
    public GameControllerImpl getGameControllerImpl(Client client) {
        return userToGame.get(clientNicknameBiMap.get(client));
    }

    /**
     * Method called to update all the in lobby users with the not started
     * games specs.
     */
    private void updateInLobbyUsersGamesSpecs() {
        for (String nickname : inLobbyUsers) {
            try {
                clientNicknameBiMap.inverse().get(nickname).updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while updating client\n" + e.getMessage());
            }
        }

        for(Client client : loggedOutClients) {
            try {
                client.updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while updating client\n" + e.getMessage());
            }

        }
    }

}
