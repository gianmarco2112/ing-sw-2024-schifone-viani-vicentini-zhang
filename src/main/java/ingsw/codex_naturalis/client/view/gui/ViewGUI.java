/*
package ingsw.codex_naturalis.view.GUI;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.client.ClientImpl;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.server.model.Game;
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

public class ViewGUI extends Application implements View {
    private final HashMap<String, String> scenes;
    private String nickname;
    private int gameID;

    private Stage stage;
    private FXMLLoader fxmlLoader;
    private GameControllerFX gameControllerFX;
    private LobbiesControllerFX lobbiesControllerFX;
    private LoginControllerFX loginControllerFX;

    private EndGameControllerFX endGameControllerFX;

    private Boolean reconnecting = false;
    private ClientImpl client;
    private Scene scene ;

    public static void main(String[] args) {
        launch((String) null);
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
        run();
    }

    public ViewGUI() {
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

    @Override
    public void update(Game.Immutable msg, GameStatus ev) {
        this.gameID = msg.gameID();
        if(ev == GameStatus.ENDGAME || msg.gameStatus() == GameStatus.ENDGAME) {
            setScene("EndGame");
            Platform.runLater(() -> {
                endGameControllerFX.createRankingTable(msg);
            });
            return;
        }
        if (gameControllerFX != null) {
            gameControllerFX.update(ev, msg);
        } else {
            setScene("Game");
        }
    }

    */
/**
     * Set the scene of the stage to the one specified by the sceneName parameter.
     * @param sceneName The name of the scene to set
     *//*

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
                    loginControllerFX = fxmlLoader.getController();
                    loginControllerFX.setClient(client,this);
                    break;
                case "Game":
                    stage.setMinWidth(1280);
                    stage.setMinHeight(720);
                    gameControllerFX = fxmlLoader.getController();
                    //this.nickname = this.client.getNickname();
                    gameControllerFX.setMyNickname(this.nickname);
                    gameControllerFX.setClient(this.client);
                    break;
                case "EndGame":
                    endGameControllerFX = fxmlLoader.getController();
                    break;
                case "Lobbies":
                    lobbiesControllerFX = fxmlLoader.getController();
                    lobbiesControllerFX.setClient(client,this);
                    break;
            }
            stage.show();
        });
    }

    @Override
    public void run() {
        setScene("Login");
    }

    @Override
    public void endLoginPhase() {
        // the Client object is created during the login phase, after a name has been chosen
        this.client = loginControllerFX.getClient();
        if (reconnecting) {
            setScene("Game");
        } else {
            setScene("Lobbies");
        }
    }

    @Override
    public void endLobbyPhase() {
        setScene("Game");
    }

    @Override
    public int getGameID() {
        return gameID;
    }

    @Override
    public Game.Immutable getGameView() {
        return null;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void setAvailableGames(List<GameControllerImpl> availableGamesList) {
        Platform.runLater(() -> {
            if (lobbiesControllerFX != null) {
                lobbiesControllerFX.updateLobbies(availableGamesList);
                //update...
            } else {
                System.out.println("LobbiesControllerFX is null. Unable to set available games.");
            }
        });
    }

    @Override
    public void setReconnecting(boolean reconnecting) {
        this.reconnecting = reconnecting;
    }

    @Override
    public void nicknameAlreadyUsed() {
        loginControllerFX.nicknameAlreadyUsed();
    }
}
*/
