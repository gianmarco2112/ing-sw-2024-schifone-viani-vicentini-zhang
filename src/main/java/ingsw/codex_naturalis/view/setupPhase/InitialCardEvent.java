package ingsw.codex_naturalis.view.setupPhase;

public enum InitialCardEvent {

    FLIP ("Flip the initial card"),
    PLAY ("Play the initial card");

    private final String description;

    InitialCardEvent(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
