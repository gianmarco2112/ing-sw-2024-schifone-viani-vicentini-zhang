package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCTwentyPointsReached implements MessageToClient {

    private String jsonImmGame;

    public STCTwentyPointsReached() {
    }

    public STCTwentyPointsReached(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().twentyPointsReached(jsonImmGame);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
}
