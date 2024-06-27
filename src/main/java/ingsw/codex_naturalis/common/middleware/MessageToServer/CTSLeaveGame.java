package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSLeaveGame implements MessageToServer{
    /**
     * To run the clientSkeleton and send the message to the server that a client wants to leave the game
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().leaveGame(clientSkeleton);
    }
}
