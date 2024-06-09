package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;


public class STCInitialCardUpdated implements MessageToClient {

    private String jsonImmGame;
    private String jsonInitialCardEvent;

    public STCInitialCardUpdated() {
    }

    public STCInitialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) {
        this.jsonImmGame = jsonImmGame;
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().initialCardUpdated(jsonImmGame, jsonInitialCardEvent);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    public String getJsonInitialCardEvent() {
        return jsonInitialCardEvent;
    }

    public void setJsonInitialCardEvent(String jsonInitialCardEvent) {
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }
}
