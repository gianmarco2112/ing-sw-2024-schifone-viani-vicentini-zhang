package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCGameResumed implements MessageToClient{
    /**
     * To run the serverStub and send the message to the client that the game has been resumed
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameResumed();
    }
}
