package ingsw.codex_naturalis.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    void register(Client client) throws RemoteException;


    void chooseNickname(Client client, String nickname) throws RemoteException;

    void viewIsReady(Client client) throws RemoteException;


    void accessExistingGame(Client client, int gameID) throws RemoteException;
    void accessNewGame(Client client, int numOfPlayers) throws RemoteException;

    GameController getGameController(Client client) throws RemoteException;

    void leaveGame(Client client, boolean hasDisconnected) throws RemoteException;

    void imAlive(Client client) throws RemoteException;

}
