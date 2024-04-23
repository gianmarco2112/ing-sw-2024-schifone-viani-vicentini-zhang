package ingsw.codex_naturalis.distributed.util;

public interface LobbyObserver {

    void updateGameToAccess(int gameID, String nickname);
    void updateNewGame(int numOfPlayers, String nickname);

}
