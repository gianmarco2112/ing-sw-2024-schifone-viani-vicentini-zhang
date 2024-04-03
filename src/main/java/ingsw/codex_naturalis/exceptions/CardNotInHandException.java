package ingsw.codex_naturalis.exceptions;

public class CardNotInHandException extends RuntimeException{
    public CardNotInHandException(){
        super("The card is not in the player's hand");
    }
}
