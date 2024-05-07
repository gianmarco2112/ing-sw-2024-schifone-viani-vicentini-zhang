package ingsw.codex_naturalis.view.GUI;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.client.ClientImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LobbiesControllerFX implements Initializable {
    @FXML
    private Button CreateGame_BTN;
    @FXML
    private VBox LobbyContainer;
    @FXML
    private ChoiceBox<Integer> Players_CB;
    @FXML
    private Button refresh_BTN;
    private HashMap<Integer,LobbyControllerFX> lobbiesFX;
    private ClientImpl client;
    private String gameID;
    private ViewGUI viewGUI;

    public void setClient(ClientImpl client, ViewGUI viewGUI) {
        this.client = client;
        this.viewGUI = viewGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbiesFX = new HashMap<>();
        ObservableList<Integer> playerOptions = FXCollections.observableArrayList(2, 3, 4);
        Players_CB.setItems(playerOptions);
    }

    public void createLobbies(List<GameControllerImpl> lobbies) {

    }
    public void updateLobbies(List<GameControllerImpl> lobbies) {
        LobbyContainer.getChildren().clear();
        createLobbies(lobbies);
    }
    public void refresh() {
        //notify...
    }
    public void createGame() {
        if(Players_CB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a number of players");
            alert.showAndWait();
            return;
        }
        //notify...
        viewGUI.endLobbyPhase();
    }
}
