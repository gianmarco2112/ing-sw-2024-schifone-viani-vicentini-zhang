package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCMessageSent implements MessageToClient {

    private String jsonImmGame;

    public STCMessageSent() {
    }

    public STCMessageSent(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().messageSent(jsonImmGame);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
}
