package ingsw.codex_naturalis.server.util;

import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;


public interface GameObserver {

    //void update(Game game, GameEvent gameEvent, String playerWhoUpdated);

    void update(Game game, GameEvent gameEvent);

    void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated);

}
