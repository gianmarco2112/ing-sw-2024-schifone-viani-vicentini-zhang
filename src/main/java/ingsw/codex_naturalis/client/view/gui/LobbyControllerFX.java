package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.client.ClientImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

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

    public void setData(GameSpecs lobby) {
        bulletPointTile.setImage(new Image(Objects.requireNonNull(getClass().getResource("/lobbiesPageResources/title.png")).toString()));
        GameNameJoin_LB.setText(String.valueOf(lobby.ID()));
        MaxNumPlayers_LB.setText(lobby.maxNumOfPlayers() + "");
        NumPlayersConnected_LB.setText(lobby.currentNumOfPlayers() + "");
        JoinGame_BTN.setOnAction(actionEvent -> viewGUI.endLobbyPhase(lobby.ID()));
    }
}
