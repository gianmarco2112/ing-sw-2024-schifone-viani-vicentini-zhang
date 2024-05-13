package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSChooseColor implements MessageToServer {

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String jsonColor = reader.readLine();
            clientSkeleton.getGameControllerImpl().chooseColor(clientSkeleton.getNickname(), jsonColor);
        } catch (IOException e) {
            System.err.println("Error while receiving from client\n"+e.getMessage());
        }
    }
}
