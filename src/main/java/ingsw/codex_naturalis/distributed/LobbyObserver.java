package ingsw.codex_naturalis.distributed;

public interface LobbyObserver {

    void updateGameToAccess(int gameID, String nickname);
    void updateNewGame(int numOfPlayers, String nickname);

}
