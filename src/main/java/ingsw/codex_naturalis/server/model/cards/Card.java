package ingsw.codex_naturalis.server.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Abstract class that would implement INITIAL, RESOURCE and GOLD Cards
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public abstract class Card {
    /**
     * Unique identifier of the card
     */
    private final String cardID;

    /**
     * Card's constructor
     * @param cardID : the identifier of the card
     */
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
