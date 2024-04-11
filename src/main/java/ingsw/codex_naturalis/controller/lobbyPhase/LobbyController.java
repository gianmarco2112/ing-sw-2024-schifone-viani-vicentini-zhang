package ingsw.codex_naturalis.controller.lobbyPhase;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

public class LobbyController {

    private final Game model;
    private final LobbyUI view;

    //--------------------------------------------------------------------------------------
    public LobbyController(Game model, LobbyUI view) {
        this.model = model;
        this.view = view;
    }

}
