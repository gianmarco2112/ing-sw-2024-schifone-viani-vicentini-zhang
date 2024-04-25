package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {

    void register(Client client) throws RemoteException;


    void ctsUpdateGameToAccess(Client client, int gameID, String nickname) throws RemoteException;
    void ctsUpdateNewGame(Client client, int numOfPlayers, String nickname) throws RemoteException;


    void ctsUpdateReady(Client client) throws RemoteException;

    void ctsUpdateInitialCard(Client client, String jsonInitialCardEvent) throws RemoteException;

    void ctsUpdateColor(Client client, String jsonColor) throws RemoteException;


    void ctsUpdateFlipCard(Client client, FlipCard flipCard) throws RemoteException;
    void ctsUpdatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException, RemoteException;
    void ctsUpdateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException, RemoteException;
    void ctsUpdateText(Client client, Message message, String content, List<String> receivers) throws RemoteException;

}
