package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.Card;

import java.util.Collections;
import java.util.List;

public class Deck <T extends Card>{

    private final List<T> cards;


    public Deck(List<T> cards){
        this.cards = cards;
    }


    public void shuffle(){
        Collections.shuffle(cards);
    }

    public T drawACard(){
        return cards.removeFirst();
    }

    public void discardACard(T cardToDiscard){
        cards.add(cardToDiscard);
    }
}
