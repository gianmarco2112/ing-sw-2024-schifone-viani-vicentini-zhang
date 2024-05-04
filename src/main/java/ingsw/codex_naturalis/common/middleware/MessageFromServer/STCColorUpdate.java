package ingsw.codex_naturalis.common.middleware.MessageFromServer;

import ingsw.codex_naturalis.common.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class STCColorUpdate implements MessageFromServer {

    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            String jsonColor = reader.readLine();
            client.stcUpdateSetupUIColor(jsonColor);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }

}
