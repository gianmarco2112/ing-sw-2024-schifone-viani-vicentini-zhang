package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import ingsw.codex_naturalis.distributed.Client;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class STCGameStartingUIGameIDUpdate implements MessageFromServer{
    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            int gameID = parseInt(reader.readLine());
            client.stcUpdateGameStartingUIGameID(gameID);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
