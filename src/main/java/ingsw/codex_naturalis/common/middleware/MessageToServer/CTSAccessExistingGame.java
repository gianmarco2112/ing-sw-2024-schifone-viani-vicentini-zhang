package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSAccessExistingGame implements MessageToServer {

    private int gameID;

    public CTSAccessExistingGame() {
    }

    public CTSAccessExistingGame(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        server.accessExistingGame(clientSkeleton, gameID);
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
