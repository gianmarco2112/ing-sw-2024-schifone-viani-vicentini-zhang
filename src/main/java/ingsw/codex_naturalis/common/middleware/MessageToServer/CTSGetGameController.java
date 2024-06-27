package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ServerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;
/**
 * Message from client to server: a client wants to get the game controller of the game
 */
public class CTSGetGameController implements MessageToServer {

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        clientSkeleton.setGameController(server.getGameControllerImpl(clientSkeleton));
    }
}
