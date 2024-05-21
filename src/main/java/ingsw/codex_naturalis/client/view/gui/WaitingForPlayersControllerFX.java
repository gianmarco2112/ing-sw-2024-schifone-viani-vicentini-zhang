package ingsw.codex_naturalis.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class WaitingForPlayersControllerFX {
    @FXML
    private Button btnReady;

    @FXML
    private Pane content;

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
    private GraphicalUI viewGUI;

    @FXML
    void actionIamReady(ActionEvent event) {


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PlayerLobbyCard1.fxml"));
            Parent playerLobbyCard = fxmlLoader.load();
            PlayerLobbyCardControllerFX playerLobbyCardControllerFX = fxmlLoader.getController();

            List<Pane> panes = Arrays.asList(pane0, pane1, pane2, pane3);

            // Trova il primo Pane libero
            for (Pane pane : panes) {
                if (pane.getChildren().isEmpty()) {
                    pane.getChildren().add(playerLobbyCard);
                    break;
                }
            }

            btnReady.setVisible(false);
            pressEnter.setText("You are ready to play. Please wait for the other players to be ready");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionIamLeave(ActionEvent event) {

    }
    public void ready(Boolean visible){
        btnReady.setVisible(visible);
        pressEnter.setVisible(visible);
    }

    public void full() {
        waitingForPlayers.setVisible(false);
        spinner.setVisible(false);
    }

    public void nickname(String nickname, int gameId) {
        nicknameLable.setText(nickname);
        gameidLabel.setText("GameID: " + gameId);
    }

    public void setView(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    public void initializes(int maxNumOfPlayers){
        List<Pane> panes = Arrays.asList(pane0, pane1, pane2, pane3);
        List<Pane> ready = Arrays.asList(ready0, ready1, ready2, ready3);

        if(maxNumOfPlayers<4){
            System.out.println(maxNumOfPlayers);
            for(int i = maxNumOfPlayers; i <4; i++){
                panes.get(i).setVisible(false);
                ready.get(i).setVisible(false);
                System.out.println(i);
            }
        }
    }
}
