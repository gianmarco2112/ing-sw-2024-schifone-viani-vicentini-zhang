package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.GameView;

public abstract class GameplayUI extends GameplayObservable implements Observer<GameView, Event>, Runnable{

}
