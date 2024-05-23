package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;

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
    private ImageView user2Initialcard;

    @FXML
    private ImageView user3Initialcard;

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
    private int layoutXOfCardClicked;
    private int layoutYOfCardClicked;

    public void setViewGUI(GraphicalUI viewGUI) {
        this.viewGUI = viewGUI;
    }

    public void endSetup(String initialCard, Boolean showingFront, String myObjectiveCard,
                         String handCard1, String handCard2, String handCard3,
                         String commonObjective1, String commonObjective2,
                         String topGoldCard, String topResourceCard, Symbol kingdomG, Symbol kingdomR,
                         String revealedResourceCard1, String revealedResourceCard2,
                         String revealedGoldCard1, String revealedGoldCard2,
                         Color myColor, String firstPlayer) {

        myPlayerAreaAnchorPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        myPlayerAreaAnchorPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

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
        ) {

            myInitialCard.setImage(new Image(initialCardStream));

            ImageView pedinaRossa = new ImageView(new Image(rossaStream));
            pedinaRossa.setFitHeight(33);
            pedinaRossa.setFitWidth(33);
            pedinaRossa.setLayoutX(32);
            pedinaRossa.setLayoutY(323);
            globalPane.getChildren().add(pedinaRossa);

            ImageView pedinaBlu = new ImageView(new Image(bluStream));
            pedinaBlu.setFitHeight(33);
            pedinaBlu.setFitWidth(33);
            pedinaBlu.setLayoutX(32);
            pedinaBlu.setLayoutY(323-3);
            globalPane.getChildren().add(pedinaBlu);

            ImageView pedinaGialla = new ImageView(new Image(giallaStream));
            pedinaGialla.setFitHeight(33);
            pedinaGialla.setFitWidth(33);
            pedinaGialla.setLayoutX(32);
            pedinaGialla.setLayoutY(323-3-3);
            globalPane.getChildren().add(pedinaGialla);

            ImageView pedinaVerde = new ImageView(new Image(verdeStream));
            pedinaVerde.setFitHeight(33);
            pedinaVerde.setFitWidth(33);
            pedinaVerde.setLayoutX(32);
            pedinaVerde.setLayoutY(323-3-3-3);
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
            myPedina.setId("myPedina");
            myPlayerAreaAnchorPane.getChildren().add(myPedina);

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
        cornerClicked = corner;
        layoutXOfCardClicked = (int) layoutX;
        layoutYOfCardClicked = (int) layoutY;

        //evento play card da notificare
        System.out.println(corner + "Clicked!!");

        if(!ok){
            switch (corner) {
                case "TopLeft" -> {
                    int x = ((9999 - (int) layoutX)/104)-1;
                    int y = ((9999 - (int) layoutY)/54)+1;
                    viewGUI.playingCard(selectedCardIndex, x, y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("carta giocata in (" + x + "," + y + ")");
                }
                case "TopRight" -> {
                    int x = (((int) layoutX - 9999)/104) + 1;
                    int y = ((9999 - (int) layoutY)/54) + 1;
                    viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("carta giocata in (" + x + "," + y + ")");
                }
                case "BottomLeft" -> {
                    int x = ((9999 - (int) layoutX)/104) - 1;
                    int y = (((int) layoutY - 9999)/54) - 1;
                    viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("carta giocata in (" + x + "," + y + ")");
                }
                case "BottomRight" -> {
                    int x = (((int) layoutX - 9999)/104) + 1;
                    int y = (((int) layoutY - 9999)/54) - 1;
                    viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY);
                    System.out.println("carta giocata in (" + x + "," + y + ")");
                }
            }
        }

        Platform.runLater(()->{
            if(selectedCard != null && ok) {
                System.out.println(selectedCard);
                Pane pane;
                int x, y;
                //if corner è topLeft allora LayoutX-104 e LayoutY-54
                //if corner è topRight allora LayoutX+104 e LayoutY-54
                //if corner è bottomLeft allora LayoutX-104 e LayoutY+54
                //if corner è bottomRight allora LayoutX+104 e LayoutY+54
                switch (corner) {
                    case "TopLeft" -> {
                        selectedCard.setLayoutX((int) layoutX - 104);
                        selectedCard.setLayoutY((int) layoutY - 54);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutX - 104, (int) layoutY - 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        //normalizzare le coordinate (9999,9999) è il mio (0,0) //faccio [(9999 - (int) layoutX)/104]-1 e [(9999 - (int) layoutY)/54]+1
                        x = ((9999 - (int) layoutX)/104)-1;
                        y = ((9999 - (int) layoutY)/54)+1;
                        //chiamo uiObservableItem.notify...
                        //viewGUI.playingCard(selectedCardIndex, x, y, corner,(int) layoutX,(int) layoutY, selectedCard);//indice carta giocata e posizione (-1,+1) devo avere un modo per normalizzare prima di inserire le coordinate
                        //TODO ottonere l'indice della carta giocata, normalizzazione delle coordinate
                        System.out.println("carta giocata in (" + x + "," + y + ")");
                    }
                    case "TopRight" -> {
                        selectedCard.setLayoutX((int) layoutX + 104);
                        selectedCard.setLayoutY((int) layoutY - 54);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutX + 104, (int) layoutY - 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        x = (((int) layoutX - 9999)/104) + 1;
                        y = ((9999 - (int) layoutY)/54) + 1;
                        //viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY, selectedCard);
                        System.out.println("carta giocata in (" + x + "," + y + ")");
                    }
                    case "BottomLeft" -> {
                        selectedCard.setLayoutX((int) layoutX - 104);
                        selectedCard.setLayoutY((int) layoutY + 54);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutX - 104, (int) layoutY + 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        x = ((9999 - (int) layoutX)/104) - 1;
                        y = (((int) layoutY - 9999)/54) - 1;
                        //viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY, selectedCard);
                        System.out.println("carta giocata in (" + x + "," + y + ")");
                    }
                    case "BottomRight" -> {
                        selectedCard.setLayoutX((int) layoutX + 104);
                        selectedCard.setLayoutY((int) layoutY + 54);
                        myPlayerAreaAnchorPane.getChildren().add(selectedCard);
                        selectedCard = null;

                        pane = createPaneCard((int) layoutX + 104, (int) layoutY + 54);
                        myPlayerAreaAnchorPane.getChildren().add(pane);

                        x = (((int) layoutX - 9999)/104) + 1;
                        y = (((int) layoutY - 9999)/54) - 1;
                        //viewGUI.playingCard(selectedCardIndex,x,y, corner,(int) layoutX,(int) layoutY, selectedCard);
                        System.out.println("carta giocata in (" + x + "," + y + ")");
                    }
                }

                if(selectedCardIndex==0 || selectedCardIndex==1 || selectedCardIndex==2) {
                    switch (selectedCardIndex) {
                        case 0 -> handCard1.setVisible(false);
                        case 1 -> handCard2.setVisible(false);
                        case 2 -> handCard3.setVisible(false);
                    }
                }

                if(points>0){
                    //aggiorno punteggio sul tabellone
                    switch (points) {
                        case 1 -> {
                            //cancello la mia pedina da 0
                            Node nodo = myPlayerAreaAnchorPane.lookup("#myPedinaId");
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
                }
            }
        });


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
    private AnchorPane point0;

    @FXML
    private AnchorPane point011;

    @FXML
    private ImageView point0_1;

    @FXML
    private ImageView point0_2;

    @FXML
    private ImageView point0_3;

    @FXML
    private ImageView point0_4;

    @FXML
    private AnchorPane point1;

    @FXML
    private ImageView point1_1;

    @FXML
    private ImageView point1_2;

    @FXML
    private ImageView point1_3;

    @FXML
    private ImageView point1_4;

    @FXML
    private ImageView point2_1;

    @FXML
    private ImageView point2_2;

    @FXML
    private ImageView point2_3;

    @FXML
    private ImageView point2_4;
}
