package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
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
    private HashMap<Integer,LobbyControllerFX> lobbiesFX;
    private GraphicalUI viewGUI;

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbiesFX = new HashMap<>();
        ObservableList<Integer> playerOptions = FXCollections.observableArrayList(2, 3, 4);
        Players_CB.setItems(playerOptions);
    }

    public void createLobbies(List<GameSpecs> lobbies) {
        for(GameSpecs lobby : lobbies) {
            System.out.println(lobby.ID());
            if (lobby.currentNumOfPlayers()<=lobby.maxNumOfPlayers()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXML/LobbyFXML.fxml"));
                try {
                    HBox lobbyHBox = fxmlLoader.load();
                    lobbyHBox.maxWidthProperty().bind(LobbyContainer.widthProperty());
                    lobbyHBox.minWidthProperty().bind(LobbyContainer.widthProperty());
                    LobbyControllerFX lobbyControllerFX = fxmlLoader.getController();
                    lobbyControllerFX.setViewGUI(viewGUI);
                    lobbiesFX.put(lobby.ID(), lobbyControllerFX);
                    lobbyControllerFX.setData(lobby);
                    LobbyContainer.getChildren().add(lobbyHBox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void updateLobbies(List<GameSpecs> lobbies) {
        LobbyContainer.getChildren().clear();
        createLobbies(lobbies);
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
        viewGUI.endLobbiesPhase(Players_CB.getValue());
    }
    public Integer getNumOfPlayers() {
        return Players_CB.getValue();
    }
}
