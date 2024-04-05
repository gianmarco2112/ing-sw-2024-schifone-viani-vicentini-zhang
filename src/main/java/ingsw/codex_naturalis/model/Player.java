package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.exceptions.CardNotInHandException;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
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
    private PlayableCard initialCard;

    /**
     * Center of the table
     */
    private CenterOfTable centerOfTable;

    /**
     * This list contains the card/cards the player currently has in his hands.
     * It will contain two resource cards and a gold card at the start.
     * During the game, it will contain from two to three resource/golden cards.
     */
    private final List<PlayableCard> hand;

    /**
     * This list contains the two objective cards given at the start, then
     * it will be empty
     */
    private final List<ObjectiveCard> objectiveCards = new ArrayList<>();

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
     * Center of table getter
     * @return Center of table
     */
    public CenterOfTable getCenterOfTable() {
        return centerOfTable;
    }

    /**
     * Center of table setter (called at the start)
     * @param centerOfTable Center of table
     */
    public void setCenterOfTable(CenterOfTable centerOfTable) {
        this.centerOfTable = centerOfTable;
    }

    /**
     * Initial card getter
     * @return Initial card
     */
    public PlayableCard getInitialCard() {
        return initialCard;
    }

    /**
     * Initial card setter (called at the start)
     * @param initialCard Initial card
     */
    public void setInitialCard(PlayableCard initialCard) {
        this.initialCard = initialCard;
    }

    /**
     * This method is called when the player wants to see the other side of the card.
     * @param card Card to flip
     */
    public void flip(PlayableCard card) throws CardNotInHandException{

        if (!(card != null && (card == initialCard || hand.contains(card)))){
            throw new CardNotInHandException();
        }
        card.flip();
    }

    /**
     * Method to play the initial card
     */
    public void playInitialCard(){
        initialCard.play(playerArea);
        playerArea.setCardOnCoordinates(initialCard, 0, 0);
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
    public void playCard(PlayableCard card, int x, int y) throws NotPlayableException{
        if (card.isPlayable(playerArea ,x,y)) {
            card.play(playerArea, x, y);
            playerArea.setCardOnCoordinates(card, x, y);
            hand.remove(card);
        }else{
            throw new NotPlayableException();
        }
    }

    /**
     * Draws from the resource cards deck
     */
    public void drawFromResourceCardsDeck() {
        PlayableCard card = centerOfTable.removeFromResourceCardsDeck();
        card.flip();
        addCardToHand(card);
    }

    /**
     * Draws from the gold cards deck
     */
    public void drawFromGoldCardsDeck() {
        PlayableCard card = centerOfTable.removeFromGoldCardsDeck();
        card.flip();
        addCardToHand(card);
    }

    /**
     * Draws the first revealed resource card on the table
     */
    public void drawFirstFromRevealedResourceCards() {
        PlayableCard card = centerOfTable.removeFirstFromRevealedResourceCards();
        addCardToHand(card);
    }

    /**
     * Draws the second revealed resource card on the table
     */
    public void drawLastFromRevealedResourceCards() {
        PlayableCard card = centerOfTable.removeLastFromRevealedResourceCards();
        addCardToHand(card);
    }

    /**
     * Draws the first revealed gold card on the table
     */
    public void drawFirstFromRevealedGoldCards() {
        PlayableCard card = centerOfTable.removeFirstFromRevealedGoldCards();
        addCardToHand(card);
    }

    /**
     * Draws the second revealed gold card on the table
     */
    public void drawLastFromRevealedGoldCards() {
        PlayableCard card = centerOfTable.removeLastFromRevealedGoldCards();
        addCardToHand(card);
    }

    /**
     * Adds the drawn card to the hand
     * @param card Card to add
     */
    private void addCardToHand(PlayableCard card){
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

    /**
     * Test method
     * @return playerArea
     */
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

    public List<PlayableCard> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Only for test
     * @return hand
     */
    @Deprecated
    public List<PlayableCard> testGethand(){
        return hand;
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

    public void setObjectiveCards(ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2){
        objectiveCards.add(objectiveCard1);
        objectiveCards.add(objectiveCard2);
    }

    public void chooseObjectiveCard(ObjectiveCard objectiveCard){
        playerArea.setObjectiveCard(objectiveCard);
        objectiveCards.remove(objectiveCard);
        centerOfTable.discardObjectiveCard(objectiveCards.getFirst());
        objectiveCards.clear();
    }
    public int getExtraPoints(){
        return playerArea.getExtraPoints(); //potrebbe tornare utile anche in futuro e potrebbe sostituire i metodi deprecati
    }
}