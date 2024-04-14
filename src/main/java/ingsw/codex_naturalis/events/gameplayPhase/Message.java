package ingsw.codex_naturalis.events.gameplayPhase;

public enum Message {

    TEXT_A_PLAYER("Text a player"),
    TEXT_ALL_PLAYERS("Text all players");

    private final String description;

    Message(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
