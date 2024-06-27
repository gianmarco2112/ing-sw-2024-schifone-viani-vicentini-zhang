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
     * Method called if the user clicks on blue pawn image
     * @param event : the click of the mouse
     */
    @FXML
    void blueSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.BLUE);
    }

    /**
     * Method called if the user clicks on green pawn image
     * @param event : the click of the mouse
     */
    @FXML
    void greenSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.GREEN);
    }

    /**
     * Method called if the user clicks on red pawn image
     * @param event : the click of the mouse
     */
    @FXML
    void redSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.RED);
    }

    /**
     * Method called if the user clicks on yellow pawn image
     * @param event : the click of the mouse
     */
    @FXML
    void yellowSelected(MouseEvent event) {
        viewGUI.colorChosen(Color.YELLOW);
    }
    /**
     * ViewGUI's setter
     * @param viewGUI : the view to set
     */
    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * Initialization method called by JavaFX, it streams the pawn images
     * @param url : of the image to stream
     * @param resourceBundle : resource bundle
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
     * Method called when player chooses the color and sets the color invisible and makes other pawn unable to click
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
