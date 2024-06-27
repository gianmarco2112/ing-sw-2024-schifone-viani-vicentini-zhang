package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCColorUpdated implements MessageToClient {

    private String jsonColor;
    /**
     * STCColorUpdated's constructor
     */
    public STCColorUpdated() {
    }
    /**
     * STCColorUpdated's constructor
     * @param jsonColor : the color that has been updated
     */
    public STCColorUpdated(String jsonColor) {
        this.jsonColor = jsonColor;
    }
    /**
     * To run the serverStub and send the message to the client that the color has been updated
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().colorUpdated(jsonColor);
    }
    /**
     * JsonColor's getter
     * @return jsonColor
     */
    public String getJsonColor() {
        return jsonColor;
    }
    /**
     * JsonColor's setter
     * @param jsonColor to set
     */
    public void setJsonColor(String jsonColor) {
        this.jsonColor = jsonColor;
    }
}
