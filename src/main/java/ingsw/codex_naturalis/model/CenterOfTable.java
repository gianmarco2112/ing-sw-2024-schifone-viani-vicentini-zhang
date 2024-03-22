package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.gold.GoldCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;

import java.util.*;

public class CenterOfTable {

    private Deque<ResourceCard> resourceCardsDeck;

    private Deque<GoldCard> goldCardsDeck;

    private List<ResourceCard> revealedResourceCards;

    private List<GoldCard> revealedGoldCards;

    private List<ObjectiveCard> objectiveCardsDeck;

    private List<ObjectiveCard> commonObjectiveCards;


    public CenterOfTable(){}


    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    public ResourceCard removeFromResourceCardsDeck(){
        return resourceCardsDeck.removeLast();
    }
    public GoldCard removeFromGoldCardsDeck(){
        return goldCardsDeck.removeLast();
    }

    public ResourceCard removeFirstFromRevealedResourceCards(){
        ResourceCard resourceCard = revealedResourceCards.removeFirst();
        revealedResourceCards.add(resourceCardsDeck.removeLast());
        return resourceCard;
    }
    public ResourceCard removeLastFromRevealedResourceCards(){
        ResourceCard resourceCard = revealedResourceCards.removeLast();
        revealedResourceCards.add(resourceCardsDeck.removeLast());
        return resourceCard;
    }
    public GoldCard removeFirstFromRevealedGoldCards(){
        GoldCard goldCard = revealedGoldCards.removeFirst();
        revealedGoldCards.add(goldCardsDeck.removeLast());
        return goldCard;
    }
    public GoldCard removeLastFromRevealedGoldCards(){
        GoldCard goldCard = revealedGoldCards.removeLast();
        revealedGoldCards.add(goldCardsDeck.removeLast());
        return goldCard;
    }
}
