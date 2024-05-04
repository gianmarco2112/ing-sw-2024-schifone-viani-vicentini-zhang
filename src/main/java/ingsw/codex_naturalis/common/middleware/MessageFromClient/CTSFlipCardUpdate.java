package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSFlipCardUpdate implements MessageFromClient{
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonFlipCardUpdate = reader.readLine();
            GameControllerImpl gameControllerImpl = clientSkeleton.getGameControllerImpl();
            gameControllerImpl.updateFlipCard(clientSkeleton.getNickname(), jsonFlipCardUpdate);
        } catch (IOException e) {
            System.err.println("Error while processing json" + e.getMessage());
        }
    }
}
