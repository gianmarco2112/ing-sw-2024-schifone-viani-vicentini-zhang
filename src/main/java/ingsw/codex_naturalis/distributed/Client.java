package ingsw.codex_naturalis.distributed;


import ingsw.codex_naturalis.view.UI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    void updateUI(UI ui) throws RemoteException;

    void updateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportLobbyUIError(String error) throws RemoteException;

    void updateGameStartingUIGameID(int gameID) throws RemoteException;

}
