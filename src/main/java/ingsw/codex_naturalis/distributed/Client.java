package ingsw.codex_naturalis.distributed;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    void stcUpdateUI(String jsonUI) throws RemoteException;

    void stcUpdateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportLobbyUIError(String error) throws RemoteException;

    void stcUpdateGameStartingUIGameID(int gameID) throws RemoteException;

    void stcUpdateInitialCard(String jsonImmGame, String jsonInitialCardEvent) throws RemoteException;

    void stcUpdateColor(String jsonColor) throws RemoteException;

    void reportSetupUIError(String message) throws RemoteException;

    void stcUpdate(String jsonImmGame, String jsonGameEvent) throws RemoteException;
}
