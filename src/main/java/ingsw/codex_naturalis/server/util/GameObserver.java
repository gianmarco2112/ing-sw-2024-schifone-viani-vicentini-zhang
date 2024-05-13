package ingsw.codex_naturalis.server.util;

import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;


public interface GameObserver {

    void updatePlayerJoined(Game game, String nickname);

    void update(Game game, GameEvent gameEvent);

    void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated, String playerNicknameWhoUpdated);

    void updateException(String error, String playerNicknameWhoUpdated);

    void updateTurnChanged(String playerNickname);

    void updatePlayerConnectionStatus(Game game, String playerNickname, boolean inGame);

    void updatePlayerLeft(Game game, String playerNicknameWhoLeft);

    void updateGameRunningStatus(Game game, GameRunningStatus gameRunningStatus);
}
