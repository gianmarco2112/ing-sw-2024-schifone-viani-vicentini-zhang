package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class STCGameJoined implements MessageToClient {
    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            int gameID = parseInt(reader.readLine());
            serverStub.getClient().gameJoined(gameID);
        } catch (IOException e) {
            System.err.println("Error while receiving from server\n"+e.getMessage());
        }
    }
}
