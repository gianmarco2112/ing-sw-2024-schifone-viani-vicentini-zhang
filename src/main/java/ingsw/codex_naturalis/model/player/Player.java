package ingsw.codex_naturalis.model.player;

import ingsw.codex_naturalis.model.cards.Card;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.enumerations.TurnStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.Observable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Player class
 */
public class Player extends Observable<Event> {

    public record Immutable(String nickname, Color color, TurnStatus turnStatus,
                            PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> hand,
                            PlayerArea.Immutable playerArea) implements Serializable {
        @Serial
        private static final long serialVersionUID = 5L; }

    public record ImmutableHidden(String nickname, Color color, TurnStatus turnStatus,
                                  PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> hand,
                            PlayerArea.ImmutableHidden playerArea) implements Serializable {
        @Serial
        private static final long serialVersionUID = 7L; }

    public Player.Immutable getImmutablePlayer(){

        List<PlayableCard.Immutable> immutableHand = new ArrayList<>();
        for (PlayableCard playableCard : hand)
            immutableHand.add(playableCard.getImmutablePlayableCard());
        return new Player.Immutable(nickname, color, turnStatus, initialCard.getImmutablePlayableCard(),
                immutableHand, playerArea.getImmutablePlayerArea());
    }

    public Player.ImmutableHidden getImmutableHiddenPlayer(){

        List<PlayableCard.Immutable> immutableHiddenHand = new ArrayList<>();
        for (PlayableCard playableCard : hand)
            immutableHiddenHand.add(playableCard.getImmutableHiddenPlayableCard());
        return new Player.ImmutableHidden(nickname, color, turnStatus, initialCard.getImmutablePlayableCard(),
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

        playerArea.setInitialCard(initialCard, nickname);
        initialCard = null;

    }

    public TurnStatus getTurnStatus() {
        return turnStatus;
    }
    public void setTurnStatus(TurnStatus turnStatus) {
        this.turnStatus = turnStatus;
        notifyObservers(Event.TURN_STATUS_CHANGED, nickname);
    }

    public PlayerArea getPlayerArea(){
        return playerArea;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
        notifyObservers(Event.COLOR_SETUP, "");
    }

    public String getNickname() {
        return nickname;
    }

    public List<PlayableCard> getHand(){
        return new ArrayList<>(hand);
    }

    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
        notifyObservers(Event.HAND_CHANGED, nickname);
    }

    public void setupHand(List<PlayableCard> hand){
        this.hand = hand;
    }







}