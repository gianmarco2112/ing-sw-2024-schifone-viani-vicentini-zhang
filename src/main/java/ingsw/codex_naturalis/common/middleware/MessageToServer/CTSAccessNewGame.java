package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSAccessNewGame implements MessageToServer {

    private int numOfPlayers;

    public CTSAccessNewGame() {
    }

    public CTSAccessNewGame(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        server.accessNewGame(clientSkeleton, numOfPlayers);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }
}
