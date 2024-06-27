package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCGamesSpecsUpdated implements MessageToClient {

    private String jsonGamesSpecs;
    /**
     * STCGamesSpecsUpdated's constructor
     */
    public STCGamesSpecsUpdated() {
    }
    /**
     * STCGameSpecsUpdated's constructor
     * @param jsonGamesSpecs : the updated game's specs
     */
    public STCGamesSpecsUpdated(String jsonGamesSpecs) {
        this.jsonGamesSpecs = jsonGamesSpecs;
    }
    /**
     * To run the serverStub and send the message to the client that the game's specs has been updated
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().updateGamesSpecs(jsonGamesSpecs);
    }
    /**
     * JsonGamesSpecs's getter
     * @return jsonGamesSpecs
     */
    public String getJsonGamesSpecs() {
        return jsonGamesSpecs;
    }
    /**
     * JsonGameSpecse's setter
     * @param jsonGamesSpecs to set
     */
    public void setJsonGamesSpecs(String jsonGamesSpecs) {
        this.jsonGamesSpecs = jsonGamesSpecs;
    }
}
