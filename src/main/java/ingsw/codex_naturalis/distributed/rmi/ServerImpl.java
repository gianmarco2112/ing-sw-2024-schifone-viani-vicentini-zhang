package ingsw.codex_naturalis.distributed.rmi;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;
import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.controller.setupPhase.SetupController;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.gameplayPhase.Observer;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl implements Server, Observer<Game, Event> {


    public record GameSpecs(int ID, int currentNumOfPlayers, int maxNumOfPlayers) implements Serializable {
        @Serial
        private static final long serialVersionUID = 11L; }

    private final List<Client> newClients = new ArrayList<>();

    private final List<SetupController> setupControllersOfStartingGames = new ArrayList<>();
    private final List<SetupController> setupControllers = new ArrayList<>();
    private final List<GameplayController> gameplayControllers = new ArrayList<>();



    @Override
    public void register(Client client) {

        try {
            client.updateLobbyUI(getGamesSpecs());
        } catch (RemoteException e) {
            System.err.println("Error while updating the client");
        }

    }

    private List<ServerImpl.GameSpecs> getGamesSpecs() {
        List<ServerImpl.GameSpecs> gamesSpecs = new ArrayList<>();
        for (SetupController setupControllerOfStartingGame : setupControllersOfStartingGames) {
            gamesSpecs.add(new GameSpecs(setupControllerOfStartingGame.getModel().getGameID(),
                    setupControllerOfStartingGame.getModel().getPlayerOrder().size(),
                    setupControllerOfStartingGame.getModel().getNumOfPlayers()));
        }
        return gamesSpecs;
    }






    @Override
    public void updateNetworkProtocol(Client client, NetworkProtocol networkProtocol) {
        //TODO Network Choice
    }

    @Override
    public synchronized void updateGameToAccess(Client client, int gameID, String nickname) throws NicknameAlreadyExistsException, MaxNumOfPlayersInException, NotReachableGameException{

        SetupController setupController = null;
        for (SetupController setupControllerOfStartingGame : setupControllersOfStartingGames){
            if (setupControllerOfStartingGame.getModel().getGameID() == gameID)
                setupController = setupControllerOfStartingGame;
        }
        if (setupController == null)
            throw new NotReachableGameException();

        setupController.addView(client);

        Game gameToAccess = setupController.getModel();
        List<Client> clients = setupController.getViews();

        Player player = new Player(nickname);
        try {
            gameToAccess.addPlayer(player);
            try {
                client.setNickname(nickname);
            } catch (RemoteException e) {
                System.err.println("Error while updating the client");
            }
            if (gameToAccess.getPlayerOrder().size() < gameToAccess.getNumOfPlayers()) {
                try {
                    client.updateView(GameStatus.SETUP, PlayersConnectedStatus.WAIT);
                } catch (RemoteException e) {
                    System.err.println("Error while updating the client");
                }
            }
            else {
                setupControllersOfStartingGames.remove(setupController);
                setupControllers.add(setupController);
                try {
                    client.updateView(GameStatus.SETUP, PlayersConnectedStatus.GO);
                } catch (RemoteException e) {
                    System.err.println("Error while updating the client");
                }
                for (Client c : clients)
                    if (!c.equals(client)) {
                        try {
                            c.updatePlayersConnectedStatus(PlayersConnectedStatus.GO);
                        } catch (RemoteException e) {
                            System.err.println("Error while updating the client");
                        }
                    }
            }

        } catch (NicknameAlreadyExistsException | MaxNumOfPlayersInException e) {

            setupController.removeView(client);
            try {
                client.setNickname(null);
            } catch (RemoteException ex) {
                System.err.println("Error while updating the client");
            }
            throw e;

        }
    }

    @Override
    public synchronized void updateNewGame(Client client, int numOfPlayers, String nickname) {

        int gameID = new Random().nextInt(100) + 1;
        Game game = new Game(gameID, numOfPlayers);
        Player player = new Player(nickname);
        game.addPlayer(player);

        try {
            client.setNickname(nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the client");
        }

        SetupController setupController = new SetupController(game, client);

        setupControllersOfStartingGames.add(setupController);

        try {
            client.updateView(GameStatus.SETUP, PlayersConnectedStatus.WAIT);
        } catch (RemoteException e) {
            System.err.println("Error while updating the client");
        }

    }



    private SetupController findSetupControllerByClient(Client client) throws NoControllerFoundException{
        for (SetupController setupController : setupControllers)
            if (setupController.getViews().contains(client))
                return setupController;
        throw new NoControllerFoundException();
    }

    @Override
    public void updateReady(Client client) {
        findSetupControllerByClient(client).updateReady(client);
    }

    @Override
    public void updateInitialCard(Client client, InitialCardEvent initialCardEvent) {
        findSetupControllerByClient(client).updateInitialCard(client, initialCardEvent);
    }

    @Override
    public void updateColor(Client client, Color color) {
        findSetupControllerByClient(client).updateColor(client, color);
    }


    @Override
    public void updateFlipCard(Client client, FlipCard flipCard) {
        //this.gameplayController.updateFlipCard(client, flipCard);
    }

    @Override
    public void updatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException {
        //this.gameplayController.updatePlayCard(client, playCard, x, y);
    }

    @Override
    public void updateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {
       // this.gameplayController.updateDrawCard(client, drawCard);
    }

    @Override
    public void updateText(Client client, Message message, String content, List<String> receivers) {
        //this.gameplayController.updateText(client, message, content, receivers);
    }




    @Override
    public void update(Game o, Event arg, String playerWhoUpdated) {

        for (SetupController setupController : setupControllers) {
            if (o.equals(setupController.getModel())) {
                for (Client client : setupController.getViews()) {
                    try {
                        client.updateGameUI(o.getImmutableGame(client.getNickname()), arg, playerWhoUpdated);
                    } catch (RemoteException e) {
                        System.err.println("Error while getting the client nickname");
                    }
                }
                return;
            }
        }

        for (GameplayController gameplayController : gameplayControllers) {
            if (o.equals(gameplayController.getModel())) {
                for (Client client : gameplayController.getViews()) {
                    try {
                        client.updateGameUI(o.getImmutableGame(client.getNickname()), arg, playerWhoUpdated);
                    } catch (RemoteException e) {
                        System.err.println("Error while getting the client nickname");
                    }
                }
                return;
            }
        }

    }
}
