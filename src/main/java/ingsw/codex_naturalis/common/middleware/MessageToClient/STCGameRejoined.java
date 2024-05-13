package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

public class STCGameRejoined implements MessageToClient{
    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            String jsonImmGame = reader.readLine();
            String nickname = reader.readLine();
            serverStub.getClient().gameRejoined(jsonImmGame, nickname);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
