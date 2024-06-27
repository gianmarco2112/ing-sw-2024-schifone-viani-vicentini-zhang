package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the decks are empty
 */
public class STCDecksEmpty implements MessageToClient {

    private String jsonImmGame;
    /**
     * STCDecksEmpty's constructor
     */
    public STCDecksEmpty() {
    }
    /**
     * STCDecksEmpty's constructor
     * @param jsonImmGame : game where the decks are empty
     */
    public STCDecksEmpty(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().decksEmpty(jsonImmGame);
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
