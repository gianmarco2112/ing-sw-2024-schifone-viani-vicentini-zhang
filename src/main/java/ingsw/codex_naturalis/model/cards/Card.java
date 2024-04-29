package ingsw.codex_naturalis.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Abstract class that would implement INITIAL, RESOURCE and GOLD Cards
 */
public abstract class Card {
    /**
     * Unique identifier of the card
     */
    private final String cardID;

    @JsonCreator
    public Card(@JsonProperty("cardID") String cardID){
        this.cardID = cardID;
    }
    /**
     * Getter of the identifier of the card
     * @return cardID
     */
    public String getCardID(){
        return cardID;
    }
    /**
     * Abstract method to flip the card
     */
    public abstract void flip();

}
