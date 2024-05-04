package ingsw.codex_naturalis.common.exceptions;

public class CardNotInHandException extends RuntimeException{
    public CardNotInHandException(){
        super("The card is not in the player's hand");
    }
}
