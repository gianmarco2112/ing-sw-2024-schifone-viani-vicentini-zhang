package ingsw.codex_naturalis.server.exceptions;

public class NotYourDrawTurnStatusException extends RuntimeException{

    public NotYourDrawTurnStatusException(){
        super("You have to play a card first!");
    }
}
