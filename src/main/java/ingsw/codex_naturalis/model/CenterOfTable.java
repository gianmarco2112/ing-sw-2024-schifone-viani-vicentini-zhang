package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandCard;

import java.util.*;

public class CenterOfTable {

    private List<HandCard> revealedResourceCards;

    private List<HandCard> revealedGoldCards;

    private Deque<HandCard> resourceCardsDeck;

    private Deque<HandCard> goldCardsDeck;

    private List<ObjectiveCard> objectiveCardsDeck;

    private List<ObjectiveCard> commonObjectiveCards;


    public CenterOfTable(){}


    public List<HandCard> getRevealedResourceCards(){
        return revealedResourceCards;
    }

    public List<HandCard> getRevealedGoldCards() {
        return revealedGoldCards;
    }

    public HandCard getResourceCardFromDeck() {
        return resourceCardsDeck.getLast();
    }

    public HandCard getGoldCardFromDeck() {
        return goldCardsDeck.getLast();
    }

    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    //Visitor pattern?
    public void remove(HandCard card){

    }
}
