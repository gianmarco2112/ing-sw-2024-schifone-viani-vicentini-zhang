package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

public class CTSUpdateInitialCard implements MessageToServer {

    private String jsonInitialCardEvent;

    public CTSUpdateInitialCard() {}

    public CTSUpdateInitialCard(String jsonInitialCardEvent) {
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().updateInitialCard(clientSkeleton.getNickname(), jsonInitialCardEvent);;
    }

    public String getJsonInitialCardEvent() {
        return jsonInitialCardEvent;
    }

    public void setJsonInitialCardEvent(String jsonInitialCardEvent) {
        this.jsonInitialCardEvent = jsonInitialCardEvent;
    }
}
