package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;

import java.io.BufferedReader;
import java.io.IOException;

public class InitialCardFSUpdate implements MessageFromServer {
    @Override
    public void run(Client client, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonInitialCard = reader.readLine();
            PlayableCard.Immutable initialCard = objectMapper.readValue(jsonInitialCard, PlayableCard.Immutable.class);
            String jsonInitialCardEvent = reader.readLine();
            InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
            client.updateInitialCardFS(initialCard, initialCardEvent);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
