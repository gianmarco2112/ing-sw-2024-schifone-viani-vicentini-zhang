package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSImAlive implements MessageToServer{
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().imAlive(clientSkeleton);
    }
}
