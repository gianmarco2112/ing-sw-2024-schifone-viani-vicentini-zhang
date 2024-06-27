package ingsw.codex_naturalis.common;

import ingsw.codex_naturalis.server.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.server.exceptions.NotYourTurnException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameController extends Remote {

    /**
     * Method called by a client to notify he is ready to play
     */
    void readyToPlay(String nickname) throws RemoteException;
    /**
     * Method called by a client to flip or play his initial card.
     * @param nickname client's nickname
     * @param jsonInitialCardEvent flip or play
     */
    void updateInitialCard(String nickname, String jsonInitialCardEvent) throws RemoteException;
    /**
     * Method called by a client to choose his color.
     * @param nickname client's nickname
     * @param jsonColor color
     */
    void chooseColor(String nickname, String jsonColor) throws RemoteException;
    /**
     * Method called by a client to choose his secret objective card.
     * @param nickname client's nickname
     * @param index objective card choice (index)
     */
    void chooseSecretObjectiveCard(String nickname, int index) throws RemoteException;

    /**
     * Method called by a client to flip his hand card.
     * @param nickname client's nickname
     * @param index index of the hand card
     */
    void flipCard(String nickname, int index) throws RemoteException;
    /**
     * Method called by a client to play his hand card.
     * @param nickname client's nickname
     * @param index index of the hand card
     * @param x coordinate x of his play area
     * @param y coordinate y of his play area
     */
    void playCard(String nickname, int index, int x, int y) throws RemoteException;
    /**
     * Method called by a client to draw a card.
     * @param nickname client's nickname
     * @param jsonDrawCardEvent card to draw
     * @throws NotYourTurnException when it's not his turn
     * @throws NotYourDrawTurnStatusException when he has to play a card first
     */
    void drawCard(String nickname, String jsonDrawCardEvent) throws RemoteException;
    /**
     * Method called by a client to send a message in the game's chat
     * @param nickname client's nickname sender
     * @param receiver receiver (null if it's a global message)
     * @param content message content
     */
    void sendMessage(String nickname, String receiver, String content) throws RemoteException;

}
