package ingsw.codex_naturalis.common.exceptions;

public class NotYourTurnException extends RuntimeException{

    public NotYourTurnException(){
        super("It's not your turn!");
    }
}
