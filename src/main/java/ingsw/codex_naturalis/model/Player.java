package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.TurnStatus;

import java.util.*;

/**
 * Player class
 */
public class Player {

    private final int playerID;
    /**
     * Nickname of the player
     */
    private final String nickname;

    /**
     * Color of the player
     */
    private Color color;

    private TurnStatus turnStatus;

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
     * Player area
     */
    private final PlayerArea playerArea;

    /**
     * Sent messages
     */
    private final List<Message> sentMessages;


    /**
     * Constructor
     * @param nickname Nickname
     */
    public Player(int playerID, String nickname) {
        this.playerArea = new PlayerArea();
        this.playerID = playerID;
        this.nickname = nickname;
        this.initialCard = null;
        this.sentMessages = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.turnStatus = TurnStatus.PLAY;
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

    public TurnStatus getTurnStatus() {
        return turnStatus;
    }

    public void setTurnStatus(TurnStatus turnStatus) {
        this.turnStatus = turnStatus;
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
    public void setColor(Color color) {
        this.color = color;
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


   /* public void chooseObjectiveCard(ObjectiveCard objectiveCard){
        playerArea.setObjectiveCard(objectiveCard);
        objectiveCards.remove(objectiveCard);
        centerOfTable.discardObjectiveCard(objectiveCards.getFirst());
        objectiveCards.clear();
    }*/
}