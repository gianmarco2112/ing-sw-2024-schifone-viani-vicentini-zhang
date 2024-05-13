package ingsw.codex_naturalis.server.exceptions;

public class NoExistingGamesAvailable extends RuntimeException{

    public NoExistingGamesAvailable(){
        super("I'm sorry, there aren't any existing games yet");
    }
}
