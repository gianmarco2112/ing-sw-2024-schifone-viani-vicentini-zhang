package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.NotPlayableException;
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
     * Method to play the initial card
     */
    public void playInitialCard(){
        initialCard.play(playerArea);
        playerArea.setCardOnCoordinates(initialCard, 0, 0);
        initialCard = null;
    }


    /**
     * Method to write a message
     * @param content Content of the message
     * @param receivers Receivers
     */
    public void writeMessage(String content, List<Player> receivers){
        sentMessages.add(new Message(content, receivers));
    }


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

    public List<PlayableCard> getHand(){
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

   /* public void chooseObjectiveCard(ObjectiveCard objectiveCard){
        playerArea.setObjectiveCard(objectiveCard);
        objectiveCards.remove(objectiveCard);
        centerOfTable.discardObjectiveCard(objectiveCards.getFirst());
        objectiveCards.clear();
    }*/
    public int getExtraPoints(){
        return playerArea.getExtraPoints(); //potrebbe tornare utile anche in futuro e potrebbe sostituire i metodi deprecati
    }
}