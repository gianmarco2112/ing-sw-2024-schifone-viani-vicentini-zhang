package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSChooseColor implements MessageToServer {

    private String jsonColor;

    public CTSChooseColor() {
    }

    public CTSChooseColor(String jsonColor) {
        this.jsonColor = jsonColor;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().chooseColor(clientSkeleton.getNickname(), jsonColor);
    }

    public String getJsonColor() {
        return jsonColor;
    }

    public void setJsonColor(String jsonColor) {
        this.jsonColor = jsonColor;
    }
}
