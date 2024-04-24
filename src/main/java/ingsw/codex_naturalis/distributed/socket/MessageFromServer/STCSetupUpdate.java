package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.util.GameEvent;

import java.io.BufferedReader;
import java.io.IOException;

public class STCSetupUpdate implements MessageFromServer{

    @Override
    public void run(Client client, BufferedReader reader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonImmGame = reader.readLine();
            Game.Immutable immGame = objectMapper.readValue(jsonImmGame, Game.Immutable.class);

            String jsonGameEvent = reader.readLine();
            GameEvent gameEvent = objectMapper.readValue(jsonGameEvent, GameEvent.class);

            client.stcUpdate(immGame, gameEvent);

        } catch (IOException e) {
            System.err.println("Error while receiving from server");
        }
    }
}
