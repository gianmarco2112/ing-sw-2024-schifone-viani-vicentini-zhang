package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Setup1Update implements MessageFromServer{

    @Override
    public void run(Client client, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonInitialCard = reader.readLine();
            PlayableCard.Immutable initialCard = objectMapper.readValue(jsonInitialCard, PlayableCard.Immutable.class);

            String jsonResourceCards = reader.readLine();
            List<PlayableCard.Immutable> resourceCards = objectMapper.readValue(jsonResourceCards, new TypeReference<List<PlayableCard.Immutable>>(){});

            String jsonGoldCards = reader.readLine();
            List<PlayableCard.Immutable> goldCards = objectMapper.readValue(jsonGoldCards, new TypeReference<List<PlayableCard.Immutable>>(){});

            client.updateSetup1(initialCard, resourceCards, goldCards);

        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
