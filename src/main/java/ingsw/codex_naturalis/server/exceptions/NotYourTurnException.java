package ingsw.codex_naturalis.server.exceptions;

public class NotYourTurnException extends RuntimeException{

    public NotYourTurnException(){
        super("It's not your turn!");
    }
}
