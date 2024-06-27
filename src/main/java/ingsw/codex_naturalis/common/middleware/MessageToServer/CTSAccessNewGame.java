package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;
/**
 * Message from client to server: a player wants to access to a new game
 */
public class CTSAccessNewGame implements MessageToServer {

    private int numOfPlayers;
    /**
     * CTSAccessNewGame's constructor
     */
    public CTSAccessNewGame() {
    }
    /**
     * CTSAccessNewGame's constructor
     * @param numOfPlayers : numOfPlayer of the game to create
     */
    public CTSAccessNewGame(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        server.accessNewGame(clientSkeleton, numOfPlayers);
    }
    /**
     * NumOfPlayers's getter
     * @return numOfPlayers
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    /**
     * NumOfPlayers's setter
     * @param numOfPlayers to set
     */
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }
}
