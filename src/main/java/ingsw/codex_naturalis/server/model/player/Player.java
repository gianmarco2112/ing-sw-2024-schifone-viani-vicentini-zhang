package ingsw.codex_naturalis.server.model.player;

import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.TurnStatus;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;
import ingsw.codex_naturalis.server.model.util.PlayerObservable;

import java.util.*;

/**
 * Class that represents a Player
 */
public class Player extends PlayerObservable {

    /**
     * Part of the model's view: immutable overview with any attribute of the Player
     * (intended for the Controller in order to manage the game)
     */

    public record Immutable(String nickname, Color color, TurnStatus turnStatus,
                            PlayableCard.Immutable initialCard, List<ObjectiveCard.Immutable> secretObjectiveCards, List<PlayableCard.Immutable> hand,
                            PlayerArea.Immutable playerArea) {}
    /**
     * Part of the model's view: immutable overview with any attribute of the Player
     * (intended for the View -> the Player's secret objective card is hidden)
     */

    public record ImmutableHidden(String nickname, Color color, TurnStatus turnStatus,
                                  PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> hand,
                            PlayerArea.ImmutableHidden playerArea) {}
    /**
     * Getter of the immutable Player
     */
    public Player.Immutable getImmutablePlayer(){

        PlayableCard.Immutable immInitialCard = null;
        if (initialCard != null)
            immInitialCard = initialCard.getImmutablePlayableCard();

        List<ObjectiveCard.Immutable> immSecretObjCards = new ArrayList<>();
        for (ObjectiveCard card : secretObjectiveCards)
            if (card != null)
                immSecretObjCards.add(card.getImmutableObjectiveCard());

        List<PlayableCard.Immutable> immutableHand = new ArrayList<>();
        for (PlayableCard playableCard : hand)
            if (playableCard != null)
                immutableHand.add(playableCard.getImmutablePlayableCard());

        return new Player.Immutable(nickname, color, turnStatus, immInitialCard, immSecretObjCards,
                immutableHand, playerArea.getImmutablePlayerArea());
    }
    /**
     * Getter of the immutable Player (without the secret objective cards)
     */
    public Player.ImmutableHidden getImmutableHiddenPlayer() {

        PlayableCard.Immutable immInitialCard = null;
        if (initialCard != null)
            immInitialCard = initialCard.getImmutableHiddenPlayableCard();

        List<PlayableCard.Immutable> immutableHiddenHand = new ArrayList<>();
        for (PlayableCard playableCard : hand)
            immutableHiddenHand.add(playableCard.getImmutableHiddenPlayableCard());

        return new Player.ImmutableHidden(nickname, color, turnStatus, immInitialCard,
                    immutableHiddenHand, playerArea.getImmutableHiddenPlayerArea());
    }

    /**
     * Nickname of the player
     */
    private final String nickname;

    /**
     * Color of the player
     */
    private Color color;

    /**
     * Turn status of the player: play or draw
     */
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
    private List<PlayableCard> hand;
    /**
     * This list contains the player's secret Objective cards.
     * At the beginning of the game it will contain 2 Objective cards,
     * after that the Player chooses one of them and this list will contain
     * only one Objective card (unknown to the other players) until the end of the game
     */
    private List<ObjectiveCard> secretObjectiveCards;
    /**
     * Player area
     */
    private final PlayerArea playerArea;
    /**
     * Player's constructor
     * @param nickname: Nickname of the Player
     */
    public Player(String nickname) {
        this.playerArea = new PlayerArea();
        this.nickname = nickname;
        this.initialCard = null;
        this.hand = new ArrayList<>();
        this.turnStatus = TurnStatus.PLAY;
        this.color = null;
        secretObjectiveCards = new ArrayList<>();
    }

    /**
     * This method allows the player to flip a card and shows
     * the other side of the card to everyone in the game
     * @param cardToFlip : the playable card that the player wants to flip
     */

    public void flip(PlayableCard cardToFlip) {
        cardToFlip.flip();
        if (initialCard != null)
            notifyObservers(this, PlayerEvent.INITIAL_CARD_FLIPPED);
        else
            notifyObservers(this, PlayerEvent.HAND_CARD_FLIPPED);
    }

    /**
     * Initial card's getter
     * @return Initial card
     */
    public PlayableCard getInitialCard() {
        return initialCard;
    }
    /**
     * Initial card's setter (invoked at the beginning of the game)
     * @param initialCard Initial card
     */
    public void setInitialCard(PlayableCard initialCard) {
        this.initialCard = initialCard;
    }
    /**
     * Method to play the initial card into the PlayerArea of the player that invokes the method
     */
    public void playInitialCard(){
        playerArea.setInitialCard(initialCard);
        initialCard = null;
        notifyObservers(this, PlayerEvent.INITIAL_CARD_PLAYED);
    }
    /**
     * Method to play a resource or gold card into the PlayerArea of the
     * player that invokes the method, on the specified coordinates
     */
    public void playCard(PlayableCard cardToPlay, int x, int y) {
        playerArea.setCardOnCoordinates(cardToPlay, x, y);
        hand.remove(cardToPlay);
        turnStatus = TurnStatus.DRAW;

        notifyObservers(this, PlayerEvent.HAND_CARD_PLAYED);
    }
    /**
     * Method to draw a card from the centre of the table
     * (from the deck or from the revealed cards at the centre)
     */
    public void drawCard(PlayableCard playableCard) {
        hand.add(playableCard);
        turnStatus = TurnStatus.PLAY;
        notifyObservers(this, PlayerEvent.CARD_DRAWN);
    }
    /**
     * Getter of the player's secret objective cards
     * @return secretObjectiveCards
     */
    public List<ObjectiveCard> getSecretObjectiveCards() {
        return secretObjectiveCards;
    }
    /**
     * Method to choose one Objective card from the list secretObjectiveCards
     * (which contains the 2 cards to choose from)
     */
    public void chooseObjectiveCard(ObjectiveCard objectiveCard) {
        playerArea.setObjectiveCard(objectiveCard);
        notifyObservers(this, PlayerEvent.OBJECTIVE_CARD_CHOSEN);
    }
    /**
     * Getter of the turn status (play or draw) of the Player
     * @return turnStatus
     */
    public TurnStatus getTurnStatus() {
        return turnStatus;
    }
    /**
     * Setter of the turn status (play or draw) of the Player
     * @param turnStatus
     */
    public void setTurnStatus(TurnStatus turnStatus) {
        this.turnStatus = turnStatus;
        //notifyObservers(GameEvent.TURN_STATUS_CHANGED, nickname);
    }
    /**
     * Getter of the PlayerArea of the Player
     * @return playerArea
     */
    public PlayerArea getPlayerArea(){
        return playerArea;
    }
    /**
     * Getter of the color of the Player
     * @return color
     */
    public Color getColor() {
        return color;
    }
    /**
     * Setter of the color of the Player
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
        notifyObservers(this, PlayerEvent.COLOR_SETUP);
    }
    /**
     * Getter of the nickname of the Player
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * Getter of the cards that the Player has in his hand
     * @return a list of the cards in the hand of the Player
     */
    public List<PlayableCard> getHand(){
        return new ArrayList<>(hand);
    }
    /**
     * Setter of the cards that the Player has in his hand
     * @param hand: a list of the cards in the hand of the Player
     */
    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
        //notifyObservers(GameEvent.HAND_CHANGED, nickname);
    }
    /**
     * Setter of the cards that the Player has in his hand
     * @param hand: a list of the cards in the hand of the Player
     */
    public void setupHand(List<PlayableCard> hand){
        this.hand = hand;
    }
    /**
     * Setter of the secret objective cards between those the Player has to choose
     * @param secretObjectiveCards: a list of the 2 objective cards between those the player has to chose
     */
    public void setupSecretObjectiveCards(List<ObjectiveCard> secretObjectiveCards) {
        this.secretObjectiveCards = secretObjectiveCards;
    }









}