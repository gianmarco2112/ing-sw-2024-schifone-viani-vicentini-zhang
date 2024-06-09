package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

public class CTSPlayCard implements MessageToServer {

    private int index;
    private int x;
    private int y;

    public CTSPlayCard() {
    }

    public CTSPlayCard(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().playCard(clientSkeleton.getNickname(), index, x, y);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
