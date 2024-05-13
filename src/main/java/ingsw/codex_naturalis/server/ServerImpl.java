package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
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

    private final Map<Client, ScheduledFuture<?>> clientToTimeout = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);


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


    @Override
    public void register(Client client) {
        synchronized (loggedOutClients) {
            loggedOutClients.add(client);
        }
    }


    @Override
    public synchronized void chooseNickname(Client client, String nickname) {
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
        } catch (RemoteException e) {
            System.err.println("Error while updating client");
        }
    }

    @Override
    public synchronized void viewIsReady(Client client) {
        try {
            updatesQueue.put(() -> {
                try {
                    client.updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while trying to update client's lobby UIState");
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void accessExistingGame(Client client, int gameID) {
        try {
            updatesQueue.put(() -> {
                GameControllerImpl gameController;
                gameController = gameIDToGame.get(gameID);
                try {
                    boolean isGameStarted = gameController.addPlayer(client, clientNicknameBiMap.get(client));
                    if (isGameStarted) {
                        startedGames.add(gameController);
                        notStartedGames.remove(gameController);
                    }
                    inLobbyUsers.remove(clientNicknameBiMap.get(client));
                    userToGame.put(clientNicknameBiMap.get(client), gameController);
                    for (String nickname : inLobbyUsers) {
                        try {
                            clientNicknameBiMap.inverse().get(nickname).updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                        } catch (RemoteException | JsonProcessingException e) {
                            System.err.println("Error while updating client\n" + e.getMessage());
                        }
                    }
                } catch (MaxNumOfPlayersInException e) {
                    try {
                        client.reportException(e.getMessage());
                    } catch (RemoteException ex) {
                        System.err.println("Error while updating client\n" + e.getMessage());
                    }
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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
                    for (String nickname : inLobbyUsers) {
                        try {
                            clientNicknameBiMap.inverse().get(nickname).updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                        } catch (RemoteException | JsonProcessingException e) {
                            System.err.println("Error while updating client\n" + e.getMessage());
                        }
                    }
                } catch (InvalidNumOfPlayersException e) {
                    try {
                        client.reportException(e.getMessage());
                    } catch (RemoteException ex) {
                        System.err.println("Error while updating client\n" + e.getMessage());
                    }
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized GameController getGameController(Client client) throws RemoteException {
        GameControllerImpl gameController = userToGame.get(clientNicknameBiMap.get(client));
        if (!gameToExportedGame.containsKey(gameController))
            gameToExportedGame.put(gameController, (GameController) UnicastRemoteObject.exportObject(gameController, 0));
        return gameToExportedGame.get(gameController);
    }

    @Override
    public synchronized void leaveGame(Client client, boolean hasDisconnected) {
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
                checkGameRunningStatus(gameController);
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void imAlive(Client client) {
        ScheduledFuture<?> timeoutTaskToCancel = clientToTimeout.remove(client);
        if (timeoutTaskToCancel != null)
            timeoutTaskToCancel.cancel(false);
        ScheduledFuture<?> timeoutTask = executorService.schedule(() -> {
            disconnect(client);
        }, 10, TimeUnit.SECONDS);
        clientToTimeout.put(client, timeoutTask);
    }

    private synchronized void disconnect(Client client) {
        //don't care about his heartbeat
        clientToTimeout.remove(client);
        //don't care if he disconnected before logging in
        if (loggedOutClients.contains(client))
            return;

        String nickname = clientNicknameBiMap.get(client);
        //don't care if he crashed while in lobby
        if (inLobbyUsers.contains(nickname)) {
            inLobbyUsers.remove(nickname);
            return;
        }

        clientNicknameBiMap.remove(client);

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

        VirtualView view = gameController.getVirtualViews().getFirst();
        gameController.getModel().deleteObserver(view);
        userToGame.remove(clientNicknameBiMap.get(view.getClient()));
        inLobbyUsers.add(clientNicknameBiMap.get(view.getClient()));
        try {
            view.getClient().updateGamesSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client\n" + e.getMessage());
        }
    }

    private void reconnect(Client client, String nickname, GameControllerImpl gameController) {
        if (gameToDisconnectedUsers.containsKey(gameController)){
            Set<String> disconnectedUsers = gameToDisconnectedUsers.get(gameController);
            disconnectedUsers.remove(nickname);
        }
        clientNicknameBiMap.put(client, nickname);
        loggedOutClients.remove(client);
        gameController.reconnect(client, nickname);
    }

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

    private int createGameID() {
        int gameID;
        do {
            gameID = new Random().nextInt(100) + 1;
        } while (gameIDToGame.containsKey(gameID));
        return gameID;
    }

    public GameControllerImpl getGameControllerImpl(Client client) {
        return userToGame.get(clientNicknameBiMap.get(client));
    }

}
