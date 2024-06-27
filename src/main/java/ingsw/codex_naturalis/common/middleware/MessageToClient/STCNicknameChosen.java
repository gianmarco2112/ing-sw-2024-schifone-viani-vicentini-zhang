package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;
/**
 * Message from server to client: a nickname has been chosen
 */
public class STCNicknameChosen implements MessageToClient {

    private String nickname;
    /**
     * STCNicknameChosen's constructor
     */
    public STCNicknameChosen() {
    }
    /**
     * STCNicknameChosen's constructor
     * @param nickname : nickname chosen by the player
     */
    public STCNicknameChosen(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().setNickname(nickname);
    }
    /**
     * Nickname's getter
     * @return the chosen nickname
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * Nickname's setter
     * @param nickname : the nickname chosen by the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
