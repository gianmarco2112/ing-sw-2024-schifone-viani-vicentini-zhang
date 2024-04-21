package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.Observable;


public interface Observer {

    void update(Game o, Event arg, String playerWhoUpdated);

}
