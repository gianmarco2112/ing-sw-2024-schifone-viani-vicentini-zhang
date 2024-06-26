package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.immutableModel.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Controller for the GameFXML scene.
 * This class is responsible for handling all the events that happen in the game, updating
 * the GUI accordingly to the events received by the server, and collecting the events from the player
 * and notify the to update the Server calling viewGUI's method.
 */
public class GameControllerFX {

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
    private AnchorPane myPlayerAreaAnchorPane;

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
    private ImageView user1Initialcard;

    @FXML
    private AnchorPane user1PlayerArea;

    @FXML
    private ImageView user2Initialcard;

    @FXML
    private AnchorPane user2PlayerArea;

    @FXML
    private ImageView user3Initialcard;

    @FXML
    private AnchorPane user3PlayerArea;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Label username3;

    private GraphicalUI viewGUI;
    private ImageView selectedCard = null;
    private int selectedCardIndex;
    @FXML
    private ImageView user1HandCard1;

    @FXML
    private ImageView user1HandCard2;

    @FXML
    private ImageView user1HandCard3;

    @FXML
    private ImageView user2HandCard1;

    @FXML
    private ImageView user2HandCard2;

    @FXML
    private ImageView user2HandCard3;

    @FXML
    private ImageView user3HandCard1;

    @FXML
    private ImageView user3HandCard2;

    @FXML
    private ImageView user3HandCard3;

    @FXML
    private ListView<String> importantEventsList;

    //posizioni delle anchorPane
    private final HashMap<Integer, List<Integer>> boardPointsPosition = new HashMap<>();
    private String nickname;
    @FXML
    private Text myNickText;
    private final List<AnchorPane> anchorPanesOnBoard = new ArrayList<>();
    private Color myColor;
    private ImmGame game;

    /**
     * initialize board position
     */
    private void initializeBoard(){
        boardPointsPosition.put(0, List.of(32,312));
        boardPointsPosition.put(1, List.of(75,312));
        boardPointsPosition.put(2, List.of(118,312));
        boardPointsPosition.put(3, List.of(139, 272));
        boardPointsPosition.put(4, List.of(96, 272));
        boardPointsPosition.put(5, List.of(54, 272));
        boardPointsPosition.put(6, List.of(11, 272));
        boardPointsPosition.put(7, List.of(11, 234));
        boardPointsPosition.put(8, List.of(54, 234));
        boardPointsPosition.put(9, List.of(96, 234));
        boardPointsPosition.put(10, List.of(139, 234));
        boardPointsPosition.put(11, List.of(139, 194));
        boardPointsPosition.put(12, List.of(96, 194));
        boardPointsPosition.put(13, List.of(54, 194));
        boardPointsPosition.put(14, List.of(11, 194));
        boardPointsPosition.put(15, List.of(11, 155));
        boardPointsPosition.put(16, List.of(54, 155));
        boardPointsPosition.put(17, List.of(96, 155));
        boardPointsPosition.put(18, List.of(139, 155));
        boardPointsPosition.put(19, List.of(139, 116));
        boardPointsPosition.put(20, List.of(75, 99));
        boardPointsPosition.put(21, List.of(11, 117));
        boardPointsPosition.put(22, List.of(11, 77));
        boardPointsPosition.put(23, List.of(11, 38));
        boardPointsPosition.put(24, List.of(36, 7));
        boardPointsPosition.put(25, List.of(75, 0));
        boardPointsPosition.put(26, List.of(114, 7));
        boardPointsPosition.put(27, List.of(139, 38));
        boardPointsPosition.put(28, List.of(139, 78));
        boardPointsPosition.put(29, List.of(75, 47));
    }


    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    /**
     * method called by viewGUI when the setupPhase has ended
     * @param game immutable game
     */
    public void endSetup(ImmGame game) {

        leaveButton.setVisible(true);
        lobbyButton.setVisible(false);

        List<ImmPlayableCard> initialCards = new ArrayList<>();
        List<Color> colors = new ArrayList<>();

        for(int i = 0; i<game.playerOrderNicknames().size() - 1 ; i++){
            initialCards.add(game.otherPlayers().get(i).playerArea().area().get(List.of(0,0)));
            colors.add(game.otherPlayers().get(i).color());
        }

        this.game = game;

        if (comboBoxMessage.getItems().isEmpty()) {
            comboBoxMessage.getItems().add("");
            for (String p : game.playerOrderNicknames()) {
                if (!p.equals(game.player().nickname())) {
                    comboBoxMessage.getItems().add(p);
                }
            }
            comboBoxMessage.getSelectionModel().selectFirst();
        }

        initializeBoard();

        for(int i = 0; i<=29; i++) {
            AnchorPane anchorPane = createAnchorPaneOnBoard(boardPointsPosition.get(i).get(0),boardPointsPosition.get(i).get(1));
            anchorPanesOnBoard.add(anchorPane);
        }

        int index = game.playerOrderNicknames().indexOf(game.player().nickname());
        colors.add(index,game.player().color());

        posizionaPedine(colors,0,anchorPanesOnBoard.getFirst());
        colors.remove(game.player().color());

        this.myColor = game.player().color();

        nickname = game.player().nickname();
        myNickText.setText(game.player().nickname());

        myPlayerAreaAnchorPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        myPlayerAreaAnchorPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

        setAreaSize(user1PlayerArea,playerArea1);

        user1Initialcard.setLayoutX(9999);
        user1Initialcard.setLayoutY(9999);

        username1.setText(game.playerOrderNicknames().getFirst());

        streamInitialCard(initialCards.getFirst().cardID(), user1Initialcard, initialCards.getFirst().showingFront());

        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().getFirst().cardID(),user1HandCard1);
        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().get(1).cardID(),user1HandCard2);
        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().getLast().cardID(),user1HandCard3);

        if(game.playerOrderNicknames().size() == 2){
            user2PlayerArea.setVisible(false);
            playerArea2.setVisible(false);
            username2.setVisible(false);
            user3PlayerArea.setVisible(false);
            playerArea3.setVisible(false);
            username3.setVisible(false);
        }else{
            switch (game.playerOrderNicknames().size()) {
                case 3 -> {
                    setAreaSize(user2PlayerArea,playerArea2);
                    user3PlayerArea.setVisible(false);
                    playerArea3.setVisible(false);
                    username3.setVisible(false);

                    user2Initialcard.setLayoutX(9999);
                    user2Initialcard.setLayoutY(9999);
                    streamInitialCard(initialCards.getLast().cardID(),user2Initialcard,initialCards.getLast().showingFront());

                    ImageView u2Pedina = new ImageView();
                    streamUPedina(colors.getLast(),u2Pedina,"u2Pedina", user2PlayerArea);

                    username2.setText(game.playerOrderNicknames().getLast());

                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().getFirst().cardID(),user2HandCard1);
                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().get(1).cardID(),user2HandCard2);
                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().getLast().cardID(),user2HandCard3);
                }
                case 4 -> {
                    setAreaSize(user2PlayerArea,playerArea2);
                    setAreaSize(user3PlayerArea,playerArea3);

                    user2Initialcard.setLayoutX(9999);
                    user2Initialcard.setLayoutY(9999);
                    username2.setText(game.playerOrderNicknames().get(1));
                    user3Initialcard.setLayoutX(9999);
                    user3Initialcard.setLayoutY(9999);
                    username3.setText(game.playerOrderNicknames().getLast());
                    streamInitialCard(initialCards.get(1).cardID(), user2Initialcard, initialCards.get(1).showingFront());
                    streamInitialCard(initialCards.getLast().cardID(), user3Initialcard, initialCards.getLast().showingFront());

                    ImageView u2Pedina = new ImageView();
                    streamUPedina(colors.get(1) ,u2Pedina, "u2Pedina", user2PlayerArea);

                    ImageView u3Pedina = new ImageView();
                    streamUPedina(colors.getLast() ,u3Pedina, "u3Pedina", user3PlayerArea);

                    streamOtherPlayerCard(game.otherPlayers().get(1).hand().getFirst().cardID(),user2HandCard1);
                    streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(1).cardID(),user2HandCard2);
                    streamOtherPlayerCard(game.otherPlayers().get(1).hand().getLast().cardID(),user2HandCard3);

                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().getFirst().cardID(),user3HandCard1);
                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().get(1).cardID(),user3HandCard2);
                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().getLast().cardID(),user3HandCard3);
                }
            }
        }

        myPlayerArea.setHvalue(1.0);
        myPlayerArea.setVvalue(1.0);

        myInitialCard.setLayoutX(9999);
        myInitialCard.setLayoutY(9999);

        streamMyHandcard(game.player().hand().get(0).cardID(),handCard1,game.player().hand().get(0).showingFront());
        streamMyHandcard(game.player().hand().get(1).cardID(),handCard2,game.player().hand().get(1).showingFront());
        streamMyHandcard(game.player().hand().get(2).cardID(),handCard3,game.player().hand().get(2).showingFront());

        streamObjectiveCard(game.player().playerArea().objectiveCard().cardID(), mySecretObjective);
        streamObjectiveCard(game.commonObjectiveCards().getFirst().cardID(), commonObjective1);
        streamObjectiveCard(game.commonObjectiveCards().getLast().cardID(), commonObjective2);

        streamRevealedId(game.revealedGoldCards().get(0).cardID(), game.revealedGoldCards().get(1).cardID(), game.revealedResourceCards().get(0).cardID(), game.revealedResourceCards().get(1).cardID());
        streamTopRGImage(game.topResourceCard().cardID(),game.topGoldCard().cardID(),true);
        streamTopRGImage(game.topResourceCard().cardID(),game.topGoldCard().cardID(),false);

        String myColorChosen = null;

        streamInitialCard(game.player().playerArea().area().get(List.of(0, 0)).cardID(),myInitialCard,game.player().playerArea().area().get(List.of(0, 0)).showingFront());

        switch (game.player().color()){
            case Color.BLUE -> myColorChosen = "/pedine/pedina_blu.png";
            case Color.RED -> myColorChosen = "/pedine/pedina_rossa.png";
            case Color.GREEN -> myColorChosen = "/pedine/pedina_verde.png";
            case Color.YELLOW -> myColorChosen = "/pedine/pedina_gialla.png";
        }


        try (InputStream myColorStream = getClass().getResourceAsStream(myColorChosen)) {

            ImageView myPedina = new ImageView(new Image(myColorStream));
            myPedina.setFitHeight(33);
            myPedina.setFitWidth(33);
            myPedina.setLayoutX(9999+50);
            myPedina.setLayoutY(9999-17);
            myPlayerAreaAnchorPane.getChildren().add(myPedina);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView u1Pedina = new ImageView();
        streamUPedina(colors.getFirst(),u1Pedina,"u1Pedina", user1PlayerArea);

        setUpdateText(List.of("The game setup ended!\n" + game.playerOrderNicknames().getFirst() + "'s turn"));

        Pane pane = createPaneCard(9999,9999);
        myPlayerAreaAnchorPane.getChildren().add(pane);

        String pedinanera = "/pedine/pedina_nera.png";
        try(InputStream neroStream = getClass().getResourceAsStream(pedinanera)){
            ImageView blackPawn = new ImageView(new Image(neroStream));
            blackPawn.setFitHeight(33);
            blackPawn.setFitWidth(33);
            if(game.player().nickname().equals(game.playerOrderNicknames().getFirst())){
                //posiziono la pedina nera sul mio
                blackPawn.setLayoutX(9999+50);
                blackPawn.setLayoutY(9999-17-5);
                myPlayerAreaAnchorPane.getChildren().add(blackPawn);
            }else{
                //posiziono la pedina nera sul campo di un altro giocatore
                blackPawn.setLayoutX(9999 + 37);
                blackPawn.setLayoutY(9999 - 20 - 5);
                switch (game.playerOrderNicknames().indexOf(game.playerOrderNicknames().getFirst())) {
                    case 0 -> user1PlayerArea.getChildren().add(blackPawn);
                    case 1 -> user2PlayerArea.getChildren().add(blackPawn);
                    case 2 -> user3PlayerArea.getChildren().add(blackPawn);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method called to stream Objective card image
     * @param cardId card id
     * @param objectiveCardView objectiveCard imageview
     */
    private void streamObjectiveCard(String cardId, ImageView objectiveCardView) {
        String path = "/CardsImages/Objective/" + cardId + ".png";

        try(InputStream objectiveCardStream = getClass().getResourceAsStream(path)){
            objectiveCardView.setImage(new Image(objectiveCardStream));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * method called to set player's area size
     * @param userPlayerArea anchor pane
     * @param playerArea scroll pane
     */
    private void setAreaSize(AnchorPane userPlayerArea, ScrollPane playerArea) {
        userPlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
        userPlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
        playerArea.setHvalue(1.0);
        playerArea.setVvalue(1.0);
    }

    /**
     * method called to stream pawn color
     * @param color color to stream
     * @param uPedina pawn image view
     * @param id id to set for image view
     * @param userPlayerArea player area anchor pane
     */
    private void streamUPedina(Color color, ImageView uPedina, String id, AnchorPane userPlayerArea) {
        streamColor(color,uPedina);
        uPedina.setFitHeight(33);
        uPedina.setFitWidth(33);
        uPedina.setLayoutX(9999+37);
        uPedina.setLayoutY(9999-20);
        uPedina.setId(id);
        userPlayerArea.getChildren().add(uPedina);
    }

    /**
     * method called to place a pawn on board according to the points reached
     * @param colors list of color to place
     * @param points points
     * @param anchorPane anchor pane on board
     */
    private void posizionaPedine(List<Color> colors, int points, AnchorPane anchorPane) {
        ImageView view;

        if (points == 0){
            if (colors.size() >= 2){
                streamColor(colors.getFirst(),(ImageView) anchorPane.lookup("#pawn1"));
                streamColor(colors.get(1),(ImageView) anchorPane.lookup("#pawn2"));
                view = (ImageView) anchorPane.lookup("#pawn1");
                view.setId(colors.getFirst() + "pawn1");
                view = (ImageView) anchorPane.lookup("#pawn2");
                view.setId(colors.get(1) + "pawn2");
            }
            if (colors.size() >= 3){
                streamColor(colors.get(2),(ImageView) anchorPane.lookup("#pawn3"));
                view = (ImageView) anchorPane.lookup("#pawn3");
                view.setId(colors.get(2) + "pawn3");
            }
            if (colors.size() == 4){
                streamColor(colors.get(3),(ImageView) anchorPane.lookup("#pawn4"));
                view = (ImageView) anchorPane.lookup("#pawn4");
                view.setId(colors.get(3) + "pawn4");
            }
        }
        else{
            movePawn(points, colors);
        }
    }

    // Moves the pawn to the specified cell
    /**
     * method called to move the pawn
     * @param cell cell on which place the pawn
     * @param colors list of colors to move
     */
    private void movePawn(int cell, List<Color> colors){
        if(removeFromBoard(colors.getFirst(), cell)) {
            ImageView firstAvailable = computeFirstAvailablePosition(cell,colors.getFirst());
            streamColor(colors.getFirst(),firstAvailable);
        }
    }

    /**
     * method called to remove pawn from board
     * @param color color to remove from board
     * @param points points
     * @return true if on points there is not the color, false id there is already
     */
    private Boolean removeFromBoard(Color color, int points) {
        //se in quella posizione c'è già color, allora non aggiorno
        ImageView p1 = (ImageView) anchorPanesOnBoard.get(points).lookup("#" + color + "pawn1");
        ImageView p2 = (ImageView) anchorPanesOnBoard.get(points).lookup("#" + color + "pawn2");
        ImageView p3 = (ImageView) anchorPanesOnBoard.get(points).lookup("#" + color + "pawn3");
        ImageView p4 = (ImageView) anchorPanesOnBoard.get(points).lookup("#" + color + "pawn4");
        if(p1==null && p2==null && p3==null && p4==null) {
            for(int i = 0; i<=29; i++){
                ImageView pawn1 = (ImageView) anchorPanesOnBoard.get(i).lookup("#" + color + "pawn1");
                ImageView pawn2 = (ImageView) anchorPanesOnBoard.get(i).lookup("#" + color + "pawn2");
                ImageView pawn3 = (ImageView) anchorPanesOnBoard.get(i).lookup("#" + color + "pawn3");
                ImageView pawn4 = (ImageView) anchorPanesOnBoard.get(i).lookup("#" + color + "pawn4");

                if(pawn1!=null){
                    pawn1.setId("pawn1");
                    pawn1.setImage(null);
                    pawn1.setVisible(false);
                    return true;
                }
                if(pawn2!=null){
                    pawn2.setId("pawn2");
                    pawn2.setImage(null);
                    pawn2.setVisible(false);
                    return true;
                }
                if(pawn3!=null){
                    pawn3.setId("pawn3");
                    pawn3.setImage(null);
                    pawn3.setVisible(false);
                    return true;
                }
                if(pawn4!=null){
                    pawn4.setId("pawn4");
                    pawn4.setImage(null);
                    pawn4.setVisible(false);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method called to create an anchor pane on board in order to put the image view of the pawn
     * @param x layout x of the anchorPane in which put pawn view
     * @param y layout y of the anchorPane in which put pawn view
     * @return anchor pane
     */
    private AnchorPane createAnchorPaneOnBoard(int x, int y) {
        int width = 33;
        int height = 44;

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLayoutX(x);
        anchorPane.setLayoutY(y);

        anchorPane.setPrefWidth(width);
        anchorPane.setPrefHeight(height);

        ImageView pawn1 = new ImageView();
        pawn1.setFitWidth(33);
        pawn1.setFitHeight(33);
        pawn1.setLayoutX(0);
        pawn1.setLayoutY(11);
        pawn1.setId("pawn1");
        ImageView pawn2 = new ImageView();
        pawn2.setFitWidth(33);
        pawn2.setFitHeight(33);
        pawn2.setLayoutX(0);
        pawn2.setLayoutY(8);
        pawn2.setId("pawn2");
        ImageView pawn3 = new ImageView();
        pawn3.setFitWidth(33);
        pawn3.setFitHeight(33);
        pawn3.setLayoutX(0);
        pawn3.setLayoutY(5);
        pawn3.setId("pawn3");
        ImageView pawn4 = new ImageView();
        pawn4.setFitWidth(33);
        pawn4.setFitHeight(33);
        pawn4.setLayoutX(0);
        pawn4.setLayoutY(2);
        pawn4.setId("pawn4");

        anchorPane.getChildren().add(pawn1);
        anchorPane.getChildren().add(pawn2);
        anchorPane.getChildren().add(pawn3);
        anchorPane.getChildren().add(pawn4);

        globalPane.getChildren().add(anchorPane);

        return anchorPane;
    }

    /**
     * on the board the pawn are in the stack, this method computes the first available position of the stack
     * @param point point of the board on which put the pawn
     * @param color color
     * @return image view of the position
     */
    private ImageView computeFirstAvailablePosition(int point,Color color){
        ImageView pawn1 = (ImageView) anchorPanesOnBoard.get(point).lookup("#pawn1");
        ImageView pawn2 = (ImageView) anchorPanesOnBoard.get(point).lookup("#pawn2");
        ImageView pawn3 = (ImageView) anchorPanesOnBoard.get(point).lookup("#pawn3");
        ImageView pawn4 = (ImageView) anchorPanesOnBoard.get(point).lookup("#pawn4");

        if(pawn1!=null && pawn1.getImage() == null) {
            pawn1.setId(color+"pawn1");
            return pawn1;
        }
        if(pawn2!=null && pawn2.getImage() == null) {
            pawn2.setId(color+"pawn2");
            return pawn2;
        }
        if(pawn3!=null && pawn3.getImage() == null) {
            pawn3.setId(color+"pawn3");
            return pawn3;
        }
        if(pawn4!=null && pawn4.getImage() == null) {
            pawn4.setId(color+"pawn4");
            return pawn4;
        }
        return null;
    }

    /**
     * method called to stream other players' handCard
     * @param id card id
     * @param userHandCard user hand card image view
     */
    private void streamOtherPlayerCard(String id, ImageView userHandCard) {
        String path = "/CardsImages/HandCards/back/" + id + ".png";

        try(InputStream otherPlayerCardStream = getClass().getResourceAsStream(path)){
            userHandCard.setImage(new Image(otherPlayerCardStream));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * method called to stream initialCard image
     * @param initialCardId initialcard id
     * @param userInitialcardView initialcard image view
     * @param showingFront indicates if it's showing front
     */
    private void streamInitialCard(String initialCardId, ImageView userInitialcardView, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/Initial/fronts/" + initialCardId + ".png";

            try(InputStream initialcardStream = getClass().getResourceAsStream(path)){
                userInitialcardView.setImage(new Image(initialcardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/Initial/backs/" + initialCardId + ".png";

            try(InputStream initialcardStream = getClass().getResourceAsStream(path)){
                userInitialcardView.setImage(new Image(initialcardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * method called when a player click flip button under hancard 1
     */
    @FXML
    void flipHandCard1(ActionEvent event) {
        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.flippingCard(0);
    }

    /**
     * method called when a player click flip button under hancard 2
     */
    @FXML
    void flipHandCard2(ActionEvent event) {
        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.flippingCard(1);
    }

    /**
     * method called when a player click flip button under hancard 3
     */
    @FXML
    void flipHandCard3(ActionEvent event) {
        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.flippingCard(2);
    }

    /**
     * method called when the card is flipped
     * @param cardId flipped card id
     * @param showingFront indicates if is showing front
     * @param kingdom kingdom of the card flipped
     * @param playableCardType flipped card's type
     * @param indexOfFlippedCard index of the card flipped
     */
    public void cardFlipped(String cardId, boolean showingFront, Symbol kingdom, PlayableCardType playableCardType, int indexOfFlippedCard) {
        Platform.runLater(()->{
            selectedCard = null;
            String handCard = null;

            if(showingFront){
                handCard = "/CardsImages/HandCards/" + cardId + ".png";
            } else {
                if(playableCardType == PlayableCardType.RESOURCE){
                    switch (kingdom){
                        case Symbol.ANIMAL -> handCard = "/CardsImages/HandCards/RbackANIMAL.png";
                        case Symbol.FUNGI -> handCard = "/CardsImages/HandCards/RbackFUNGI.png";
                        case Symbol.PLANT -> handCard = "/CardsImages/HandCards/RbackPLANT.png";
                        case Symbol.INSECT -> handCard = "/CardsImages/HandCards/RbackINSECT.png";
                    }
                }
                if(playableCardType == PlayableCardType.GOLD){
                    switch (kingdom){
                        case Symbol.ANIMAL -> handCard = "/CardsImages/HandCards/GbackANIMAL.png";
                        case Symbol.FUNGI -> handCard = "/CardsImages/HandCards/GbackFUNGI.png";
                        case Symbol.PLANT -> handCard = "/CardsImages/HandCards/GbackPLANT.png";
                        case Symbol.INSECT -> handCard = "/CardsImages/HandCards/GbackINSECT.png";
                    }
                }
            }

            try(InputStream handCardStream = getClass().getResourceAsStream(handCard)){
                switch (indexOfFlippedCard){
                    case 0 -> this.handCard1.setImage(new Image(handCardStream));
                    case 1 -> this.handCard2.setImage(new Image(handCardStream));
                    case 2 -> this.handCard3.setImage(new Image(handCardStream));
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            handCard1.getStyleClass().remove("selected");
            handCard2.getStyleClass().remove("selected");
            handCard3.getStyleClass().remove("selected");
        });
    }

    /**
     * method called to create a Pane that represents the card placed
     * @param x coordinate x
     * @param y coordinate y
     * @return a Pane on coordinate (x,y)
     */
    private Pane createPaneCard(int x, int y) {
        int width = 133;
        int height = 93;

        Pane pane = new Pane();
        pane.setLayoutX(x);
        pane.setLayoutY(y);

        pane.setMinWidth(width);
        pane.setMinHeight(height);

        createClickableCorners(pane);

        return pane;
    }
    /**
     * this method creates clickable corners
     * @param card a Pane (card) created by createPaneCard {@link #createPaneCard}
     */
    private void createClickableCorners(Pane card) {
        int width = 29;
        int height = 35;

        Rectangle topLeft = createCorner(width, height);
        topLeft.setOnMouseClicked(event -> handlerCornerClick(event, "TopLeft", card.getLayoutX(), card.getLayoutY(), false,-1, null));

        Rectangle topRight = createCorner(width, height);
        topRight.setOnMouseClicked(event -> handlerCornerClick(event, "TopRight", card.getLayoutX(), card.getLayoutY(), false,-1, null));

        Rectangle bottomLeft = createCorner(width, height);
        bottomLeft.setOnMouseClicked(event -> handlerCornerClick(event, "BottomLeft", card.getLayoutX(), card.getLayoutY(), false,-1, null));

        Rectangle bottomRight = createCorner(width, height);
        bottomRight.setOnMouseClicked(event -> handlerCornerClick(event, "BottomRight", card.getLayoutX(), card.getLayoutY(), false,-1, null));

        topLeft.setLayoutX(0);
        topLeft.setLayoutY(0);
        topRight.setLayoutX(104);
        topRight.setLayoutY(0);
        bottomLeft.setLayoutX(0);
        bottomLeft.setLayoutY(53);
        bottomRight.setLayoutX(104);
        bottomRight.setLayoutY(53);

        card.getChildren().addAll(topRight,topLeft,bottomRight,bottomLeft);
    }

    /**
     * method called by {@link #createClickableCorners} to create a corner
     * @param width width of the corner
     * @param height height of the corner
     * @return a corner
     */
    private Rectangle createCorner(int width, int height) {
        Rectangle corner = new Rectangle(width,height);
        //corner.setFill(javafx.scene.paint.Color.RED);
        corner.setFill(javafx.scene.paint.Color.TRANSPARENT);
        corner.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        return corner;
    }

    /**
     * method called when player clicked on a corner of the card to play a card on that corner
     */
    public void handlerCornerClick(MouseEvent event, String corner, double layoutX, double layoutY, boolean ok, int points, Color myColor) {
        //evento play card da notificare
        System.out.println(corner + "Clicked!!");
        if(selectedCard!=null) {
            switch (corner) {
                case "TopLeft" -> {
                    int x = (-(9999 - (int) layoutX)/104) - 1;
                    int y = ((9999 - (int) layoutY)/54) + 1;
                    viewGUI.playingCard(selectedCardIndex, x, y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("voglio giocare la carta in (" + x + "," + y + ")");
                }
                case "TopRight" -> {
                    int x = (((int) layoutX - 9999)/104) + 1;
                    int y = ((9999 - (int) layoutY)/54) + 1;
                    viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("voglio giocare la carta in (" + x + "," + y + ")");
                }
                case "BottomLeft" -> {
                    int x = (-(9999 - (int) layoutX)/104) - 1;
                    int y = (-((int) layoutY - 9999)/54) - 1;
                    viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("voglio giocare la carta in (" + x + "," + y + ")");
                }
                case "BottomRight" -> {
                    int x = (((int) layoutX - 9999)/104) + 1;
                    int y = (-((int) layoutY - 9999)/54) - 1;
                    viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("voglio giocare la carta in (" + x + "," + y + ")");
                }
            }
        }
    }

    /**
     * method called to copy the image in the imageView. This is used to slide the cards from left to right
     */
    private static ImageView copyImageView(ImageView imageToCopie) {
        ImageView copy = new ImageView(imageToCopie.getImage());
        copy.setFitWidth(imageToCopie.getFitWidth());
        copy.setFitHeight(imageToCopie.getFitHeight());
        return copy;
    }

    /**
     * method called when player clicked hand card 1
     */
    @FXML
    void playHandCard1(MouseEvent event) {
        System.out.println("Voglio giocare la prima carta!");
        selectedCard = copyImageView(handCard1);

        handCard1.getStyleClass().add("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        selectedCardIndex = 0;
    }

    /**
     * method called when player clicked hand card 2
     */
    @FXML
    void playHandCard2(MouseEvent event) {
        System.out.println("Voglio giocare la seconda carta!");
        selectedCard = copyImageView(handCard2);

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().add("selected");
        handCard3.getStyleClass().remove("selected");

        selectedCardIndex = 1;
    }

    /**
     * method called when player clicked hand card 3
     */
    @FXML
    void playHandCard3(MouseEvent event) {
        System.out.println("Voglio giocare la terza carta!");
        selectedCard = copyImageView(handCard3);

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().add("selected");

        selectedCardIndex = 2;
    }

    /**
     * method called when player clicked on gaol cards deck
     */
    @FXML
    void drawFromGoldDeck(MouseEvent event) {
        System.out.println("voglio pescare dal deck di oro");

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");


        viewGUI.drawingCard(DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK);
    }

    /**
     * method called when player clicked on resource cards deck
     */
    @FXML
    void drawFromResourceDeck(MouseEvent event) {
        System.out.println("voglio pescare dal deck di risorse");

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.drawingCard(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK);
    }

    /**
     * method called when player clicked on revealed gold card 1
     */
    @FXML
    void drawFromRevealedGold1(MouseEvent event) {
        System.out.println("voglio pescare la prima carta oro rivelata");

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);
    }

    /**
     * method called when player clicked on revealed gold card 2
     */
    @FXML
    void drawFromRevealedGold2(MouseEvent event) {
        System.out.println("voglio pescare la seconda carta oro rivelata");

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2);
    }

    /**
     * method called when player clicked on revealed resource card 1
     */
    @FXML
    void drawFromRevealedResource1(MouseEvent event) {
        System.out.println("voglio pescare la prima carta risorsa rivelata");

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
    }

    /**
     * method called when player clicked on revealed resource card 1
     */
    @FXML
    void drawFromRevealedResource2(MouseEvent event) {
        System.out.println("voglio pescare la prima carta risorsa rivelata");

        handCard1.getStyleClass().remove("selected");
        handCard2.getStyleClass().remove("selected");
        handCard3.getStyleClass().remove("selected");

        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2);
    }

    /**
     * method called when a card is drawn, the card drawn has to be replaced by another card
     * @param drawCardEvent draw card event
     * @param topResourceCardId top resource card id
     * @param topGoldCardId top gold card id
     * @param drawnCardId drawn card id
     * @param revR1 revealed resource card 1 id
     * @param revR2 revealed resource card 2 id
     * @param revG1 revealed gold card 1 id
     * @param revG2 revealed gold card 2 id
     */
    public void cardDrawn(DrawCardEvent drawCardEvent, String topResourceCardId, String topGoldCardId, String drawnCardId, String revR1, String revR2, String revG1, String revG2) {
        Platform.runLater(()->{
            switch (drawCardEvent) {
                case DRAW_FROM_RESOURCE_CARDS_DECK -> {
                    //scambio la carta pescata con hancard e aggiorno la carta in cima al deck
                    //Image image = resourceDeck.getImage();

                    String path = "/CardsImages/HandCards/front/" + drawnCardId + ".png";

                    try(InputStream imageStream = getClass().getResourceAsStream(path)){
                        if(!handCard1.isVisible()) {
                            handCard1.setImage(new Image(imageStream));
                            handCard1.setVisible(true);
                            flipCard1.setVisible(true);
                            streamTopRGImage(topResourceCardId, topGoldCardId, false);
                        }
                        if(!handCard2.isVisible()) {
                            handCard2.setImage(new Image(imageStream));
                            handCard2.setVisible(true);
                            flipCard2.setVisible(true);
                            streamTopRGImage(topResourceCardId, topGoldCardId, false);
                        }
                        if(!handCard3.isVisible()) {
                            handCard3.setImage(new Image(imageStream));
                            handCard3.setVisible(true);
                            flipCard3.setVisible(true);
                            streamTopRGImage(topResourceCardId, topGoldCardId, false);
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                case DRAW_FROM_GOLD_CARDS_DECK -> {

                    String path = "/CardsImages/HandCards/front/" + drawnCardId + ".png";

                    //sembra funzionare, ma in realtà se siamo a fine partita e alcuni possono avere solo due carte, creerà casino, meglio mettere il controllo sul giocatore che ha fatto update
                    try(InputStream imageStream = getClass().getResourceAsStream(path)){
                        if(!handCard1.isVisible()) {
                            handCard1.setImage(new Image(imageStream));
                            handCard1.setVisible(true);
                            flipCard1.setVisible(true);
                            streamTopRGImage(topResourceCardId, topGoldCardId, true);
                        }
                        if(!handCard2.isVisible()) {
                            handCard2.setImage(new Image(imageStream));
                            handCard2.setVisible(true);
                            flipCard2.setVisible(true);
                            streamTopRGImage(topResourceCardId, topGoldCardId, true);
                        }
                        if(!handCard3.isVisible()) {
                            handCard3.setImage(new Image(imageStream));
                            handCard3.setVisible(true);
                            flipCard3.setVisible(true);
                            streamTopRGImage(topResourceCardId, topGoldCardId, true);
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case DRAW_REVEALED_GOLD_CARD_1, DRAW_REVEALED_GOLD_CARD_2, DRAW_REVEALED_RESOURCE_CARD_1, DRAW_REVEALED_RESOURCE_CARD_2 -> {
                    String path = "/CardsImages/HandCards/front/" + drawnCardId + ".png";

                    try(InputStream imageStream = getClass().getResourceAsStream(path)){
                        if(!handCard1.isVisible()) {
                            handCard1.setImage(new Image(imageStream));
                            handCard1.setVisible(true);
                            flipCard1.setVisible(true);
                            streamRevealedId(revG1,revG2,revR1,revR2);
                        }
                        if(!handCard2.isVisible()) {
                            handCard2.setImage(new Image(imageStream));
                            handCard2.setVisible(true);
                            flipCard2.setVisible(true);
                            streamRevealedId(revG1,revG2,revR1,revR2);
                        }
                        if(!handCard3.isVisible()) {
                            handCard3.setImage(new Image(imageStream));
                            handCard3.setVisible(true);
                            flipCard3.setVisible(true);
                            streamRevealedId(revG1,revG2,revR1,revR2);
                        }
                        streamTopRGImage(topResourceCardId, topGoldCardId, false);
                        streamTopRGImage(topResourceCardId, topGoldCardId, true);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * method called to stream top reaource or gold card images
     */
    private void streamTopRGImage(String topResourceCardId, String topGoldCardId, boolean isGold) {
        String kingdomTopG;
        String kingdomTopR;

        if(isGold && !topGoldCardId.equals("empty")){
            kingdomTopG = "/CardsImages/HandCards/back/" + topGoldCardId + ".png";

            try(InputStream topGStream = getClass().getResourceAsStream(kingdomTopG)){
                this.goldDeck.setImage(new Image(topGStream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else if(!topResourceCardId.equals("empty")){
            kingdomTopR = "/CardsImages/HandCards/back/" + topResourceCardId + ".png";

            try(InputStream topRStream = getClass().getResourceAsStream(kingdomTopR)){
                this.resourceDeck.setImage(new Image(topRStream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(isGold && topGoldCardId.equals("empty")){
            this.goldDeck.setVisible(false);
        }else if(topResourceCardId.equals("empty")){
            this.resourceDeck.setVisible(false);
        }
    }

    /**
     * method called when a card is played
     */
    public void cardPlayed(String cornerClicked, int layoutXOfCardClicked, int layoutYOfCardClicked, int points) {
        Platform.runLater(()->{
            if(selectedCard != null) {
                Pane pane;
                int x, y;
                //if corner è topLeft allora LayoutX-104 e LayoutY-54
                //if corner è topRight allora LayoutX+104 e LayoutY-54
                //if corner è bottomLeft allora LayoutX-104 e LayoutY+54
                //if corner è bottomRight allora LayoutX+104 e LayoutY+54
                switch (cornerClicked) {
                    case "TopLeft" -> {
                        selectedCard.setLayoutX( layoutXOfCardClicked - 104 +1);
                        selectedCard.setLayoutY( layoutYOfCardClicked - 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard( layoutXOfCardClicked - 104,  layoutYOfCardClicked - 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (-(9999 - layoutXOfCardClicked)/104)-1;
                        y = ((9999 -  layoutYOfCardClicked)/54)+1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                    case "TopRight" -> {
                        selectedCard.setLayoutX( layoutXOfCardClicked + 104 +1);
                        selectedCard.setLayoutY( layoutYOfCardClicked - 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard( layoutXOfCardClicked + 104,  layoutYOfCardClicked - 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (( layoutXOfCardClicked - 9999)/104) + 1;
                        y = ((9999 - layoutYOfCardClicked)/54) + 1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                    case "BottomLeft" -> {
                        selectedCard.setLayoutX( layoutXOfCardClicked - 104 +1);
                        selectedCard.setLayoutY( layoutYOfCardClicked + 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard( layoutXOfCardClicked - 104,  layoutYOfCardClicked + 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (-(9999 - layoutXOfCardClicked)/104) - 1;
                        y = (-( layoutYOfCardClicked - 9999)/54) - 1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                    case "BottomRight" -> {
                        selectedCard.setLayoutX( layoutXOfCardClicked + 104 +1);
                        selectedCard.setLayoutY( layoutYOfCardClicked + 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard( layoutXOfCardClicked + 104,  layoutYOfCardClicked + 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (( layoutXOfCardClicked - 9999)/104) + 1;
                        y = (-( layoutYOfCardClicked - 9999)/54) - 1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                }

                //aggiorno posizioni carte in mano
                //per pescare una carta in mano vediamo la prima posizione in cui handCard.isVisible == false
                switch (selectedCardIndex) {
                    case 0 -> {
                        handCard3.setVisible(false);
                        flipCard3.setVisible(false);


                        Image handCard2Image = handCard2.getImage();
                        Image handcard3Image = handCard3.getImage();

                        handCard1.setImage(handCard2Image);
                        handCard2.setImage(handcard3Image);
                    }
                    case 1 -> {
                        handCard3.setVisible(false);
                        flipCard3.setVisible(false);

                        Image handcard3Image = handCard3.getImage();

                        handCard2.setImage(handcard3Image);
                    }
                    case 2 -> {
                        handCard3.setVisible(false);
                        flipCard3.setVisible(false);
                    }
                }

                handCard1.getStyleClass().remove("selected");
                handCard2.getStyleClass().remove("selected");
                handCard3.getStyleClass().remove("selected");

                //aggiorno punteggio
                posizionaPedine(List.of(myColor),points,anchorPanesOnBoard.get(points));
            }
        });
    }

    /**
     * method called to update others players' view of the deck
     */
    public void updateOtherAfterCardDrawn(String topGCardId, String topRCardId,
                                          String revealedR1Id, String revealedR2Id,
                                          String revealedG1Id, String revealedG2Id,
                                          String playerNicknameWhoUpdated, ImmGame immGame) {
        Platform.runLater(()->{
            streamTopRGImage(topRCardId,topGCardId,true);
            streamTopRGImage(topRCardId,topGCardId,false);

            streamRevealedId(revealedG1Id,revealedG2Id,
                    revealedR1Id,revealedR2Id);


            //inserisco nella prima posizione libera
            List<String> nicks = immGame.playerOrderNicknames();
            nicks.remove(nickname);
            switch (nicks.indexOf(playerNicknameWhoUpdated)){
                case 0 -> //aggiorno user1
                    //NB per ottenere la carta pescata basta fare getLast della hand
                        updateOtherUserHandCard(immGame, playerNicknameWhoUpdated, user1HandCard1, user1HandCard2, user1HandCard3);
                case 1 -> //aggiorno user 2
                        updateOtherUserHandCard(immGame, playerNicknameWhoUpdated, user2HandCard1, user2HandCard2, user2HandCard3);
                case 2 -> //aggiorno user 3
                        updateOtherUserHandCard(immGame, playerNicknameWhoUpdated, user3HandCard1, user3HandCard2, user3HandCard3);
            }
        });

    }

    /**
     * method called to update others players' hand cards
     */
    private void updateOtherUserHandCard(ImmGame immGame, String playerNicknameWhoUpdated, ImageView userHandCard1, ImageView userHandCard2, ImageView userHandCard3) {
        if(!userHandCard1.isVisible()){
            streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),userHandCard1);
            userHandCard1.setVisible(true);
        }
        if(!userHandCard2.isVisible()){
            streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),userHandCard2);
            userHandCard2.setVisible(true);
        }
        if(!userHandCard3.isVisible()){
            streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),userHandCard3);
            userHandCard3.setVisible(true);
        }
    }

    /**
     * method used to stream revealed cards
     */
    private void streamRevealedId(String revealedG1Id, String revealedG2Id, String revealedR1Id, String revealedR2Id) {
        if(revealedG1Id.equals("empty")){
            this.revealedGold1.setVisible(false);
        }else{
            String revG1 = "/CardsImages/HandCards/front/" + revealedG1Id + ".png";
            try(InputStream revG1Stream = getClass().getResourceAsStream(revG1)){
                this.revealedGold1.setImage(new Image(revG1Stream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(revealedG2Id.equals("empty")){
            this.revealedGold2.setVisible(false);
        }else{
            String revG2 = "/CardsImages/HandCards/front/" + revealedG2Id + ".png";
            try(InputStream revG2Stream = getClass().getResourceAsStream(revG2)){
                this.revealedGold2.setImage(new Image(revG2Stream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(revealedR1Id.equals("empty")){
            this.revealedResource1.setVisible(false);
        }else{
            String revR1 = "/CardsImages/HandCards/front/" + revealedR1Id + ".png";
            try(InputStream revR1Stream = getClass().getResourceAsStream(revR1)){
                this.revealedResource1.setImage(new Image(revR1Stream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(revealedR2Id.equals("empty")){
            this.revealedResource2.setVisible(false);
        }else{
            String revR2 = "/CardsImages/HandCards/front/" + revealedR2Id + ".png";
            try(InputStream revR2Stream = getClass().getResourceAsStream(revR2)){
                this.revealedResource2.setImage(new Image(revR2Stream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method used to stream color
     */
    private void streamColor(Color color,ImageView imageview){
        String path = "/pedine/" + color + ".png";

        try(InputStream colorStream = getClass().getResourceAsStream(path)){
            imageview.setImage(new Image(colorStream));
            imageview.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * method used to update other players' player area
     */
    public void updateUardPlayedForOthers(String playerNicknameWhoUpdated, ImmGame immGame, String cornerClicked, int layoutXOfCardClicked, int layoutYOfCardClicked) {
        Platform.runLater(()->{
            List<String> nicknames = immGame.playerOrderNicknames();
            nicknames.remove(nickname);
            int indexOfplayerWhoupdated=-1;
            for(int i = 0; i<immGame.otherPlayers().size();i++){
                if(immGame.otherPlayers().get(i).nickname().equals(playerNicknameWhoUpdated)){
                    indexOfplayerWhoupdated=i;
                    break;
                }
            }
            switch (nicknames.indexOf(playerNicknameWhoUpdated)){
                case 0 -> updateForPlayer1(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                case 1 -> updateForPlayer2(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                case 2 -> updateForPlayer3(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
            }
            posizionaPedine(List.of(immGame.otherPlayers().get(indexOfplayerWhoupdated).color()),
                    immGame.otherPlayers().get(indexOfplayerWhoupdated).playerArea().points(),
                    anchorPanesOnBoard.get(immGame.otherPlayers().get(indexOfplayerWhoupdated).playerArea().points()));
        });
    }

    /**
     * method used to update player 1 player area
     */
    private void updateForPlayer1(String playerNicknameWhoUpdated, ImmGame immGame, String cornerClicked, int layoutXOfCardClicked, int layoutYOfCardClicked){
        //devo ottenere che carta è stata giocata dal giocatore sulla base delle coordinate normalizzate con le layout
        //LinkedHashMap<List<Integer>, ImmPlayableCard> cartePosizionateInOrdine = new LinkedHashMap<>();
        Map.Entry<List<Integer>, ImmPlayableCard> lastPlaced = null;
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).playerArea().area().entrySet()) {
            //cartePosizionateInOrdine.put(cardAndCoordinates.getKey(), cardAndCoordinates.getValue());
            lastPlaced = cardAndCoordinates;
        }
        int lastx = lastPlaced.getKey().getFirst();
        int lasty = lastPlaced.getKey().getLast();
        String idlast = lastPlaced.getValue().cardID();

        int layoutX = lastx*83 + 9999;
        int layoutY = 9999 - 43*lasty;

        ImageView toPlace = new ImageView();
        streamCard(idlast,toPlace,lastPlaced.getValue().showingFront());

        toPlace.setLayoutX(layoutX);
        toPlace.setLayoutY(layoutY);
        user1PlayerArea.getChildren().add(toPlace);

        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getFirst().cardID(),user1HandCard1);
        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user1HandCard2);
        user1HandCard3.setVisible(false);
    }

    /**
     * method used to update player 2 player area
     */
    private void updateForPlayer2(String playerNicknameWhoUpdated, ImmGame immGame, String cornerClicked, int layoutXOfCardClicked, int layoutYOfCardClicked){
        Map.Entry<List<Integer>, ImmPlayableCard> lastPlaced = null;
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).playerArea().area().entrySet()) {
            //cartePosizionateInOrdine.put(cardAndCoordinates.getKey(), cardAndCoordinates.getValue());
            lastPlaced = cardAndCoordinates;
        }
        int lastx = lastPlaced.getKey().getFirst();
        int lasty = lastPlaced.getKey().getLast();
        String idlast = lastPlaced.getValue().cardID();

        int layoutX = lastx*104 + 9999;
        int layoutY = 9999 - 54*lasty;

        ImageView toPlace = new ImageView();
        streamCard(idlast,toPlace,lastPlaced.getValue().showingFront());

        toPlace.setLayoutX(layoutX);
        toPlace.setLayoutY(layoutY);
        user2PlayerArea.getChildren().add(toPlace);

        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getFirst().cardID(),user2HandCard1);
        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user2HandCard2);
        user2HandCard3.setVisible(false);
    }

    /**
     * method used to update player 3 player area
     */
    private void updateForPlayer3(String playerNicknameWhoUpdated, ImmGame immGame, String cornerClicked, int layoutXOfCardClicked, int layoutYOfCardClicked){
        Map.Entry<List<Integer>, ImmPlayableCard> lastPlaced = null;
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).playerArea().area().entrySet()) {
            //cartePosizionateInOrdine.put(cardAndCoordinates.getKey(), cardAndCoordinates.getValue());
            lastPlaced = cardAndCoordinates;
        }
        int lastx = lastPlaced.getKey().getFirst();
        int lasty = lastPlaced.getKey().getLast();
        String idlast = lastPlaced.getValue().cardID();

        int layoutX = lastx*104 + 9999;
        int layoutY = 9999 - 54*lasty;

        ImageView toPlace = new ImageView();
        streamCard(idlast,toPlace,lastPlaced.getValue().showingFront());

        toPlace.setLayoutX(layoutX);
        toPlace.setLayoutY(layoutY);
        user3PlayerArea.getChildren().add(toPlace);

        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getFirst().cardID(),user3HandCard1);
        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user3HandCard2);
        user3HandCard3.setVisible(false);
    }

    /**
     * method called to stream a card
     */
    private void streamCard(String id, ImageView toPlace, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/HandCards/front/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path)){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/HandCards/back/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path)){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    /**
     * method called to change turn
     * @param currentPlayer current player
     */
    public void turnChanged(String currentPlayer) {
        Platform.runLater(()->{
            if(currentPlayer.equals(nickname)){
                setUpdateText(List.of("It's your turn!"));
            }else{
                setUpdateText(List.of("It's " + currentPlayer + "'s turn!"));
            }
        });

    }

    /**
     * method called when 20 points is reached
     */
    public void twentyPointsReached(ImmGame immGame) {
        Platform.runLater(()->{
            setUpdateText(List.of("20 Points reached!"));
        });
    }

    /**
     * method called when decks are empty
     */
    public void deckesEmpty(ImmGame immGame) {
        Platform.runLater(()->{
            setUpdateText(List.of("All decks are empty!"));
        });
    }

    @FXML
    private Button leaveButton;
    @FXML
    private Button lobbyButton;

    /**
     * method called when game ends
     * @param players player order list
     */
    public void gameEnded(List<ImmPlayer> players) {
        Platform.runLater(()->{
            leaveButton.setVisible(false);
            lobbyButton.setVisible(true);

            flipCard1.setVisible(false);
            flipCard2.setVisible(false);
            flipCard3.setVisible(false);

            HashMap<String,List<Integer>> pointsMap = new HashMap<>();

            for(ImmPlayer p: players){
                List<Integer> pointsAndExtraPoints = new ArrayList<>();
                pointsAndExtraPoints.clear();
                pointsAndExtraPoints.add(p.playerArea().points());
                pointsAndExtraPoints.add(p.playerArea().extraPoints());
                pointsMap.put(p.nickname(),pointsAndExtraPoints);
            }
            String winner = computeWinner(pointsMap);
            setUpdateText(List.of("Game ended!\nThe winner " + (winner.contains(" ") ? "are" : "is") + " " + winner + "!\nPress LOBBY"));


            //mostro al posto delle carte in mano agli altri giocatori l'obiettivo segreto
            int index=1;
            Color color = null;
            for(int i = 0; i<players.size(); i++){
                if(!players.get(i).nickname().equals(nickname)) {
                    switch (index){
                        case 1 -> {
                            for(ImmOtherPlayer p: game.otherPlayers()){
                                if(p.nickname().equals(players.get(i).nickname())){
                                    color = p.color();
                                    break;
                                }
                            }
                            if(color!=null){
                                posizionaPedine(List.of(color),players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints(),anchorPanesOnBoard.get(players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints()));
                            }
                            user1HandCard3.setVisible(false);
                            user1HandCard2.setVisible(false);
                            streamObjectiveEndGame(players.get(i).playerArea().objectiveCard().cardID(),user1HandCard1);
                        }
                        case 2 -> {
                            for(ImmOtherPlayer p: game.otherPlayers()){
                                if(p.nickname().equals(players.get(i).nickname())){
                                    color = p.color();
                                    break;
                                }
                            }
                            if(color!=null){
                                posizionaPedine(List.of(color),players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints(),anchorPanesOnBoard.get(players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints()));
                            }
                            user1HandCard3.setVisible(false);
                            user1HandCard2.setVisible(false);
                            streamObjectiveEndGame(players.get(i).playerArea().objectiveCard().cardID(),user2HandCard1);
                        }
                        case 3 -> {
                            for(ImmOtherPlayer p: game.otherPlayers()){
                                if(p.nickname().equals(players.get(i).nickname())){
                                    color = p.color();
                                    break;
                                }
                            }
                            if(color!=null){
                                posizionaPedine(List.of(color),players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints(),anchorPanesOnBoard.get(players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints()));
                            }
                            user1HandCard3.setVisible(false);
                            user1HandCard2.setVisible(false);
                            streamObjectiveEndGame(players.get(i).playerArea().objectiveCard().cardID(),user3HandCard1);
                        }
                    }
                    index++;
                }else{
                    //aggiorno posizione della mia pedina
                    posizionaPedine(List.of(myColor),players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints(),anchorPanesOnBoard.get(players.get(i).playerArea().points()+players.get(i).playerArea().extraPoints()));
                }
            }
        });
    }

    /**
     * method called to compute the winner
     * @param pointsMap hash map of players' nickname and List od points and extra points
     * @return winner
     */
    private String computeWinner(HashMap<String, List<Integer>> pointsMap) {
        String winner = "";
        int maxPoints = Integer.MIN_VALUE;
        int maxExtraPoints = Integer.MIN_VALUE;
        List<String> winners = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : pointsMap.entrySet()) {
            String player = entry.getKey();
            List<Integer> points = entry.getValue();
            int totalPoints = points.get(0) + points.get(1);
            int extraPoints = points.get(1);

            if (totalPoints > maxPoints || (totalPoints == maxPoints && extraPoints > maxExtraPoints)) {
                winner = player;
                maxPoints = totalPoints;
                maxExtraPoints = extraPoints;
                winners.clear();
                winners.add(player);
            } else if (totalPoints == maxPoints && extraPoints == maxExtraPoints) {
                winners.add(player);
            }
        }

        if (winners.size() == 1) {
            return winner;
        } else {
            return String.join(" ", winners);
        }
    }

    /**
     * method called to stream objetive cards
     */
    private void streamObjectiveEndGame(String id, ImageView userHandCard1) {
        String path = "/CardsImages/Objective/" + id + ".png";

        try(InputStream cardStream = getClass().getResourceAsStream(path)){
            userHandCard1.setFitHeight(71);
            userHandCard1.setFitWidth(107);
            userHandCard1.setImage(new Image(cardStream));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void gameCanceled() {
        Platform.runLater(()->{
            leaveButton.setVisible(false);
            lobbyButton.setVisible(true);
            setUpdateText(List.of("You won!\nPress LOBBY"));
        });
    }

    public void updatePlayerInGameStatus(String message, ImmGame immGame, String playerNickname) {
        Platform.runLater(()->{
            setUpdateText(List.of(playerNickname + " " + message));

        });
    }

    public void gamePaused() {
        Platform.runLater(()->{
            setUpdateText(List.of("Currently, you're the only player connected.\nPlease wait for another player to rejoin within 10 seconds."));
        });
    }

    public void gameResumed() {
        Platform.runLater(()->{
            setUpdateText(List.of("Game resumed!"));
        });
    }
    @FXML
    private TextField messageText;
    @FXML
    private ComboBox<String> comboBoxMessage;
    @FXML
    private ListView<String> chatList;
    @FXML
    void actionSendMessage(MouseEvent event) {
        if (!messageText.getText().isEmpty()) {

            if (comboBoxMessage.getValue().isEmpty()) {
                viewGUI.sendMessage(null,messageText.getText());
            } else {
                //Player wants to send a private message
                viewGUI.sendMessage(comboBoxMessage.getValue(), messageText.getText());
                comboBoxMessage.getSelectionModel().selectFirst();
            }
            messageText.setText("");

        }
    }
    @FXML
    public void actionKeyPressedOnTextMessage(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            actionSendMessage(null);
        }
    }

    public void messageSent(ImmGame immGame) {
        Platform.runLater(()->{
            chatList.getItems().clear();
            for (ImmMessage message : immGame.chat()) {
                String m = "[" + message.sentTime() + "]" + message.sender() + ": " + message.content();

                if(message.receivers().size() == immGame.playerOrderNicknames().size()-1) {
                    chatList.getItems().add(m);
                }else if(message.receivers().getFirst().equals(nickname)||message.sender().equals(nickname)){
                    chatList.getItems().add("[Private] " + m);
                }
            }
        });
    }

    public void rejoined(ImmGame game) {
        this.game = game;
        nickname = game.player().nickname();
        myColor = game.player().color();

        lobbyButton.setVisible(false);

        setAreaSize(user1PlayerArea,playerArea1);
        setAreaSize(user2PlayerArea,playerArea2);
        setAreaSize(user3PlayerArea,playerArea3);
        setAreaSize(myPlayerAreaAnchorPane,myPlayerArea);

        user1Initialcard.setLayoutX(9999);
        user1Initialcard.setLayoutY(9999);

        user2Initialcard.setLayoutX(9999);
        user2Initialcard.setLayoutY(9999);

        user3Initialcard.setLayoutX(9999);
        user3Initialcard.setLayoutY(9999);

        myInitialCard.setLayoutX(9999);
        myInitialCard.setLayoutY(9999);

        ////////////////////////////////////////////////////////
        ///////tabellone////////////////////////////////////////
        ////////////////////////////////////////////////////////
        initializeBoard();
        for(int i = 0; i<=29; i++) {
            AnchorPane anchorPane = createAnchorPaneOnBoard(boardPointsPosition.get(i).get(0),boardPointsPosition.get(i).get(1));
            anchorPanesOnBoard.add(anchorPane);
        }
        ///////NB: se più di una pedina sullo stesso punteggio, questo potrebbe essere non visualizzato uguale per il giocatore riconesso
        List<Color> colorsIn0 = new ArrayList<>();
        List<String> playersIn0 = new ArrayList<>();
        if(game.player().playerArea().points()==0){
            colorsIn0.add(game.player().color());
            playersIn0.add(game.player().nickname());
        }
        for(ImmOtherPlayer p: game.otherPlayers()){
            if(p.playerArea().points()==0){
                colorsIn0.add(p.color());
                playersIn0.add(p.nickname());
            }
        }
        if(!colorsIn0.isEmpty()){
            ImageView view;

            streamColor(colorsIn0.getFirst(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1"));
            view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1");
            view.setId(colorsIn0.getFirst() + "pawn1");
            if (colorsIn0.size() >= 2 ){
                streamColor(colorsIn0.getLast(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2"));
                view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2");
                view.setId(colorsIn0.getLast() + "pawn2");
            }
            if (colorsIn0.size() >= 3 ){
                streamColor(colorsIn0.getLast(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn3"));
                view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn3");
                view.setId(colorsIn0.getLast() + "pawn3");
            }
            if (colorsIn0.size() == 4 ){
                streamColor(colorsIn0.getLast(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn4"));
                view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn4");
                view.setId(colorsIn0.getLast() + "pawn4");
            }
        }

        for(String p: game.playerOrderNicknames()){
            if(!playersIn0.contains(p)){
                if(p.equals(game.player().nickname())){
                    ImageView firstAvailable = computeFirstAvailablePosition(game.player().playerArea().points(),game.player().color());
                    streamColor(game.player().color(),firstAvailable);
                }else{
                    int index = -1;
                    for(ImmOtherPlayer o: game.otherPlayers()){
                        if(o.nickname().equals(p)){
                            index = game.otherPlayers().indexOf(o);
                            break;
                        }
                    }
                    ImageView firstAvailable = computeFirstAvailablePosition(game.otherPlayers().get(index).playerArea().points(),game.otherPlayers().get(index).color());
                    streamColor(game.otherPlayers().get(index).color(),firstAvailable);
                }
            }
        }

        ////////////////////////////////////////////////////////
        ///////mazzi e carte scoperte///////////////////////////
        ////////////////////////////////////////////////////////

        List<String> resourceCards = new ArrayList<>();
        if (game.topResourceCard() != null) {
            resourceCards.add(game.topResourceCard().cardID());
        }else{
            resourceCards.add("empty");
        }
        for(ImmPlayableCard c : game.revealedResourceCards()){
            resourceCards.add(c.cardID());
        }
        while(resourceCards.size()!=3){
            resourceCards.add("empty");
        }

        List<String> goldCards = new ArrayList<>();
        if(game.topGoldCard() != null) {
            goldCards.add(game.topGoldCard().cardID());
        }else{
            goldCards.add("empty");
        }
        for(ImmPlayableCard c : game.revealedGoldCards()){
            goldCards.add(c.cardID());
        }
        while(goldCards.size()!=3){
            goldCards.add("empty");
        }

        //streamTopRGImage(game.revealedResourceCards().getFirst().cardID(),game.revealedResourceCards().getLast().cardID(),false);//????
        streamTopRGImage(resourceCards.getFirst(),goldCards.getFirst(),true);
        streamTopRGImage(resourceCards.getFirst(),goldCards.getFirst(),false);

        streamRevealedId(goldCards.get(1),goldCards.getLast(),resourceCards.get(1),resourceCards.getLast());

        ////////////////////////////////////////////////////////
        ///////obiettivi comuni e segreti///////////////////////
        ////////////////////////////////////////////////////////
        String secretObjective = "/CardsImages/Objective/" + game.player().playerArea().objectiveCard().cardID() + ".png";
        String commonObj1 = "/CardsImages/Objective/" + game.commonObjectiveCards().getFirst().cardID() + ".png";
        String commonObj2 = "/CardsImages/Objective/" + game.commonObjectiveCards().getLast().cardID() + ".png";

        try(InputStream secretObj = getClass().getResourceAsStream(secretObjective);
            InputStream commonObj1Stream = getClass().getResourceAsStream(commonObj1);
            InputStream commonObj2Stream = getClass().getResourceAsStream(commonObj2)
        ){
            this.commonObjective1.setImage(new Image(commonObj1Stream));
            this.commonObjective2.setImage(new Image(commonObj2Stream));

            mySecretObjective.setImage(new Image(secretObj));
        }catch (IOException e){
            e.printStackTrace();
        }

        ////////////////////////////////////////////////////////
        ///////Chat/////////////////////////////////////////////
        ////////////////////////////////////////////////////////

        if (comboBoxMessage.getItems().size() == 0) {
            comboBoxMessage.getItems().add("");
            for (String p : game.playerOrderNicknames()) {
                if(!p.equals(game.player().nickname())){
                    comboBoxMessage.getItems().add(p);
                }
            }
            comboBoxMessage.getSelectionModel().selectFirst();
        }

        chatList.getItems().clear();
        for (ImmMessage message : game.chat()) {
            String m = "[" + message.sentTime() + "]" + message.sender() + ": " + message.content();

            if(message.receivers().size() == game.playerOrderNicknames().size()-1) {
                chatList.getItems().add(m);
            }else if(message.receivers().getFirst().equals(nickname)||message.sender().equals(nickname)){
                chatList.getItems().add("[Private] " + m);
            }
        }

        ////////////////////////////////////////////////////////
        ///////mio campo + carte in mano////////////////////////
        ////////////////////////////////////////////////////////

        Map.Entry<List<Integer>, ImmPlayableCard> placed = null;
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : game.player().playerArea().area().entrySet()) {
            placed = cardAndCoordinates;

            int x = placed.getKey().getFirst();
            int y = placed.getKey().getLast();
            String idlast = placed.getValue().cardID();

            int layoutX = x*104 + 9999;
            int layoutY = 9999 - 54*y;

            if(placed.getValue().playableCardType()==PlayableCardType.INITIAL){
                streamInitialCard(idlast,myInitialCard,placed.getValue().showingFront());

                myInitialCard.setLayoutX(layoutX);
                myInitialCard.setLayoutY(layoutY);
                Pane pane = createPaneCard(layoutX,layoutY);
                myPlayerAreaAnchorPane.getChildren().add(pane);
                //myPlayerAreaAnchorPane.getChildren().add(toPlace);
            }else{
                ImageView toPlace = new ImageView();
                toPlace.setFitWidth(133);
                toPlace.setFitHeight(93);
                streamCardRejoin(idlast,toPlace,placed.getValue().showingFront());

                toPlace.setLayoutX(layoutX);
                toPlace.setLayoutY(layoutY);
                myPlayerAreaAnchorPane.getChildren().add(toPlace);
                Pane pane = createPaneCard(layoutX,layoutY);
                myPlayerAreaAnchorPane.getChildren().add(pane);
            }

        }
        myNickText.setText(game.player().nickname());

        streamMyHandcard(game.player().hand().getFirst().cardID(),handCard1,game.player().hand().getFirst().showingFront());
        streamMyHandcard(game.player().hand().get(1).cardID(),handCard2,game.player().hand().get(1).showingFront());
        streamMyHandcard(game.player().hand().get(2).cardID(),handCard3,game.player().hand().get(2).showingFront());
        ////////////////////////////////////////////////////////
        ///////campo degli altri + carte in mano////////////////
        ////////////////////////////////////////////////////////
        List<String> playerOrder = new ArrayList<>(game.playerOrderNicknames());
        playerOrder.remove(game.player().nickname());
        List<Color> colors = new ArrayList<>();
        for(int i = 0; i<playerOrder.size(); i++){
            colors.add(game.otherPlayers().get(i).color());
        }

        username1.setText(playerOrder.getLast());


        int index1 = -1;
        int index2 = -1;
        int index3 = -1;
        for(String p: game.playerOrderNicknames()){
            if(!p.equals(game.player().nickname())){
                for(ImmOtherPlayer o: game.otherPlayers()){
                    if(o.nickname().equals(p)){
                        if(index1==-1){
                            index1 = game.otherPlayers().indexOf(o);
                        }
                        if(index2==-1){
                            index2 = game.otherPlayers().indexOf(o);
                        }
                        if(index3==-1){
                            index3 = game.otherPlayers().indexOf(o);
                        }
                    }

                }
            }
        }

        streamOtherPlayerarea(user1PlayerArea,game,index1);
        if(game.otherPlayers().size()==2){
            streamOtherPlayerarea(user2PlayerArea,game,index2);
        }else{
            streamOtherPlayerarea(user2PlayerArea,game,index2);
            streamOtherPlayerarea(user3PlayerArea,game,index3);
        }

        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().getFirst().cardID(),user1HandCard1);
        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().get(1).cardID(),user1HandCard2);
        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().get(2).cardID(),user1HandCard3);

        if(game.otherPlayers().size()==2){
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().getFirst().cardID(),user2HandCard1);
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(1).cardID(),user2HandCard2);
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(2).cardID(),user2HandCard3);
        }else if (game.otherPlayers().size()==3){
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().getFirst().cardID(),user2HandCard1);
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(1).cardID(),user2HandCard2);
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(2).cardID(),user2HandCard3);

            streamOtherPlayerCard(game.otherPlayers().get(1).hand().getFirst().cardID(),user3HandCard1);
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(1).cardID(),user3HandCard2);
            streamOtherPlayerCard(game.otherPlayers().get(1).hand().get(2).cardID(),user3HandCard3);
        }

        //////////posizionare pedine sulle playerarea//////////////////////////////
        ImageView myPedina = new ImageView();
        streamColor(myColor,myPedina);
        myPedina.setFitHeight(33);
        myPedina.setFitWidth(33);
        myPedina.setLayoutX(9999+50);
        myPedina.setLayoutY(9999-17);
        myPlayerAreaAnchorPane.getChildren().add(myPedina);

        if(game.playerOrderNicknames().size()==2){
            user2PlayerArea.setVisible(false);
            playerArea2.setVisible(false);
            username2.setVisible(false);
            user3PlayerArea.setVisible(false);
            playerArea3.setVisible(false);
            username3.setVisible(false);

            ImageView u1Pedina = new ImageView();
            streamUPedina(colors.getFirst(),u1Pedina,"u1Pedina",user1PlayerArea);
        }else{
            switch (game.playerOrderNicknames().size()) {
                case 3 -> {

                    user3PlayerArea.setVisible(false);
                    playerArea3.setVisible(false);
                    username3.setVisible(false);

                    ImageView u2Pedina = new ImageView();
                    streamUPedina(colors.getLast(),u2Pedina,"u2Pedina",user2PlayerArea);

                    username2.setText(playerOrder.getLast());

                }
                case 4 -> {
                    ImageView u2Pedina = new ImageView();
                    streamUPedina(colors.get(1),u2Pedina,"u2Pedina",user2PlayerArea);

                    username2.setText(playerOrder.getLast());

                    ImageView u3Pedina = new ImageView();
                    streamUPedina(colors.getLast(),u3Pedina,"u3Pedina",user3PlayerArea);

                    username3.setText(playerOrder.getLast());
                }
            }
        }

        String pedinanera = "/pedine/pedina_nera.png";
        try(InputStream neroStream = getClass().getResourceAsStream(pedinanera)){
            if(game.player().nickname().equals(game.playerOrderNicknames().getFirst())){
                //posiziono la pedina nera sul mio
                ImageView blackPawn = new ImageView(new Image(neroStream));
                blackPawn.setFitHeight(33);
                blackPawn.setFitWidth(33);
                blackPawn.setLayoutX(9999+50);
                blackPawn.setLayoutY(9999-17-5);
                myPlayerAreaAnchorPane.getChildren().add(blackPawn);
            }else{
                //posiziono la pedina nera sul campo di un altro giocatore
                int index = -1;
                for(ImmOtherPlayer p: game.otherPlayers()){
                    if(p.nickname().equals(game.playerOrderNicknames().getFirst())){
                        index = game.otherPlayers().indexOf(p);
                        break;
                    }
                }
                ImageView blackPawn = new ImageView(new Image(neroStream));
                switch (index){
                    case 0 -> {
                        blackPawn.setFitHeight(33);
                        blackPawn.setFitWidth(33);
                        blackPawn.setLayoutX(9999+37);
                        blackPawn.setLayoutY(9999-20-5);
                        user1PlayerArea.getChildren().add(blackPawn);
                    }
                    case 1 -> {
                        blackPawn.setFitHeight(33);
                        blackPawn.setFitWidth(33);
                        blackPawn.setLayoutX(9999+37);
                        blackPawn.setLayoutY(9999-20-5);
                        user2PlayerArea.getChildren().add(blackPawn);
                    }
                    case 2 -> {
                        blackPawn.setFitHeight(33);
                        blackPawn.setFitWidth(33);
                        blackPawn.setLayoutX(9999+37);
                        blackPawn.setLayoutY(9999-20-5);
                        user3PlayerArea.getChildren().add(blackPawn);
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void streamCardRejoin(String id, ImageView toPlace, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/HandCards/front/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path)){
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/HandCards/back/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path)){
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void streamOtherPlayerarea(AnchorPane userPlayerArea, ImmGame game, int index) {
        Map.Entry<List<Integer>, ImmPlayableCard> placed;
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : game.otherPlayers().get(index).playerArea().area().entrySet()) {
            placed = cardAndCoordinates;
            int x = placed.getKey().getFirst();
            int y = placed.getKey().getLast();
            String idlast = placed.getValue().cardID();

            int layoutX = x*83 + 9999;
            int layoutY = 9999 - 43*y;

            if(cardAndCoordinates.getValue().playableCardType()==PlayableCardType.INITIAL){
                if(userPlayerArea==user1PlayerArea){
                    streamInitialCardRejoin(idlast,user1Initialcard,placed.getValue().showingFront());
                    user1Initialcard.setLayoutX(layoutX);
                    user1Initialcard.setLayoutY(layoutY);
                }
                if(userPlayerArea==user2PlayerArea){
                    streamInitialCardRejoin(idlast,user2Initialcard,placed.getValue().showingFront());
                    user2Initialcard.setLayoutX(layoutX);
                    user2Initialcard.setLayoutY(layoutY);
                }
                if(userPlayerArea==user3PlayerArea){
                    streamInitialCardRejoin(idlast,user3Initialcard,placed.getValue().showingFront());
                    user3Initialcard.setLayoutX(layoutX);
                    user3Initialcard.setLayoutY(layoutY);
                }

            }else{
                ImageView toPlace = new ImageView();
                streamCard(idlast,toPlace,placed.getValue().showingFront());

                toPlace.setLayoutX(layoutX);
                toPlace.setLayoutY(layoutY);
                userPlayerArea.getChildren().add(toPlace);
            }
        }
    }

    private void streamInitialCardRejoin(String id, ImageView toPlace, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/Initial/fronts/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path)){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/Initial/backs/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path)){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void streamMyHandcard(String id, ImageView handCard, Boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/HandCards/front/" + id + ".png";

            try(InputStream handCardStream = getClass().getResourceAsStream(path)){
                handCard.setImage(new Image(handCardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/HandCards/back/" + id + ".png";

            try(InputStream handCardStream = getClass().getResourceAsStream(path)){
                handCard.setImage(new Image(handCardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void leaveButton(ActionEvent event) {
        viewGUI.leaveGame();
    }

    @FXML
    void lobbyButton(ActionEvent event) {
        viewGUI.returnToLobby();
    }

    public void setUpdateText (List<String> updatesText) {
        importantEventsList.getItems().clear();
        for (String s : updatesText) {
            importantEventsList.getItems().add(s);
        }
        importantEventsList.scrollTo(importantEventsList.getItems().size());
    }

    public void updateChatComboBox(String playerNickname) {
        comboBoxMessage.getItems().remove(playerNickname);
    }

}
