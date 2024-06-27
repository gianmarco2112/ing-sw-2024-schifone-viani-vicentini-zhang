package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.IOException;

public class STCAllPlayersJoined implements MessageToClient {
    /**
     * To run the serverStub and send the message to the client that all the players joined the game
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().allPlayersJoined();
    }
}
