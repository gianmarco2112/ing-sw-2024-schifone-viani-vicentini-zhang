package ingsw.codex_naturalis.view.lobbyPhase;

import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;
import ingsw.codex_naturalis.distributed.rmi.LobbyObserver;

import java.util.ArrayList;
import java.util.List;

public class LobbyObservable {

    private final List<LobbyObserver> obs;


    public LobbyObservable() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(LobbyObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public void deleteObservers() {
        synchronized (obs) {
            obs.clear();
        }
    }


    public void notifyNetworkProtocol(NetworkProtocol networkProtocol) {
        for (LobbyObserver o : obs){
            o.updateNetworkProtocol(networkProtocol);
        }
    }

    public void notifyNewGame(int numOfPlayers, String nickname) {
        for (LobbyObserver o : obs){
            o.updateNewGame(numOfPlayers, nickname);
        }
    }

    public void notifyGameToAccess(int gameID, String nickname) {
        for (LobbyObserver o : obs){
            o.updateGameToAccess(gameID, nickname);
        }
    }

}
