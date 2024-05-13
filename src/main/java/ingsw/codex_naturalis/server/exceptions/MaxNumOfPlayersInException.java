package ingsw.codex_naturalis.server.exceptions;

public class MaxNumOfPlayersInException extends RuntimeException{
    public MaxNumOfPlayersInException(){
        super("Max number of players in a game reached!");
    }
}
