package ingsw.codex_naturalis.exceptions;

public class EmptyDeckException extends RuntimeException{
    public EmptyDeckException(){
        super("Empty deck, no more cards left");
    }
}
