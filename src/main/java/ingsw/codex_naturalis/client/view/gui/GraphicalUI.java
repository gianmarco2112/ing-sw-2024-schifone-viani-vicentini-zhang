package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.client.ServerStub;
import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.common.immutableModel.*;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GraphicalUI extends Application implements UI {

    private ClientImpl client;
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
    private List<String> playerOrder;
    private String nickname;
    private List<ImmPlayableCard> initialCards = new ArrayList<>();
    private int maxNumOfPlayers;
    private List<Color> colors = new ArrayList<>();
    private Boolean rejoined = false;
    private GameControllerFX gameControllerFX;
    private LobbiesControllerFX lobbiesControllerFX;
    private LoginControllerFX loginControllerFX;
    private EndGameControllerFX endGameControllerFX;
    private WaitingRoomControllerFX waitingRoomControllerFX;
    private ColorSetupControllerFX colorSetupControllerFX;
    private CardsSetupControllerFX cardsSetupControllerFX;
    private Boolean startFirstSetup = false;
    private Boolean startSecondSetup = false;
    private Boolean allPlayersJoined = false;

    private static String networkProtocol;
    private static String ipAddress = "localhost";

    public static void main(String[] args){
        networkProtocol = args[0];
        ipAddress = args[1];
        launch();
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
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        setScene("Lobbies");
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
        setScene("WaitingRoom");
    }

    @Override
    public void allPlayersJoined() {
        allPlayersJoined = true;
        setScene("WaitingRoom");
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
        startFirstSetup = true;
    }

    private void secondSetup() {
        setScene("CardsSetup");
        startSecondSetup = true;
    }

    @Override
    public void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent) {
        this.game = game;
        if(initialCardEvent == InitialCardEvent.PLAY) {
            setScene("ColorSetup");
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
        playerOrder = new ArrayList<>(game.playerOrderNicknames());
        maxNumOfPlayers = playerOrder.size();
        playerOrder.remove(nickname);
        //when exit and join another game
        initialCards.clear();
        colors.clear();
        /////////////////////////////////////
        for(int i = 0; i<playerOrder.size(); i++){
            initialCards.add(game.otherPlayers().get(i).playerArea().area().get(List.of(0,0)));
            colors.add(game.otherPlayers().get(i).color());
        }

        setScene("Game");

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
        System.out.println(playerNicknameWhoUpdated + "ha giocato una carta");
        this.game = immGame;
        //devo aggiornare punteggi
        //gameControllerFX.handlerCornerClick(null,cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked,
        //true,game.player().playerArea().points(),myColor); //TODO NO myColor, ma è il colore del giocatore che ha giocato la carta, la pedina si deve aggiornare per tutti

        if(playerNicknameWhoUpdated.equals(this.game.player().nickname())){
            gameControllerFX.cardPlayed(cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked,
                    game.player().playerArea().points());
        }else{
            //TODO aggiornare i campi degli altri
            gameControllerFX.updateUardPlayedForOthers(playerNicknameWhoUpdated,immGame, cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked);
        }

    }

    @Override
    public void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated) {
        System.out.println(playerNicknameWhoUpdated + "ha pescato una carta");
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
                    game.revealedGoldCards().getFirst().cardID(),game.revealedGoldCards().getLast().cardID(),
                    playerNicknameWhoUpdated, immGame
            );
        }

    }

    @Override
    public void turnChanged(String currentPlayer) {
        gameControllerFX.turnChanged(currentPlayer);
    }

    @Override
    public void messageSent(ImmGame immGame) {
        this.game = immGame;

        gameControllerFX.messageSent(immGame);
    }

    @Override
    public void twentyPointsReached(ImmGame immGame) {
        this.game = immGame;
        gameControllerFX.twentyPointsReached(immGame);
    }

    @Override
    public void decksEmpty(ImmGame immGame) {
        this.game = immGame;
        gameControllerFX.deckesEmpty(immGame);
    }

    @Override
    public void gameEnded(List<ImmPlayer> players) {
        gameControllerFX.gameEnded(players);
    }

    @Override
    public void gameCanceled() {
        gameControllerFX.gameCanceled();
    }

    @Override
    public void gameLeft() {
        setScene("Lobbies");
    }

    @Override
    public void gameRejoined(ImmGame game) {
        this.game = game;
        client.updateGetGameController();
        rejoined = true;
        setScene("Game");
    }

    @Override
    public void updatePlayerInGameStatus(ImmGame immGame, String playerNickname, boolean inGame, boolean hasDisconnected) {
        this.game = immGame;
        if (inGame)
            gameControllerFX.updatePlayerInGameStatus("has rejoined the game",immGame,playerNickname);
        else {
            if (hasDisconnected)
                gameControllerFX.updatePlayerInGameStatus("has disconnected!",immGame,playerNickname);
            else
                gameControllerFX.updatePlayerInGameStatus("has left the game",immGame,playerNickname);
        }
    }

    @Override
    public void gamePaused() {
        gameControllerFX.gamePaused();
    }

    @Override
    public void gameResumed() {
        gameControllerFX.gameResumed();
        turnChanged(game.currentPlayerNickname());
    }

    @Override
    public void playerIsReady(String playerNickname) {
        System.out.println(playerNickname + " è pronto!");
        if(playerNickname.equals(nickname)){
            waitingRoomControllerFX.setConfirmedView();
        }
        waitingRoomControllerFX.showAvatar(playerNickname);
    }

    public GraphicalUI() {
        try {
            switch (networkProtocol) {
                case "RMI" -> {
                    createRMIClient();
                }
                case "socket" -> {
                    createSocketClient();
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException();
        }
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

    private void createRMIClient() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, 1235);
        Server server;
        try {
            server = (Server) registry.lookup("Server");
        } catch (NotBoundException e) {
            throw new RemoteException();
        }

        this.client = new ClientImpl(server, NetworkProtocol.RMI, this);
    }

    private void createSocketClient() throws RemoteException {
        ServerStub serverStub = new ServerStub(ipAddress, 1234);
        this.client = new ClientImpl(serverStub, NetworkProtocol.SOCKET, this);
        new Thread(() -> {
            while (true) {
                try {
                    serverStub.receive();
                } catch (IOException e) {
                    System.err.println("Error: won't receive from server\n" + e.getMessage());
                    try {
                        serverStub.close();
                    } catch (RemoteException ex) {
                        System.err.println("Error while closing connection with server");
                    }
                    System.exit(1);
                }
            }
        }).start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.getIcons().add(new Image(getClass().getResource("/lobbiesPageResources/title.png").toString()));
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setMinWidth(1280);
        stage.setMinHeight(760);
        setScene("Login");
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
                    if(rejoined){
                        gameControllerFX.rejoined(this.game);
                    }else{
                        gameControllerFX.endSetup(hand.get(0).cardID(),hand.get(1).cardID(),hand.get(2).cardID(),
                                commonObjectiveCard.get(0).cardID(),commonObjectiveCard.get(1).cardID(),
                                topGoldCard.cardID(),topResourceCard.cardID(), topGoldCard.kingdom(), topResourceCard.kingdom(),
                                revealedResourceCards.get(0).cardID(),revealedResourceCards.get(1).cardID(),
                                revealedGoldCards.get(0).cardID(),revealedGoldCards.get(1).cardID(),
                                myColor,
                                firstPlayer,
                                maxNumOfPlayers,nickname,playerOrder,initialCards,colors,
                                game

                        );
                    }
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
                    waitingRoomControllerFX.setNickname(nickname);
                    waitingRoomControllerFX.showNicknameAndGameid(nickname,gameID);
                    waitingRoomControllerFX.showPlayerVan(numOfPlayers);
                    if(allPlayersJoined){
                        waitingRoomControllerFX.setConfirmView();
                    }
                    break;
                case "ColorSetup":
                    colorSetupControllerFX = fxmlLoader.getController();
                    colorSetupControllerFX.setViewGUI(this);
                    break;
                case "CardsSetup":
                    cardsSetupControllerFX = fxmlLoader.getController();
                    cardsSetupControllerFX.setViewGUI(this);
                    if(startFirstSetup) {
                        cardsSetupControllerFX.showInitialCard(game.player().initialCard().cardID());
                        initialCardID = game.player().initialCard().cardID();
                        startFirstSetup = false;
                    }
                    if(startSecondSetup) {
                        cardsSetupControllerFX.chooseObjective(game.player().secretObjectiveCards());
                        startSecondSetup = false;
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
        client.ctsUpdateNickname(nickname);
    }

    public void endLobbiesPhase(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        client.ctsUpdateNewGame(numOfPlayers);
        setScene("WaitingRoom");
    }

    public void joinGame(int gameID, int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        client.ctsUpdateGameToAccess(gameID);
        setScene("WaitingRoom");
    }

    public void playerPressEnter(){
        client.ctsUpdateReady();
    }

    public void playingInitialCard(boolean front) {
        if(front) {
            initialCardPlayedFront = true;
            client.ctsUpdateInitialCard(InitialCardEvent.FLIP);
            client.ctsUpdateInitialCard(InitialCardEvent.PLAY);
        }else {
            initialCardPlayedFront = false;
            client.ctsUpdateInitialCard(InitialCardEvent.PLAY);
        }
    }

    public void colorChosen(Color color) {
        client.ctsUpdateColor(color);
    }

    public void objectiveChosen(int i) {
        client.ctsUpdateObjectiveCardChoice(i);
    }

    public void flippingCard(int index) {
        indexOfFlippedHandCard = index;
        client.ctsUpdateFlipCard(index);
    }

    public void playingCard(int selectedCardIndex, int x, int y, String corner, int layoutX, int layoutY) {
        cornerClicked = corner;
        layoutXOfCardClicked = layoutX;
        layoutYOfCardClicked = layoutY;

        client.ctsUpdatePlayCard(selectedCardIndex, x, y);
    }

    public void drawingCard(DrawCardEvent drawCardEvent) {
        Platform.runLater(()->{
            this.drawCardEvent = drawCardEvent;
        });

        client.ctsUpdateDrawCard(drawCardEvent);
    }

    public void sendMessage(String receiver, String text) {
        client.ctsUpdateSendMessage(receiver,text);
    }

    public void leaveGame() {
        client.updateLeaveGame();
    }
}
