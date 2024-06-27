package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

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
    /**
     * To run the serverStub and send the message to the client that a nickname has been chosen
     */
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
