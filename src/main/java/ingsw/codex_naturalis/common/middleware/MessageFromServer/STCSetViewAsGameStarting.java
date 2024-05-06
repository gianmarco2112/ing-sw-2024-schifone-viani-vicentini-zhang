package ingsw.codex_naturalis.common.middleware.MessageFromServer;

import ingsw.codex_naturalis.common.Client;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class STCSetViewAsGameStarting implements MessageFromServer{
    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            int gameID = parseInt(reader.readLine());
            client.setViewAsGameStarting(gameID);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
