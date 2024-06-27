package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSChooseColor implements MessageToServer {

    private String jsonColor;
    /**
     * CTSChooseColor's constructor
     */
    public CTSChooseColor() {
    }
    /**
     * CTSChooseColor's constructor
     * @param jsonColor : the color that the client wants to choose
     */
    public CTSChooseColor(String jsonColor) {
        this.jsonColor = jsonColor;
    }
    /**
     * To run the clientSkeleton and send the message to the server that a player wants to choose a color
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().chooseColor(clientSkeleton.getNickname(), jsonColor);
    }
    /**
     * JsonColor's getter
     * @return jsonColor
     */
    public String getJsonColor() {
        return jsonColor;
    }
    /**
     *JsonColor's setter
     * @param jsonColor to set
     */
    public void setJsonColor(String jsonColor) {
        this.jsonColor = jsonColor;
    }
}
