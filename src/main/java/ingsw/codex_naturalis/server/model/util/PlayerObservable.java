package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerObservable {

    private final List<PlayerObserver> obs;

    public PlayerObservable() {
        obs = new ArrayList<>();
    }

    public synchronized void addObserver(PlayerObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }


    public void notifyObservers(Player player, PlayerEvent playerEvent) {
        for (PlayerObserver o : obs) {
            o.update(player, playerEvent);
        }
    }

    public void notifyPlayerInGameStatus(Player player, boolean inGame) {
        for (PlayerObserver o : obs) {
            o.updatePlayerConnectionStatus(player, inGame);
        }
    }

    public void notifyReady(Player player){
        for (PlayerObserver o : obs) {
            o.updateReady(player);
        }
    }
}
