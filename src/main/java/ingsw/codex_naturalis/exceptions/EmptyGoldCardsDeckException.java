package ingsw.codex_naturalis.exceptions;

public class EmptyGoldCardsDeckException extends RuntimeException{

    public EmptyGoldCardsDeckException(){
        super("No more gold cards left! Please draw a resource card");
    }
}
