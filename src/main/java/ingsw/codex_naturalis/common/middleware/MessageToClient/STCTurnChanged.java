package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCTurnChanged implements MessageToClient {

    private String currentPlayer;
    /**
     * STCTurnChanged's constructor
     */
    public STCTurnChanged() {
    }
    /**
     * STCTurnChanged's constructor
     * @param currentPlayer : the new current player
     */
    public STCTurnChanged(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    /**
     * To run the serverStub and send the message to the client that the turn has changed
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().turnChanged(currentPlayer);
    }
    /**
     * CurrentPlayer's getter
     * @return currentPlayer
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    /**
     * CurrentPlayer's setter
     * @param currentPlayer to set
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
