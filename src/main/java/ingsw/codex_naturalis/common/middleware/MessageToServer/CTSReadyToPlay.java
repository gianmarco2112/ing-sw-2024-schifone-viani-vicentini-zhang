package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
/**
 * Message from client to server: a player is ready to play
 */
public class CTSReadyToPlay implements MessageToServer {

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().readyToPlay(clientSkeleton.getNickname());
    }
}
