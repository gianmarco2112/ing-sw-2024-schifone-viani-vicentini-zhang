package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCTurnChanged implements MessageToClient {

    private String currentPlayer;

    public STCTurnChanged() {
    }

    public STCTurnChanged(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().turnChanged(currentPlayer);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
