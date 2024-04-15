package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.distributed.local.ServerImpl;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;

import java.util.List;

public interface Client {

    void updateGameUI(Game.Immutable o, Event arg, String playerWhoUpdated);

    void updateLobbyUI(List<ServerImpl.GameSpecs> gamesSpecs);

    void setNickname(String nickname);

    String getNickname();

    void updateView(GameStatus gameStatus, PlayersConnectedStatus playersConnectedStatus);

    void updatePlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus);

}
