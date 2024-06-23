package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.server.model.player.Player;
/**
 * PlayerObserver's class
 */

public interface PlayerObserver {
    /**
     * To send the update that a playerEvent has occurred to a game
     * @param player : the player who generated the playerEvent
     * @param playerEvent :  the playerEvent that has been thrown
     */
    void update(Player player, PlayerEvent playerEvent);
    /**
     * To send the update that a player changed his playerConnectionStatus
     * @param player : the player whose connectionStatus has changed
     * @param inGame :  true if the player is in the game, false otherwise
     */
    void updatePlayerConnectionStatus(Player player, boolean inGame);
    /**
     * To send the update that a player is ready
     * @param player : the player who is ready
     */
    void updateReady(Player player);
}
