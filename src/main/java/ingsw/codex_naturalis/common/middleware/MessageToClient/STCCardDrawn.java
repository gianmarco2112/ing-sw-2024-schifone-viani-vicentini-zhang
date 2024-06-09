package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCCardDrawn implements MessageToClient {

    private String jsonImmGame;
    private String playerNicknameWhoUpdated;

    public STCCardDrawn() {
    }

    public STCCardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) {
        this.jsonImmGame = jsonImmGame;
        this.playerNicknameWhoUpdated = playerNicknameWhoUpdated;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().cardDrawn(jsonImmGame, playerNicknameWhoUpdated);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    public String getPlayerNicknameWhoUpdated() {
        return playerNicknameWhoUpdated;
    }

    public void setPlayerNicknameWhoUpdated(String playerNicknameWhoUpdated) {
        this.playerNicknameWhoUpdated = playerNicknameWhoUpdated;
    }
}
