package ingsw.codex_naturalis.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginControllerFX implements Initializable {
    private GraphicalUI viewGUI;

    @FXML
    private TextField nickname_LBL;

    @FXML
    void sendNickname(ActionEvent event) {
        String nickname = nickname_LBL.getText();
        if (nickname_LBL.getText().isEmpty() || !validateString(nickname_LBL.getText()) || nickname_LBL.getText().length() >= 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nickname not inserted");
            alert.showAndWait();
        }else {
            viewGUI.endLoginPhase(nickname);
        }
    }

    private boolean validateString(String input) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    @FXML
    private Button endLoginPhaseButton_BTN;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endLoginPhaseButton_BTN.setOnAction(e -> {
            String nickname = nickname_LBL.getText();
            if (nickname_LBL.getText().isEmpty() || !validateString(nickname_LBL.getText()) || nickname_LBL.getText().length() >= 15) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Nickname not inserted");
                alert.showAndWait();
            }else {
                viewGUI.endLoginPhase(nickname);
            }
        });

        endLoginPhaseButton_BTN.setDefaultButton(true);
    }
}
