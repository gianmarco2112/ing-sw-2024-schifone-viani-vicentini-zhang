package ingsw.codex_naturalis.common;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    String getNickname() throws RemoteException;
    void setNickname(String nickname) throws RemoteException;


    void updateGamesSpecs(String jsonGameSpecs) throws RemoteException;

    void reportException(String error) throws RemoteException;


    void gameJoined(int gameID) throws RemoteException;


    void allPlayersJoined() throws RemoteException;


    void setupUpdated(String jsonImmGame, String jsonGameEvent) throws RemoteException;
    void initialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) throws RemoteException;
    void colorUpdated(String jsonColor) throws RemoteException;
    void objectiveCardChosen(String jsonImmGame) throws RemoteException;


    void setupEnded(String jsonImmGame) throws RemoteException;

    void cardFlipped(String jsonGame) throws RemoteException;

    void cardPlayed(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException;

    void cardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException;

    void turnChanged(String currentPlayer) throws RemoteException;

    void messageSent(String jsonImmGame) throws RemoteException;

    void twentyPointsReached(String jsonImmGame) throws RemoteException;

    void decksEmpty(String jsonImmGame) throws RemoteException;

    void gameEnded(String jsonPlayers) throws RemoteException;

    void gameCanceled() throws RemoteException;

    void gameLeft() throws RemoteException;

    void gameRejoined(String jsonImmGame, String nickname) throws RemoteException;

    void updatePlayerInGameStatus(String jsonImmGame, String playerNickname,
                                  String jsonInGame, String jsonHasDisconnected) throws RemoteException;

    void gameToCancelLater() throws RemoteException;

    void gameResumed() throws RemoteException;

    void playerIsReady(String playerNickname) throws RemoteException;
}
