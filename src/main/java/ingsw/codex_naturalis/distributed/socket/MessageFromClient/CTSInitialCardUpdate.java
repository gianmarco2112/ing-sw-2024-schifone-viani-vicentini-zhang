package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSInitialCardUpdate implements MessageFromClient {
    @Override
    public void run(Client client, Server server, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonInitialCardEvent = reader.readLine();
            InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
            server.ctsUpdateInitialCard(client, initialCardEvent);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
