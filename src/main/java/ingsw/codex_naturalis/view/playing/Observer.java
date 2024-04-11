package ingsw.codex_naturalis.view.playing;

import ingsw.codex_naturalis.model.observerObservable.Observable;


public interface Observer<ObjectToObserve extends Observable<Event>, Event extends Enum<Event>> {

    void update(ObjectToObserve o, Event arg, String nickname);

}
