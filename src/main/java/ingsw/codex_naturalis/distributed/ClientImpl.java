package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingUI;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientImpl extends UnicastRemoteObject implements Client, LobbyObserver, SetupObserver, GameplayObserver, Runnable {

    private enum UI {
        LOBBY,
        GAME_STARTING,
        SETUP,
        GAMEPLAY
    }

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private String nickname;

    private  UIChoice uiChoice;

    private UI uiState;

    private LobbyUI lobbyView;
    private GameStartingUI gameStartingView;
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
        System.out.println("Please choose your preferred user interface (UI) option:");
        Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();
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
    public void updateLobbyUIGameSpecs(String jsonGamesSpecs) {
        lobbyView.updateGamesSpecs(jsonGamesSpecs);
    }

    @Override
    public void reportError(String error) throws RemoteException {
        System.err.println(error);
    }

    @Override
    public void updateUItoGameStarting(int gameID, String nickname) {

        this.nickname = nickname;

        gameStartingView = uiChoice.createGameStartingUI(gameID);

        lobbyView.stop();
        //lobbyView.deleteObservers();

        uiState = UI.GAME_STARTING;

        executorService.execute(gameStartingView);

    }



    @Override
    public void updateUItoSetup() {

        setupView = uiChoice.createSetupUI();

        switch (uiState) {
            case LOBBY -> {
                lobbyView.stop();
                //lobbyView.deleteObservers();
            }
            case GAME_STARTING -> gameStartingView.stop();
        }

        uiState = UI.SETUP;

        executorService.execute(setupView);

    }





    @Override
    public String getNickname() {
        return nickname;
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

        uiState = UI.LOBBY;
        lobbyView.run();


    }


}
