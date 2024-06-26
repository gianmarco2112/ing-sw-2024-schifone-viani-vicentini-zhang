package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * This class is the controller for the view of a single game lobby
 */
public class LobbyControllerFX {
    private GraphicalUI viewGUI;
    @FXML
    private Label GameNameJoin_LB;

    @FXML
    private Button JoinGame_BTN;

    @FXML
    private Label MaxNumPlayers_LB;

    @FXML
    private Label NumPlayersConnected_LB;

    @FXML
    private ImageView bulletPointTile;

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * This method is used to set the data of the lobby.
     * @param lobby information of a specific game
     */
    public void setData(GameSpecs lobby) {
        bulletPointTile.setImage(new Image(Objects.requireNonNull(getClass().getResource("/lobbiesPageResources/title.png")).toString()));
        GameNameJoin_LB.setText("GameID: " + lobby.ID());
        MaxNumPlayers_LB.setText(lobby.maxNumOfPlayers() + "");
        NumPlayersConnected_LB.setText(lobby.currentNumOfPlayers() + "");
        JoinGame_BTN.setOnAction(actionEvent -> viewGUI.joinGame(lobby.ID(),lobby.maxNumOfPlayers()));
    }
}
