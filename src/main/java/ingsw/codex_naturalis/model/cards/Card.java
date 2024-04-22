package ingsw.codex_naturalis.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.Observable;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Card extends Observable {

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
