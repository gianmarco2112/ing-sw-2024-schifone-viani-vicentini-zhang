package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

/**
 *  Message from server to client: 20 points have been reached
 */

public class STCTwentyPointsReached implements MessageToClient {

    private String jsonImmGame;

    /**
     * STCTwentyPointsReached's constructor
     */
    public STCTwentyPointsReached() {
    }

    /**
     * STCTwentyPointsReached's constructor
     * @param jsonImmGame : the game where 20 points have been reached
     */
    public STCTwentyPointsReached(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().twentyPointsReached(jsonImmGame);
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
