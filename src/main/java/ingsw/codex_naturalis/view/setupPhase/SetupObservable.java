package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;

import java.util.ArrayList;
import java.util.List;

public class SetupObservable {

    private final List<SetupObserver> obs;


    public SetupObservable() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(SetupObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObservers() {
        obs.clear();
    }


    public void notifyReady() {
        for (SetupObserver o : obs){
            o.updateReady();
        }
    }

    /*public void notifyFlipCard(FlipCard flipCardCommand) {
        for (SetupObserver o : obs){
            o.updateFlipCard(flipCardCommand);
        }
    }*/
}
