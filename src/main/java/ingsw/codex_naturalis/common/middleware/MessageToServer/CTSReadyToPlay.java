package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSReadyToPlay implements MessageToServer {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().readyToPlay(clientSkeleton.getNickname());
    }
}
