package ingsw.codex_naturalis.client.view.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is the controller for the view of waitingRoom screen.
 */
public class WaitingRoomControllerFX implements Initializable {
    private GraphicalUI viewGUI;

    @FXML
    private Button btnLeave;

    @FXML
    private Button btnReady;

    @FXML
    private Pane content;

    @FXML
    private StackPane gamePane;

    @FXML
    private Text gameidLabel;

    @FXML
    private Text nicknameLable;

    @FXML
    private Pane pane0;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Text pressEnter;

    @FXML
    private Pane ready0;

    @FXML
    private Pane ready1;

    @FXML
    private Pane ready2;

    @FXML
    private Pane ready3;

    @FXML
    private ImageView spinner;

    @FXML
    private Text waitingForPlayers;
    private String nickname;

    /**
     * method called when a player click leave button while is waiting
     */
    @FXML
    void actionIamLeave(ActionEvent event) {
        viewGUI.leaveGame();
    }

    /**
     * method called when a player click ready button
     */
    @FXML
    void actionIamReady(ActionEvent event) {
        btnReady.setVisible(false);
        pressEnter.setText("You are ready to play. Please wait for the other players to be ready");
        viewGUI.playerPressEnter();
    }

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * when the game reached number of player
     */
    public void setConfirmView() {
        Platform.runLater(()->{
            btnReady.setVisible(true);
            pressEnter.setVisible(true);

            waitingForPlayers.setVisible(false);
            spinner.setVisible(false);
        });
    }

    /**
     * Initialization method called by JavaFX
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReady.setVisible(false);
        pressEnter.setVisible(false);

        btnLeave.setVisible(true);
    }

    /**
     * it sets the nickname od the player
     * @param nickname nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * after player clicked on ready make that button invisible
     */
    public void setConfirmedView() {
        btnReady.setVisible(false);
    }

    /**
     * it shows player lobby card
     * @param playerNickname player nickname who clicked ready button
     */
    public void showAvatar(String playerNickname) {
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PlayerLobbyCard1.fxml"));
            Parent playerLobbyCard = null;
            try {
                playerLobbyCard = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PlayerLobbyCardControllerFX playerLobbyCardControllerFX = fxmlLoader.getController();

            List<Pane> panes = Arrays.asList(pane0, pane1, pane2, pane3);

            // Trova il primo Pane libero
            for (Pane pane : panes) {
                if (pane.getChildren().isEmpty()) {
                    playerLobbyCardControllerFX.setNicknameLable(playerNickname);
                    pane.getChildren().add(playerLobbyCard);
                    break;
                }
            }
        });
    }

    /**
     * it shows player nickname and game id of the game joined
     * @param nickname nickname
     * @param gameID gameId
     */
    public void showNicknameAndGameid(String nickname, int gameID){
        nicknameLable.setText(nickname);
        gameidLabel.setText("Game ID: " + gameID);
    }

    /**
     * if shows number of players van (in which there will be a player card) equal to number of players
     * @param numOfPlayers number of players
     */
    public void showPlayerVan(int numOfPlayers) {
        List<Pane> panes = Arrays.asList(pane0, pane1, pane2, pane3);
        List<Pane> ready = Arrays.asList(ready0, ready1, ready2, ready3);

        if(numOfPlayers<4){
            for(int i = numOfPlayers; i <4; i++){
                panes.get(i).setVisible(false);
                ready.get(i).setVisible(false);
            }
        }
    }

    /**
     * when a player clicked ready button is not allowed to leave anymore
     */
    public void setLeaveNotAllowed() {
        Platform.runLater(()->{
            btnLeave.setVisible(false);
        });
    }
}
