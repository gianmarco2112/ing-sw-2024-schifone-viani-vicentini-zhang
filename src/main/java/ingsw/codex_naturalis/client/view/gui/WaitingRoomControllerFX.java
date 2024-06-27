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
     * Method called when a player clicks the leave button while he is waiting
     */
    @FXML
    void actionIamLeave(ActionEvent event) {
        viewGUI.leaveGame();
    }

    /**
     * Method called when a player clicks the ready button
     */
    @FXML
    void actionIamReady(ActionEvent event) {
        btnReady.setVisible(false);
        pressEnter.setText("You are ready to play. Please wait for the other players to be ready");
        viewGUI.playerPressEnter();
    }
    /**
     * ViewGUI's setter
     * @param viewGUI : the view to set
     */
    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * When the game reaches the declared number of player
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
     * It sets the nickname of the player
     * @param nickname nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * After the player clicks on ready button, this method makes that button invisible
     */
    public void setConfirmedView() {
        btnReady.setVisible(false);
    }

    /**
     * It shows player lobby card
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
     * It shows player's nickname and game id of the joined game
     * @param nickname nickname
     * @param gameID gameId
     */
    public void showNicknameAndGameid(String nickname, int gameID){
        nicknameLable.setText(nickname);
        gameidLabel.setText("Game ID: " + gameID);
    }

    /**
     * If shows number of players van (in which there will be a player card) equal to the number of players
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
     * When a player clicks the ready button is not allowed to leave anymore
     */
    public void setLeaveNotAllowed() {
        Platform.runLater(()->{
            btnLeave.setVisible(false);
        });
    }
}
