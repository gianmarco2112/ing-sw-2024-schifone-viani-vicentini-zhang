package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
/**
 * Message from client to server: a player wants to play a card on the specified coordinates
 */
public class CTSPlayCard implements MessageToServer {

    private int index;
    private int x;
    private int y;
    /**
     * CTSPlayCard's constructor
     */
    public CTSPlayCard() {
    }
    /**
     * CTSPlayCard's constructor
     * @param index : of the card that the player wants to play
     * @param x : x coordinate where the player wants to play the card
     * @param y : y coordinate where the player wants to play the card
     */
    public CTSPlayCard(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().playCard(clientSkeleton.getNickname(), index, x, y);
    }
    /**
     * Index's getter
     * @return index
     */
    public int getIndex() {
        return index;
    }
    /**
     * Index's setter
     * @param index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }
    /**
     * X's getter
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * X's setter
     * @param x coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Y's getter
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
    /**
     * Y's setter
     * @param y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }
}
