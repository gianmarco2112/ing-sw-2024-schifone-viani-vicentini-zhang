package ingsw.codex_naturalis.client.util;

public interface LobbyObserver {

    void ctsUpdateGameToAccess(int gameID, String nickname);
    void ctsUpdateNewGame(int numOfPlayers, String nickname);

}
