package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCPlayerIsReady implements MessageToClient {

    private String playerNickname;

    public STCPlayerIsReady() {
    }

    public STCPlayerIsReady(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().playerIsReady(playerNickname);
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }
}
