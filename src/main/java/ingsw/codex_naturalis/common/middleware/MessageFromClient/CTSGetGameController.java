package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.server.ServerImpl;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

public class CTSGetGameController implements MessageFromClient{
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        clientSkeleton.setGameController(server.getClientToGame().get(clientSkeleton));
    }
}
