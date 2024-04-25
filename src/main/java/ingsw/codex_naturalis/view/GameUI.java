package ingsw.codex_naturalis.view;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.util.GameEvent;

public interface GameUI {

    void update(Game.Immutable o, GameEvent arg, String nickname, String playerWhoUpdated);

    void setPlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus);

    //void run();

    void stop();
}
