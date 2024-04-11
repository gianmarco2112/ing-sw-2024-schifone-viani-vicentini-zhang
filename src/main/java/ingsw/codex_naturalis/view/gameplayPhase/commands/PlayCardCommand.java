package ingsw.codex_naturalis.view.gameplayPhase.commands;

public enum PlayCardCommand{

    PLAY_CARD_1("Play card 1"),
    PLAY_CARD_2("Play card 2"),
    PLAY_CARD_3("Play card 3");

    private final String description;

    PlayCardCommand(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
