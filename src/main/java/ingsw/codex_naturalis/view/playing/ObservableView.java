package ingsw.codex_naturalis.view.playing;

import ingsw.codex_naturalis.controller.ObserverController;
import ingsw.codex_naturalis.view.playing.events.MessageEvent;
import ingsw.codex_naturalis.view.playing.events.PlayCardEvent;
import ingsw.codex_naturalis.view.playing.events.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.playing.events.commands.FlipCardCommand;

import java.util.ArrayList;
import java.util.List;

public class ObservableView {

    private final List<ObserverController> obs;


    public ObservableView() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(ObserverController o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(ObserverController o) {
        obs.remove(o);
    }


    public void notifyObservers(FlipCardCommand arg) {
        /*for (ObserverController o : obs){
            o.update(arg);
        }*/
    }

    public void notifyObservers(PlayCardEvent arg) {
        /*for (ObserverController o : obs){
            o.update(arg);
        }*/
    }

    public void notifyObservers(DrawCardCommand arg) {
        /*for (ObserverController o : obs){
            o.update(arg);
        }*/
    }

    public void notifyObservers(MessageEvent arg) {
        /*for (ObserverController o : obs){
            o.update(arg);
        }*/
    }
}