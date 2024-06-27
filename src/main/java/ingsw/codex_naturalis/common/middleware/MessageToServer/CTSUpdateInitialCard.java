package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;
/**
 * Message from client to server: a client wants to update the initial card
 */
public class CTSUpdateInitialCard implements MessageToServer {

    private String jsonInitialCardEvent;
    /**
     * CTSUpdateInitialCard's constructor
     */
    public CTSUpdateInitialCard() {}
    /**
     * CTSUpdateInitialCard's constructor
     * @param jsonInitialCardEvent : initialCardEvent
     */
    public CTSUpdateInitialCard(String jsonInitialCardEvent) {
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().updateInitialCard(clientSkeleton.getNickname(), jsonInitialCardEvent);;
    }
    /**
     * JsonInitialCardEvent's getter
     * @return jsonInitialCardEvent
     */
    public String getJsonInitialCardEvent() {
        return jsonInitialCardEvent;
    }
    /**
     * JsonInitialCardEvent's setter
     * @param jsonInitialCardEvent to set
     */
    public void setJsonInitialCardEvent(String jsonInitialCardEvent) {
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }
}
