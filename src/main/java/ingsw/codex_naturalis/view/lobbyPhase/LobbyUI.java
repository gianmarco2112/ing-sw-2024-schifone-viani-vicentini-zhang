package ingsw.codex_naturalis.view.lobbyPhase;


public abstract class LobbyUI extends LobbyObservable implements Runnable {

    public abstract void updateGamesSpecs(String jsonGamesSpecs);

    public abstract void stop();
}
