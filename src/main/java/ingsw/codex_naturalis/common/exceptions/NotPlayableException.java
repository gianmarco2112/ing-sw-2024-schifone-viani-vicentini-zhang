package ingsw.codex_naturalis.common.exceptions;

public class NotPlayableException extends RuntimeException {
    public NotPlayableException(){
        super("This card is not playable here!");
    }
}
