package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the game has been joined
 */
public class STCGameJoined implements MessageToClient {

    private int gameID;
    /**
     * STCGameJoined's constructor
     */
    public STCGameJoined() {
    }
    /**
     * STCGameJoined's constructor
     * @param gameID : of the game
     */
    public STCGameJoined(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameJoined(gameID);
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
