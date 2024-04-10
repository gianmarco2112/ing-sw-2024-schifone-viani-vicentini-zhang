package ingsw.codex_naturalis.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.Observable;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Card extends Observable<Event> {

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

    public void setShowingFront(boolean showingFront) {
        this.showingFront = showingFront;
    }

    public abstract void flip(String nickname);

    public abstract String getDescription();




    public Immutable getImmutableCard(){
        return new Immutable(cardID, showingFront, getDescription());
    }

    public record Immutable(String cardID, boolean showingFront, String description) {}

}
