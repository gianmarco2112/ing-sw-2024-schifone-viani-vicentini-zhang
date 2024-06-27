package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
/**
 * Message from client to server: the client's status is "connected to the server"
 */
public class CTSImAlive implements MessageToServer{

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().imAlive(clientSkeleton);
    }
}
