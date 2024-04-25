package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class STCSetupUpdate implements MessageFromServer{

    @Override
    public void run(Client client, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonImmGame = reader.readLine();

            String jsonGameEvent = reader.readLine();

            client.stcUpdateSetupUI(jsonImmGame, jsonGameEvent);

        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
