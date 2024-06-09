package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCSetupUpdated implements MessageToClient {

    private String jsonImmGame;
    private String jsonGameEvent;

    public STCSetupUpdated() {
    }

    public STCSetupUpdated(String jsonImmGame, String jsonGameEvent) {
        this.jsonImmGame = jsonImmGame;
        this.jsonGameEvent = jsonGameEvent;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().setupUpdated(jsonImmGame, jsonGameEvent);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    public String getJsonGameEvent() {
        return jsonGameEvent;
    }

    public void setJsonGameEvent(String jsonGameEvent) {
        this.jsonGameEvent = jsonGameEvent;
    }
}
