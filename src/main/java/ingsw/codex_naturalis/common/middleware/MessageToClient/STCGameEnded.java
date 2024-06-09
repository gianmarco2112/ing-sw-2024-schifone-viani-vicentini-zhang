package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCGameEnded implements MessageToClient {

    private String jsonPlayers;

    public STCGameEnded() {
    }

    public STCGameEnded(String jsonPlayers) {
        this.jsonPlayers = jsonPlayers;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameEnded(jsonPlayers);
    }

    public String getJsonPlayers() {
        return jsonPlayers;
    }

    public void setJsonPlayers(String jsonPlayers) {
        this.jsonPlayers = jsonPlayers;
    }
}
