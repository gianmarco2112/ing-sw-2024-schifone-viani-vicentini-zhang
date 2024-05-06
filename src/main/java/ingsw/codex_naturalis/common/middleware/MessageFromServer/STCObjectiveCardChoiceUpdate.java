package ingsw.codex_naturalis.common.middleware.MessageFromServer;

import ingsw.codex_naturalis.common.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class STCObjectiveCardChoiceUpdate implements MessageFromServer{
    @Override
    public void run(Client client, BufferedReader reader) {
        try {
            String jsonGame = reader.readLine();
            client.stcUpdateSetupUIObjectiveCardChoice(jsonGame);
        } catch (IOException e) {
            System.err.println("Error while processing json");
        }
    }
}
