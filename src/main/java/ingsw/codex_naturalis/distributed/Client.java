package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.distributed.rmi.ServerImpl;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Remote {

    void updateGameUI(Game.Immutable o, Event arg, String playerWhoUpdated) throws RemoteException;

    void updateLobbyUI(List<ServerImpl.GameSpecs> gamesSpecs) throws RemoteException;

    void setNickname(String nickname) throws RemoteException;

    String getNickname() throws RemoteException;

    void updateView(GameStatus gameStatus, PlayersConnectedStatus playersConnectedStatus) throws RemoteException;

    void updatePlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus) throws RemoteException;

}
