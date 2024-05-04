package ingsw.codex_naturalis.common;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    String getNickname() throws RemoteException;
    void setNickname(String nickname) throws RemoteException;

    void setViewAsLobby(String jsonGamesSpecs) throws RemoteException;
    void stcUpdateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportLobbyUIError(String error) throws RemoteException;


    void setViewAsGameStarting(int gameID) throws RemoteException;


    void setViewAsSetup() throws RemoteException;
    void stcUpdateSetupUIInitialCard(String jsonImmGame, String jsonInitialCardEvent) throws RemoteException;
    void stcUpdateSetupUIColor(String jsonColor) throws RemoteException;
    void reportSetupUIError(String error) throws RemoteException;
    void stcUpdateSetupUI(String jsonImmGame, String jsonGameEvent) throws RemoteException;
    void stcUpdateSetupUIObjectiveCardChoice(String jsonImmGame) throws RemoteException;


    void setViewAsGameplay(String jsonImmGame) throws RemoteException;

    void stcUpdateGameplayUI(String jsonImmGame) throws RemoteException;

    void reportGameplayUIError(String error) throws RemoteException;

}
