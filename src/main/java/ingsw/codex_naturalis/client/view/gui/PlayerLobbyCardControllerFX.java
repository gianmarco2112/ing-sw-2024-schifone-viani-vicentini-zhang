package ingsw.codex_naturalis.client.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class PlayerLobbyCardControllerFX {
    @FXML
    private Text nicknameLable;

    @FXML
    private AnchorPane playerLobbyCard;

    public void setNicknameLable(String nicknameLable){
        Platform.runLater(()->{
            this.nicknameLable.setText(nicknameLable);
        });
    }
}
