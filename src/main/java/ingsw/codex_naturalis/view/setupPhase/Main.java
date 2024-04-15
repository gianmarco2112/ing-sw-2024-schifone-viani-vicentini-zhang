package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;

public class Main {
    public static void main(String[] args){
        SetupUI setupUI = new SetupTextualUI(PlayersConnectedStatus.GO);
        setupUI.run();
    }
}
