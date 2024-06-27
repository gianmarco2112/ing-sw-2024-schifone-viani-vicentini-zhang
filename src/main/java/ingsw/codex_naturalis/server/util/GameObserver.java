package ingsw.codex_naturalis.server.util;

import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;

/**
 * GameObserver's class
 */
public interface GameObserver {
    /**
     * To send the update that a player has joined the game
     * @param game : the game that the player joined
     * @param nickname: the nickname of the player who joined
     */
    void updatePlayerJoined(Game game, String nickname);
    /**
     * To send the update that a gameEvent has occurred to a game
     * @param game : the game where the gameEvent happened
     * @param gameEvent: the gameEvent that occurred
     */
    void update(Game game, GameEvent gameEvent);
    /**
     * To send the update that a playerEvent has occurred to a player
     * @param game : the game where the playerEvent happened
     * @param playerEvent: the playerEvent that occurred
     * @param playerNicknameWhoUpdated : the nickname of the player who has thrown the playerEvent
     * @param playerWhoUpdated : the player who has thrown the playerEvent
     */
    void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated, String playerNicknameWhoUpdated);
    /**
     * To send the update that an exception has occurred
     * @param error : the error that occurred
     * @param playerNicknameWhoUpdated : the nickname of the player who has thrown the exception
     */
    void updateException(String error, String playerNicknameWhoUpdated);
    /**
     * To send the update that an exception has occurred
     * @param playerNickname : the nickname of the player whose turn changed
     */
    void updateTurnChanged(String playerNickname);
    /**
     * To send the update that a player has changed his connectionStatus
     * @param game : the game where the player who changed his connectionStatus was playing
     * @param playerNickname : the nickname of the player whose ConnectionStatus changed
     * @param inGame : true if the player is in the game, false otherwise
     */
    void updatePlayerConnectionStatus(Game game, String playerNickname, boolean inGame);
    /**
     * To send the update that a player has left the game
     * @param game : the game where the player who left was playing
     * @param playerNicknameWhoLeft : the nickname of the player who left
     */
    void updatePlayerLeft(Game game, String playerNicknameWhoLeft);
    /**
     * To send the update that the gameRunningStatus has changed
     * @param game : the game whose gameRunningStatus has changed
     * @param gameRunningStatus: the new gameRunningStatus of the game
     */
    void updateGameRunningStatus(Game game, GameRunningStatus gameRunningStatus);
    /**
     * To send the update that a player is ready
     * @param playerNickname : the nickname of the player who is ready
     */
    void updatePlayerReady(String playerNickname);
}
