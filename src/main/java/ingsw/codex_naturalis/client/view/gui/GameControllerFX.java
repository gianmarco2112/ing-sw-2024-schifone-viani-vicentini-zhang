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
    private List<AnchorPane> anchorPanesOnBoard = new ArrayList<>();
    private Color myColor;

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

        if (comboBoxMessage.getItems().size() == 0) {
            comboBoxMessage.getItems().add("");
            for (String p : otherNicknames) {
                comboBoxMessage.getItems().add(p);
            }
            comboBoxMessage.getSelectionModel().selectFirst();
        }

        initializeBoard();

        for(int i = 0; i<=29; i++) {
            AnchorPane anchorPane = createAnchorPaneOnBoard(boardPointsPosition.get(i).get(0),boardPointsPosition.get(i).get(1));
            anchorPanesOnBoard.add(anchorPane);
        }

        //colors.add(myColor);

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////
        //per stampare le pedine in ordine uguale per tutti
        while(colors.size()!=game.playerOrderNicknames().size()){
            colors.add(colors.getLast());//inserisco un elemento a caso...
        }
        int index = game.playerOrderNicknames().indexOf(mynickname);
        for(int i = index; i<colors.size()-1; i++){
            colors.set(i+1,colors.get(i));
        }
        colors.set(index,myColor);

        posizionaPedine(colors,0,anchorPanesOnBoard.get(0));
        colors.remove(myColor);
        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        this.myColor = myColor;

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
        //String rossa = "/pedine/pedina_rossa.png";
        //String blu = "/pedine/pedina_blu.png";
        //String gialla = "/pedine/pedina_gialla.png";
        //String verde = "/pedine/pedina_verde.png";
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
             //InputStream rossaStream = getClass().getResourceAsStream(rossa);
             //InputStream bluStream = getClass().getResourceAsStream(blu);
             //InputStream giallaStream = getClass().getResourceAsStream(gialla);
             //InputStream verdeStream = getClass().getResourceAsStream(verde);
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

            /*ImageView pedinaRossa = new ImageView(new Image(rossaStream));
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
            globalPane.getChildren().add(pedinaVerde);*/

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
        /*Text testo = new Text();
        testo.setText("The game setup ended!\n" + firstPlayer + "'s turn");
        testo.setLayoutY(20);
        testo.setLayoutX(20);
        gameUpdates.getChildren().add(testo);
        testo.wrappingWidthProperty().bind(gameUpdates.maxWidthProperty());
        testo.wrappingWidthProperty().bind(gameUpdates.maxHeightProperty());*/
        updateBoxText.setText("The game setup ended!\n" + firstPlayer + "'s turn");

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

    private void posizionaPedine(List<Color> colors, int points, AnchorPane anchorPane) {
        ImageView view = null;
        Boolean update;
        switch (points) {
            case 0 -> {
                switch (colors.size()){
                    case 2 -> {
                        streamColor(colors.getFirst(),(ImageView) anchorPane.lookup("#pawn1"));
                        streamColor(colors.getLast(),(ImageView) anchorPane.lookup("#pawn2"));
                        view = (ImageView) anchorPane.lookup("#pawn1");
                        view.setId(colors.getFirst() + "pawn1");
                        view = (ImageView) anchorPane.lookup("#pawn2");
                        view.setId(colors.getLast() + "pawn2");
                    }
                    case 3 -> {
                        streamColor(colors.getFirst(),(ImageView) anchorPane.lookup("#pawn1"));
                        streamColor(colors.get(1),(ImageView) anchorPane.lookup("#pawn2"));
                        streamColor(colors.getLast(),(ImageView) anchorPane.lookup("#pawn3"));
                        view = (ImageView) anchorPane.lookup("#pawn1");
                        view.setId(colors.getFirst() + "pawn1");
                        view = (ImageView) anchorPane.lookup("#pawn2");
                        view.setId(colors.get(1) + "pawn2");
                        view = (ImageView) anchorPane.lookup("#pawn3");
                        view.setId(colors.getLast() + "pawn3");
                    }
                    case 4 -> {
                        streamColor(colors.getFirst(),(ImageView) anchorPane.lookup("#pawn1"));
                        streamColor(colors.get(1),(ImageView) anchorPane.lookup("#pawn2"));
                        streamColor(colors.get(2),(ImageView) anchorPane.lookup("#pawn3"));
                        streamColor(colors.getLast(),(ImageView) anchorPane.lookup("#pawn4"));
                        view = (ImageView) anchorPane.lookup("#pawn1");
                        view.setId(colors.getFirst() + "pawn1");
                        view = (ImageView) anchorPane.lookup("#pawn2");
                        view.setId(colors.get(1) + "pawn2");
                        view = (ImageView) anchorPane.lookup("#pawn3");
                        view.setId(colors.get(2) + "pawn3");
                        view = (ImageView) anchorPane.lookup("#pawn4");
                        view.setId(colors.getLast() + "pawn3");
                    }
                }
            }
            case 1 -> {
                //rimuovo colore dalla sua posizione precedente
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    //calcolo la prima posizione libera all'interno del anchor pane nella board
                    ImageView firstAvailable = computeFirstAvailablePosition(1,colors.getFirst());
                    //posiziono il colore nella posizione appena trovata prima
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 2 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(2,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 3 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(3,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 4 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(4,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 5 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(5,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 6 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(6,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 7 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(7,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 8 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(8,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 9 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(9,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 10 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(10,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 11 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(11,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 12 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(12,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 13 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(13,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 14 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(14,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 15 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(15,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 16 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(16,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 17 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(17,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 18 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(18,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 19 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(19,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 20 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(20,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 21 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(21,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 22 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(22,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 23 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(23,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 24 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(24,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 25 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(25,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 26 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(26,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 27 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(27,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 28 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(28,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
            case 29 -> {
                update = removeFromBoard(colors.getFirst(),points);
                if(update) {
                    ImageView firstAvailable = computeFirstAvailablePosition(29,colors.getFirst());
                    streamColor(colors.getFirst(),firstAvailable);
                }
            }
        }
    }

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
        });
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
                        streamTopRGImage(topResourceCardId, topGoldCardId, false);
                        streamTopRGImage(topResourceCardId, topGoldCardId, true);
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
                        y = (-((int) layoutYOfCardClicked - 9999)/54) - 1;
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

                //aggiorno punteggio
                posizionaPedine(List.of(myColor),points,anchorPanesOnBoard.get(points));
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
            imageview.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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
                case 0 -> {
                    updateForPlayer1(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                }
                case 1 -> {
                    updateForPlayer2(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                }
                case 2 -> {
                    updateForPlayer3(playerNicknameWhoUpdated, immGame, cornerClicked, layoutXOfCardClicked, layoutYOfCardClicked);
                }
            }
            posizionaPedine(List.of(immGame.otherPlayers().get(indexOfplayerWhoupdated).color()),
                    immGame.otherPlayers().get(indexOfplayerWhoupdated).playerArea().points(),
                    anchorPanesOnBoard.get(immGame.otherPlayers().get(indexOfplayerWhoupdated).playerArea().points()));
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
            String path = "/CardsImages/HandCards/back/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    @FXML
    private Text updateBoxText;

    public void turnChanged(String currentPlayer) {
        Platform.runLater(()->{
            if(currentPlayer.equals(nickname)){
                updateBoxText.setText("It's your turn!");
            }else{
                updateBoxText.setText("It's " + currentPlayer + "'s turn!");
            }
        });

    }

    public void twentyPointsReached(ImmGame immGame) {
        Platform.runLater(()->{
            updateBoxText.setText("20 Points reached!");
        });
    }

    public void deckesEmpty(ImmGame immGame) {
        Platform.runLater(()->{
            updateBoxText.setText("All decks are empty!");
        });
    }

    public void gameEnded(String winner, List<String> players, List<Integer> points, List<ImmObjectiveCard> secretObjectiveCards) {
        Platform.runLater(()->{
            updateBoxText.setText("Game ended!\nThe winner is " + winner + "!\nPress LOBBY");
            //faccio comparire un bottone LOBBY, quando viene schiacciato si torna alla LOBBY
        });
    }

    public void gameCanceled() {
        Platform.runLater(()->{
            updateBoxText.setText("You won!\nPress LOBBY");
            //faccio comparire un bottone LOBBY, quando viene schiacciato si torna alla LOBBY
        });
    }

    public void updatePlayerInGameStatus(String message, ImmGame immGame, String playerNickname) {
        Platform.runLater(()->{
            updateBoxText.setText(playerNickname + " " + message);
        });
    }

    public void gamePaused() {
        Platform.runLater(()->{
            updateBoxText.setText("Currently, you're the only player connected.\nPlease wait for another player to rejoin within 10 seconds.");
        });
    }

    public void gameResumed() {
        Platform.runLater(()->{
            updateBoxText.setText("Game resumed!");
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

            if (comboBoxMessage.getValue().toString().isEmpty()) {
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
        nickname = game.player().nickname();
        myColor = game.player().color();

        user1PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
        user1PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
        playerArea1.setHvalue(1.0);
        playerArea1.setVvalue(1.0);
        user1Initialcard.setLayoutX(9999);
        user1Initialcard.setLayoutY(9999);

        user2PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
        user2PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
        playerArea2.setHvalue(1.0);
        playerArea2.setVvalue(1.0);
        user2Initialcard.setLayoutX(9999);
        user2Initialcard.setLayoutY(9999);

        user3PlayerArea.setPrefWidth(Region.USE_COMPUTED_SIZE);
        user3PlayerArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
        playerArea3.setHvalue(1.0);
        playerArea3.setVvalue(1.0);
        user3Initialcard.setLayoutX(9999);
        user3Initialcard.setLayoutY(9999);

        myPlayerAreaAnchorPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        myPlayerAreaAnchorPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        myPlayerArea.setHvalue(1.0);
        myPlayerArea.setVvalue(1.0);
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
            ImageView view = null;
            switch (colorsIn0.size()){
                case 1 -> {
                    streamColor(colorsIn0.getFirst(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1"));
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1");
                    view.setId(colorsIn0.getFirst() + "pawn1");
                }
                case 2 -> {
                    streamColor(colorsIn0.getFirst(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1"));
                    streamColor(colorsIn0.getLast(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2"));
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1");
                    view.setId(colorsIn0.getFirst() + "pawn1");
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2");
                    view.setId(colorsIn0.getLast() + "pawn2");
                }
                case 3 -> {
                    streamColor(colorsIn0.getFirst(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1"));
                    streamColor(colorsIn0.get(1),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2"));
                    streamColor(colorsIn0.getLast(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn3"));
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1");
                    view.setId(colorsIn0.getFirst() + "pawn1");
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2");
                    view.setId(colorsIn0.get(1) + "pawn2");
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn3");
                    view.setId(colorsIn0.getLast() + "pawn3");
                }
                case 4 -> {
                    streamColor(colorsIn0.getFirst(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1"));
                    streamColor(colorsIn0.get(1),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2"));
                    streamColor(colorsIn0.get(2),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn3"));
                    streamColor(colorsIn0.getLast(),(ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn4"));
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn1");
                    view.setId(colorsIn0.getFirst() + "pawn1");
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn2");
                    view.setId(colorsIn0.get(1) + "pawn2");
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn3");
                    view.setId(colorsIn0.get(2) + "pawn3");
                    view = (ImageView) anchorPanesOnBoard.getFirst().lookup("#pawn4");
                    view.setId(colorsIn0.getLast() + "pawn3");
                }
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

        streamTopRGImage(game.revealedResourceCards().getFirst().cardID(),game.revealedResourceCards().getLast().cardID(),false);
        streamTopRGImage(game.topResourceCard().cardID(),game.topGoldCard().cardID(),true);
        streamTopRGImage(game.topResourceCard().cardID(),game.topGoldCard().cardID(),false);

        streamRevealedId(game.revealedGoldCards().get(0).cardID(),game.revealedGoldCards().get(1).cardID(),game.revealedResourceCards().get(0).cardID(),game.revealedResourceCards().get(1).cardID());

        ////////////////////////////////////////////////////////
        ///////obiettivi comuni e segreti///////////////////////
        ////////////////////////////////////////////////////////
        String secretObjective = "/CardsImages/Objective/" + game.player().playerArea().objectiveCard().cardID() + ".png";
        String commonObj1 = "/CardsImages/Objective/" + game.commonObjectiveCards().getFirst().cardID() + ".png";
        String commonObj2 = "/CardsImages/Objective/" + game.commonObjectiveCards().getLast().cardID() + ".png";

        try(InputStream secretObj = getClass().getResourceAsStream(secretObjective);
            InputStream commonObj1Stream = getClass().getResourceAsStream(commonObj1);
            InputStream commonObj2Stream = getClass().getResourceAsStream(commonObj2);
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
            streamColor(colors.getFirst(),u1Pedina);
            u1Pedina.setFitHeight(33);
            u1Pedina.setFitWidth(33);
            u1Pedina.setLayoutX(9999+37);
            u1Pedina.setLayoutY(9999-20);
            user1PlayerArea.getChildren().add(u1Pedina);
        }else{
            switch (game.playerOrderNicknames().size()) {
                case 3 -> {

                    user3PlayerArea.setVisible(false);
                    playerArea3.setVisible(false);
                    username3.setVisible(false);

                    ImageView u2Pedina = new ImageView();
                    streamColor(colors.getLast(),u2Pedina);
                    u2Pedina.setFitHeight(33);
                    u2Pedina.setFitWidth(33);
                    u2Pedina.setLayoutX(9999+37);
                    u2Pedina.setLayoutY(9999-20);
                    user2PlayerArea.getChildren().add(u2Pedina);

                    username2.setText(playerOrder.getLast());

                }
                case 4 -> {

                    ImageView u2Pedina = new ImageView();
                    streamColor(colors.get(1),u2Pedina);
                    u2Pedina.setFitHeight(33);
                    u2Pedina.setFitWidth(33);
                    u2Pedina.setLayoutX(9999+37);
                    u2Pedina.setLayoutY(9999-20);
                    user2PlayerArea.getChildren().add(u2Pedina);

                    username2.setText(playerOrder.getLast());

                    ImageView u3Pedina = new ImageView();
                    streamColor(colors.getLast(),u3Pedina);
                    u3Pedina.setFitHeight(33);
                    u3Pedina.setFitWidth(33);
                    u3Pedina.setLayoutX(9999+37);
                    u3Pedina.setLayoutY(9999-20);
                    user3PlayerArea.getChildren().add(u3Pedina);

                    username3.setText(playerOrder.getLast());
                }
            }
        }

        String pedinanera = "/pedine/pedina_nera.png";
        try(InputStream neroStream = getClass().getResourceAsStream(pedinanera);){
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
                switch (index){
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
        /////////////////////////////////////////////////

        //Platform.runLater(()->{

        //});
    }

    private void streamCardRejoin(String id, ImageView toPlace, boolean showingFront) {
        if(showingFront){
            String path = "/CardsImages/HandCards/front/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/HandCards/back/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void streamOtherPlayerarea(AnchorPane userPlayerArea, ImmGame game, int index) {
        Map.Entry<List<Integer>, ImmPlayableCard> placed = null;
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : game.otherPlayers().get(index).playerArea().area().entrySet()) {
            placed = cardAndCoordinates;
            int x = placed.getKey().getFirst();
            int y = placed.getKey().getLast();
            String idlast = placed.getValue().cardID();

            int layoutX = x*83 + 9999;
            int layoutY = 9999 - 43*y;

            if(cardAndCoordinates.getValue().playableCardType()==PlayableCardType.INITIAL){
                if(userPlayerArea==user1PlayerArea){
                    ImageView toPlace = new ImageView();
                    streamInitialCardRejoin(idlast,user1Initialcard,placed.getValue().showingFront());
                    user1Initialcard.setLayoutX(layoutX);
                    user1Initialcard.setLayoutY(layoutY);
                    //userPlayerArea.getChildren().add(toPlace);
                }
                if(userPlayerArea==user2PlayerArea){
                    ImageView toPlace = new ImageView();
                    streamInitialCardRejoin(idlast,user2Initialcard,placed.getValue().showingFront());
                    user2Initialcard.setLayoutX(layoutX);
                    user2Initialcard.setLayoutY(layoutY);
                    //userPlayerArea.getChildren().add(toPlace);
                }
                if(userPlayerArea==user3PlayerArea){
                    ImageView toPlace = new ImageView();
                    streamInitialCardRejoin(idlast,user3Initialcard,placed.getValue().showingFront());
                    user3Initialcard.setLayoutX(layoutX);
                    user3Initialcard.setLayoutY(layoutY);
                    //userPlayerArea.getChildren().add(toPlace);
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

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
                toPlace.setFitHeight(71);
                toPlace.setFitWidth(107);
                toPlace.setImage(new Image(cardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/Initial/backs/" + id + ".png";

            try(InputStream cardStream = getClass().getResourceAsStream(path);){
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
            String path = "/CardsImages/Handcards/front/" + id + ".png";

            try(InputStream handCardStream = getClass().getResourceAsStream(path);){
                handCard.setImage(new Image(handCardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String path = "/CardsImages/Handcards/back/" + id + ".png";

            try(InputStream handCardStream = getClass().getResourceAsStream(path);){
                handCard.setImage(new Image(handCardStream));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
