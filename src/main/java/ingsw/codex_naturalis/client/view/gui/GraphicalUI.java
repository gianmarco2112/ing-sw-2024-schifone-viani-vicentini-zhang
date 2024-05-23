package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.tui.TextualUI;
import ingsw.codex_naturalis.client.view.util.UIObservableItem;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import ingsw.codex_naturalis.common.immutableModel.ImmPlayableCard;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GraphicalUI extends Application implements UI {

    private enum State {
        LOGIN,
        LOBBY,
        WAIT,
        CONFIRM,
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

    private static GraphicalUI instance;
    private UIObservableItem uiObservableItem;
    private final HashMap<String, String> scenes;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private int gameID;
    private int numOfPlayers;
    private ImmGame game;
    private String initialCardID;
    private Boolean initialCardPlayedFront;
    private ImmObjectiveCard myObjectiveCard;
    private List<ImmPlayableCard> hand;
    private List<ImmObjectiveCard> commonObjectiveCard;
    private List<String> readyPlayersList = new ArrayList<>();
    private List<GameSpecs> gamesSpecs;
    private ImmPlayableCard topResourceCard;
    private ImmPlayableCard topGoldCard;
    private List<ImmPlayableCard> revealedResourceCards;
    private List<ImmPlayableCard> revealedGoldCards;
    private Color myColor;
    private int indexOfFlippedHandCard;
    private String firstPlayer;
    private String cornerClicked;
    private int layoutXOfCardClicked;
    private int layoutYOfCardClicked;
    private DrawCardEvent drawCardEvent;
    private GameControllerFX gameControllerFX;
    private LobbiesControllerFX lobbiesControllerFX;
    private LoginControllerFX loginControllerFX;
    private EndGameControllerFX endGameControllerFX;
    private WaitingRoomControllerFX waitingRoomControllerFX;
    private ColorSetupControllerFX colorSetupControllerFX;
    private CardsSetupControllerFX cardsSetupControllerFX;

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

    private void running() {
        switch (getState()) {
            case LOGIN -> loginView();
            case LOBBY -> lobbyView();
            case WAIT -> waitView();
            case CONFIRM -> confirmView();
            //case GAME -> gameView();
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

    private void loginView() {
        setScene("Login");
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    private void lobbyView() {
        setScene("Lobbies");
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    private void waitView() {
        setScene("WaitingRoom");
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    private void confirmView() {
        if(waitingRoomControllerFX==null){
            setScene("WaitingRoom");
            setRunningState(RunningState.WAITING_FOR_UPDATE);
            try {
                waitForRunLater();
            } catch (InterruptedException ignored) {}
        }
        waitingRoomControllerFX.setConfirmView();
    }

    private void gameView() {
        setScene("Game");
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    @Override
    public void reportError(String error) {
        System.out.println(error);
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(error);
            alert.showAndWait();
        });
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void setNickname(String nickname) {
        setState(State.LOBBY);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {
        this.gamesSpecs = gamesSpecs;
        Platform.runLater(() -> {
            if (lobbiesControllerFX != null) {
                lobbiesControllerFX.updateLobbies(this.gamesSpecs);
            }
        });
    }

    @Override
    public void updateGameID(int gameID) {
        this.gameID = gameID;

        setGameState(GameState.WAITING_FOR_PLAYERS);
        setState(State.WAIT);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void allPlayersJoined() {
        setState(State.CONFIRM);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void updateSetup(ImmGame game, GameEvent gameEvent) {
        this.game = game;
        switch (gameEvent) {
            case SETUP_1 -> firstSetup();
            case SETUP_2 -> secondSetup();
        }
    }

    private void firstSetup() {
        setScene("CardsSetup");
        setGameState(GameState.SETUP_INITIAL_CARD);
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    private void secondSetup() {
        setScene("CardsSetup");
        setGameState(GameState.SETUP_OBJECTIVE_CARD);
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    @Override
    public void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent) {
        this.game = game;
        if(initialCardEvent == InitialCardEvent.PLAY) {
            setScene("ColorSetup");
            setRunningState(RunningState.WAITING_FOR_UPDATE);
        }
    }

    @Override
    public void updateColor(Color color) {
        colorSetupControllerFX.selectedColor(color);
    }

    @Override
    public void updateObjectiveCardChoice(ImmGame immGame) {
        this.game = immGame;

    }

    @Override
    public void endSetup(ImmGame game) {
        this.game = game;
        myObjectiveCard = game.player().playerArea().objectiveCard();
        hand = game.player().hand();
        commonObjectiveCard = game.commonObjectiveCards();
        topResourceCard = game.topResourceCard();
        topGoldCard = game.topGoldCard();
        revealedResourceCards = game.revealedResourceCards();
        revealedGoldCards = game.revealedGoldCards();
        myColor = game.player().color();
        firstPlayer = game.playerOrderNicknames().getFirst();
        setScene("Game");
        setState(State.GAME);
        setRunningState(RunningState.WAITING_FOR_UPDATE);
    }

    @Override
    public void cardFlipped(ImmGame game) {
        this.game = game;
        gameControllerFX.cardFlipped(game.player().hand().get(indexOfFlippedHandCard).cardID(),
                game.player().hand().get(indexOfFlippedHandCard).showingFront(),
                game.player().hand().get(indexOfFlippedHandCard).kingdom(),
                game.player().hand().get(indexOfFlippedHandCard).playableCardType(),
                indexOfFlippedHandCard);
    }

    @Override
    public void cardPlayed(ImmGame immGame, String playerNicknameWhoUpdated) {
        System.out.println(playerNicknameWhoUpdated + "ha giocato una parta");
        this.game = immGame;
        //devo aggiornare punteggi
        //gameControllerFX.handlerCornerClick(null,cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked,
                //true,game.player().playerArea().points(),myColor); //TODO NO myColor, ma è il colore del giocatore che ha giocato la carta, la pedina si deve aggiornare per tutti

        if(playerNicknameWhoUpdated.equals(this.game.player().nickname())){
            gameControllerFX.cardPlayed(cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked,
                    game.player().playerArea().points());
        }else{
            //TODO aggiornare i campi degli altri
        }

    }

    @Override
    public void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated) {
        System.out.println(playerNicknameWhoUpdated + "ha pescato una parta");
        this.game = immGame;
        //nel metodo che chiamo successivamente devo usare Platform.runlater!!
        if(playerNicknameWhoUpdated.equals(this.game.player().nickname())){
            gameControllerFX.cardDrawn(drawCardEvent,
                    game.topResourceCard().cardID(),game.topGoldCard().cardID(),
                    game.player().hand().getLast().cardID(),
                    game.revealedResourceCards().getFirst().cardID(),game.revealedResourceCards().getLast().cardID(),
                    game.revealedGoldCards().getFirst().cardID(),game.revealedGoldCards().getLast().cardID());
        }else{
            //aggiorno per tutti
            gameControllerFX.updateOtherAfterCardDrawn(
                    game.topGoldCard().cardID(),
                    game.topResourceCard().cardID(),
                    game.revealedResourceCards().getFirst().cardID(),game.revealedResourceCards().getLast().cardID(),
                    game.revealedGoldCards().getFirst().cardID(),game.revealedGoldCards().getLast().cardID()
            );
        }

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

    @Override
    public void playerIsReady(String playerNickname) {
        System.out.println(playerNickname + " è pronto!");
    }

    public GraphicalUI() {
        scenes = new HashMap<>();
        scenes.put("Game", "/FXML/GameFXML.fxml");
        scenes.put("EndGame", "/FXML/EndGameFXML.fxml");
        scenes.put("Lobbies", "/FXML/LobbiesFXML.fxml");
        scenes.put("Login", "/FXML/LoginFXML.fxml");
        scenes.put("WaitingRoom", "/FXML/WaitingForPlayersFXML.fxml");
        scenes.put("ColorSetup", "/FXML/selectColorGUI.fxml");
        scenes.put("CardsSetup", "/FXML/selectCardsInSetupGUI.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(scenes.get("Login")));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static GraphicalUI getInstance() {
        return instance;
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
                    gameControllerFX.endSetup(initialCardID,
                            initialCardPlayedFront,
                            myObjectiveCard.cardID(),
                            hand.get(0).cardID(),hand.get(1).cardID(),hand.get(2).cardID(),
                            commonObjectiveCard.get(0).cardID(),commonObjectiveCard.get(1).cardID(),
                            topGoldCard.cardID(),topResourceCard.cardID(), topGoldCard.kingdom(), topResourceCard.kingdom(),
                            revealedResourceCards.get(0).cardID(),revealedResourceCards.get(1).cardID(),
                            revealedGoldCards.get(0).cardID(),revealedGoldCards.get(1).cardID(),
                            myColor,
                            firstPlayer
                            );
                    break;
                case "EndGame":
                    endGameControllerFX = fxmlLoader.getController();
                    endGameControllerFX.setViewGUI(this);
                    break;
                case "Lobbies":
                    lobbiesControllerFX = fxmlLoader.getController();
                    lobbiesControllerFX.setViewGUI(this);
                    updateGamesSpecs(gamesSpecs);
                    break;
                case "WaitingRoom":
                    waitingRoomControllerFX = fxmlLoader.getController();
                    waitingRoomControllerFX.setViewGUI(this);
                    break;
                case "ColorSetup":
                    colorSetupControllerFX = fxmlLoader.getController();
                    colorSetupControllerFX.setViewGUI(this);
                    break;
                case "CardsSetup":
                    cardsSetupControllerFX = fxmlLoader.getController();
                    cardsSetupControllerFX.setViewGUI(this);
                    if(getGameState()==GameState.SETUP_INITIAL_CARD){
                        cardsSetupControllerFX.showInitialCard(game.player().initialCard().cardID());
                        initialCardID = game.player().initialCard().cardID();
                    }
                    if(getGameState()==GameState.SETUP_OBJECTIVE_CARD){
                        cardsSetupControllerFX.chooseObjective(game.player().secretObjectiveCards());
                    }
                    break;
            }
            stage.show();
        });
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

    public void endLoginPhase(String nickname) {
        uiObservableItem.notifyNickname(nickname);
    }

    public void endLobbiesPhase(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        uiObservableItem.notifyNewGame(numOfPlayers);
        setScene("WaitingRoom");
    }

    public void joinGame(int gameID, int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        setRunningState(RunningState.WAITING_FOR_UPDATE);
        uiObservableItem.notifyGameToAccess(gameID);
        setScene("WaitingRoom");
    }

    public void playerPressEnter(){
        uiObservableItem.notifyReady();
    }

    public void playingInitialCard(boolean front) {
        if(front) {
            initialCardPlayedFront = true;
            uiObservableItem.notifyInitialCard(InitialCardEvent.FLIP);
            uiObservableItem.notifyInitialCard(InitialCardEvent.PLAY);
        }else {
            initialCardPlayedFront = false;
            uiObservableItem.notifyInitialCard(InitialCardEvent.PLAY);
        }
    }

    public void colorChoosed(Color color) {
        uiObservableItem.notifyColor(color);
    }

    public void choosedObjective(int i) {
        uiObservableItem.notifyObjectiveCardChoice(i);
    }

    public void flippingCard(int index) {
        indexOfFlippedHandCard = index;
        uiObservableItem.notifyFlipCard(index);
    }

    public void playingCard(int selectedCardIndex, int x, int y, String corner, int layoutX, int layoutY) {
        cornerClicked = corner;
        layoutXOfCardClicked = layoutX;
        layoutYOfCardClicked = layoutY;

        uiObservableItem.notifyPlayCard(selectedCardIndex, x, y);
    }

    public void drawingCard(DrawCardEvent drawCardEvent) {
        Platform.runLater(()->{
            this.drawCardEvent = drawCardEvent;
        });

        uiObservableItem.notifyDrawCard(drawCardEvent);
    }
}
