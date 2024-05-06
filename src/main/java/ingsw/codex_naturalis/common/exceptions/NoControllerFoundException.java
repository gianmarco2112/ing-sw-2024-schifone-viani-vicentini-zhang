package ingsw.codex_naturalis.common.exceptions;

public class NoControllerFoundException extends RuntimeException{

    public NoControllerFoundException(){
        super("Error: no controller found.");
    }
}
