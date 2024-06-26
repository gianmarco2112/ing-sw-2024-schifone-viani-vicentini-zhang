package ingsw.codex_naturalis.client.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * This class is the controller for the view of a player card in waitingRoom screen.
 */
public class PlayerLobbyCardControllerFX {
    @FXML
    private Text nicknameLable;

    @FXML
    private AnchorPane playerLobbyCard;

    /**
     * method called when a player is ready
     * @param nicknameLable nickname of the player who is ready
     */
    public void setNicknameLable(String nicknameLable){
        Platform.runLater(()->{
            this.nicknameLable.setText(nicknameLable);
        });
    }
}
