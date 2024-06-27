package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSReadyToPlay implements MessageToServer {
    /**
     * To run the clientSkeleton and send the message to the server that a player is ready to play
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().readyToPlay(clientSkeleton.getNickname());
    }
}
