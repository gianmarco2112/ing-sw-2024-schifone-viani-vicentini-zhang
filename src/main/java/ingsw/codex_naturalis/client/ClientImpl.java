package ingsw.codex_naturalis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.server.model.GameSpecs;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.client.util.LobbyObserver;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.client.util.GameplayObserver;
import ingsw.codex_naturalis.client.util.SetupObserver;
import ingsw.codex_naturalis.common.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.common.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.common.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.gameStartingPhase.GameStartingUI;
import ingsw.codex_naturalis.client.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.common.events.gameplayPhase.DrawCardEvent;
import ingsw.codex_naturalis.client.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.common.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.common.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.client.view.setupPhase.SetupUI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ClientImpl implements Client, LobbyObserver, SetupObserver, GameplayObserver, Runnable {

    private String nickname;

    ObjectMapper objectMapper = new ObjectMapper();

    private final UIChoice uiChoice;

    private UI uiState = UI.NULL;

    private LobbyUI lobbyView;
    private GameStartingUI gameStartingView;
    private SetupUI setupView;
    private GameplayUI gameplayView;

    private final Server server;
    private GameController gameController;


    public ClientImpl(Server server, NetworkProtocol networkProtocol) throws RemoteException{

        uiChoice = askUIChoice();

        this.server = server;

        switch (networkProtocol) {
            case RMI -> server.register((Client) UnicastRemoteObject.exportObject(this, 0));
            case SOCKET -> server.register(this);
        }

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


    @Override
    public String getNickname() throws RemoteException {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) throws RemoteException {
        this.nickname = nickname;
    }

    @Override
    public void setViewAsLobby(String jsonGamesSpecs) throws RemoteException {

        lobbyView = uiChoice.createLobbyUI();
        lobbyView.addObserver(this);
        stcUpdateLobbyUIGameSpecs(jsonGamesSpecs);
        uiState = UI.LOBBY;

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




    @Override
    public void setViewAsGameStarting(int gameID) throws RemoteException {

        gameStartingView = uiChoice.createGameStartingUI();
        gameStartingView.updateGameID(gameID);
        uiState = UI.GAME_STARTING;
        lobbyView.stop();

    }





    @Override
    public void setViewAsSetup() throws RemoteException {

        this.gameController = server.getGameController(this);

        setupView = uiChoice.createSetupUI();
        setupView.addObserver(this);
        switch (uiState) {
            case LOBBY -> {
                uiState = UI.SETUP;
                lobbyView.stop();
            }
            case GAME_STARTING -> {
                uiState = UI.SETUP;
                gameStartingView.stop();
            }
        }

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
    public void reportSetupUIError(String error) {
        setupView.reportError(error);
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
    public void setViewAsGameplay(String jsonImmGame) throws RemoteException {

        try {
            Game.Immutable immGame = objectMapper.readValue(jsonImmGame, Game.Immutable.class);
            gameplayView = uiChoice.createGameplayUI();
            gameplayView.updatePlayerOrder(immGame);
            gameplayView.addObserver(this);
            gameplayView.updatePlayerOrder(immGame);
            uiState = UI.GAMEPLAY;
            setupView.stop();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void stcUpdateGameplayUI(String jsonImmGame) throws RemoteException {
        try {
            Game.Immutable immGame = objectMapper.readValue(jsonImmGame, Game.Immutable.class);
            gameplayView.update(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void reportGameplayUIError(String error) throws RemoteException {
        gameplayView.reportError(error);
    }


    @Override
    public void ctsUpdateGameToAccess(int gameID, String nickname) {
        try {
            server.ctsUpdateGameToAccess(this, gameID, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

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
            gameController.updateReady();
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }

    }

    @Override
    public void ctsUpdateInitialCard(InitialCardEvent initialCardEvent) {

        try {
            gameController.updateInitialCard(nickname, objectMapper.writeValueAsString(initialCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }

    @Override
    public void ctsUpdateColor(Color color) {

        try {
            gameController.updateColor(nickname, objectMapper.writeValueAsString(color));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }

    @Override
    public void ctsUpdateObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice) {

        try {
            gameController.updateObjectiveCard(nickname, objectMapper.writeValueAsString(objectiveCardChoice));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }


    @Override
    public void ctsUpdateFlipCard(FlipCardEvent flipCardEvent) {

        try {
            gameController.updateFlipCard(nickname, objectMapper.writeValueAsString(flipCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }
    @Override
    public void ctsUpdatePlayCard(PlayCardEvent playCardEvent, int x, int y) throws NotYourTurnException {

        try {
            gameController.updatePlayCard(nickname, objectMapper.writeValueAsString(playCardEvent), x, y);
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }
    @Override
    public void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {

        try {
            gameController.updateDrawCard(nickname, objectMapper.writeValueAsString(drawCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }

    }
    @Override
    public void ctsUpdateSendMessage(String receiver, String content) {

        try {
            gameController.updateSendMessage(nickname, receiver, content);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }

    }





    @Override
    public void run() {

        while (true) {
            try { Thread.sleep(0); }
            catch (Exception e) { e.printStackTrace(); }
            switch (uiState) {
                case LOBBY -> lobbyView.run();
                case GAME_STARTING -> gameStartingView.run();
                case SETUP -> setupView.run();
                case GAMEPLAY -> gameplayView.run();
            }
        }

    }


}
