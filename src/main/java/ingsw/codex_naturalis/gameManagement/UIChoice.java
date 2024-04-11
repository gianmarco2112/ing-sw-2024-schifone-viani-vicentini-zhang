package ingsw.codex_naturalis.gameManagement;

import ingsw.codex_naturalis.view.lobbyPhase.LobbyTextualUI;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.SetupTextualUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

public enum UIChoice {

    TUI("Textual User interface (TUI)") {
        public LobbyUI createLobbyUI(){
            return new LobbyTextualUI();
        }
        public SetupUI createSetupUI(){
            return new SetupTextualUI("nickname");
        }
    },
    GUI("Graphical User Interface (GUI)"){
        public LobbyUI createLobbyUI(){
            //TO FIX
            return new LobbyTextualUI();
        }
        public SetupUI createSetupUI(){
            // TO FIX
            return new SetupTextualUI("nickname");
        }
    };

    private final String description;


    UIChoice(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public abstract LobbyUI createLobbyUI();
    public abstract SetupUI createSetupUI();
}
