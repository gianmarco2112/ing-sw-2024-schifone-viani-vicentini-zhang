package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

public class STCUpdatePlayerInGameStatus implements MessageToClient{
    @Override
    public void run(ServerStub serverStub) {
        try {
            BufferedReader reader = serverStub.getReader();
            String jsonGame = reader.readLine();
            String playerNickname = reader.readLine();
            String inGame = reader.readLine();
            String hasDisconnected = reader.readLine();
            serverStub.getClient().updatePlayerInGameStatus(jsonGame, playerNickname, inGame, hasDisconnected);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
