package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the turn has changed
 */
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
