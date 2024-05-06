package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSInitialCardUpdate implements MessageFromClient {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonInitialCardEvent = reader.readLine();
            GameControllerImpl gameControllerImpl = clientSkeleton.getGameControllerImpl();
            gameControllerImpl.updateInitialCard(clientSkeleton.getNickname(), jsonInitialCardEvent);
        } catch (IOException e) {
            System.err.println("Error while processing json" + e.getMessage());
        }
    }
}
