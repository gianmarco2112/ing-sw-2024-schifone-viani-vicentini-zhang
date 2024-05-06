package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import ingsw.codex_naturalis.common.middleware.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSGameToAccessUpdate implements MessageFromClient {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            int gameID = parseInt(reader.readLine());
            String nickname = reader.readLine();
            ServerImpl server = clientSkeleton.getServerImpl();
            server.ctsUpdateGameToAccess(clientSkeleton, gameID, nickname);
        } catch (IOException e) {
            System.err.println("Error while receiving from client");
        }
    }
}
