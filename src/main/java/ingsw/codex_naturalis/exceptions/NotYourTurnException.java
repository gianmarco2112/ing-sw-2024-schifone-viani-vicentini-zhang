package ingsw.codex_naturalis.exceptions;

public class NotYourTurnException extends RuntimeException{

    public NotYourTurnException(){
        super("It's not your turn!");
    }
}
