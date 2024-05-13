package ingsw.codex_naturalis.server.exceptions;

public class ZeroCardsLeftException extends RuntimeException{

    public ZeroCardsLeftException() {
        super("No more cards left to draw!");
    }
}
