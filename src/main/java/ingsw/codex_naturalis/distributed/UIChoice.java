package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingTextualUI;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayTextualUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyTextualUI;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.SetupTextualUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

public enum UIChoice {

    TUI("Textual User interface (TUI)") {

        public LobbyUI createLobbyUI(){
            return new LobbyTextualUI();
        }

        public GameStartingUI createGameStartingUI(int gameID){
            return new GameStartingTextualUI(gameID);
        }

        public SetupUI createSetupUI(){
            return new SetupTextualUI();
        }

        public GameplayUI createGameplayUI() {
            return new GameplayTextualUI("nickname", null);
        }

    },



    GUI("Graphical User Interface (GUI)"){

        public LobbyUI createLobbyUI(){
            //TO FIX
            return new LobbyTextualUI();
        }

        public GameStartingUI createGameStartingUI(int gameID){
            return new GameStartingTextualUI(gameID);
        }

        public SetupUI createSetupUI(){
            // TO FIX
            return new SetupTextualUI();
        }

        public GameplayUI createGameplayUI() {
            // TO FIX
            return new GameplayTextualUI("nickname", null);
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
    public abstract GameStartingUI createGameStartingUI(int gameID);
    public abstract SetupUI createSetupUI();
    public abstract GameplayUI createGameplayUI();
}
