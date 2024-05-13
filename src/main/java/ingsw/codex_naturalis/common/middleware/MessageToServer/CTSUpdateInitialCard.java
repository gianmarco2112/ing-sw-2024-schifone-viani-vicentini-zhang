package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSUpdateInitialCard implements MessageToServer {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonInitialCardEvent = reader.readLine();
            clientSkeleton.getGameControllerImpl().updateInitialCard(clientSkeleton.getNickname(), jsonInitialCardEvent);;
        } catch (IOException e) {
            System.err.println("Error while receiving from client" + e.getMessage());
        }
    }
}
