package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

public class STCGameEnded implements MessageToClient{
    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            String jsonPlayers = reader.readLine();
            serverStub.getClient().gameEnded(jsonPlayers);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
