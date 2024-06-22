package ingsw.codex_naturalis.server.model;

import ingsw.codex_naturalis.server.exceptions.EmptyDeckException;
import ingsw.codex_naturalis.server.model.cards.Card;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Deck's class
 */
public class Deck <T extends Card> {

    private final List<T> cards;
    /**
     * Constructor
     * @param cards : the list of cards that would be in the deck
     */

    public Deck(List<T> cards){
        this.cards = cards;
    }
    /**
     * With this method, the deck would be shuffled and the cards placed in a random order
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }
    /**
     * This method draws a card from the deck
     * @throws EmptyDeckException : when the deck has no cards left
     */
    public T drawACard() throws EmptyDeckException {
        try {
            return cards.removeFirst();
        } catch (NoSuchElementException e) {
            throw new EmptyDeckException();
        }
    }
    /**
     * This method remove a card from the deck (used in the setup of the game, to discard the
     * ObjectiveCard that the player didn't choose from the 2 card he could choose from)
     */
    public void discardACard(T cardToDiscard){
        cards.add(cardToDiscard);
    }
    /**
     * Method to know if the deck is empty or not
     * @return isEmpty: True is the deck is empty, false if not
     */
    public boolean isEmpty(){
        return cards.isEmpty();
    }
    /**
     * First card's getter
     * @return null if the deck is empty,
     *         the first card of the deck otherwise
     */
    public T getFirstCard(){
        if (cards.isEmpty())
            return null;
        return cards.getFirst();
    }
}
