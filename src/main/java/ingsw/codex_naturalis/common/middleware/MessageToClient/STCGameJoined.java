package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCGameJoined implements MessageToClient {

    private int gameID;

    public STCGameJoined() {
    }

    public STCGameJoined(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameJoined(gameID);
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
