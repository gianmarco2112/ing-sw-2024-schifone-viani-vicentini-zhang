package ingsw.codex_naturalis.exceptions;

public class NotYourDrawTurnStatusException extends RuntimeException{

    public NotYourDrawTurnStatusException(){
        super("You have to play a card first!");
    }
}
