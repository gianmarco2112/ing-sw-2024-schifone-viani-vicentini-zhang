package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSDrawCardUpdate implements MessageFromClient{
    @Override
    public void run(Client client, Server server, BufferedReader reader) {
        try {
            String jsonDrawCardUpdate = reader.readLine();
            server.ctsUpdateDrawCard(client, jsonDrawCardUpdate);
        } catch (IOException e) {
            System.err.println("Error while processing json" + e.getMessage());
        }
    }
}
