package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {

    void register(Client client) throws RemoteException;


    void updateGameToAccess(Client client, int gameID, String nickname) throws RemoteException;
    void updateNewGame(Client client, int numOfPlayers, String nickname) throws RemoteException;


    void updateReady(Client client) throws RemoteException;

    void updateInitialCard(Client client, InitialCardEvent initialCardEvent) throws RemoteException;

    void updateColor(Client client, Color color) throws RemoteException;


    void updateFlipCard(Client client, FlipCard flipCard) throws RemoteException;
    void updatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException, RemoteException;
    void updateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException, RemoteException;
    void updateText(Client client, Message message, String content, List<String> receivers) throws RemoteException;

}
