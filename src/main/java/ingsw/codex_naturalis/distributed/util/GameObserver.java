package ingsw.codex_naturalis.distributed.util;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.util.PlayerEvent;


public interface GameObserver {

    //void update(Game game, GameEvent gameEvent, String playerWhoUpdated);

    void update(Game game, GameEvent gameEvent);

    void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated);

}
