package ingsw.codex_naturalis.common.exceptions;

public class ZeroCardsLeftException extends RuntimeException{

    public ZeroCardsLeftException() {
        super("No more cards left to draw!");
    }
}
