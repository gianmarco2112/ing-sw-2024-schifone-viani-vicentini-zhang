package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
import ingsw.codex_naturalis.server.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSChooseNickname implements MessageToServer{

    private String nickname;

    public CTSChooseNickname() {}

    public CTSChooseNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        ServerImpl server = clientSkeleton.getServerImpl();
        server.chooseNickname(clientSkeleton, nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
