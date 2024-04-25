package ingsw.codex_naturalis.distributed;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    void stcUpdateUI(String jsonUI) throws RemoteException;


    void stcUpdateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportLobbyUIError(String error) throws RemoteException;


    void stcUpdateGameStartingUIGameID(int gameID) throws RemoteException;


    void stcUpdateSetupUIInitialCard(String jsonImmGame, String jsonInitialCardEvent) throws RemoteException;
    void stcUpdateSetupUIColor(String jsonColor) throws RemoteException;
    void reportSetupUIError(String message) throws RemoteException;
    void stcUpdateSetupUI(String jsonImmGame, String jsonGameEvent) throws RemoteException;
    void stcUpdateSetupUIObjectiveCardChoice(String jsonImmGame) throws RemoteException;


    void stcUpdateGameplayUIPlayerOrder(String jsonImmGame) throws RemoteException;
}
