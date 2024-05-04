package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSPlayCardUpdate implements MessageFromClient{
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonPlayCardUpdate = reader.readLine();
            int x = parseInt(reader.readLine());
            int y = parseInt(reader.readLine());
            GameControllerImpl gameControllerImpl = clientSkeleton.getGameControllerImpl();
            gameControllerImpl.updatePlayCard(clientSkeleton.getNickname(), jsonPlayCardUpdate, x, y);
        } catch (IOException e) {
            System.err.println("Error while processing json" + e.getMessage());
        }
    }
}
