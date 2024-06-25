package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;
/**
 * PlayerObservable's class
 */
public class PlayerObservable {
    /**
     * List of all the observers
     */
    private final List<PlayerObserver> obs;
    /**
     * PlayerObservable's constructor: create an array list of observers
     */
    public PlayerObservable() {
        obs = new ArrayList<>();
    }
    /**
     * To add an observer to the list of entities that would be notified
     * @param o : the observer to be added
     */
    public synchronized void addObserver(PlayerObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }
    /**
     * To notify the observers that a playerEvent has occurred
     * @param player : the player who had thrown the playerEvent
     * @param playerEvent : the playerEvent that has been thrown
     */
    public void notifyObservers(Player player, PlayerEvent playerEvent) {
        for (PlayerObserver o : obs) {
            o.update(player, playerEvent);
        }
    }
    /**
     * To notify the observers that an update of the inGame status of a player has occurred
     * @param player : the player whose InGame status has changed
     * @param inGame : true if the player is in game, false otherwise
     */
    public void notifyPlayerInGameStatus(Player player, boolean inGame) {
        for (PlayerObserver o : obs) {
            o.updatePlayerConnectionStatus(player, inGame);
        }
    }
    /**
     * To notify the observers that a player is ready
     * @param player : the player who is ready
     */

    public void notifyReady(Player player){
        for (PlayerObserver o : obs) {
            o.updateReady(player);
        }
    }
}
