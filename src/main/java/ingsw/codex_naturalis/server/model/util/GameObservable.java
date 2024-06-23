package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.util.GameObserver;

import java.util.ArrayList;
import java.util.List;
/**
 * GameObservable's class
 */
public class GameObservable {
    /**
     * List of all the observers
     */
    private final List<GameObserver> obs;
    /**
     * GameObservable's constructor: create an array list of observers
     */
    public GameObservable() {
        obs = new ArrayList<>();
    }
    /**
     * To add an observer to the list of entities that would be notified
     * @param o : the observer to be added
     */
    public synchronized void addObserver(GameObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }
    /**
     * To delete an observer to the list of entities that would be notified
     * @param o : the observer to be deleted
     */
    public synchronized void deleteObserver(GameObserver o) {
        obs.remove(o);
    }

    /**
     * To notify the observers that a player has joined the game
     * @param game: which game the player has joined
     * @param nickname: of the player who joined the game
     */
    public void notifyPlayerJoined(Game game, String nickname) {
        for (GameObserver o : obs) {
            o.updatePlayerJoined(game, nickname);
        }
    }
    /**
     * To notify the observers that a gameEvent has occurred
     * @param game : the game to notify
     * @param gameEvent : the gameEvent that has occurred
     */
    public void notifyGameEvent(Game game, GameEvent gameEvent) {
        for (GameObserver o : obs) {
            o.update(game, gameEvent);
        }
    }
    /**
     * To notify the observers that a playerEvent has occurred
     * @param game : the game to notify
     * @param playerEvent : the playerEvent that occurred to the player
     * @param playerWhoUpdated : the player who has thrown the playerEvent
     * @param playerNicknameWhoUpdated : nickname of the player who has thrown the playerEvent
     */
    public void notifyPlayerEvent(Game game, PlayerEvent playerEvent, Player playerWhoUpdated, String playerNicknameWhoUpdated) {
        for (GameObserver o : obs) {
            o.update(game, playerEvent, playerWhoUpdated, playerNicknameWhoUpdated);
        }
    }
    /**
     * To notify the observers that an exception has been thrown
     * @param error: the exception that was thrown
     * @param playerNicknameWhoUpdated : nickname of the player who has thrown the exception
     */
    public void notifyException(String error, String playerNicknameWhoUpdated) {
        for (GameObserver o : obs) {
            o.updateException(error, playerNicknameWhoUpdated);
        }
    }
    /**
     * To notify the observers that the turn changed
     * @param playerNickname: the nickname of the player whose turn changed
     */
    public void notifyTurnChanged(String playerNickname) {
        for (GameObserver o : obs) {
            o.updateTurnChanged(playerNickname);
        }
    }
    /**
     * To notify the observers that the connection status of a player has changed
     * @param game : the game to notify
     * @param playerNickname: the nickname of the player whose connection status has changed
     * @param inGame: true if the player is still in the game, false otherwise
     */
    public void notifyPlayerConnectionStatus(Game game, String playerNickname, boolean inGame) {
        for (GameObserver o : obs) {
            o.updatePlayerConnectionStatus(game, playerNickname, inGame);
        }
    }
    /**
     * To notify the observers that a player left the game
     * @param game : the game to notify
     * @param playerNicknameWhoLeft: the nickname of the player who left
     */
    public void notifyPlayerLeft(Game game, String playerNicknameWhoLeft) {
        for (GameObserver o : obs) {
            o.updatePlayerLeft(game, playerNicknameWhoLeft);
        }
    }
    /**
     * To notify the observers that the gameRunningStatus changed
     * @param game: the game whose RunningStatus changed
     * @param gameRunningStatus: the new GameRunningStatus of the game
     */
    public void notifyGameRunningStatus(Game game, GameRunningStatus gameRunningStatus){
        for (GameObserver o : obs) {
            o.updateGameRunningStatus(game, gameRunningStatus);
        }
    }
    /**
     * To notify the observers that a player is ready
     * @param playerNickname: the nickname of the player who is ready
     */
    public void notifyPlayerReady(String playerNickname){
        for (GameObserver o : obs) {
            o.updatePlayerReady(playerNickname);
        }
    }
}

