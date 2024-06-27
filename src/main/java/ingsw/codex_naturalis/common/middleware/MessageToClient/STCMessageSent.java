package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCMessageSent implements MessageToClient {

    private String jsonImmGame;
    /**
     * STCMessageSent's constructor
     */
    public STCMessageSent() {
    }
    /**
     * STCMessageSent's constructor
     * @param jsonImmGame : game where the message has been sent
     */
    public STCMessageSent(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
    /**
     * To run the serverStub and send the message to the client that a message has been sent
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().messageSent(jsonImmGame);
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
