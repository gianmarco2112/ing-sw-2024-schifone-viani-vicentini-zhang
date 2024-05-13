package ingsw.codex_naturalis.server.exceptions;

public class NotYourPlayTurnStatusException extends RuntimeException{
    public NotYourPlayTurnStatusException(){
        super("You have already played a card, you have to draw a card!");
    }
}
