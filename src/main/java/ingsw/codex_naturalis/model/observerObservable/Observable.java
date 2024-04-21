package ingsw.codex_naturalis.model.observerObservable;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.view.gameplayPhase.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private final List<Observer> obs;

    public Observable() {
        obs = new ArrayList<>();
    }

    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(Observer o) {
        obs.remove(o);
    }



    public void notifyObservers(Game game, Event arg, String playerWhoUpdated) {
        for (Observer o : obs) {
            o.update(game, arg, playerWhoUpdated);
        }
    }
}
