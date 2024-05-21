package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.client.view.UI;
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
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class GraphicalUI extends Application implements UI {
    //////////////////////////////////////////////////////////////////////
    //////////FSM/////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
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
    private State state = GraphicalUI.State.LOGIN;
    private RunningState runningState = GraphicalUI.RunningState.RUNNING;
    private GameState gameState = null;

    private final Object lock = new Object();

    private State getState() {
        synchronized (lock) {
            return state;
        }
    }
    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }
    private RunningState getRunningState() {
        synchronized (lock) {
            return runningState;
        }
    }
    private void setRunningState(RunningState runningState) {
        synchronized (lock) {
            this.runningState = runningState;
            lock.notifyAll();
        }
    }
    private GameState getGameState() {
        synchronized (lock) {
            return gameState;
        }
    }
    private void setGameState(GameState gameState) {
        synchronized (lock) {
            this.gameState = gameState;
            lock.notifyAll();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////












    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////MVC/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    private List<GameSpecs> gamesSpecs = null;
    private int gameID;
    private ImmGame game = null;
    private final Scanner scanner = new Scanner(System.in);
    private String nickname;
    private int maxNunOfPlayers;
    @Override
    public void run() {
        while (true) {
            switch (getRunningState()) {
                case RUNNING -> running();
                case WAITING_FOR_UPDATE -> waitForUpdate();
                case STOP -> { setRunningState(RunningState.RUNNING); }
            }
        }
    }

    private void waitForUpdate() {
        while (getRunningState() == RunningState.WAITING_FOR_UPDATE) {
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

        switch (gameState) {
            case WAITING_FOR_PLAYERS -> {
                scanner.next();//da completare con un bottone che esce mentre si aspettano gli altri giocatori
                if (getRunningState() == RunningState.STOP)
                    return;
                setRunningState(RunningState.WAITING_FOR_UPDATE);
                switch (getGameState()) {
                    case WAITING_FOR_PLAYERS -> uiObservableItem.notifyLeaveGame();
                    case READY -> {
                        uiObservableItem.notifyReady();

                    }
                }
            }
            //case SETUP_INITIAL_CARD -> playingInitialCard();
            //case SETUP_COLOR -> choosingColor();
            //case SETUP_OBJECTIVE_CARD -> choosingObjectiveCard();
            //case PLAYING -> playing();
        }
    }

    private void lobbyView() {
        setScene("Lobbies");
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }
    private void loginView() {
        setScene("Login");
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    @Override
    public void setNickname(String nickname) {
        setState(State.LOBBY);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void reportError(String error) {

    }

    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {
        this.gamesSpecs = gamesSpecs;
        System.out.println(gamesSpecs);
        Platform.runLater(() -> {
            if (lobbiesControllerFX != null) {
                lobbiesControllerFX.updateLobbies(this.gamesSpecs);
            }
        });
    }

    @Override
    public void updateGameID(int gameID) {
        this.gameID = gameID;
        System.out.println("updateGameID " + this.gameID);
        setState(State.GAME);
        setGameState(GameState.WAITING_FOR_PLAYERS);
        setRunningState(GraphicalUI.RunningState.RUNNING);
    }

    @Override
    public void allPlayersJoined() {
        try {
            waitForRunLater();
        } catch (InterruptedException ignored) {}
        if(gameControllerFX!=null){
            gameControllerFX.allPlayersJoined();
        }else{
            System.out.println("gameControllerFX is null");
        }
        setGameState(GameState.READY);
    }

    /**
     * Util method to wait for the JavaFX thread to execute a Runnable.
     * Call this method after executing a command that is queued in the JavaFX thread with
     * a {@code Platform.runLater} call.
     */
    protected static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

    @Override
    public void updateSetup(ImmGame game, GameEvent gameEvent) {
        this.game = game;
        switch (gameEvent) {
            //case SETUP_1 -> firstSetup();
            //case SETUP_2 -> secondSetup();
        }
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
        this.nickname = nickname;
        uiObservableItem.notifyNickname(nickname);
    }
    public void endLobbiesPhase(int numOfPlayers) {
        uiObservableItem.notifyNewGame(numOfPlayers);
        maxNunOfPlayers = numOfPlayers;
    }
    public void endLobbyPhase(int id, int numOfPlayers) {
        setRunningState(RunningState.WAITING_FOR_UPDATE);
        uiObservableItem.notifyGameToAccess(id);
        maxNunOfPlayers = numOfPlayers;
    }
    public String getNickname(){
        return nickname;
    }
    public int getGameId(){
        return gameID;
    }
    public void refreshLobbies(){
        updateGamesSpecs(gamesSpecs);
    }
    public int getMaxNumOfPlayers(){
        return maxNunOfPlayers;
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////











    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////FX///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    private static GraphicalUI instance;
    private UIObservableItem uiObservableItem;
    private final HashMap<String, String> scenes;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
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
            stage.setResizable(false);
            //stage.setMaximized(true);
            stage.setFullScreen(false);
            stage.setMaximized(false);
            stage.setTitle("Codex Naturalis");
            stage.setMinWidth(1280);
            stage.setMinHeight(760);
            switch (sceneName) {
                case "Login":
                    loginControllerFX = fxmlLoader.getController();
                    loginControllerFX.setViewGUI(this);
                    break;
                case "Game":
                    stage.setMinWidth(1280);
                    stage.setMinHeight(760);
                    stage.setResizable(false);
                    gameControllerFX = fxmlLoader.getController();
                    gameControllerFX.setViewGUI(this);
                    break;
                case "EndGame":
                    endGameControllerFX = fxmlLoader.getController();
                    break;
                case "Lobbies":
                    lobbiesControllerFX = fxmlLoader.getController();
                    lobbiesControllerFX.setViewGUI(this);
                    updateGamesSpecs(gamesSpecs);
                    /*lobbiesControllerFX = fxmlLoader.getController();
                    if (lobbiesControllerFX == null) {
                        System.out.println("Il controller è ancora null dopo il caricamento del FXML.");
                    } else {
                        System.out.println("Controller di lobbies caricato con successo.");
                        lobbiesControllerFX.setViewGUI(this);
                        updateGamesSpecs(gamesSpecs);
                    }*/
                    break;
            }
            stage.show();
        });
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
}
