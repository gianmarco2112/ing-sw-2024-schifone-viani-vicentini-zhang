package ingsw.codex_naturalis.distributed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.view.UI;
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

    ObjectMapper objectMapper = new ObjectMapper();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private  UIChoice uiChoice;

    private UI uiState;

    private LobbyUI lobbyView;
    private GameStartingUI gameStartingView;
    private SetupUI setupView;
    private GameplayUI gameplayView;

    private Server server;


    public ClientImpl(Server server) throws RemoteException{
        super();
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

        System.out.print("\n\nWelcome to ");

        //red, green, blue, purple
        String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[34m", "\u001B[35m"};
        String text = "Codex Naturalis!";

        //print fancy codex naturalis text
        for (int i = 0; i < text.length(); i++) {
            int colorIndex = i % colors.length;
            String color = colors[colorIndex];
            System.out.print(color + text.charAt(i));
        }

        //color reset
        System.out.println("\u001B[0m");

        System.out.println("""
                
                --------------------------------------------------------
                Please choose your preferred user interface (UI) option:
                
                (1) Textual User interface - TUI
                (2) Graphical User Interface - GUI
                --------------------------------------------------------
                                
                
                """);

        Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();

        int option;
        while (true) {
            try{
                option = s.nextInt();
                switch (option) {
                    case 1 -> { return UIChoice.TUI; }
                    case 2 -> { System.err.println("We're working on it, please choose an other option."); }
                    default -> System.err.println("Invalid option");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid option");
            }
        }

    }



    //client
    @Override
    public void updateUI(UI ui) throws RemoteException {
        switch (ui) {
            case GAME_STARTING -> {
                gameStartingView = uiChoice.createGameStartingUI();
                lobbyView.stop();
                //lobbyView.deleteObservers();
                uiState = UI.GAME_STARTING;
                executorService.execute(gameStartingView);
            }
            case UI.SETUP -> {
                setupView = uiChoice.createSetupUI();
                setupView.addObserver(this);
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
        }
    }

    //client
    @Override
    public void updateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException {

        List<ServerImpl.GameSpecs> gamesSpecs = new ArrayList<>();

        try {
            gamesSpecs = objectMapper.readValue(jsonGamesSpecs, new TypeReference<List<ServerImpl.GameSpecs>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json value");
            return;
        }

        lobbyView.updateGamesSpecs(gamesSpecs);

    }

    //client
    @Override
    public void reportLobbyUIError(String error) throws RemoteException {
        lobbyView.reportError(error);
    }

    //client
    @Override
    public void updateGameStartingUIGameID(int gameID) throws RemoteException {

        gameStartingView.updateGameID(gameID);

    }

    //client
    @Override
    public void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards) {
        setupView.updateSetup1(initialCard, resourceCards, goldCards);
    }


    //lobby observer
    @Override
    public void updateGameToAccess(int gameID, String nickname) {
        try {
            server.updateGameToAccess(this, gameID, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    //lobby observer
    @Override
    public void updateNewGame(int numOfPlayers, String nickname) {
        try {
            server.updateNewGame(this, numOfPlayers, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }



    //setup observer
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
