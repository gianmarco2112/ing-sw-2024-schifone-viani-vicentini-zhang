package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSDrawCard implements MessageToServer {

    private String jsonDrawCardUpdate;

    public CTSDrawCard() {
    }

    public CTSDrawCard(String jsonDrawCardUpdate) {
        this.jsonDrawCardUpdate = jsonDrawCardUpdate;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().drawCard(clientSkeleton.getNickname(), jsonDrawCardUpdate);
    }

    public String getJsonDrawCardUpdate() {
        return jsonDrawCardUpdate;
    }

    public void setJsonDrawCardUpdate(String jsonDrawCardUpdate) {
        this.jsonDrawCardUpdate = jsonDrawCardUpdate;
    }
}
