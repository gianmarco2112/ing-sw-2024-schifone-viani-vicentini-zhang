package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.exceptions.NoSuchNicknameException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;

import java.util.ArrayList;
import java.util.List;

public class SetupController {

    private final Game model;
    private final List<Client> views = new ArrayList<>();
    private int readyPlayers = 0;

    //--------------------------------------------------------------------------------------
    public SetupController(Game model, Client view) {
        this.model = model;
        this.views.add(view);
    }


    public Game getModel() {
        return model;
    }


    public void updateReady(String nickname){
        readyPlayers++;
        if (readyPlayers == model.getNumOfPlayers()) {
            model.setupResourceAndGoldCards();
            model.dealInitialCards();
            readyPlayers = 0;
        }
    }

    public void updateInitialCard(Client client, InitialCardEvent initialCardEvent) {
        /*Player player = null;
        try {
            player = getPlayerByNickname(client.getNickname());
        } catch (RemoteException e) {
            System.err.println("Error while getting nickname");
        }
        switch (initialCardEvent) {
            case FLIP -> player.getInitialCard().flip("");
            case PLAY -> player.playInitialCard();
        }*/
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
