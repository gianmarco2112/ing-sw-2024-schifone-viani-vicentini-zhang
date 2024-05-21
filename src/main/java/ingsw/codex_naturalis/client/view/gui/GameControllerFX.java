package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;

public class GameControllerFX {
    @FXML
    private ImageView board;

    @FXML
    private ScrollPane chatBox;

    @FXML
    private Label chatLBL;

    @FXML
    private TextField chatTextField;

    @FXML
    private VBox chatbox;

    @FXML
    private ImageView commonObjective1;

    @FXML
    private ImageView commonObjective2;

    @FXML
    private Button flipCard1;

    @FXML
    private Button flipCard2;

    @FXML
    private Button flipCard3;

    @FXML
    private Label gameUpdateLBL;

    @FXML
    private AnchorPane gameUpdates;

    @FXML
    private BorderPane globalPane;

    @FXML
    private ImageView goldDeck;

    @FXML
    private ImageView handCard1;

    @FXML
    private ImageView handCard2;

    @FXML
    private ImageView handCard3;

    @FXML
    private ImageView myInitialCard;

    @FXML
    private ScrollPane myPlayerArea;

    @FXML
    private ImageView mySecretObjective;

    @FXML
    private ScrollPane playerArea1;

    @FXML
    private ScrollPane playerArea2;

    @FXML
    private ScrollPane playerArea3;

    @FXML
    private ImageView resourceDeck;

    @FXML
    private ImageView revealedGold1;

    @FXML
    private ImageView revealedGold2;

    @FXML
    private ImageView revealedResource1;

    @FXML
    private ImageView revealedResource2;

    @FXML
    private Label secretObjextiveLBL;

    @FXML
    private Button sendMessageButton;

    @FXML
    private ImageView user1Initialcard;

    @FXML
    private ImageView user2Initialcard;

    @FXML
    private ImageView user3Initialcard;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Label username3;

    private GraphicalUI viewGUI;

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    public void endSetup(String initialCard, Boolean showingFront) {

        String myinitalcard;
        String handCard1;
        String handCard2;
        String handCard3;

        if(showingFront){
            myinitalcard = "/CardsImages/Initial/fronts/" + initialCard + ".png";
        } else {
            myinitalcard = "/CardsImages/Initial/backs/" + initialCard + ".png";
        }

        try (InputStream initialCardStream = getClass().getResourceAsStream(myinitalcard)) {

            myInitialCard.setImage(new Image(initialCardStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
