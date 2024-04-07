package ingsw.codex_naturalis.model.observerObservable;

import ingsw.codex_naturalis.controller.ObserverController;
import ingsw.codex_naturalis.view.playing.ObserverView;
import ingsw.codex_naturalis.view.playing.events.commands.FlipCardCommand;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel {

    private final List<ObserverView> obs;


    public ObservableModel() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(ObserverView o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(ObserverView o) {
        obs.remove(o);
    }


    public void notifyObservers(FlipCardCommand arg) {
        /*for (ObserverController o : obs){
            o.update(arg);
        }*/
    }
}
