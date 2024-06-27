package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the game is to be cancelled later
 */
public class STCGameToCancelLater implements MessageToClient{

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameToCancelLater();
    }
}
