package ingsw.codex_naturalis.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Card {

    private final String cardID;
    public boolean showingFront;


    @JsonCreator
    public Card(@JsonProperty("cardID") String cardID){
        this.cardID = cardID;
        showingFront = false;
    }


    public String getCardID(){
        return cardID;
    }

    public boolean isShowingFront() {
        return showingFront;
    }

    public abstract void flip();

}
