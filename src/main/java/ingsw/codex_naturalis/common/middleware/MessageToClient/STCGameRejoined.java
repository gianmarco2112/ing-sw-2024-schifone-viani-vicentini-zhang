package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

public class STCGameRejoined implements MessageToClient {

    private String jsonImmGame;
    private String nickname;

    public STCGameRejoined() {
    }

    public STCGameRejoined(String jsonImmGame, String nickname) {
        this.jsonImmGame = jsonImmGame;
        this.nickname = nickname;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameRejoined(jsonImmGame, nickname);
    }

    public String getJsonImmGame() {
        return jsonImmGame;
    }

    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
