package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;


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
    /**
     * To run the serverStub and send the message to the client that the initialCard has been updated
     */
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
