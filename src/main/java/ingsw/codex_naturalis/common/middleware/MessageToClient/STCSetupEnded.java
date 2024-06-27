package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCSetupEnded implements MessageToClient {

    private String jsonImmGame;
    /**
     * STCSetupEnded's constructor
     */
    public STCSetupEnded() {
    }
    /**
     * STCSetupEnded's constructor
     * @param jsonImmGame : game where the setup has ended
     */
    public STCSetupEnded(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
    /**
     * To run the serverStub and send the message to the client that the setup has ended
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().setupEnded(jsonImmGame);
    }
    /**
     * JsonImmGame's getter
     * @return jsonImmGame
     */
    public String getJsonImmGame() {
        return jsonImmGame;
    }
    /**
     * JsonImmGame's setter
     * @param jsonImmGame to set
     */
    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
}
