package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SetupController {

    private final Game model;
    private final List<Client> views;
    private int readyPlayers = 0;

    //--------------------------------------------------------------------------------------
    public SetupController(Game model, List<Client> views) {
        this.model = model;
        this.views = views;
    }


    public Game getModel() {
        return model;
    }


    public synchronized void updateReady(String nickname){
        readyPlayers++;
        if (readyPlayers == model.getNumOfPlayers()) {
            model.setupResourceAndGoldCards();
            model.dealInitialCards();
            readyPlayers = 0;
        }
    }

    public void updateInitialCard(String nickname, InitialCardEvent initialCardEvent) {
        Player player = model.getPlayerByNickname(nickname);
        PlayableCard initialCard = player.getInitialCard();
        switch (initialCardEvent) {
            case FLIP -> player.flip(initialCard);
            case PLAY -> player.playInitialCard();
        }

    }





    public void updateColor(Client client, Color color) {
        /*readyClients++;
        Player player = null;
        try {
            player = getPlayerByNickname(client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }
        model.setColorToPlayer(color, player);
        if (readyClients == model.getNumOfPlayers()) {
            model.setupHands();
            model.setupCommonObjectiveCards();
            readyClients = 0;
        }*/
    }

}
