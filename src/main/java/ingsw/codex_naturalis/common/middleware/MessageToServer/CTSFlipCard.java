package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSFlipCard implements MessageToServer {

    private int index;

    public CTSFlipCard() {
    }

    public CTSFlipCard(int index) {
        this.index = index;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().flipCard(clientSkeleton.getNickname(), index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
