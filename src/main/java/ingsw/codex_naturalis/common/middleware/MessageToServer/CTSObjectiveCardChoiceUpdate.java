package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSObjectiveCardChoiceUpdate implements MessageToServer {
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        try {
            BufferedReader reader = clientSkeleton.getReader();
            int index = parseInt(reader.readLine());
            clientSkeleton.getGameControllerImpl().chooseSecretObjectiveCard(clientSkeleton.getNickname(), index);
        } catch (IOException e) {
            System.err.println("Error while receiving from client\n"+e.getMessage());
        }
    }
}
