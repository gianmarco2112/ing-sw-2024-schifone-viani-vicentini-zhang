package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSSendMessage implements MessageToServer {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            String receiver = reader.readLine();
            String content = reader.readLine();
            clientSkeleton.getGameControllerImpl().sendMessage(clientSkeleton.getNickname(), receiver, content);
        } catch (IOException e) {
            System.err.println("Error while receiving from client");
        }
    }
}
