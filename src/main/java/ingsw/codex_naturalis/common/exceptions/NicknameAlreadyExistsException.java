package ingsw.codex_naturalis.common.exceptions;

/**
 * This exception is thrown when a nickname already exists
 */
public class NicknameAlreadyExistsException extends RuntimeException{
    public NicknameAlreadyExistsException(){
        super("This nickname already exists, please choose another nickname");
    }
}
