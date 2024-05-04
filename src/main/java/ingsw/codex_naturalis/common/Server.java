package ingsw.codex_naturalis.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    void register(Client client) throws RemoteException;


    void ctsUpdateGameToAccess(Client client, int gameID, String nickname) throws RemoteException;
    void ctsUpdateNewGame(Client client, int numOfPlayers, String nickname) throws RemoteException;

    GameController getGameController(Client client) throws RemoteException;

}
