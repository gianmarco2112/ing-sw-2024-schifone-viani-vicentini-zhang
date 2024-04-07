package ingsw.codex_naturalis.view.playing.events.commands;

public enum Command{

    COMMANDS_LIST("Show the commands list"),
    FLIP_CARD("Flip a card"),
    PLAY_CARD("Play a card"),
    DRAW_CARD("Draw a card"),
    TEXT("Send a message");

    private final String description;

    Command(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
