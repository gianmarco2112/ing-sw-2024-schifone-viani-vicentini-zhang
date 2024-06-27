package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.client.ClientCreation;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.client.view.UI;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is the graphical interface of the client.
 */
public class GraphicalUI extends Application implements UI {

    private final ClientImpl client;
    private final HashMap<String, String> scenes;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private final Scene scene;
    private int gameID;
    private int numOfPlayers;
    private ImmGame game;
    private List<GameSpecs> gamesSpecs;
    private int indexOfFlippedHandCard;
    private String cornerClicked;
    private int layoutXOfCardClicked;
    private int layoutYOfCardClicked;
    private DrawCardEvent drawCardEvent;
    private String nickname;
    private Boolean rejoined = false;
    private GameControllerFX gameControllerFX;
    private LobbiesControllerFX lobbiesControllerFX;
    private LoginControllerFX loginControllerFX;
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

    /**
     * To report an error
     * @param error error message to show on alert window
     */
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

    /**
     * It sets the nickname chosen by the player and changes the scene from Login to Lobbies (that contains Lobby
     * a single game match)
     * @param nickname nickname chosen by the player
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        setScene("Lobbies");
    }

    /**
     * It updates the Lobbies than a game is available
     * @param gamesSpecs List of game available to join
     */
    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {
        this.gamesSpecs = gamesSpecs;
        Platform.runLater(() -> {
            if (lobbiesControllerFX != null) {
                lobbiesControllerFX.updateLobbies(this.gamesSpecs);
            }
        });
    }

    /**
     *  Method called when a player creates a new game and sets the scene from Lobbies to WaitingRoom
     *  @param gameID id of the new game created
     */
    @Override
    public void updateGameID(int gameID) {
        this.gameID = gameID;
        setScene("WaitingRoom");
    }

    /**
     * Method called when a game reaches the number of players established
     */
    @Override
    public void allPlayersJoined() {
        allPlayersJoined = true;
        setScene("WaitingRoom");
    }

    /**
     * Method called initially for setup phase before starting game
     * @param game immutable game with the updates
     * @param gameEvent setup 1 or setup 2
     */
    @Override
    public void updateSetup(ImmGame game, GameEvent gameEvent) {
        this.game = game;
        switch (gameEvent) {
            case SETUP_1 -> firstSetup();
            case SETUP_2 -> secondSetup();
        }
    }

    /**
     * Method called for first setup
     */
    private void firstSetup() {
        setScene("CardsSetup");
        startFirstSetup = true;
    }

    /**
     * Method called for second setup
     */
    private void secondSetup() {
        setScene("CardsSetup");
        startSecondSetup = true;
    }

    /**
     * Method called when a player chooses on which side to play the initial card
     * @param game immutable game with updates
     * @param initialCardEvent initialCard event
     */
    @Override
    public void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent) {
        this.game = game;
        if(initialCardEvent == InitialCardEvent.PLAY) {
            setScene("ColorSetup");
        }
    }

    /**
     * Method called when a player has chosen his color
     * @param color color chosen by player
     */
    @Override
    public void updateColor(Color color) {
        colorSetupControllerFX.selectedColor(color);
    }

    /**
     * Method called when a player has chosen his secret objective, it ends setup phase
     * @param immGame immutable game
     */
    @Override
    public void updateObjectiveCardChoice(ImmGame immGame) {
        this.game = immGame;

    }

    /**
     * Method called when setup phase has ended
     * @param game immutable game
     */
    @Override
    public void endSetup(ImmGame game) {
        this.game = game;

        setScene("Game");

    }

    /**
     * Method called when a player flips a card on his hand
     * @param game immutable game
     */
    @Override
    public void cardFlipped(ImmGame game) {
        this.game = game;
        gameControllerFX.cardFlipped(game.player().hand().get(indexOfFlippedHandCard).cardID(),
                game.player().hand().get(indexOfFlippedHandCard).showingFront(),
                game.player().hand().get(indexOfFlippedHandCard).kingdom(),
                game.player().hand().get(indexOfFlippedHandCard).playableCardType(),
                indexOfFlippedHandCard);
    }

    /**
     * Method called when a player plays a card
     * @param immGame immutable game
     * @param playerNicknameWhoUpdated nickname of the player who has played a card
     */
    @Override
    public void cardPlayed(ImmGame immGame, String playerNicknameWhoUpdated) {
        System.out.println(playerNicknameWhoUpdated + "ha giocato una carta");
        this.game = immGame;
        //I need to upload scores
        //gameControllerFX.handlerCornerClick(null,cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked,
        //true,game.player().playerArea().points(),myColor);

        if(playerNicknameWhoUpdated.equals(this.game.player().nickname())){
            gameControllerFX.cardPlayed(cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked,
                    game.player().playerArea().points());
        }else{

            gameControllerFX.updateUardPlayedForOthers(playerNicknameWhoUpdated,immGame, cornerClicked,layoutXOfCardClicked,layoutYOfCardClicked);
        }

    }

    /**
     * Method called when a player draws a card
     * @param immGame immutable game
     * @param playerNicknameWhoUpdated player who has drawn a card
     */
    @Override
    public void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated) {
        System.out.println(playerNicknameWhoUpdated + "ha pescato una carta");
        this.game = immGame;
        List<String> resourceCards = new ArrayList<>();
        if (game.topResourceCard() != null) {
            resourceCards.add(game.topResourceCard().cardID());
        }else{
            resourceCards.add("empty");
        }
        for(ImmPlayableCard c : game.revealedResourceCards()){
            resourceCards.add(c.cardID());
        }
        while(resourceCards.size()!=3){
            resourceCards.add("empty");
        }

        List<String> goldCards = new ArrayList<>();
        if(game.topGoldCard() != null) {
            goldCards.add(game.topGoldCard().cardID());
        }else{
            goldCards.add("empty");
        }
        for(ImmPlayableCard c : game.revealedGoldCards()){
            goldCards.add(c.cardID());
        }
        while(goldCards.size()!=3){
            goldCards.add("empty");
        }
        //In the method invoked after I need to use Platform.runlater!!
        if(playerNicknameWhoUpdated.equals(this.game.player().nickname())){
            gameControllerFX.cardDrawn(drawCardEvent,
                    resourceCards.getFirst(),goldCards.getFirst(),
                    game.player().hand().getLast().cardID(),
                    resourceCards.get(1),resourceCards.getLast(),
                    goldCards.get(1),goldCards.getLast());
        }else{
            //I update for everybody
            gameControllerFX.updateOtherAfterCardDrawn(
                    goldCards.getFirst(),
                    resourceCards.getFirst(),
                    resourceCards.get(1),resourceCards.getLast(),
                    goldCards.get(1),goldCards.getLast(),
                    playerNicknameWhoUpdated, immGame
            );
        }

    }

    /**
     * Method called when the turn changes
     * @param currentPlayer current player
     */
    @Override
    public void turnChanged(String currentPlayer) {
        gameControllerFX.turnChanged(currentPlayer);
    }

    /**
     * Method called when a message has been sent
     * @param immGame immutable game
     */
    @Override
    public void messageSent(ImmGame immGame) {
        this.game = immGame;

        gameControllerFX.messageSent(immGame);
    }

    /**
     * Method called when 20 points has been reached
     * @param immGame immutable game
     */
    @Override
    public void twentyPointsReached(ImmGame immGame) {
        this.game = immGame;
        gameControllerFX.twentyPointsReached(immGame);
    }

    /**
     * Method called when all the decks are empty
     * @param immGame immutable game
     */
    @Override
    public void decksEmpty(ImmGame immGame) {
        this.game = immGame;
        gameControllerFX.deckesEmpty(immGame);
    }

    /**
     * Method called when the game ends
     * @param players list of immutable players
     */
    @Override
    public void gameEnded(List<ImmPlayer> players) {
        gameControllerFX.gameEnded(players);
    }

    /**
     * Method called when game is cancelled
     */
    @Override
    public void gameCanceled() {
        gameControllerFX.gameCanceled();
    }

    /**
     * Method called when a player leaves the game
     */
    @Override
    public void gameLeft() {
        setScene("Lobbies");
        allPlayersJoined = false;
    }

    /**
     * Method called when a player rejoins the game
     * @param game immutable game
     */
    @Override
    public void gameRejoined(ImmGame game) {
        this.game = game;
        client.updateGetGameController();
        rejoined = true;
        setScene("Game");
    }

    /**
     * Method called to update player's inGame status
     * @param immGame immutable game
     * @param playerNickname player who has rejoined, disconnected or left the game
     * @param inGame indicates if the players is still in game
     * @param hasDisconnected indicated if the player has disconnected
     */
    @Override
    public void updatePlayerInGameStatus(ImmGame immGame, String playerNickname, boolean inGame, boolean hasDisconnected) {
        this.game = immGame;
        if (inGame)
            gameControllerFX.updatePlayerInGameStatus("has rejoined the game",immGame,playerNickname);
        else {
            if (hasDisconnected)
                gameControllerFX.updatePlayerInGameStatus("has disconnected!",immGame,playerNickname);
            else {
                gameControllerFX.updatePlayerInGameStatus("has left the game", immGame, playerNickname);
                gameControllerFX.updateChatComboBox(playerNickname);
            }
        }
    }

    /**
     * Method called when the game is paused
     */
    @Override
    public void gamePaused() {
        gameControllerFX.gamePaused();
    }

    /**
     * Method called when the game resumes
     */
    @Override
    public void gameResumed() {
        gameControllerFX.gameResumed();
        turnChanged(game.currentPlayerNickname());
    }

    /**
     * Method called when a player is ready to play
     * @param playerNickname nickname of the player who is ready to play
     */
    @Override
    public void playerIsReady(String playerNickname) {
        System.out.println(playerNickname + " è pronto!");
        if(playerNickname.equals(nickname)){
            waitingRoomControllerFX.setConfirmedView();
        }
        waitingRoomControllerFX.showAvatar(playerNickname);
    }


    /**
     * Constructor for the ViewGUI class. It creates a new Client object and connects to the server.
     * Then it initializes a map containing the names of the scenes and their paths. This will be used
     * to set the scene of the stage
     */
    public GraphicalUI() {
        try {
            this.client = ClientCreation.createClient(this, networkProtocol, ipAddress);
        } catch (RemoteException e) {
            throw new RuntimeException();
        }
        scenes = new HashMap<>();
        scenes.put("Game", "/FXML/GameFXML.fxml");
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

    /**
     * Start the JavaFX application.
     * @param stage The primary stage
     */
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


    /**
     * Set the scene of the stage to the one specified by the sceneName parameter.
     * @param sceneName The name of the scene to set
     */
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
                        rejoined = false;
                    }else{
                        gameControllerFX.endSetup(game);
                    }
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
                        waitingRoomControllerFX.setLeaveNotAllowed();
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
     * Method called by LoginControllerFX when a player clicks the button to confirm his nickname
     * @param nickname nickname
     */
    public void endLoginPhase(String nickname) {
        client.ctsUpdateNickname(nickname);
    }

    /**
     * Method called by LobbiesControllerFX when a player creates a new game
     * @param numOfPlayers num of player of a game
     */
    public void endLobbiesPhase(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        client.ctsUpdateNewGame(numOfPlayers);
        setScene("WaitingRoom");
    }

    /**
     * Method called by LobbyController when a player joins a game
     * @param gameID id of the game the player joined
     * @param numOfPlayers num of the player of the game
     */
    public void joinGame(int gameID, int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        client.ctsUpdateGameToAccess(gameID);
        setScene("WaitingRoom");
    }

    /**
     * Method called by WaitingRoomControllerFX when a player presses ready button
     */
    public void playerPressEnter(){
        client.ctsUpdateReady();
    }

    /**
     * Method called by CardsSetupControllerFX when a player chooses which side of the initialCard to play
     * @param front indicates is player play the initialCard on its front
     */
    public void playingInitialCard(boolean front) {
        if(front) {
            client.ctsUpdateInitialCard(InitialCardEvent.FLIP);
            client.ctsUpdateInitialCard(InitialCardEvent.PLAY);
        }else {
            client.ctsUpdateInitialCard(InitialCardEvent.PLAY);
        }
    }

    /**
     * Method called by ColorSetupControllerFX when a player chooses the color
     * @param color color chosen
     */
    public void colorChosen(Color color) {
        client.ctsUpdateColor(color);
    }

    /**
     * Method called by CardsSetupControllerFX when a player chooses the secretObjectiveCard
     * @param i index of the secretObjecriveCard (0 or 1)
     */
    public void objectiveChosen(int i) {
        client.ctsUpdateObjectiveCardChoice(i);
    }

    /**
     * Method called by GameControllerFX when a player is trying to flip a card
     * @param index index of hand card to flip
     */
    public void flippingCard(int index) {
        indexOfFlippedHandCard = index;
        client.ctsUpdateFlipCard(index);
    }

    /**
     * Method called by GameControllerFX when a player is trying to play a card
     * @param selectedCardIndex selected handCard index
     * @param x coordinate x
     * @param y coordinate y
     * @param corner corner on which the player wants to place the card
     */
    public void playingCard(int selectedCardIndex, int x, int y, String corner, int layoutX, int layoutY) {
        cornerClicked = corner;
        layoutXOfCardClicked = layoutX;
        layoutYOfCardClicked = layoutY;

        client.ctsUpdatePlayCard(selectedCardIndex, x, y);
    }

    /**
     * Method called by GameControllerFX when a player is trying to draw a card
     * @param drawCardEvent drawCardEvent
     */
    public void drawingCard(DrawCardEvent drawCardEvent) {
        Platform.runLater(()->{
            this.drawCardEvent = drawCardEvent;
        });

        client.ctsUpdateDrawCard(drawCardEvent);
    }

    /**
     * Method called by GameControllerFX when a player wants to send a message
     * @param receiver receiver
     * @param text message content
     */
    public void sendMessage(String receiver, String text) {
        client.ctsUpdateSendMessage(receiver,text);
    }

    /**
     * Method called by GameControllerFX when a player clicks the button to leave the game
     */
    public void leaveGame() {
        client.updateLeaveGame();
    }

    /**
     * Method called by GameControllerFX when the game ended: it is used to return back to Lobbies
     */
    public void returnToLobby() {
        setScene("Lobbies");
        allPlayersJoined = false;
    }
}
