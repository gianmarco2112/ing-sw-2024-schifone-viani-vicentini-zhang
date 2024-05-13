package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSDrawCard implements MessageToServer {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonDrawCardUpdate = reader.readLine();
            clientSkeleton.getGameControllerImpl().drawCard(clientSkeleton.getNickname(), jsonDrawCardUpdate);
        } catch (IOException e) {
            System.err.println("Error while receiving from client" + e.getMessage());
        }
    }
}
