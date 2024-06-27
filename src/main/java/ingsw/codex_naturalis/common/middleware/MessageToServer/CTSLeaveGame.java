package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
/**
 * Message from client to server: the client wants to leave the game
 */
public class CTSLeaveGame implements MessageToServer{

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().leaveGame(clientSkeleton);
    }
}
