package ingsw.codex_naturalis.events.gameplayPhase;

public enum PlayCard {

    PLAY_CARD_1("Play card 1"),
    PLAY_CARD_2("Play card 2"),
    PLAY_CARD_3("Play card 3");

    private final String description;

    PlayCard(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
