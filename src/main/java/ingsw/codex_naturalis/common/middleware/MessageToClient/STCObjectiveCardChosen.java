package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCObjectiveCardChosen implements MessageToClient {

    private String jsonImmGame;

    public STCObjectiveCardChosen() {
    }

    public STCObjectiveCardChosen(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().objectiveCardChosen(jsonImmGame);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
}
