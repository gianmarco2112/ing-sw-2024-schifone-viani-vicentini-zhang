package ingsw.codex_naturalis.view.playing.commands;

public enum TextCommand {

    TEXT_A_PLAYER("Text a player"),
    TEXT_ALL_PLAYERS("Text all players");

    private final String description;

    TextCommand(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
