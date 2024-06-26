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

/**
 * This class is the controller for the view of setup phase
 */
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

    /**
     * method called if user clicked on blue pawn image
     */
    @FXML
    void blueSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.BLUE);
    }

    /**
     * method called if user clicked on green pawn image
     */
    @FXML
    void greenSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.GREEN);
    }

    /**
     * method called if user clicked on red pawn image
     */
    @FXML
    void redSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.RED);
    }

    /**
     * method called if user clicked on yellow pawn image
     */
    @FXML
    void yellowSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.YELLOW);
    }

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * Initialization method called by JavaFX, it streams the pawn images
     */
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

    /**
     * method called when player chose the color and set the color invisible and made other pawn unable to click
     * @param color color chosen
     */
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
