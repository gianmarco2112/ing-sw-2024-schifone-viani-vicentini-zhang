package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 *  Message from server to client: the game's setup has ended
 */
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
