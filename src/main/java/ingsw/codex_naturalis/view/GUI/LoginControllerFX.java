package ingsw.codex_naturalis.view.GUI;

import ingsw.codex_naturalis.client.ClientImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginControllerFX implements Initializable {
    @FXML
    private TextField nickname_LBL;
    @FXML
    private VBox nickname_VB;
    private ClientImpl client;
    private ViewGUI viewGUI;
    public void setClient(ClientImpl client,ViewGUI viewGUI) {
        this.client = client;
        this.viewGUI = viewGUI;
    }

    public ClientImpl getClient() {
        return client;
    }

    public void nicknameAlreadyUsed() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Nickname already used");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nickname_VB.setManaged(true);
        nickname_VB.setVisible(true);
    }

    @FXML
    void sendNickname(ActionEvent event) {
        String nickname = nickname_LBL.getText();
        if (nickname.isEmpty() || !validateString(nickname) || nickname.length() >= 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nickname not inserted");
            alert.showAndWait();
        }else {
            //notify...
            viewGUI.endLoginPhase();
        }
    }

    private boolean validateString(String input) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
