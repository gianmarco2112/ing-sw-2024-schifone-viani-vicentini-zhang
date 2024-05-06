package ingsw.codex_naturalis.client.view.setupPhase;


import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.util.GameEvent;

public abstract class SetupUI extends SetupObservable implements Runnable {

    public abstract void stop();

    public abstract void updateInitialCard(Game.Immutable game, InitialCardEvent initialCardEvent);

    public abstract void updateColor(Color color);

    public abstract void reportError(String message);

    public abstract void update(Game.Immutable immGame, GameEvent gameEvent);

    public abstract void updateObjectiveCardChoice(Game.Immutable immGame);
}
