package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This class is the controller for the view of setup phase
 */
public class CardsSetupControllerFX {
    private GraphicalUI viewGUI;
    @FXML
    private Label objectiveCardLBL;
    @FXML
    private Label initialCardLBL;

    @FXML
    private ImageView backInitial;

    @FXML
    private ImageView frontInitial;

    @FXML
    private ImageView objectiveCard1;

    @FXML
    private ImageView objectiveCard2;

    /**
     * When a player chooses to play initialCard on its back
     * @param event: the click of the mouse
     */
    @FXML
    void backInitialSelected(MouseEvent event) {
        backInitial.setVisible(false);
        viewGUI.playingInitialCard(false);
    }

    /**
     * When a player chooses to play initialCard on its front
     * @param event: the click of the mouse
     */
    @FXML
    void frontInitialSelected(MouseEvent event) {
        frontInitial.setVisible(false);
        viewGUI.playingInitialCard(true);
    }

    /**
     * When a player chooses the first objectiveCard
     * @param event : the click of the mouse
     */
    @FXML
    void objectiveCard1Selected(MouseEvent event) {
        objectiveCard1.setVisible(false);
        viewGUI.objectiveChosen(0);

        objectiveCard2.setDisable(true);
        objectiveCard1.setDisable(true);
    }

    /**
     * When a player chooses the second objectiveCard
     * @param event : the click of the mouse
     */
    @FXML
    void objectiveCard2Selected(MouseEvent event) {
        objectiveCard2.setVisible(false);
        viewGUI.objectiveChosen(1);

        objectiveCard2.setDisable(true);
        objectiveCard1.setDisable(true);
    }
    /**
     * When a player confirm the choice of the card that has made
     * @param event : the click of the mouse
     */
    @FXML
    void cardChoiceConfirmed(MouseEvent event) {

    }
    /**
     * View GUI's setter
     * @param viewGUI : the view to set
     */
    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * Method called to stream card images
     * @param s : InitialCard id
     */
    public void showInitialCard(String s) {
        objectiveCardLBL.setVisible(false);

        System.out.println("initial card " + s);

        String frontImagePath = "/CardsImages/Initial/fronts/" + s + ".png";
        String backImagePath = "/CardsImages/Initial/backs/" + s + ".png";

        try (InputStream frontStream = getClass().getResourceAsStream(frontImagePath);
             InputStream backStream = getClass().getResourceAsStream(backImagePath)) {

            if (frontStream == null) {
                System.out.println("Front image not found: " + frontImagePath);
            } else {
                frontInitial.setImage(new Image(frontStream));
            }

            if (backStream == null) {
                System.out.println("Back image not found: " + backImagePath);
            } else {
                backInitial.setImage(new Image(backStream));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called to stream objective cards
     * @param immObjectiveCards : objectiveCards
     */
    public void chooseObjective(List<ImmObjectiveCard> immObjectiveCards) {
        initialCardLBL.setVisible(false);

        String objective1 = "/CardsImages/Objective/" + immObjectiveCards.get(0).cardID() + ".png";
        String objective2 = "/CardsImages/Objective/" + immObjectiveCards.get(1).cardID() + ".png";

        try (InputStream obj1Stream = getClass().getResourceAsStream(objective1);
             InputStream obj2Stream = getClass().getResourceAsStream(objective2)) {

            objectiveCard1.setImage(new Image(obj1Stream));
            objectiveCard2.setImage(new Image(obj2Stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
