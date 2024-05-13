package ingsw.codex_naturalis.server.model.util;

import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.util.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameObservable {

    private final List<GameObserver> obs;

    public GameObservable() {
        obs = new ArrayList<>();
    }

    public synchronized void addObserver(GameObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(GameObserver o) {
        obs.remove(o);
    }


    public void notifyPlayerJoined(Game game, String nickname) {
        for (GameObserver o : obs) {
            o.updatePlayerJoined(game, nickname);
        }
    }

    public void notifyGameEvent(Game game, GameEvent gameEvent) {
        for (GameObserver o : obs) {
            o.update(game, gameEvent);
        }
    }

    public void notifyPlayerEvent(Game game, PlayerEvent playerEvent, Player playerWhoUpdated, String playerNicknameWhoUpdated) {
        for (GameObserver o : obs) {
            o.update(game, playerEvent, playerWhoUpdated, playerNicknameWhoUpdated);
        }
    }

    public void notifyException(String error, String playerNicknameWhoUpdated) {
        for (GameObserver o : obs) {
            o.updateException(error, playerNicknameWhoUpdated);
        }
    }

    public void notifyTurnChanged(String playerNickname) {
        for (GameObserver o : obs) {
            o.updateTurnChanged(playerNickname);
        }
    }

    public void notifyPlayerConnectionStatus(Game game, String playerNickname, boolean inGame) {
        for (GameObserver o : obs) {
            o.updatePlayerConnectionStatus(game, playerNickname, inGame);
        }
    }

    public void notifyPlayerLeft(Game game, String playerNicknameWhoLeft) {
        for (GameObserver o : obs) {
            o.updatePlayerLeft(game, playerNicknameWhoLeft);
        }
    }

    public void notifyGameRunningStatus(Game game, GameRunningStatus gameRunningStatus){
        for (GameObserver o : obs) {
            o.updateGameRunningStatus(game, gameRunningStatus);
        }
    }
}

