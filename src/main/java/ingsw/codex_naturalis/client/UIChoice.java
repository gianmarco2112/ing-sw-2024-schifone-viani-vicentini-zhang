package ingsw.codex_naturalis.client;

import ingsw.codex_naturalis.client.view.gameStartingPhase.GameStartingGraphicUI;
import ingsw.codex_naturalis.client.view.gameStartingPhase.GameStartingTextualUI;
import ingsw.codex_naturalis.client.view.gameStartingPhase.GameStartingUI;
import ingsw.codex_naturalis.client.view.gameplayPhase.GameplayTextualUI;
import ingsw.codex_naturalis.client.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.client.view.lobbyPhase.LobbyGraphicUI;
import ingsw.codex_naturalis.client.view.lobbyPhase.LobbyTextualUI;
import ingsw.codex_naturalis.client.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.client.view.setupPhase.SetupGraphicUI;
import ingsw.codex_naturalis.client.view.setupPhase.SetupTextualUI;
import ingsw.codex_naturalis.client.view.setupPhase.SetupUI;

public enum UIChoice {

    TUI {

        public LobbyUI createLobbyUI(){
            return new LobbyTextualUI();
        }

        public GameStartingUI createGameStartingUI(){
            return new GameStartingTextualUI();
        }

        public SetupUI createSetupUI(){
            return new SetupTextualUI();
        }

        public GameplayUI createGameplayUI() {
            return new GameplayTextualUI();
        }

    },



    GUI{

        public LobbyUI createLobbyUI(){
            return new LobbyGraphicUI();
        }

        public GameStartingGraphicUI createGameStartingUI(){
            return new GameStartingGraphicUI();
        }

        public SetupGraphicUI createSetupUI(){
            return new SetupGraphicUI();
        }

        public GameplayUI createGameplayUI() {
            // TO FIX
            return new GameplayTextualUI();
        }

    };




    public abstract LobbyUI createLobbyUI();
    public abstract GameStartingUI createGameStartingUI();
    public abstract SetupUI createSetupUI();
    public abstract GameplayUI createGameplayUI();
}
