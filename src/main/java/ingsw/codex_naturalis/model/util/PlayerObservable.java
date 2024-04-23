package ingsw.codex_naturalis.model.util;

import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.util.PlayerEvent;
import ingsw.codex_naturalis.model.util.PlayerObserver;

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

    public synchronized void deleteObservers() {
        obs.clear();
    }



    public void notifyObservers(Player player, PlayerEvent playerEvent) {
        for (PlayerObserver o : obs) {
            o.update(player, playerEvent);
        }
    }

}
