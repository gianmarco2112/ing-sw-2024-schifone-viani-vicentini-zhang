package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSAccessExistingGame implements MessageToServer {

    private int gameID;
    /**
     * CTSAccessExistingGame's constructor
     */
    public CTSAccessExistingGame() {
    }
    /**
     * CTSAccessExistingGame's constructor
     * @param gameID : ID of the game to access
     */
    public CTSAccessExistingGame(int gameID) {
        this.gameID = gameID;
    }
    /**
     * To run the clientSkeleton and send the message to the server that a player wants to access to an existing game
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        server.accessExistingGame(clientSkeleton, gameID);
    }
    /**
     * GameID's getter
     * @return gameID
     */
    public int getGameID() {
        return gameID;
    }
    /**
     * GameID's setter
     * @param gameID to set
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
