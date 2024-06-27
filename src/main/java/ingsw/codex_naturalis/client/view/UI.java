package ingsw.codex_naturalis.client.view;

import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.common.immutableModel.ImmPlayer;
import ingsw.codex_naturalis.server.model.util.GameEvent;

import java.util.List;

/**
 * Interface implemented by the TUI and the GUI
 */
public interface UI {
    /**
     * To report an error
     * @param error : the error to report
     */
    void reportError(String error);
    /**
     * To set the nickname
     * @param nickname : the nickmane to set
     */
    void setNickname(String nickname);
    /**
     * To update the game specs of a game
     * @param gamesSpecs : list of the game specs
     */
    void updateGamesSpecs(List<GameSpecs> gamesSpecs);
    /**
     * To update the game ID of a game
     * @param gameID : the ID to update
     */
    void updateGameID(int gameID);
    /**
     * To verify if all players have joined
     */
    void allPlayersJoined();
    /**
     * To update the game's setup
     */
    void updateSetup(ImmGame immGame, GameEvent gameEvent);
    /**
     * To update the initial card
     */

    void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent);
    /**
     * To update the player's color
     */
    void updateColor(Color color);
    /**
     * To update the choice of the objective card made by a player
     */
    void updateObjectiveCardChoice(ImmGame immGame);
    /**
     * To end the game's setup
     */
    void endSetup(ImmGame game);
    /**
     * To update that a card has been flipped
     */
    void cardFlipped(ImmGame game);
    /**
     * To update that a card has been played
     */
    void cardPlayed(ImmGame immGame, String playerNicknameWhoUpdated);
    /**
     * To update that a card has been drawn
     */
    void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated);
    /**
     * To update that the turn has changed
     * @param currentPlayer : the new currentplayer
     */
    void turnChanged(String currentPlayer);
    /**
     * To update that a message has been sent
     */
    void messageSent(ImmGame immGame);
    /**
     * To update that 20 points has been reached
     */
    void twentyPointsReached(ImmGame immGame);
    /**
     * To update that all the decks are empty
     */
    void decksEmpty(ImmGame immGame);
    /**
     * To update that the game ended
     */
    void gameEnded(List<ImmPlayer> players);
    /**
     * To update that the game has been cancelled
     */
    void gameCanceled();
    /**
     * To update that a player has left the game
     */
    void gameLeft();
    /**
     * To update that a player has rejoined the game
     */
    void gameRejoined(ImmGame game);
    /**
     * To update the player's InGame status
     */
    void updatePlayerInGameStatus(ImmGame immGame, String playerNickname,
                                         boolean inGame, boolean hasDisconnected);
    /**
     * To update that the game is paused
     */
    void gamePaused();
    /**
     * To update that the game is resumed
     */
    void gameResumed();
    /**
     * To update that a player is ready
     * @param playerNickname : the nickname of the player who is ready
     */
    void playerIsReady(String playerNickname);
}
