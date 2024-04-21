package ingsw.codex_naturalis.view.lobbyPhase;


import ingsw.codex_naturalis.distributed.ServerImpl;

import java.util.List;

public abstract class LobbyUI extends LobbyObservable implements Runnable {

    public abstract void updateGamesSpecs(List<ServerImpl.GameSpecs> gamesSpecs);

    public abstract void reportError(String error);

    public abstract void stop();


}
