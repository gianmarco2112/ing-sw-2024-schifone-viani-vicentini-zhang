package ingsw.codex_naturalis.common.exceptions;

public class NoSuchNicknameException extends RuntimeException{

    public NoSuchNicknameException(){
        super("This nickname doesn't exist!");
    }
}
