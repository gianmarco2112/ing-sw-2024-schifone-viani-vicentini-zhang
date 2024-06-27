package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: game's specs has been updated
 */
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
