package ingsw.codex_naturalis.distributed;


import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.view.UI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    void stcUpdateUI(UI ui) throws RemoteException;

    void stcUpdateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportLobbyUIError(String error) throws RemoteException;

    void stcUpdateGameStartingUIGameID(int gameID) throws RemoteException;

    void stcUpdateInitialCard(Game.Immutable game, InitialCardEvent initialCardEvent) throws RemoteException;

    void stcUpdateColor(Color color) throws RemoteException;

    void reportSetupUIError(String message) throws RemoteException;

    void stcUpdate(Game.Immutable immGame, GameEvent gameEvent) throws RemoteException;
}
