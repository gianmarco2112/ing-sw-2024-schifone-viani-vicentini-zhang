package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class STCNicknameChosen implements MessageToClient {

    private String nickname;

    public STCNicknameChosen() {
    }

    public STCNicknameChosen(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().setNickname(nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
