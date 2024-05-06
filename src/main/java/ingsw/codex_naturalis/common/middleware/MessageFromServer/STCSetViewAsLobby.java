package ingsw.codex_naturalis.common.middleware.MessageFromServer;

import ingsw.codex_naturalis.common.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class STCSetViewAsLobby implements MessageFromServer{
    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            String jsonGamesSpecs = reader.readLine();
            client.setViewAsLobby(jsonGamesSpecs);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
