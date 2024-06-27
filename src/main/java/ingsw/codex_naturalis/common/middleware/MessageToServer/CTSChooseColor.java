package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;
/**
 * Message from client to server: a player wants to choose a color
 */
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
