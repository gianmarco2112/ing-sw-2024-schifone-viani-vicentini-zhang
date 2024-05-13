package ingsw.codex_naturalis.server.exceptions;

public class InvalidNumOfPlayersException extends RuntimeException{
    public InvalidNumOfPlayersException() {
        super("Invalid number of players!");
    }
}
