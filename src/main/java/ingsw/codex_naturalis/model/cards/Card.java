package ingsw.codex_naturalis.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Card {

    private final String cardID;


    @JsonCreator
    public Card(@JsonProperty("cardID") String cardID){
        this.cardID = cardID;
    }


    public String getCardID(){
        return cardID;
    }

    public abstract void flip();

}
