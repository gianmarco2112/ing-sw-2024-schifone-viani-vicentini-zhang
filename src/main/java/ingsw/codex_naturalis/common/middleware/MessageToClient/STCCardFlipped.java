package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: a card has been flipped
 */
public class STCCardFlipped implements MessageToClient {

    private String jsonImmGame;
    /**
     * STCCardFlipped's constructor
     */

    public STCCardFlipped() {
    }
    /**
     * STCCardFlipped's constructor
     * @param jsonImmGame : game where the card has been flipped
     */
    public STCCardFlipped(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().cardFlipped(jsonImmGame);
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
