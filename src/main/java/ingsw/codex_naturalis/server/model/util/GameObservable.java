package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.util.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameObservable {

    private final List<GameObserver> obs;

    public GameObservable() {
        obs = new ArrayList<>();
    }

    public synchronized void addObserver(GameObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObservers(GameObserver o) {
        obs.clear();
    }



    public void notifyObservers(Game game, GameEvent gameEvent) {
        for (GameObserver o : obs) {
            o.update(game, gameEvent);
        }
    }

    public void notifyObservers(Game game, PlayerEvent playerEvent, Player playerWhoUpdated) {
        for (GameObserver o : obs) {
            o.update(game, playerEvent, playerWhoUpdated);
        }
    }
}
