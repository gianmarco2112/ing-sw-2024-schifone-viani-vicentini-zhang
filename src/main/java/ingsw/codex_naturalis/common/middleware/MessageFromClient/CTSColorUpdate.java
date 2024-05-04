package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSColorUpdate implements MessageFromClient{

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonColor = reader.readLine();
            GameControllerImpl gameControllerImpl = clientSkeleton.getGameControllerImpl();
            gameControllerImpl.updateColor(clientSkeleton.getNickname(), jsonColor);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
