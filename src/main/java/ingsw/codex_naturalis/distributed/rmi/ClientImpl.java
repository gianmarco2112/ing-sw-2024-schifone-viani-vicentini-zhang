package ingsw.codex_naturalis.distributed.rmi;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;
import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.distributed.UIChoice;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.view.GameUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.view.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ClientImpl extends UnicastRemoteObject implements Client, LobbyObserver, SetupObserver, GameplayObserver, Runnable {

    private String nickname;

    private final Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();
    private  UIChoice uiChoice;

    private GameUI currentGameView;

    private  LobbyUI lobbyView;
    private SetupUI setupView;
    private GameplayUI gameplayView;

    private  Server server;

    public ClientImpl(Server server) throws RemoteException{
        super();
        initClientImpl(server);
    }

    public ClientImpl(Server server, int port) throws RemoteException {
        super(port);
        initClientImpl(server);
    }

    public ClientImpl(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initClientImpl(server);
    }

    private void initClientImpl(Server server) throws RemoteException{
        uiChoice = askUIChoice();

        lobbyView = uiChoice.createLobbyUI();
        lobbyView.addObserver(this);

        this.server = server;
        server.register(this);
    }



    private UIChoice askUIChoice() {
        Scanner s = new Scanner(System.in);
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_GREEN = "\u001B[32m";
        String[] colors = {ANSI_RED, ANSI_GREEN, ANSI_BLUE, ANSI_PURPLE};
        String text = "Codex Naturalis!";
        System.out.print("Welcome to ");
        for (int i = 0; i < text.length(); i++) {
            int colorIndex = i % colors.length;
            String color = colors[colorIndex];
            System.out.print(color + text.charAt(i));
        }
        System.out.println(ANSI_RESET);
        System.out.println();
        System.out.println("Before we begin, please choose your preferred user interface (UI) option:");
        for (int key = 0; key < UIChoice.values().length; key++) {
            uiChoices.put(key+1, UIChoice.values()[key]);
        }
        for (Map.Entry<Integer, UIChoice> entry : uiChoices.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }

        while (true) {
            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (uiChoices.containsKey(number))
                    if (uiChoices.get(number) == UIChoice.GUI)
                        System.err.println("We're working on it, please choose an other option.");
                    else
                        return uiChoices.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }
    }



    @Override
    public void updateGameUI(Game.Immutable o, Event arg, String playerWhoUpdated) {
        this.currentGameView.update(o, arg, nickname, playerWhoUpdated);
    }

    @Override
    public void updateLobbyUI(List<ServerImpl.GameSpecs> gamesSpecs) {
        lobbyView.updateGamesSpecs(gamesSpecs);
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void updateView(GameStatus gameStatus, PlayersConnectedStatus playersConnectedStatus) {
        switch (gameStatus) {
            case SETUP -> {
                lobbyView.stop();
                lobbyView.deleteObservers();
                currentGameView = uiChoice.createSetupUI(playersConnectedStatus);
            }
            case GAMEPLAY -> {
                currentGameView.stop();
                currentGameView = uiChoice.createGameplayUI();
            }
        }
        currentGameView.run();
    }

    @Override
    public void updatePlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus) {
        currentGameView.setPlayersConnectedStatus(playersConnectedStatus);
    }


    @Override
    public void updateNetworkProtocol(NetworkProtocol networkProtocol) {
        try {
            server.updateNetworkProtocol(this, networkProtocol);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updateGameToAccess(int gameID, String nickname) {
        try {
            server.updateGameToAccess(this, gameID, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updateNewGame(int numOfPlayers, String nickname) {
        try {
            server.updateNewGame(this, numOfPlayers, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }




    @Override
    public void updateReady() {
        try {
            server.updateReady(this);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void updateInitialCard(InitialCardEvent initialCardEvent) {
        try {
            server.updateInitialCard(this, initialCardEvent);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void updateColor(Color color) {
        try {
            server.updateColor(this, color);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void updateObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice) {

    }


    @Override
    public void updateFlipCard(FlipCard flipCard) {
        try {
            server.updateFlipCard(this, flipCard);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updatePlayCard(PlayCard playCard, int x, int y) throws NotYourTurnException {
        try {
            server.updatePlayCard(this, playCard, x, y);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updateDrawCard(DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {
        try {
            server.updateDrawCard(this, drawCard);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updateText(Message message, String content, List<String> receivers) {
        try {
            server.updateText(this, message, content, receivers);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }





    @Override
    public void run() {

        lobbyView.run();

    }

}
