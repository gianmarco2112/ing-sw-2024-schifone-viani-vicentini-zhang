package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCUpdatePlayerInGameStatus implements MessageToClient {

    private String jsonImmGame;
    private String playerNickname;
    private String jsonInGame;
    private String jsonHasDisconnected;
    /**
     * STCUpdatedPlayerInGameStatus's constructor
     */
    public STCUpdatePlayerInGameStatus() {
    }
    /**
     * STCUpdatedPlayerInGameStatus's constructor
     * @param jsonImmGame : game where a player's inGame status has changed
     * @param playerNickname : nickname of the player who updated his inGame status
     */
    public STCUpdatePlayerInGameStatus(String jsonImmGame, String playerNickname, String jsonInGame, String jsonHasDisconnected) {
        this.jsonImmGame = jsonImmGame;
        this.playerNickname = playerNickname;
        this.jsonInGame = jsonInGame;
        this.jsonHasDisconnected = jsonHasDisconnected;
    }
    /**
     * To run the serverStub and send the message to the client that a player has changed his inGame status
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().updatePlayerInGameStatus(jsonImmGame, playerNickname, jsonInGame, jsonHasDisconnected);
    }
    /**
     * JsonImmGame's getter
     * @return jsonImmGame
     */
    public String getJsonImmGame() {
        return jsonImmGame;
    }
    /**
     * JsonImmGame's setter
     * @param jsonImmGame to set
     */
    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
    /**
     * PlayerNickname's getter
     * @return playerNickname of the player who updated his inGame status
     */
    public String getPlayerNickname() {
        return playerNickname;
    }
    /**
     * PlayerNickname's setter
     * @param playerNickname to set
     */
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }
    /**
     * JsonInGame's getter
     * @return jsonInGame
     */
    public String getJsonInGame() {
        return jsonInGame;
    }
    /**
     * JsonInGame's setter
     * @param jsonInGame to set
     */
    public void setJsonInGame(String jsonInGame) {
        this.jsonInGame = jsonInGame;
    }
    /**
     * JsonHasDisconnected's getter
     * @return jsonHasDisconnected
     */
    public String getJsonHasDisconnected() {
        return jsonHasDisconnected;
    }
    /**
     * JsonHasDisconnected's setter
     * @param jsonHasDisconnected to set
     */
    public void setJsonHasDisconnected(String jsonHasDisconnected) {
        this.jsonHasDisconnected = jsonHasDisconnected;
    }
}
