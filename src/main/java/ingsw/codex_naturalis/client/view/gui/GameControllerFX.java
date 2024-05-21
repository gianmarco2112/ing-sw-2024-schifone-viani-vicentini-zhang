package ingsw.codex_naturalis.client.view.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class GameControllerFX implements Initializable {
    @FXML
    private ScrollPane chatBox;

    @FXML
    private Label chatLBL;

    @FXML
    private TextField chatTextField;

    @FXML
    private Label confirm_ready_to_play_LBL;

    @FXML
    private Button enterButton;

    @FXML
    private Button flipCard1;

    @FXML
    private Button flipCard2;

    @FXML
    private Button flipCard3;

    @FXML
    private Label gameUpdateLBL;

    @FXML
    private ScrollPane gameUpdates;

    @FXML
    private BorderPane globalPane;

    @FXML
    private ScrollPane myPlayerArea;

    @FXML
    private AnchorPane overlay;

    @FXML
    private ScrollPane playerArea1;

    @FXML
    private ScrollPane playerArea2;

    @FXML
    private ScrollPane playerArea3;

    @FXML
    private Label secretObjextiveLBL;

    @FXML
    private Button sendMessageButton;

    @FXML
    private ImageView spinner;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Label username3;

    @FXML
    private Label waiting_for_players_LBL;
    private GraphicalUI viewGUI;

    WaitingForPlayersControllerFX waitingForPlayersControllerFX;

    public void allPlayersJoined() {
        /*spinner.setVisible(false);
        waiting_for_players_LBL.setVisible(false);
        confirm_ready_to_play_LBL.setVisible(true);
        enterButton.setVisible(true);*/
        waitingForPlayersControllerFX.ready(true);
        waitingForPlayersControllerFX.full();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/WaitingForPlayersFXML.fxml"));
            Parent waitingForPlayersRoot = fxmlLoader.load();
            waitingForPlayersControllerFX = fxmlLoader.getController();
            waitingForPlayersControllerFX.setView(viewGUI);
            waitingForPlayersControllerFX.ready(false);
            //waitingForPlayersControllerFX.initializes();

            globalPane.getChildren().add(waitingForPlayersRoot);

            globalPane.getStyleClass().add("waiting-page-image");

        } catch (Exception e) {
            e.printStackTrace();
        }






        username1.setVisible(false);
        username2.setVisible(false);
        username3.setVisible(false);

        secretObjextiveLBL.setVisible(false);
        sendMessageButton.setVisible(false);

        myPlayerArea.setVisible(false);
        playerArea1.setVisible(false);
        playerArea2.setVisible(false);
        playerArea3.setVisible(false);

        gameUpdates.setVisible(false);
        gameUpdateLBL.setVisible(false);

        chatBox.setVisible(false);
        chatLBL.setVisible(false);
        chatTextField.setVisible(false);

        flipCard1.setVisible(false);
        flipCard2.setVisible(false);
        flipCard3.setVisible(false);
    }

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;

        waitingForPlayersControllerFX.nickname(viewGUI.getNickname(),viewGUI.getGameId());
        waitingForPlayersControllerFX.initializes(viewGUI.getMaxNumOfPlayers());
    }
}
