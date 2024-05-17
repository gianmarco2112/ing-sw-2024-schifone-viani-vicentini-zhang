package ingsw.codex_naturalis.client.view.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameControllerFX implements Initializable {
    @FXML
    private Label confirm_ready_to_play_LBL;

    @FXML
    private AnchorPane overlay;

    @FXML
    private ImageView spinner;

    @FXML
    private Label waiting_for_players_LBL;
    @FXML
    private Button enterButton;

    public void allPlayersJoined() {
        spinner.setVisible(false);
        waiting_for_players_LBL.setVisible(false);
        confirm_ready_to_play_LBL.setVisible(true);
        enterButton.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirm_ready_to_play_LBL.setVisible(false);
        enterButton.setVisible(false);
    }
}
