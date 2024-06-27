package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;
/**
 *  Message from client to server: a player wants to choose a nickname
 */
public class CTSChooseNickname implements MessageToServer{

    private String nickname;
    /**
     * CTSChooseNickname's constructor
     */
    public CTSChooseNickname() {}
    /**
     * CTSChooseNickname's constructor
     * @param nickname : nickname that the player wants to choose
     */
    public CTSChooseNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        server.chooseNickname(clientSkeleton, nickname);
    }
    /**
     * Nickname's getter
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * Nickname's setter
     * @param nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
