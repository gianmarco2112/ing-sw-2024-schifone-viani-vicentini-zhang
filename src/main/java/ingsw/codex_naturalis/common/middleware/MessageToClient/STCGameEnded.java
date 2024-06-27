package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

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
    /**
     * To run the serverStub and send the message to the client that the game has ended
     */
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
