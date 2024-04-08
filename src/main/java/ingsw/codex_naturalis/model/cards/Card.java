package ingsw.codex_naturalis.model.cards;

public abstract class Card {

    private final String cardID;
    public boolean showingFront;


    public Card(String cardID){
        this.cardID = cardID;
        showingFront = false;
    }


    public String getCardID(){
        return cardID;
    }

    public boolean isShowingFront(){
        return showingFront;
    }

    public void flip(){
        if(!showingFront)
            showingFront = true;
        else
            showingFront = false;
    }

    public abstract String getDescription();



}
