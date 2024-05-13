package ingsw.codex_naturalis.server.exceptions;

public class EmptyDeckException extends RuntimeException{

    public EmptyDeckException(){
        super("This deck is empty!");
    }
}
