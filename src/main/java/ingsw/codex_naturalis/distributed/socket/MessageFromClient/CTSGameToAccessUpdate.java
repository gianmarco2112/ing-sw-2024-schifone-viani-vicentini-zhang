package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSGameToAccessUpdate implements MessageFromClient {
    @Override
    public void run(Client client, Server server, BufferedReader reader) {
        try {
            int gameID = parseInt(reader.readLine());
            String nickname = reader.readLine();
            server.ctsUpdateGameToAccess(client, gameID, nickname);
        } catch (IOException e) {
            System.err.println("Error while receiving from client");
        }
    }
}
