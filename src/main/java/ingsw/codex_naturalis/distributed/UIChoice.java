package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingGraphicUI;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingTextualUI;
import ingsw.codex_naturalis.view.gameStartingPhase.GameStartingUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayTextualUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyGraphicUI;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyTextualUI;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.SetupGraphicUI;
import ingsw.codex_naturalis.view.setupPhase.SetupTextualUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

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
