package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSLeaveGame implements MessageToServer{
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().leaveGame(clientSkeleton);
    }
}
