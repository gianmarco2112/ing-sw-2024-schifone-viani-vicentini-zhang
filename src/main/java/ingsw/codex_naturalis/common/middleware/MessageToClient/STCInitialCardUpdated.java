package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the initialCard has been updated
 */

public class STCInitialCardUpdated implements MessageToClient {

    private String jsonImmGame;
    private String jsonInitialCardEvent;
    /**
     * STCInitialCardUpdated's constructor
     */
    public STCInitialCardUpdated() {
    }
    /**
     * STCInitialCardUpdated's constructor
     * @param jsonImmGame : game where the initialCard has been updated
     */
    public STCInitialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) {
        this.jsonImmGame = jsonImmGame;
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().initialCardUpdated(jsonImmGame, jsonInitialCardEvent);
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
     * JsonInitialCardEvent's getter
     * @return jsonCardEvent
     */
    public String getJsonInitialCardEvent() {
        return jsonInitialCardEvent;
    }
    /**
     * JsonInitialCardEvent's setter
     * @param jsonInitialCardEvent to set
     */
    public void setJsonInitialCardEvent(String jsonInitialCardEvent) {
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }
}
