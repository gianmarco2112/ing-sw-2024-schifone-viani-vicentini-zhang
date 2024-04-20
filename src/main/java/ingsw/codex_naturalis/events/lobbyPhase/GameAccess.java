package ingsw.codex_naturalis.events.lobbyPhase;

import ingsw.codex_naturalis.view.lobbyPhase.LobbyTextualUI;

import java.util.LinkedHashMap;
import java.util.Map;

public enum GameAccess {

    NEW_GAME("Create a new game"),
    EXISTING_GAME("Access an existing game");

    private final String description;

    GameAccess(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
