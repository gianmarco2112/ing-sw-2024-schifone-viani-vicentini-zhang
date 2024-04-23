package ingsw.codex_naturalis.model.util;

import ingsw.codex_naturalis.model.player.Player;

public interface PlayerObserver {

    void update(Player player, PlayerEvent playerEvent);

}
