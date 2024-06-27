package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the game has ended
 */
public class STCGameEnded implements MessageToClient {

    private String jsonPlayers;
    /**
     * STCGameEnded's constructor
     */
    public STCGameEnded() {
    }
    /**
     * STCGameEnded's constructor
     * @param jsonPlayers : players of the ended game
     */
    public STCGameEnded(String jsonPlayers) {
        this.jsonPlayers = jsonPlayers;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameEnded(jsonPlayers);
    }
    /**
     * JsonPlayers's getter
     * @return jsonPlayers
     */
    public String getJsonPlayers() {
        return jsonPlayers;
    }
    /**
     * JsonPlayerse's setter
     * @param jsonPlayers to set
     */
    public void setJsonPlayers(String jsonPlayers) {
        this.jsonPlayers = jsonPlayers;
    }
}
