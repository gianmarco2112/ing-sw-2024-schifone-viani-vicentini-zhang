package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.enumerations.Color;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSColorUpdate implements MessageFromClient{

    @Override
    public void run(Client client, Server server, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonColor = reader.readLine();
            Color color = objectMapper.readValue(jsonColor, Color.class);
            server.ctsUpdateColor(client, color);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
