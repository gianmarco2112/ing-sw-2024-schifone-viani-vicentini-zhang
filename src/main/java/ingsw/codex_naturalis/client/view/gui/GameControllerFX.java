package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmPlayableCard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class GameControllerFX {
    @FXML
    private ImageView board;

    @FXML
    private ScrollPane chatBox;

    @FXML
    private Label chatLBL;

    @FXML
    private TextField chatTextField;

    @FXML
    private VBox chatbox;

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
    private Label gameUpdateLBL;

    @FXML
    private AnchorPane gameUpdates;

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
    private Label secretObjextiveLBL;

    @FXML
    private Button sendMessageButton;

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
    private String cornerClicked;
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

    //posizioni delle anchorPane
    private HashMap<Integer, List<Integer>> boardPointsPosition = new HashMap<>();
    private String nickname;
    @FXML
    private Text myNickText;

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

    public void endSetup(String initialCard, Boolean showingFront, String myObjectiveCard,
                         String handCard1, String handCard2, String handCard3,
                         String commonObjective1, String commonObjective2,
                         String topGoldCard, String topResourceCard, Symbol kingdomG, Symbol kingdomR,
                         String revealedResourceCard1, String revealedResourceCard2,
                         String revealedGoldCard1, String revealedGoldCard2,
                         Color myColor, String firstPlayer,
                         int maxNumOfPlayers, String mynickname, List<String> otherNicknames, List<ImmPlayableCard> initialCards, List<Color> colors, ImmGame game) {

        nickname = mynickname;
        myNickText.setText(mynickname);

        myPlayerAreaAnchorPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        myPlayerAreaAnchorPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

        user1PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
        user1PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
        playerArea1.setHvalue(1.0);
        playerArea1.setVvalue(1.0);

        user1Initialcard.setLayoutX(9999);
        user1Initialcard.setLayoutY(9999);

        username1.setText(otherNicknames.getFirst());

        String u1InitialCard;
        if(initialCards.getFirst().showingFront()){
            u1InitialCard = "/CardsImages/Initial/fronts/" + initialCards.getFirst().cardID() + ".png";
        }else{
            u1InitialCard = "/CardsImages/Initial/backs/" + initialCards.getFirst().cardID() + ".png";
        }

        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().getFirst().cardID(),user1HandCard1);
        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().get(1).cardID(),user1HandCard2);
        streamOtherPlayerCard(game.otherPlayers().getFirst().hand().getLast().cardID(),user1HandCard3);

        if(maxNumOfPlayers==2){
            user2PlayerArea.setVisible(false);
            playerArea2.setVisible(false);
            username2.setVisible(false);
            user3PlayerArea.setVisible(false);
            playerArea3.setVisible(false);
            username3.setVisible(false);
        }else{
            switch (maxNumOfPlayers) {
                case 3 -> {
                    user2PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    user2PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    playerArea2.setHvalue(1.0);
                    playerArea2.setVvalue(1.0);
                    user3PlayerArea.setVisible(false);
                    playerArea3.setVisible(false);
                    username3.setVisible(false);

                    user2Initialcard.setLayoutX(9999);
                    user2Initialcard.setLayoutY(9999);
                    streamInitialCard(initialCards.getLast().cardID(),user2Initialcard,initialCards.getLast().showingFront());

                    ImageView u2Pedina = new ImageView();
                    streamColor(colors.getLast(),u2Pedina);
                    u2Pedina.setFitHeight(33);
                    u2Pedina.setFitWidth(33);
                    u2Pedina.setLayoutX(9999+37);
                    u2Pedina.setLayoutY(9999-20);
                    user2PlayerArea.getChildren().add(u2Pedina);

                    username2.setText(otherNicknames.getLast());

                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().getFirst().cardID(),user2HandCard1);
                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().get(1).cardID(),user2HandCard2);
                    streamOtherPlayerCard(game.otherPlayers().getLast().hand().getLast().cardID(),user2HandCard3);
                }
                case 4 -> {
                    user2PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    user2PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    user3PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    user3PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    playerArea2.setHvalue(1.0);
                    playerArea2.setVvalue(1.0);
                    playerArea3.setHvalue(1.0);
                    playerArea3.setVvalue(1.0);

                    user2Initialcard.setLayoutX(9999);
                    user2Initialcard.setLayoutY(9999);
                    username2.setText(otherNicknames.get(1));
                    user3Initialcard.setLayoutX(9999);
                    user3Initialcard.setLayoutY(9999);
                    username3.setText(otherNicknames.getLast());
                    streamInitialCard(initialCards.get(1).cardID(), user2Initialcard, initialCards.get(1).showingFront());
                    streamInitialCard(initialCards.getLast().cardID(), user3Initialcard, initialCards.getLast().showingFront());

                    ImageView u2Pedina = new ImageView();
                    streamColor(colors.get(1),u2Pedina);
                    u2Pedina.setFitHeight(33);
                    u2Pedina.setFitWidth(33);
                    u2Pedina.setLayoutX(9999+37);
                    u2Pedina.setLayoutY(9999-20);
                    user2PlayerArea.getChildren().add(u2Pedina);

                    ImageView u3Pedina = new ImageView();
                    streamColor(colors.getLast(),u3Pedina);
                    u3Pedina.setFitHeight(33);
                    u3Pedina.setFitWidth(33);
                    u3Pedina.setLayoutX(9999+37);
                    u3Pedina.setLayoutY(9999-20);
                    user3PlayerArea.getChildren().add(u3Pedina);

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

        String myinitalcard;
        String hand1 = "/CardsImages/HandCards/" + handCard1 + ".png";
        String hand2 = "/CardsImages/HandCards/" + handCard2 + ".png";
        String hand3 = "/CardsImages/HandCards/" + handCard3 + ".png";
        String rossa = "/pedine/pedina_rossa.png";
        String blu = "/pedine/pedina_blu.png";
        String gialla = "/pedine/pedina_gialla.png";
        String verde = "/pedine/pedina_verde.png";
        String secretObjective = "/CardsImages/Objective/" + myObjectiveCard + ".png";
        String commonObj1 = "/CardsImages/Objective/" + commonObjective1 + ".png";
        String commonObj2 = "/CardsImages/Objective/" + commonObjective2 + ".png";
        String topRCard = null;
        String topGCard = null;
        String revealedRC1 = "/CardsImages/HandCards/" + revealedResourceCard1 + ".png";
        String revealedRC2 = "/CardsImages/HandCards/" + revealedResourceCard2 + ".png";
        String revealedGC1 = "/CardsImages/HandCards/" + revealedGoldCard1 + ".png";
        String revealedGC2 = "/CardsImages/HandCards/" + revealedGoldCard2 + ".png";
        String myColorChosen = null;

        if(showingFront){
            myinitalcard = "/CardsImages/Initial/fronts/" + initialCard + ".png";
        } else {
            myinitalcard = "/CardsImages/Initial/backs/" + initialCard + ".png";
        }

        switch (kingdomG){
            case Symbol.ANIMAL -> topGCard = "/CardsImages/HandCards/GbackANIMAL.png";
            case Symbol.FUNGI -> topGCard = "/CardsImages/HandCards/GbackFUNGI.png";
            case Symbol.PLANT -> topGCard = "/CardsImages/HandCards/GbackPLANT.png";
            case Symbol.INSECT -> topGCard = "/CardsImages/HandCards/GbackINSECT.png";
        }

        switch (kingdomR){
            case Symbol.ANIMAL -> topRCard = "/CardsImages/HandCards/RbackANIMAL.png";
            case Symbol.FUNGI -> topRCard = "/CardsImages/HandCards/RbackFUNGI.png";
            case Symbol.PLANT -> topRCard = "/CardsImages/HandCards/RbackPLANT.png";
            case Symbol.INSECT -> topRCard = "/CardsImages/HandCards/RbackINSECT.png";
        }

        switch (myColor){
            case Color.BLUE -> myColorChosen = "/pedine/pedina_blu.png";
            case Color.RED -> myColorChosen = "/pedine/pedina_rossa.png";
            case Color.GREEN -> myColorChosen = "/pedine/pedina_verde.png";
            case Color.YELLOW -> myColorChosen = "/pedine/pedina_gialla.png";
        }


        try (InputStream initialCardStream = getClass().getResourceAsStream(myinitalcard);
             InputStream rossaStream = getClass().getResourceAsStream(rossa);
             InputStream bluStream = getClass().getResourceAsStream(blu);
             InputStream giallaStream = getClass().getResourceAsStream(gialla);
             InputStream verdeStream = getClass().getResourceAsStream(verde);
             InputStream secretObj = getClass().getResourceAsStream(secretObjective);
             InputStream hand1Stream = getClass().getResourceAsStream(hand1);
             InputStream hand2Stream = getClass().getResourceAsStream(hand2);
             InputStream hand3Stream = getClass().getResourceAsStream(hand3);
             InputStream commonObj1Stream = getClass().getResourceAsStream(commonObj1);
             InputStream commonObj2Stream = getClass().getResourceAsStream(commonObj2);
             InputStream revealedRC1Stream = getClass().getResourceAsStream(revealedRC1);
             InputStream revealedRC2Stream = getClass().getResourceAsStream(revealedRC2);
             InputStream revealedGC1Stream = getClass().getResourceAsStream(revealedGC1);
             InputStream revealedGC2Stream = getClass().getResourceAsStream(revealedGC2);
             InputStream topRCardStream = getClass().getResourceAsStream(topRCard);
             InputStream topGCardStream = getClass().getResourceAsStream(topGCard);
             InputStream myColorStream = getClass().getResourceAsStream(myColorChosen);

             InputStream u1initialCardStream = getClass().getResourceAsStream(u1InitialCard);
        ) {

            myInitialCard.setImage(new Image(initialCardStream));

            user1Initialcard.setImage(new Image(u1initialCardStream));

            ImageView pedinaRossa = new ImageView(new Image(rossaStream));
            pedinaRossa.setFitHeight(33);
            pedinaRossa.setFitWidth(33);
            pedinaRossa.setLayoutX(32);
            pedinaRossa.setLayoutY(323);
            pedinaRossa.setId("RED");
            globalPane.getChildren().add(pedinaRossa);

            ImageView pedinaBlu = new ImageView(new Image(bluStream));
            pedinaBlu.setFitHeight(33);
            pedinaBlu.setFitWidth(33);
            pedinaBlu.setLayoutX(32);
            pedinaBlu.setLayoutY(323-3);
            pedinaBlu.setId("BLUE");
            globalPane.getChildren().add(pedinaBlu);

            ImageView pedinaGialla = new ImageView(new Image(giallaStream));
            pedinaGialla.setFitHeight(33);
            pedinaGialla.setFitWidth(33);
            pedinaGialla.setLayoutX(32);
            pedinaGialla.setLayoutY(323-3-3);
            pedinaGialla.setId("YELLOW");
            globalPane.getChildren().add(pedinaGialla);

            ImageView pedinaVerde = new ImageView(new Image(verdeStream));
            pedinaVerde.setFitHeight(33);
            pedinaVerde.setFitWidth(33);
            pedinaVerde.setLayoutX(32);
            pedinaVerde.setLayoutY(323-3-3-3);
            pedinaVerde.setId("GREEN");
            globalPane.getChildren().add(pedinaVerde);

            mySecretObjective.setImage(new Image(secretObj));

            this.handCard1.setImage(new Image(hand1Stream));
            this.handCard2.setImage(new Image(hand2Stream));
            this.handCard3.setImage(new Image(hand3Stream));

            this.commonObjective1.setImage(new Image(commonObj1Stream));
            this.commonObjective2.setImage(new Image(commonObj2Stream));

            this.revealedResource1.setImage(new Image(revealedRC1Stream));
            this.revealedResource2.setImage(new Image(revealedRC2Stream));
            this.revealedGold1.setImage(new Image(revealedGC1Stream));
            this.revealedGold2.setImage(new Image(revealedGC2Stream));

            this.resourceDeck.setImage(new Image(topRCardStream));
            this.goldDeck.setImage(new Image(topGCardStream));

            ImageView myPedina = new ImageView(new Image(myColorStream));
            myPedina.setFitHeight(33);
            myPedina.setFitWidth(33);
            myPedina.setLayoutX(9999+50);
            myPedina.setLayoutY(9999-17);
            myPlayerAreaAnchorPane.getChildren().add(myPedina);

            ImageView u1Pedina = new ImageView();
            streamColor(colors.getFirst(),u1Pedina);
            u1Pedina.setFitHeight(33);
            u1Pedina.setFitWidth(33);
            u1Pedina.setLayoutX(9999+37);
            u1Pedina.setLayoutY(9999-20);
            user1PlayerArea.getChildren().add(u1Pedina);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Text testo = new Text();
        testo.setText("The game setup ended!\n" + firstPlayer + "'s turn");
        testo.setLayoutY(20);
        testo.setLayoutX(20);
        gameUpdates.getChildren().add(testo);
        testo.wrappingWidthProperty().bind(gameUpdates.maxWidthProperty());
        testo.wrappingWidthProperty().bind(gameUpdates.maxHeightProperty());

        Pane pane = createPaneCard(9999,9999);
        //pane.setStyle("-fx-background-color: lightblue;");
        myPlayerAreaAnchorPane.getChildren().add(pane);

        String pedinanera = "/pedine/pedina_nera.png";
        try(InputStream neroStream = getClass().getResourceAsStream(pedinanera);){
            if(mynickname.equals(firstPlayer)){
                //posiziono la pedina nera sul mio
                ImageView blackPawn = new ImageView(new Image(neroStream));
                blackPawn.setFitHeight(33);
                blackPawn.setFitWidth(33);
                blackPawn.setLayoutX(9999+50);
                blackPawn.setLayoutY(9999-17-5);
                myPlayerAreaAnchorPane.getChildren().add(blackPawn);
            }else{
                //posiziono la pedina nera sul campo di un altro giocatore
                switch (otherNicknames.indexOf(firstPlayer)){
                    case 0 -> {
                        ImageView blackPawn = new ImageView(new Image(neroStream));
                        blackPawn.setFitHeight(33);
                        blackPawn.setFitWidth(33);
                        blackPawn.setLayoutX(9999+37);
                        blackPawn.setLayoutY(9999-20-5);
                        user1PlayerArea.getChildren().add(blackPawn);
                    }
                    case 1 -> {
                        ImageView blackPawn = new ImageView(new Image(neroStream));
                        blackPawn.setFitHeight(33);
                        blackPawn.setFitWidth(33);
                        blackPawn.setLayoutX(9999+37);
                        blackPawn.setLayoutY(9999-20-5);
                        user2PlayerArea.getChildren().add(blackPawn);
                    }
                    case 2 -> {
                        ImageView blackPawn = new ImageView(new Image(neroStream));
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

    private void streamOtherPlayerCard(String id, ImageView userHandCard) {
        String path = "/CardsImages/Handcards/back/" + id + ".png";

        try(InputStream otherPlayerCardStream = getClass().getResourceAsStream(path);){
            userHandCard.setImage(new Image(otherPlayerCardStream));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void streamInitialCard(String initialCardId, ImageView userInitialcardView, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/Initial/fronts/" + initialCardId + ".png";

            try(InputStream initialcardStream = getClass().getResourceAsStream(path);){
                userInitialcardView.setImage(new Image(initialcardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/Initial/backs/" + initialCardId + ".png";

            try(InputStream initialcardStream = getClass().getResourceAsStream(path);){
                userInitialcardView.setImage(new Image(initialcardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }



    }


    @FXML
    void flipHandCard1(ActionEvent event) {
        viewGUI.flippingCard(0);
    }

    @FXML
    void flipHandCard2(ActionEvent event) {
        viewGUI.flippingCard(1);
    }

    @FXML
    void flipHandCard3(ActionEvent event) {
        viewGUI.flippingCard(2);
    }

    public void cardFlipped(String cardId, boolean showingFront, Symbol kingdom, PlayableCardType playableCardType, int indexOfFlippedCard) {
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
    }

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

    private Rectangle createCorner(int width, int height) {
        Rectangle corner = new Rectangle(width,height);
        //corner.setFill(javafx.scene.paint.Color.RED);
        corner.setFill(javafx.scene.paint.Color.TRANSPARENT);
        corner.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        return corner;
    }

    public void handlerCornerClick(MouseEvent event, String corner, double layoutX, double layoutY, boolean ok, int points, Color myColor) {
        //evento play card da notificare
        System.out.println(corner + "Clicked!!");

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

    private static ImageView copyImageView(ImageView imageToCopie) {
        ImageView copy = new ImageView(imageToCopie.getImage());
        copy.setFitWidth(imageToCopie.getFitWidth());
        copy.setFitHeight(imageToCopie.getFitHeight());
        return copy;
    }

    @FXML
    void playHandCard1(MouseEvent event) {
        System.out.println("Voglio giocare la prima carta!");
        selectedCard = copyImageView(handCard1);
        selectedCardIndex = 0;
    }

    @FXML
    void playHandCard2(MouseEvent event) {
        System.out.println("Voglio giocare la seconda carta!");
        selectedCard = copyImageView(handCard2);
        selectedCardIndex = 1;
    }

    @FXML
    void playHandCard3(MouseEvent event) {
        System.out.println("Voglio giocare la terza carta!");
        selectedCard = copyImageView(handCard3);
        selectedCardIndex = 2;
    }

    @FXML
    void drawFromGoldDeck(MouseEvent event) {
        System.out.println("voglio pescare dal deck di oro");
        viewGUI.drawingCard(DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK);
    }

    @FXML
    void drawFromResourceDeck(MouseEvent event) {
        System.out.println("voglio pescare dal deck di risorse");
        viewGUI.drawingCard(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK);
    }

    @FXML
    void drawFromRevealedGold1(MouseEvent event) {
        System.out.println("voglio pescare la prima carta oro rivelata");
        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);
    }

    @FXML
    void drawFromRevealedGold2(MouseEvent event) {
        System.out.println("voglio pescare la seconda carta oro rivelata");
        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2);
    }

    @FXML
    void drawFromRevealedResource1(MouseEvent event) {
        System.out.println("voglio pescare la prima carta risorsa rivelata");
        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
    }

    @FXML
    void drawFromRevealedResource2(MouseEvent event) {
        System.out.println("voglio pescare la prima carta risorsa rivelata");
        viewGUI.drawingCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2);
    }

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
                            streamRevealedId(revG1,revG2,revR1,revR2);;
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void streamTopRGImage(String topResourceCardId, String topGoldCardId, boolean isGold) {
        String kingdomTopG = null;
        String kingdomTopR = null;

        if(isGold){
            kingdomTopG = "/CardsImages/HandCards/back/" + topGoldCardId + ".png";

            try(InputStream topGStream = getClass().getResourceAsStream(kingdomTopG)){
                this.goldDeck.setImage(new Image(topGStream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            kingdomTopR = "/CardsImages/HandCards/back/" + topResourceCardId + ".png";

            try(InputStream topRStream = getClass().getResourceAsStream(kingdomTopR)){
                this.resourceDeck.setImage(new Image(topRStream));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                        selectedCard.setLayoutX((int) layoutXOfCardClicked - 104 +1);
                        selectedCard.setLayoutY((int) layoutYOfCardClicked - 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutXOfCardClicked - 104, (int) layoutYOfCardClicked - 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (-(9999 - (int) layoutXOfCardClicked)/104)-1;
                        y = ((9999 - (int) layoutYOfCardClicked)/54)+1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                    case "TopRight" -> {
                        selectedCard.setLayoutX((int) layoutXOfCardClicked + 104 +1);
                        selectedCard.setLayoutY((int) layoutYOfCardClicked - 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutXOfCardClicked + 104, (int) layoutYOfCardClicked - 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (((int) layoutXOfCardClicked - 9999)/104) + 1;
                        y = ((9999 - (int) layoutYOfCardClicked)/54) + 1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                    case "BottomLeft" -> {
                        selectedCard.setLayoutX((int) layoutXOfCardClicked - 104 +1);
                        selectedCard.setLayoutY((int) layoutYOfCardClicked + 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutXOfCardClicked - 104, (int) layoutYOfCardClicked + 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (-(9999 - (int) layoutXOfCardClicked)/104) - 1;
                        y = (-((int) layoutYOfCardClicked - 9999)/54) - 1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                    case "BottomRight" -> {
                        selectedCard.setLayoutX((int) layoutXOfCardClicked + 104 +1);
                        selectedCard.setLayoutY((int) layoutYOfCardClicked + 54 -4);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutXOfCardClicked + 104, (int) layoutYOfCardClicked + 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //for debugging
                        x = (((int) layoutXOfCardClicked - 9999)/104) + 1;
                        y = ((-(int) layoutYOfCardClicked - 9999)/54) - 1;
                        System.out.println("carta posizionata in (" + x + "," + y + ")");
                    }
                }

                /*if(points>0){
                    //aggiorno punteggio sul tabellone
                    switch (points) {
                        //TODO la cosa migliore sarebbe spostare le pedine in 0 sopra in endSetup qui, così quando tolgo una pedina in mezzo alle altre posso allineare
                        case 1 -> {
                            //cancello la mia pedina da 0
                            Node nodo = globalPane.lookup("#" + myColor);
                            if (nodo instanceof ImageView) {
                                ImageView foundPedina = (ImageView) nodo;
                                foundPedina.setVisible(false);
                            }
                            for(Node node : point1.getChildren()) {
                                if(node instanceof ImageView) {
                                    ImageView imageView = (ImageView) node;
                                    if(imageView.getImage() == null) {

                                        String myColorChosen = null;

                                        switch (myColor){
                                            case Color.BLUE -> myColorChosen = "/pedine/pedina_blu.png";
                                            case Color.RED -> myColorChosen = "/pedine/pedina_rossa.png";
                                            case Color.GREEN -> myColorChosen = "/pedine/pedina_verde.png";
                                            case Color.YELLOW -> myColorChosen = "/pedine/pedina_gialla.png";
                                        }

                                        try(InputStream myColorStream = getClass().getResourceAsStream(myColorChosen)){
                                            imageView.setImage(new Image(myColorStream));
                                            break;
                                        }catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }
                        }
                    }
                }*/

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
            }
        });
    }

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
                case 0 -> {
                    //aggiorno user1
                    //NB per ottenere la carta pescata basta fare getLast della hand
                    String card = immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID();
                    if(!user1HandCard1.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user1HandCard1);
                        user1HandCard1.setVisible(true);
                    }
                    if(!user1HandCard2.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user1HandCard2);
                        user1HandCard2.setVisible(true);
                    }
                    if(!user1HandCard3.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user1HandCard3);
                        user1HandCard3.setVisible(true);
                    }
                }
                case 1 -> {
                    //aggiorno user 2
                    String card = immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID();
                    if(!user2HandCard1.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user2HandCard1);
                        user2HandCard1.setVisible(true);
                    }
                    if(!user2HandCard2.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user2HandCard2);
                        user2HandCard2.setVisible(true);
                    }
                    if(!user2HandCard3.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user2HandCard3);
                        user2HandCard3.setVisible(true);
                    }
                }
                case 2 -> {
                    //aggiorno user 3
                    String card = immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID();
                    if(!user3HandCard1.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user3HandCard1);
                        user3HandCard1.setVisible(true);
                    }
                    if(!user3HandCard2.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user3HandCard2);
                        user3HandCard2.setVisible(true);
                    }
                    if(!user3HandCard3.isVisible()){
                        streamOtherPlayerCard(immGame.otherPlayers().get(immGame.playerOrderNicknames().indexOf(playerNicknameWhoUpdated)).hand().getLast().cardID(),user3HandCard3);
                        user3HandCard3.setVisible(true);
                    }
                }
            }
        });

    }

    private void streamRevealedId(String revealedG1Id, String revealedG2Id, String revealedR1Id, String revealedR2Id) {
        String revG1 = "/CardsImages/HandCards/front/" + revealedG1Id + ".png";
        String revG2 = "/CardsImages/HandCards/front/" + revealedG2Id + ".png";
        String revR1 = "/CardsImages/HandCards/front/" + revealedR1Id + ".png";
        String revR2 = "/CardsImages/HandCards/front/" + revealedR2Id + ".png";

        try(InputStream revG1Stream = getClass().getResourceAsStream(revG1);
            InputStream revG2Stream = getClass().getResourceAsStream(revG2);
            InputStream revR1Stream = getClass().getResourceAsStream(revR1);
            InputStream revR2Stream = getClass().getResourceAsStream(revR2);){

            this.revealedGold1.setImage(new Image(revG1Stream));
            this.revealedGold2.setImage(new Image(revG2Stream));
            this.revealedResource1.setImage(new Image(revR1Stream));
            this.revealedResource2.setImage(new Image(revR2Stream));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void streamColor(Color color,ImageView imageview){
        String path = "/pedine/" + color + ".png";

        try(InputStream colorStream = getClass().getResourceAsStream(path);){
            imageview.setImage(new Image(colorStream));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateUardPlayedForOthers(String playerNicknameWhoUpdated, ImmGame immGame, String cornerClicked, int layoutXOfCardClicked, int layoutYOfCardClicked) {
        Platform.runLater(()->{
            List<String> nicknames = immGame.playerOrderNicknames();
            nicknames.remove(nickname);
            switch (nicknames.indexOf(playerNicknameWhoUpdated)){
                case 0 -> updateForPlayer1(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                case 1 -> updateForPlayer2(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                case 2 -> updateForPlayer3(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
            }
        });
    }
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

    private void streamCard(String id, ImageView toPlace, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/HandCards/front/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/HandCards/back" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }
}
