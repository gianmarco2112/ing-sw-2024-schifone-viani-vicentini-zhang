package ingsw.codex_naturalis.server.exceptions;

public class ColorAlreadyChosenException extends RuntimeException{
    public ColorAlreadyChosenException(){
        super("This color has already been chosen by another player, please choose another one");
    }
}
