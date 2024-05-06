package ingsw.codex_naturalis.client.view.lobbyPhase;


import ingsw.codex_naturalis.server.model.GameSpecs;

import java.util.List;

public abstract class LobbyUI extends LobbyObservable implements Runnable {

    public abstract void updateGamesSpecs(List<GameSpecs> gamesSpecs);

    public abstract void reportError(String error);

    public abstract void stop();


}
