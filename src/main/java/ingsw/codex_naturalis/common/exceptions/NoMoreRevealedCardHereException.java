package ingsw.codex_naturalis.common.exceptions;

public class NoMoreRevealedCardHereException extends RuntimeException{

    public NoMoreRevealedCardHereException(){
        super("There isn't a revealed card here, pick the other one!");
    }
}
