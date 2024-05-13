package ingsw.codex_naturalis.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameController extends Remote {


    void readyToPlay() throws RemoteException;

    void updateInitialCard(String nickname, String jsonInitialCardEvent) throws RemoteException;

    void chooseColor(String nickname, String jsonColor) throws RemoteException;

    void chooseSecretObjectiveCard(String nickname, int index) throws RemoteException;



    void flipCard(String nickname, int index) throws RemoteException;

    void playCard(String nickname, int index, int x, int y) throws RemoteException;

    void drawCard(String nickname, String jsonDrawCardEvent) throws RemoteException;

    void sendMessage(String nickname, String receiver, String content) throws RemoteException;

}
