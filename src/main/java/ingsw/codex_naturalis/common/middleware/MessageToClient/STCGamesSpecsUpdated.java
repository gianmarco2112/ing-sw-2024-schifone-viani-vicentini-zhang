package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCGamesSpecsUpdated implements MessageToClient {

    private String jsonGamesSpecs;

    public STCGamesSpecsUpdated() {
    }

    public STCGamesSpecsUpdated(String jsonGamesSpecs) {
        this.jsonGamesSpecs = jsonGamesSpecs;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().updateGamesSpecs(jsonGamesSpecs);
    }

    public String getJsonGamesSpecs() {
        return jsonGamesSpecs;
    }

    public void setJsonGamesSpecs(String jsonGamesSpecs) {
        this.jsonGamesSpecs = jsonGamesSpecs;
    }
}
