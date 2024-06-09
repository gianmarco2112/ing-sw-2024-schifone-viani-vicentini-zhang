package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCColorUpdated implements MessageToClient {

    private String jsonColor;

    public STCColorUpdated() {
    }

    public STCColorUpdated(String jsonColor) {
        this.jsonColor = jsonColor;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().colorUpdated(jsonColor);
    }

    public String getJsonColor() {
        return jsonColor;
    }

    public void setJsonColor(String jsonColor) {
        this.jsonColor = jsonColor;
    }
}
