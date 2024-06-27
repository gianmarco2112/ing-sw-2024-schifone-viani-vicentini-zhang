package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: an objective card has been chosen
 */
public class STCObjectiveCardChosen implements MessageToClient {

    private String jsonImmGame;
    /**
     * STCObjectiveCardChosen's constructor
     */
    public STCObjectiveCardChosen() {
    }
    /**
     * STCObjectiveCardChosen's constructor
     * @param jsonImmGame : where the objective card has been chosen
     */
    public STCObjectiveCardChosen(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().objectiveCardChosen(jsonImmGame);
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
