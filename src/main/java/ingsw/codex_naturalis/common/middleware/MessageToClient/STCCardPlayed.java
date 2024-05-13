package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

public class STCCardPlayed implements MessageToClient {
    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            String jsonGame = reader.readLine();
            String playerNicknameWhoUpdated = reader.readLine();
            serverStub.getClient().cardPlayed(jsonGame, playerNicknameWhoUpdated);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
