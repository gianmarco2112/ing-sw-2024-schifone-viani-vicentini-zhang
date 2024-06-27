package ingsw.codex_naturalis.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    /**
     * First method called by a client connected to the server, it adds the client to the loggedOutClients list
     * @param client client that has connected
     */
    void register(Client client) throws RemoteException;

    /**
     * Called by a client to set his nickname, it checks that the name is unique. It also checks
     * if there is a disconnected player with that nickname associated to a game, in this case, it
     * reconnects the client to the game.
     * @param client client caller
     * @param nickname nickname chosen
     */

    void chooseNickname(Client client, String nickname) throws RemoteException;
    /**
     * Method called from the client when he wants to join an existing game that hasn't started yet.
     * @param client caller
     * @param gameID game id of the game to join
     */

    void accessExistingGame(Client client, int gameID) throws RemoteException;
    /**
     * Method called by a client when he wants to create a new game
     * @param client client caller
     * @param numOfPlayers number of players to set for the game
     */
    void accessNewGame(Client client, int numOfPlayers) throws RemoteException;
    /**
     * Method called by an RMI client to get his game controller, it makes sure to export the
     * stub only once.
     * @param client client caller
     * @return the game controller stub
     * @throws RemoteException remote exception
     */
    GameController getGameController(Client client) throws RemoteException;
    /**
     * Method called by a client when he wants to leave a game, he won't be able to rejoin.
     * @param client client caller
     */
    void leaveGame(Client client) throws RemoteException;
    /**
     * Part of the resilience, method called every 5 seconds from the client.
     * In every call, it sets a new timeout of 10 seconds on that client.
     * @param client client caller
     */
    void imAlive(Client client) throws RemoteException;

}
