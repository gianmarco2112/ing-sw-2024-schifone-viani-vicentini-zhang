package ingsw.codex_naturalis.distributed;


import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.UI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Remote {

    void updateUI(UI ui) throws RemoteException;

    void updateLobbyUIGameSpecs(String jsonGamesSpecs) throws RemoteException;
    void reportLobbyUIError(String error) throws RemoteException;

    void updateGameStartingUIGameID(int gameID) throws RemoteException;

    void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards) throws RemoteException;

}
