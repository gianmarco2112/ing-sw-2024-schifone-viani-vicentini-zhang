package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCCardFlipped implements MessageToClient {

    private String jsonImmGame;

    public STCCardFlipped() {
    }

    public STCCardFlipped(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().cardFlipped(jsonImmGame);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
}
