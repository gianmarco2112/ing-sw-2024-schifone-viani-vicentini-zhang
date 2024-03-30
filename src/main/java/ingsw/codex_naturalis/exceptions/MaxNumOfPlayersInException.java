package ingsw.codex_naturalis.exceptions;

public class MaxNumOfPlayersInException extends RuntimeException{
    public MaxNumOfPlayersInException(){
        super("Max number of players in a game reached!");
    }
}
