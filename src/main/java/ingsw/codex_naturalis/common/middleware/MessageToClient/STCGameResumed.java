package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the game has been resumed
 */
public class STCGameResumed implements MessageToClient{

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameResumed();
    }
}
