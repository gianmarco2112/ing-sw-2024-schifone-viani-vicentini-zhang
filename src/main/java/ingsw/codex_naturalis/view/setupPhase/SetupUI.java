package ingsw.codex_naturalis.view.setupPhase;


import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.util.GameEvent;

import java.util.List;

public abstract class SetupUI extends SetupObservable implements Runnable {

    public abstract void stop();

    public abstract void updateInitialCard(Game.Immutable game, InitialCardEvent initialCardEvent);

    public abstract void updateColor(Color color);

    public abstract void reportError(String message);

    public abstract void update(Game.Immutable immGame, GameEvent gameEvent);

    public abstract void updateObjectiveCardChoice(Game.Immutable immGame);
}
