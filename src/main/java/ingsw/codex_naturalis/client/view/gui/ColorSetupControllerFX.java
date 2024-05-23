package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.enumerations.Color;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ColorSetupControllerFX implements Initializable {
    private GraphicalUI viewGUI;
    @FXML
    private ImageView bluePawn;

    @FXML
    private Label colorTaken;

    @FXML
    private ImageView greenPawn;

    @FXML
    private ImageView redPawn;

    @FXML
    private ImageView yellowPawn;

    @FXML
    void blueSelected(MouseEvent event) {
        viewGUI.colorChoosed(Color.BLUE);
    }

    @FXML
    void greenSelected(MouseEvent event) {
        viewGUI.colorChoosed(Color.GREEN);
    }

    @FXML
    void redSelected(MouseEvent event) {
        viewGUI.colorChoosed(Color.RED);
    }

    @FXML
    void yellowSelected(MouseEvent event) {
        viewGUI.colorChoosed(Color.YELLOW);
    }

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String rossa = "/pedine/pedina_rossa.png";
        String blu = "/pedine/pedina_blu.png";
        String gialla = "/pedine/pedina_gialla.png";
        String verde = "/pedine/pedina_verde.png";

        try (InputStream rossaStream = getClass().getResourceAsStream(rossa);
             InputStream bluStream = getClass().getResourceAsStream(blu);
             InputStream giallaStream = getClass().getResourceAsStream(gialla);
             InputStream verdeStream = getClass().getResourceAsStream(verde)) {

            bluePawn.setImage(new Image(bluStream));
            redPawn.setImage(new Image(rossaStream));
            yellowPawn.setImage(new Image(giallaStream));
            greenPawn.setImage(new Image(verdeStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectedColor(Color color) {
        Platform.runLater(()->{
            switch (color){
                case RED -> redPawn.setVisible(false);
                case BLUE -> bluePawn.setVisible(false);
                case GREEN -> greenPawn.setVisible(false);
                case YELLOW -> yellowPawn.setVisible(false);
            }
            redPawn.setDisable(true);
            bluePawn.setDisable(true);
            greenPawn.setDisable(true);
            yellowPawn.setDisable(true);
        });
    }
}
