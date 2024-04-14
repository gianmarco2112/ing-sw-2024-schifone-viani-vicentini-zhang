package ingsw.codex_naturalis.distributed.local;

import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;

public interface LobbyObserver {

    void updateNetworkProtocol(NetworkProtocol networkProtocol);
    void updateGameToAccess(int gameID, String nickname);
    void updateNewGame(int numOfPlayers, String nickname);

}
