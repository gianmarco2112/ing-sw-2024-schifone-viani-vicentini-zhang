package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

public class CTSReadyUpdate implements MessageFromClient {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        GameControllerImpl gameControllerImpl = clientSkeleton.getGameControllerImpl();
        gameControllerImpl.updateReady();
    }
}
