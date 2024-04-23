package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;

import java.io.BufferedReader;
import java.rmi.RemoteException;

public class ReadyUpdate implements MessageFromClient {
    @Override
    public void run(Client client, Server server, BufferedReader reader) {
        try {
            server.updateReady(client);
        } catch (RemoteException e) {
            System.err.println("Error while receiving from client");
        }
    }
}
