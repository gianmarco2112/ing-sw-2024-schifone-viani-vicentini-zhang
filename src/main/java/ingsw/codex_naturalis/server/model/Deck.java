package ingsw.codex_naturalis.server.model;

import ingsw.codex_naturalis.server.exceptions.EmptyDeckException;
import ingsw.codex_naturalis.server.model.cards.Card;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Deck <T extends Card> {

    private final List<T> cards;


    public Deck(List<T> cards){
        this.cards = cards;
    }


    public void shuffle(){
        Collections.shuffle(cards);
    }

    public T drawACard() throws EmptyDeckException {
        try {
            return cards.removeFirst();
        } catch (NoSuchElementException e) {
            throw new EmptyDeckException();
        }
    }

    public void discardACard(T cardToDiscard){
        cards.add(cardToDiscard);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public T getFirstCard(){
        if (cards.isEmpty())
            return null;
        return cards.getFirst();
    }
}
