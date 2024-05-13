package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSAccessNewGame implements MessageToServer {

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            int numOfPlayers = parseInt(reader.readLine());
            ServerImpl server = clientSkeleton.getServerImpl();
            server.accessNewGame(clientSkeleton, numOfPlayers);
        } catch (IOException e) {
            System.err.println("Error while receiving from client\n"+e.getMessage());
        }
    }

}
