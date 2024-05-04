package ingsw.codex_naturalis.common.middleware.MessageFromServer;

import ingsw.codex_naturalis.common.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class STCSetViewAsGameplay implements MessageFromServer{
    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            String jsonImmGame = reader.readLine();
            client.setViewAsGameplay(jsonImmGame);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
