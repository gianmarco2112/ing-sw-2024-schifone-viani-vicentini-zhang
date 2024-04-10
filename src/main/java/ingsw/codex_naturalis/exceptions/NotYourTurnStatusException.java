package ingsw.codex_naturalis.exceptions;

public class NotYourTurnStatusException extends RuntimeException{

    public NotYourTurnStatusException(){
        super("You have to play a card first!");
    }
}
