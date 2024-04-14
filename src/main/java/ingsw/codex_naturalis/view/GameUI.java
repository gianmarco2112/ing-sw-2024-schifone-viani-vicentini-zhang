package ingsw.codex_naturalis.view;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.observerObservable.Event;

public interface GameUI {

    void update(Game.Immutable o, Event arg, String nickname, String playerWhoUpdated);

    void setPlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus);

    void run();

    void stop();
}
