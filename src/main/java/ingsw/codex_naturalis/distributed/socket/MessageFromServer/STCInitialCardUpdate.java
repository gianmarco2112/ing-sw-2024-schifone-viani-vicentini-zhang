package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.Game;

import java.io.BufferedReader;
import java.io.IOException;

public class STCInitialCardUpdate implements MessageFromServer {
    @Override
    public void run(Client client, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonGame = reader.readLine();
            String jsonInitialCardEvent = reader.readLine();
            client.stcUpdateInitialCard(jsonGame, jsonInitialCardEvent);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
