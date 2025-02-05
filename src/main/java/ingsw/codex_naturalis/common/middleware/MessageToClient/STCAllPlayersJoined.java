package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.IOException;
/**
 * Message from server to client: all the players have joined the game
 */
public class STCAllPlayersJoined implements MessageToClient {

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().allPlayersJoined();
    }
}
