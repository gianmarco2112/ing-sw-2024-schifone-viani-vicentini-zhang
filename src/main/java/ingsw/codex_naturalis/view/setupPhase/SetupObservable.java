package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;

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
            o.ctsUpdateReady();
        }
    }

    public void notifyInitialCard(InitialCardEvent initialCardEvent) {
        for (SetupObserver o : obs){
            o.ctsUpdateInitialCard(initialCardEvent);
        }
    }

    public void notifyColor(Color color) {
        for (SetupObserver o : obs){
            o.ctsUpdateColor(color);
        }
    }

    public void notifyObjectiveCardChoice(ObjectiveCardChoice objectiveCardChoice) {
        for (SetupObserver o : obs){
            o.ctsUpdateObjectiveCardChoice(objectiveCardChoice);
        }
    }


}
