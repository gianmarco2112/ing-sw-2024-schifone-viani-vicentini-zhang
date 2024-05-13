package ingsw.codex_naturalis.server.exceptions;

public class EmptyResourceCardsDeckException extends RuntimeException{
    public EmptyResourceCardsDeckException(){
        super("No more resource cards left! Please draw a gold card");
    }
}
