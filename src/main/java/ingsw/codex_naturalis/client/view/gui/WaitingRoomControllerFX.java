package ingsw.codex_naturalis.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    void actionIamLeave(ActionEvent event) {

    }

    @FXML
    void actionIamReady(ActionEvent event) {
        btnReady.setVisible(false);
        pressEnter.setText("You are ready to play. Please wait for the other players to be ready");
        viewGUI.playerPressEnter();
    }

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    public void setConfirmView() {
        btnReady.setVisible(true);
        pressEnter.setVisible(true);

        waitingForPlayers.setVisible(false);
        spinner.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReady.setVisible(false);
        pressEnter.setVisible(false);
    }
}
