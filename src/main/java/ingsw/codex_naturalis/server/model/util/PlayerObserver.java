package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.server.model.player.Player;

public interface PlayerObserver {

    void update(Player player, PlayerEvent playerEvent);

    void updatePlayerConnectionStatus(Player player, boolean inGame);
}
