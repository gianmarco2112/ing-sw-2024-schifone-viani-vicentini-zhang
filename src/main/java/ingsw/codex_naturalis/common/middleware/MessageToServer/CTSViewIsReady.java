package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.GameControllerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

public class CTSViewIsReady implements MessageToServer {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getServerImpl().viewIsReady(clientSkeleton);
    }
}
