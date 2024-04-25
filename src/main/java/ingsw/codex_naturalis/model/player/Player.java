package ingsw.codex_naturalis.model.player;

import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.enumerations.TurnStatus;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.util.PlayerEvent;
import ingsw.codex_naturalis.model.util.PlayerObservable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Player class
 */
public class Player extends PlayerObservable {



    public record Immutable(String nickname, Color color, TurnStatus turnStatus,
                            PlayableCard.Immutable initialCard, List<ObjectiveCard.Immutable> secretObjectiveCards,List<PlayableCard.Immutable> hand,
                            PlayerArea.Immutable playerArea) {}

    public record ImmutableHidden(String nickname, Color color, TurnStatus turnStatus,
                                  PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> hand,
                            PlayerArea.ImmutableHidden playerArea) {}

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

    private List<ObjectiveCard> secretObjectiveCards;

    /**
     * Player area
     */
    private final PlayerArea playerArea;



    /**
     * Constructor
     * @param nickname Nickname
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



    public void flip(PlayableCard cardToFlip) {
        cardToFlip.flip();
        if (initialCard != null)
            notifyObservers(this, PlayerEvent.INITIAL_CARD_FLIPPED);
        else
            notifyObservers(this, PlayerEvent.HAND_CARD_FLIPPED);
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
        playerArea.setInitialCard(initialCard);
        initialCard = null;
        notifyObservers(this, PlayerEvent.INITIAL_CARD_PLAYED);
    }

    public List<ObjectiveCard> getSecretObjectiveCards() {
        return secretObjectiveCards;
    }
    public void chooseObjectiveCard(ObjectiveCard objectiveCard) {
        playerArea.setObjectiveCard(objectiveCard);
        notifyObservers(this, PlayerEvent.OBJECTIVE_CARD_CHOSEN);
    }

    public TurnStatus getTurnStatus() {
        return turnStatus;
    }
    public void setTurnStatus(TurnStatus turnStatus) {
        this.turnStatus = turnStatus;
        //notifyObservers(GameEvent.TURN_STATUS_CHANGED, nickname);
    }

    public PlayerArea getPlayerArea(){
        return playerArea;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
        notifyObservers(this, PlayerEvent.COLOR_SETUP);
    }

    public String getNickname() {
        return nickname;
    }

    public List<PlayableCard> getHand(){
        return new ArrayList<>(hand);
    }

    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
        //notifyObservers(GameEvent.HAND_CHANGED, nickname);
    }

    public void setupHand(List<PlayableCard> hand){
        this.hand = hand;
    }

    public void setupSecretObjectiveCards(List<ObjectiveCard> secretObjectiveCards) {
        this.secretObjectiveCards = secretObjectiveCards;
    }









}