package ingsw.codex_naturalis.events.gameplayPhase;

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
