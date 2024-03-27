package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandPlayableCard;
import ingsw.codex_naturalis.model.cards.HandPlayableSide;
import ingsw.codex_naturalis.model.cards.PlayableCard;
import ingsw.codex_naturalis.model.cards.PlayableSide;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.enumerations.Color;

import java.util.*;

/**
 * Player class
 */
public class Player {

    /**
     * Nickname of the player
     */
    private final String nickname;
    private final int playerID; //l'idea è che ID debba essere univoco tra tutte le partite e il nickname unico in ogni partita

    /**
     * Color of the player
     */
    private final Color color;

    /**
     * Initial card (null after playing it)
     */
    private InitialCard initialCard;

    /**
     * Center of the table
     */
    private CenterOfTable centerOfTable;

    /**
     * This list contains the card/cards the player currently has in his hands.
     * It will contain two resource cards and a gold card at the start.
     * During the game, it will contain from two to three resource/golden cards.
     */
    private final List<HandPlayableCard> hand;

    /**
     * Player area
     */
    private final PlayerArea playerArea;

    /**
     * Sent messages
     */
    private final List<Message> sentMessages;

    /**
     * Players the current player can write to
     */
    private final List<Player> players;


    /**
     * Constructor
     * @param nickname Nickname
     * @param color Color
     */
    public Player(String nickname, Color color,int playerID) {
        //se il player inserisce come nickname uno spazio?
        this.nickname = nickname;
        this.color = color;
        this.initialCard = null;
        this.sentMessages = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.playerArea = new PlayerArea();
        this.playerID=playerID;
        this.players=new ArrayList<>();
    }


    /**
     * Center of table setter (called at the start)
     * @param centerOfTable Center of table
     */
    public void setCenterOfTable(CenterOfTable centerOfTable) {
        this.centerOfTable = centerOfTable;
    }

    /**
     * Initial card setter (called at the start)
     * @param initialCard Initial card
     */
    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }

    /**
     * This method is called when the player wants to see the other side of the card.
     * @param card Card to flip
     */
    public void flip(PlayableCard card) {
        if (card.isShowingFront()) {
            card.showBack();
        } else {
            card.showFront();
        }
    }

    /**
     * Method to play the initial card
     */
    public void playInitialCard(){
        PlayableSide sideCard;
        if (initialCard.isShowingFront()) {
            sideCard = initialCard.getPlayableFront();
        } else {
            sideCard = initialCard.getPlayableBack();
        }
        playerArea.setCardOnCoordinates(sideCard, 0, 0);
        sideCard.played(playerArea);
        sideCard.getSymbols();
        initialCard = null;
    }

    /**
     * This method is called when the player wants to play a card from his hand.
     * Operates on the front or back of the card, based on what side the card is currently showing.
     * Checks that the card is actually playable in that spot.
     * Covers the corners and if needed decreases the number of symbols of the player.
     * Increases the number of symbols and the points, if necessary, of the player
     * @param card card that player wants to play
     * @param x coordinate x
     * @param y coordinate y
     */
    public void playCard(HandPlayableCard card, int x, int y) {
        HandPlayableSide sideCard;
        if (card.isShowingFront()) {
            sideCard = card.getHandPlayableFront();
        } else {
            sideCard = card.getHandPlayableBack();
        }
        if (sideCard.isPlayable(x,y)) {
            playerArea.setCardOnCoordinates(sideCard, x, y);
            sideCard.played(playerArea);
            sideCard.coverCorners(x,y);
            sideCard.getSymbols();
            sideCard.calcPoints();
            hand.remove(card);
        }
    }

    /**
     * Draws from the resource cards deck
     */
    public void drawFromResourceCardsDeck() {
        HandPlayableCard card = centerOfTable.removeFromResourceCardsDeck();
        addCardToHand(card);
        card.drawn(playerArea);
    }

    /**
     * Draws from the gold cards deck
     */
    public void drawFromGoldCardsDeck() {
        HandPlayableCard card = centerOfTable.removeFromGoldCardsDeck();
        addCardToHand(card);
        card.drawn(playerArea);
    }

    /**
     * Draws the first revealed resource card on the table
     */
    public void drawFirstFromRevealedResourceCards() {
        HandPlayableCard card = centerOfTable.removeFirstFromRevealedResourceCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }

    /**
     * Draws the second revealed resource card on the table
     */
    public void drawLastFromRevealedResourceCards() {
        HandPlayableCard card = centerOfTable.removeLastFromRevealedResourceCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }

    /**
     * Draws the first revealed gold card on the table
     */
    public void drawFirstFromRevealedGoldCards() {
        HandPlayableCard card = centerOfTable.removeFirstFromRevealedGoldCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }

    /**
     * Draws the second revealed gold card on the table
     */
    public void drawLastFromRevealedGoldCards() {
        HandPlayableCard card = centerOfTable.removeLastFromRevealedGoldCards();
        addCardToHand(card);
        card.drawn(playerArea);
    }

    /**
     * Adds the drawn card to the hand
     * @param card Card to add
     */
    private void addCardToHand(HandPlayableCard card){
        hand.add(card);
    }

    /**
     * Method to write a message
     * @param content Content of the message
     * @param receivers Receivers
     */
    public void writeMessage(String content, List<Player> receivers){
        sentMessages.add(new Message(content, receivers));
    }

    //metodo aggiunto momentaneamente per fare il test degli obiettivi
    public PlayerArea getPlayerArea(){
        return playerArea;
    }

    public Color getColor() {
        return color;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getNickname() {
        return nickname;
    }

    public List<HandPlayableCard> getHand() {
        return new ArrayList<>(hand);
    }

    public List<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public void setPossibleMessageReceivers(Player player){
        players.add(player);
    }
    public List<Player> getPossibleMessageReceiver() {
        return new ArrayList<>(players);
    }
}