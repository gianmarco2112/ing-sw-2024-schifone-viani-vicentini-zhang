package ingsw.codex_naturalis.common;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Nickname's getter
     * @return nickname
     */
    String getNickname() throws RemoteException;
    /**
     * Nickname's setter
     * @param nickname to set
     */
    void setNickname(String nickname) throws RemoteException;
    /**
     * Update from server: Games specs updated
     * @param jsonGameSpecs games specks
     */
    void updateGamesSpecs(String jsonGameSpecs) throws RemoteException;
    /**
     * Exception received
     * @param error error
     */
    void reportException(String error) throws RemoteException;
    /**
     * Update from server: game joined
     */
    void gameJoined(int gameID) throws RemoteException;
    /**
     * Update from server: all players joined the game
     */
    void allPlayersJoined() throws RemoteException;

    /**
     * Update from server: setup update
     * @param jsonImmGame immutable game
     * @param jsonGameEvent game event (setup event)
     */
    void setupUpdated(String jsonImmGame, String jsonGameEvent) throws RemoteException;
    /**
     * Update from server: initial card played or flipped
     * @param jsonImmGame immutable game
     * @param jsonInitialCardEvent flipped or played
     */
    void initialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) throws RemoteException;
    /**
     * Update from server: color updated
     * @param jsonColor color
     */
    void colorUpdated(String jsonColor) throws RemoteException;
    /**
     * Update from server: objective card chosen
     * @param jsonImmGame immutable game
     */
    void objectiveCardChosen(String jsonImmGame) throws RemoteException;
    /**
     * Update from server: setup ended
     * @param jsonImmGame immutable game
     */

    void setupEnded(String jsonImmGame) throws RemoteException;
    /**
     * Update from server: hand card flipped
     * @param jsonGame immutable game
     */
    void cardFlipped(String jsonGame) throws RemoteException;
    /**
     * Update from server: hand card played
     * @param jsonImmGame immutable game
     * @param playerNicknameWhoUpdated player's nickname who has played the card
     */
    void cardPlayed(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException;
    /**
     * Update from server: card drawn
     * @param jsonImmGame immutable game
     * @param playerNicknameWhoUpdated player's nickname who has draw a card
     */
    void cardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException;
    /**
     * Update from server: turn changed
     * @param currentPlayer current player
     */
    void turnChanged(String currentPlayer) throws RemoteException;
    /**
     * Update from server: chat updated
     * @param jsonImmGame immutable game
     */
    void messageSent(String jsonImmGame) throws RemoteException;
    /**
     * Update from server: twenty points reached
     * @param jsonImmGame immutable game
     */
    void twentyPointsReached(String jsonImmGame) throws RemoteException;
    /**
     * Update from server: decks are empty
     * @param jsonImmGame immutable game
     */
    void decksEmpty(String jsonImmGame) throws RemoteException;
    /**
     * Update from server: game ended
     * @param jsonPlayers players with points and secret obj cards
     */
    void gameEnded(String jsonPlayers) throws RemoteException;
    /**
     * Update from server: game has been canceled
     */
    void gameCanceled() throws RemoteException;

    /**
     * Update from server: game left
     */
    void gameLeft() throws RemoteException;
    /**
     * Update from server: game rejoined
     * @param jsonImmGame immutable game
     * @param nickname nickname to set again
     */
    void gameRejoined(String jsonImmGame, String nickname) throws RemoteException;
    /**
     * Update from server: a player in game status changed (disconnected, reconnected, or left the game)
     * @param jsonImmGame immutable game
     * @param playerNickname player
     * @param jsonInGame boolean in game
     * @param jsonHasDisconnected boolean disconnected
     */
    void updatePlayerInGameStatus(String jsonImmGame, String playerNickname,
                                  String jsonInGame, String jsonHasDisconnected) throws RemoteException;
    /**
     * Update from server: game will be cancelled if nobody joins within 10 seconds.
     */
    void gameToCancelLater() throws RemoteException;
    /**
     * Update from server: game resumed
     */
    void gameResumed() throws RemoteException;
    /**
     * Update from server: player is ready
     */
    void playerIsReady(String playerNickname) throws RemoteException;
}
