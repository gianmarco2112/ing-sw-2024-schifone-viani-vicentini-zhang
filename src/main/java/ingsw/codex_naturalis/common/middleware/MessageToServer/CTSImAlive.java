package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSImAlive implements MessageToServer{
    /**
     * To run the clientSkeleton and send the message to the server that a client's status is "connected to the server"
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().imAlive(clientSkeleton);
    }
}
