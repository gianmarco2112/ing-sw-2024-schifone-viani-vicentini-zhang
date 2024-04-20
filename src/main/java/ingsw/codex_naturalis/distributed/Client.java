package ingsw.codex_naturalis.distributed;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    void updateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportError(String error) throws RemoteException;

    void updateUItoGameStarting(int gameID, String nickname) throws RemoteException;

    void updateUItoSetup() throws RemoteException;





    String getNickname() throws RemoteException;


}
