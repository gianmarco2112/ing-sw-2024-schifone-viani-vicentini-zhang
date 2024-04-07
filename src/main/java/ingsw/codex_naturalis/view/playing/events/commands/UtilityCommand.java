package ingsw.codex_naturalis.view.playing.events.commands;

public enum UtilityCommand {

    CANCEL("Cancel");

    private final String description;

    UtilityCommand(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
