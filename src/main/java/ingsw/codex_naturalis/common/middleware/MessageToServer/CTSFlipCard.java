package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class CTSFlipCard implements MessageToServer {

    private int index;
    /**
     * CTSFlipCard's constructor
     */
    public CTSFlipCard() {
    }
    /**
     * CTSFlipCard's constructor
     * @param index : the index of the card that the player wants to flip
     */
    public CTSFlipCard(int index) {
        this.index = index;
    }
    /**
     * To run the clientSkeleton and send the message to the server that a player wants to flip a card
     */
    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().flipCard(clientSkeleton.getNickname(), index);
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
}
