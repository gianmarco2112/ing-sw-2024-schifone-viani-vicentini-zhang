package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.view.GameUI;

public abstract class GameplayUI extends GameplayObservable implements Runnable {

    public abstract void stop();

    public abstract void updatePlayerOrder(Game.Immutable immGame);

}
