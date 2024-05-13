package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

public class STCSetupUpdated implements MessageToClient {

    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            String jsonImmGame = reader.readLine();
            String jsonGameEvent = reader.readLine();
            serverStub.getClient().setupUpdated(jsonImmGame, jsonGameEvent);
        } catch (IOException e) {
            System.err.println("Error while receiving from server\n"+e.getMessage());
        }
    }
}
