package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSObjectiveCardChoiceUpdate implements MessageFromClient{
    @Override
    public void run(Client client, Server server, BufferedReader reader) {
        try {
            String jsonObjectiveCardChoice = reader.readLine();
            server.ctsUpdateObjectiveCardChoice(client, jsonObjectiveCardChoice);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
