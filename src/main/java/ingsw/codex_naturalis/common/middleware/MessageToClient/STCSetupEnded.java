package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCSetupEnded implements MessageToClient {

    private String jsonImmGame;

    public STCSetupEnded() {
    }

    public STCSetupEnded(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().setupEnded(jsonImmGame);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
}
