package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCUpdatePlayerInGameStatus implements MessageToClient {

    private String jsonImmGame;
    private String playerNickname;
    private String jsonInGame;
    private String jsonHasDisconnected;

    public STCUpdatePlayerInGameStatus() {
    }

    public STCUpdatePlayerInGameStatus(String jsonImmGame, String playerNickname, String jsonInGame, String jsonHasDisconnected) {
        this.jsonImmGame = jsonImmGame;
        this.playerNickname = playerNickname;
        this.jsonInGame = jsonInGame;
        this.jsonHasDisconnected = jsonHasDisconnected;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().updatePlayerInGameStatus(jsonImmGame, playerNickname, jsonInGame, jsonHasDisconnected);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getJsonInGame() {
        return jsonInGame;
    }

    public void setJsonInGame(String jsonInGame) {
        this.jsonInGame = jsonInGame;
    }

    public String getJsonHasDisconnected() {
        return jsonHasDisconnected;
    }

    public void setJsonHasDisconnected(String jsonHasDisconnected) {
        this.jsonHasDisconnected = jsonHasDisconnected;
    }
}
