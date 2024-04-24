package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import ingsw.codex_naturalis.distributed.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class STCSetupUIErrorReport implements MessageFromServer{

    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            String error = reader.readLine();
            client.reportSetupUIError(error);
        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }

}
