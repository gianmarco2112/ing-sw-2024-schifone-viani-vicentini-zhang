package ingsw.codex_naturalis.model.observerObservable;

import ingsw.codex_naturalis.view.gameplayPhase.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<Event extends Enum<Event>> {

    private final List<Observer<Observable<Event>, Event>> obs;

    public Observable() {
        obs = new ArrayList<>();
    }

    public synchronized void addObserver(Observer<Observable<Event>, Event> o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(Observer<Observable<Event>, Event> o) {
        obs.remove(o);
    }



    public void notifyObservers(Event arg, String nickname) {
        for (Observer<Observable<Event>, Event> o : obs) {
            o.update(this, arg, nickname);
        }
    }
}
