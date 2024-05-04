package ingsw.codex_naturalis.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameController extends Remote {


    void updateReady() throws RemoteException;

    void updateInitialCard(String nickname, String jsonInitialCardEvent) throws RemoteException;

    void updateColor(String nickname, String jsonColor) throws RemoteException;

    void updateObjectiveCard(String nickname, String jsonObjectiveCardChoice) throws RemoteException;



    void updateFlipCard(String nickname, String jsonFlipCardEvent) throws RemoteException;

    void updatePlayCard(String nickname, String jsonPlayCardEvent, int x, int y) throws RemoteException;

    void updateDrawCard(String nickname, String jsonDrawCardEvent) throws RemoteException;

    void updateSendMessage(String nickname, String receiver, String content) throws RemoteException;

}
