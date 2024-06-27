package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: a player is ready
 */
public class STCPlayerIsReady implements MessageToClient {

    private String playerNickname;
    /**
     * STCPlayerIsReady's constructor
     */
    public STCPlayerIsReady() {
    }
    /**
     * STCPlayerIsReady's constructor
     * @param playerNickname : nickname chosen by the player
     */
    public STCPlayerIsReady(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().playerIsReady(playerNickname);
    }
    /**
     * PlayerNickname's getter
     * @return the nickname of the player who is ready
     */
    public String getPlayerNickname() {
        return playerNickname;
    }
    /**
     * PlayerNickname's setter
     * @param playerNickname : the nickname of the player who is ready
     */
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }
}
