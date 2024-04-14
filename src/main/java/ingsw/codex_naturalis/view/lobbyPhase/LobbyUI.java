package ingsw.codex_naturalis.view.lobbyPhase;


import ingsw.codex_naturalis.distributed.local.ServerImpl;

import java.util.List;

public abstract class LobbyUI extends LobbyObservable {

    public abstract void run();

    public abstract void stop();

    public abstract void updateGamesSpecs(List<ServerImpl.GameSpecs> gamesSpecs);

}
