package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCSetupUpdated implements MessageToClient {

    private String jsonImmGame;
    private String jsonGameEvent;
    /**
     * STCSetupUpdated's constructor
     */
    public STCSetupUpdated() {
    }
    /**
     * STCSetupUpdated's constructor
     * @param jsonImmGame : game where the setup has been updated
     */
    public STCSetupUpdated(String jsonImmGame, String jsonGameEvent) {
        this.jsonImmGame = jsonImmGame;
        this.jsonGameEvent = jsonGameEvent;
    }
    /**
     * To run the serverStub and send the message to the client that the setup has been updated
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().setupUpdated(jsonImmGame, jsonGameEvent);
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
    /**
     * JsonGameEvent's getter
     * @return jsonGameEvent
     */
    public String getJsonGameEvent() {
        return jsonGameEvent;
    }
    /**
     * JsonGameEvent's setter
     * @param jsonGameEvent to set
     */
    public void setJsonGameEvent(String jsonGameEvent) {
        this.jsonGameEvent = jsonGameEvent;
    }
}
