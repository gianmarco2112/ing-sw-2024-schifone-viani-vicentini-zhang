package ingsw.codex_naturalis.view.gameplayPhase.commands;

public enum FlipCardCommand {

    FLIP_CARD_1("Flip card 1"),
    FLIP_CARD_2("Flip card 2"),
    FLIP_CARD_3("Flip card 3");

    private final String description;

    FlipCardCommand(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
