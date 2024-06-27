package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.server.ClientSkeleton;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;
/**
 * Message from client to server: a player wants to choose an objective card
 */
public class CTSObjectiveCardChoiceUpdate implements MessageToServer {

    private int index;
    /**
     * CTSObjectiveCardChoiceUpdate's constructor
     */
    public CTSObjectiveCardChoiceUpdate() {
    }
    /**
     * CTSObjectiveCardChoiceUpdate's constructor
     * @param index : index of the objective card that the player wants to choose
     */
    public CTSObjectiveCardChoiceUpdate(int index) {
        this.index = index;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().chooseSecretObjectiveCard(clientSkeleton.getNickname(), index);
    }
    /**
     * Index's getter
     * @return index of the card
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
