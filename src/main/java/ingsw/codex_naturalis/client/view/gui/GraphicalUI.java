package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.tui.TextualUI;
import ingsw.codex_naturalis.client.view.util.UIObservableItem;
import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GraphicalUI extends Application implements UI {

    //////////FSM////////////////////////////////////////////////////////
    private enum State {
        LOGIN,
        LOBBY,
        GAME,
        REJOINED
    }
    private enum RunningState {
        RUNNING,
        WAITING_FOR_UPDATE,
        STOP
    }
    private enum GameState {
        WAITING_FOR_PLAYERS,
        READY,
        SETUP_INITIAL_CARD,
        SETUP_COLOR,
        SETUP_OBJECTIVE_CARD,
        PLAYING,
    }
    private GraphicalUI.State state = GraphicalUI.State.LOGIN;
    private GraphicalUI.RunningState runningState = GraphicalUI.RunningState.RUNNING;
    private GraphicalUI.GameState gameState = null;
    private final Object lock = new Object();

    private GraphicalUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }
    private void setState(GraphicalUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }
    private GraphicalUI.RunningState getRunningState() {
        synchronized (lock) {
            return runningState;
        }
    }
    private void setRunningState(GraphicalUI.RunningState runningState) {
        synchronized (lock) {
            this.runningState = runningState;
            lock.notifyAll();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    private List<GameSpecs> gamesSpecs = null;
    private int gameID;
    private ImmGame game = null;
    @Override
    public void run() {
        while (true) {
            switch (getRunningState()) {
                case RUNNING -> running();
                case WAITING_FOR_UPDATE -> waitForUpdate();
                case STOP -> { setRunningState(GraphicalUI.RunningState.RUNNING); }
            }
        }
    }

    private void waitForUpdate() {
        while (getRunningState() == GraphicalUI.RunningState.WAITING_FOR_UPDATE) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Error while waiting for server update");
                }
            }
        }
    }

    private void running() {
        switch (getState()) {
            case LOGIN -> loginView();
            case LOBBY -> lobbyView();
            case GAME -> gameView();
        }
    }

    private void gameView() {
        setScene("Game");
    }

    private void lobbyView() {
        setScene("Lobbies");
        setRunningState(GraphicalUI.RunningState.WAITING_FOR_UPDATE);
    }
    private void loginView() {
        setScene("Login");
        setRunningState(GraphicalUI.RunningState.WAITING_FOR_UPDATE);
    }

    @Override
    public void setNickname(String nickname) {
        setState(GraphicalUI.State.LOBBY);
        setRunningState(GraphicalUI.RunningState.RUNNING);
    }

    @Override
    public void reportError(String error) {

    }

    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {
        this.gamesSpecs = gamesSpecs;
        Platform.runLater(() -> {
            if (lobbiesControllerFX != null) {
                lobbiesControllerFX.updateLobbies(gamesSpecs);
            } else {
                System.out.println("LobbiesControllerFX is null. Unable to set available games.");
            }
        });
    }

    @Override
    public void updateGameID(int gameID) {
        setScene("Game");
    }

    @Override
    public void allPlayersJoined() {

    }

    @Override
    public void updateSetup(ImmGame immGame, GameEvent gameEvent) {

    }

    @Override
    public void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent) {

    }

    @Override
    public void updateColor(Color color) {

    }

    @Override
    public void updateObjectiveCardChoice(ImmGame immGame) {

    }

    @Override
    public void endSetup(ImmGame game) {

    }

    @Override
    public void cardFlipped(ImmGame game) {

    }

    @Override
    public void cardPlayed(ImmGame immGame, String playerNicknameWhoUpdated) {

    }

    @Override
    public void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated) {

    }

    @Override
    public void turnChanged(String currentPlayer) {

    }

    @Override
    public void messageSent(ImmGame immGame) {

    }

    @Override
    public void twentyPointsReached(ImmGame immGame) {

    }

    @Override
    public void decksEmpty(ImmGame immGame) {

    }

    @Override
    public void gameEnded(String winner, List<String> players, List<Integer> points, List<ImmObjectiveCard> secretObjectiveCards) {

    }

    @Override
    public void gameCanceled() {

    }

    @Override
    public void gameLeft() {

    }

    @Override
    public void gameRejoined(ImmGame game) {

    }

    @Override
    public void updatePlayerInGameStatus(ImmGame immGame, String playerNickname, boolean inGame, boolean hasDisconnected) {

    }

    @Override
    public void gamePaused() {

    }

    @Override
    public void gameResumed() {

    }

    public void endLoginPhase(String nickname) {
        uiObservableItem.notifyNickname(nickname);
    }


    public void endLobbiesPhase(int numOfPlayers) {
        uiObservableItem.notifyNewGame(numOfPlayers);
    }

    ////////////////////////////////////////////////////////////////////////////////
    private static GraphicalUI instance;
    private UIObservableItem uiObservableItem;
    private HashMap<String, String> scenes;
    private FXMLLoader fxmlLoader;
    private static Stage stage;
    private static Scene scene;
    private GameControllerFX gameControllerFX;
    private LobbiesControllerFX lobbiesControllerFX;
    private LoginControllerFX loginControllerFX;
    private EndGameControllerFX endGameControllerFX;

    public GraphicalUI(){
        scenes = new HashMap<>();
        scenes.put("Game", "/FXML/GameFXML.fxml");
        scenes.put("EndGame", "/FXML/EndGameFXML.fxml");
        scenes.put("Lobbies", "/FXML/LobbiesFXML.fxml");
        scenes.put("Login", "/FXML/LoginFXML.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(scenes.get("Login")));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static GraphicalUI getInstance() {
        return instance;
    }
    @Override
    public void start(Stage stage) throws Exception {
        instance = this;

        this.stage = stage;
        stage.getIcons().add(new Image(getClass().getResource("/lobbiesPageResources/title.png").toString()));
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setMinWidth(1280);
        stage.setMinHeight(760);
    }

    public void setUIObservableItem(UIObservableItem uiObservableItem) {
        this.uiObservableItem = uiObservableItem;
    }
    private void setScene(String sceneName) {
        Platform.runLater(() -> {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(scenes.get(sceneName)));
            try {
                scene.setRoot(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading scene " + sceneName);
                return;
            }
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.setTitle("Codex Naturalis");
            switch (sceneName) {
                case "Login":
                    //loginControllerFX = fxmlLoader.getController();
                    //loginControllerFX.setViewGUI(this);
                    loginControllerFX = fxmlLoader.getController();
                    if (loginControllerFX == null) {
                        System.out.println("Il controller è ancora null dopo il caricamento del FXML.");
                    } else {
                        System.out.println("Controller di login caricato con successo.");
                        loginControllerFX.setViewGUI(this);
                    }
                    break;
                case "Game":
                    stage.setMinWidth(1280);
                    stage.setMinHeight(720);
                    gameControllerFX = fxmlLoader.getController();
                    break;
                case "EndGame":
                    endGameControllerFX = fxmlLoader.getController();
                    break;
                case "Lobbies":
                    //lobbiesControllerFX = fxmlLoader.getController();
                    //lobbiesControllerFX.setViewGUI(this);
                    lobbiesControllerFX = fxmlLoader.getController();
                    if (lobbiesControllerFX == null) {
                        System.out.println("Il controller è ancora null dopo il caricamento del FXML.");
                    } else {
                        System.out.println("Controller di lobbies caricato con successo.");
                        lobbiesControllerFX.setViewGUI(this);
                        updateGamesSpecs(gamesSpecs);
                    }
                    break;
            }
            stage.show();
        });
    }
    ////////////////////////////////////////////////////////////////////////////////
}
