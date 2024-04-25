package ingsw.codex_naturalis.distributed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.util.LobbyObserver;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.view.UI;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

import java.rmi.RemoteException;
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

        String input;
        while (true) {
            input = s.next();
            try{
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1 -> { return UIChoice.TUI; }
                    case 2 -> { return UIChoice.GUI; }
                    default -> System.err.println("Invalid option");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }

    }



    //client
    @Override
    public void stcUpdateUI(String jsonUI) throws RemoteException {
        UI updateUI = null;
        try {
            updateUI = objectMapper.readValue(jsonUI, UI.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
            return;
        }
        switch (updateUI) {
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
            case GAMEPLAY -> {
                gameplayView = uiChoice.createGameplayUI();
                gameplayView.addObserver(this);
                setupView.stop();
                uiState = UI.GAMEPLAY;
                executorService.execute(gameplayView);
            }
        }
    }

    //client
    @Override
    public void stcUpdateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException {

        List<GameSpecs> gamesSpecs = new ArrayList<>();

        try {
            gamesSpecs = objectMapper.readValue(jsonGamesSpecs, new TypeReference<List<GameSpecs>>(){});
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
    public void stcUpdateGameStartingUIGameID(int gameID) throws RemoteException {

        gameStartingView.updateGameID(gameID);

    }

    @Override
    public void stcUpdateSetupUIInitialCard(String jsonImmGame, String jsonInitialCardEvent) {
        try {
            Game.Immutable game = objectMapper.readValue(jsonImmGame, Game.Immutable.class);
            InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
            setupView.updateInitialCard(game, initialCardEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateSetupUIColor(String jsonColor) throws RemoteException {
        try {
            Color color = objectMapper.readValue(jsonColor, Color.class);
            setupView.updateColor(color);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void reportSetupUIError(String message) {
        setupView.reportError(message);
    }

    @Override
    public void stcUpdateSetupUI(String jsonImmGame, String jsonGameEvent) {
        try {
            Game.Immutable immGame = objectMapper.readValue(jsonImmGame, Game.Immutable.class);
            GameEvent gameEvent = objectMapper.readValue(jsonGameEvent, GameEvent.class);
            setupView.update(immGame, gameEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateSetupUIObjectiveCardChoice(String jsonImmGame) throws RemoteException {
        try {
            Game.Immutable immGame = objectMapper.readValue(jsonImmGame, Game.Immutable.class);
            setupView.updateObjectiveCardChoice(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void stcUpdateGameplayUIPlayerOrder(String jsonImmGame) throws RemoteException {
        try {
            Game.Immutable immGame = objectMapper.readValue(jsonImmGame, Game.Immutable.class);
            gameplayView.updatePlayerOrder(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    //lobby observer
    @Override
    public void ctsUpdateGameToAccess(int gameID, String nickname) {
        try {
            server.ctsUpdateGameToAccess(this, gameID, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    //lobby observer
    @Override
    public void ctsUpdateNewGame(int numOfPlayers, String nickname) {
        try {
            server.ctsUpdateNewGame(this, numOfPlayers, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }



    @Override
    public void ctsUpdateReady() {
        try {
            server.ctsUpdateReady(this);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateInitialCard(InitialCardEvent initialCardEvent) {
        try {
            server.ctsUpdateInitialCard(this, objectMapper.writeValueAsString(initialCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateColor(Color color) {
        try {
            server.ctsUpdateColor(this, objectMapper.writeValueAsString(color));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice) {

        try {
            server.ctsUpdateObjectiveCardChoice(this, objectMapper.writeValueAsString(objectiveCardChoice));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }


    @Override
    public void updateFlipCard(FlipCard flipCard) {
        try {
            server.ctsUpdateFlipCard(this, flipCard);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updatePlayCard(PlayCard playCard, int x, int y) throws NotYourTurnException {
        try {
            server.ctsUpdatePlayCard(this, playCard, x, y);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updateDrawCard(DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {
        try {
            server.ctsUpdateDrawCard(this, drawCard);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }
    @Override
    public void updateText(Message message, String content, List<String> receivers) {
        try {
            server.ctsUpdateText(this, message, content, receivers);
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
