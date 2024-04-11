package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

public class SetupController {

    private final Game model;
    private final SetupUI view;

    //--------------------------------------------------------------------------------------
    public SetupController(Game model, SetupUI view) {
        this.model = model;
        this.view = view;
    }
}
