package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ServerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSGetGameController implements MessageToServer {
    /**
     * To run the clientSkeleton and send the message to the server that a client wants to get the game controller
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        clientSkeleton.setGameController(server.getGameControllerImpl(clientSkeleton));
    }
}
