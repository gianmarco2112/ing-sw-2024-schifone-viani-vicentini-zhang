package ingsw.codex_naturalis.exceptions;

public class ZeroCardsLeftException extends RuntimeException{

    public ZeroCardsLeftException() {
        super("No more cards left to draw!");
    }
}
