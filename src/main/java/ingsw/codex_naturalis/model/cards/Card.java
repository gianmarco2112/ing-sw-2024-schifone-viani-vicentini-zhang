package ingsw.codex_naturalis.model.cards;

public abstract class Card {

    private final String cardID;

    public Card(String cardID){
        this.cardID = cardID;
    }


    public String getCardID(){
        return cardID;
    }

    public abstract String getDescription();
}
