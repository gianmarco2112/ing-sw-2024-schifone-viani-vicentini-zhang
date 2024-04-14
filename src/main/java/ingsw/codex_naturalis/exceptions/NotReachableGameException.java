package ingsw.codex_naturalis.exceptions;

public class NotReachableGameException extends RuntimeException{

    public NotReachableGameException(){
        super("Error while trying to access the game, please try again");
    }
}
