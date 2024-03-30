package ingsw.codex_naturalis.exceptions;

public class NotPlayableException extends RuntimeException {
    public NotPlayableException(){
        super("This card is not playable here!");
    }
}
