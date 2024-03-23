package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ingsw.codex_naturalis.model.cards.gold.GoldCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CenterOfTable {

    public static final String resourceCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/resourceCards.json";
    public static final String goldCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/goldCards.json";
    public static final String objectiveCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/objectiveCards.json";

    private List<ResourceCard> resourceCardsDeck;
    private List<GoldCard> goldCardsDeck;
    private List<ObjectiveCard> objectiveCardsDeck;

    private List<ResourceCard> revealedResourceCards;
    private List<GoldCard> revealedGoldCards;
    private List<ObjectiveCard> commonObjectiveCards;


    public CenterOfTable(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.resourceCardsDeck = objectMapper.readValue(new File(this.resourceCardsJsonFilePath), new TypeReference<List<ResourceCard>>() {});
            this.goldCardsDeck = objectMapper.readValue(new File(this.goldCardsJsonFilePath), new TypeReference<List<GoldCard>>() {});
            // file objectiveCards.json TODO
            // this.objectiveCardsDeck = objectMapper.readValue(new File(this.objectiveCardsJsonFilePath), new TypeReference<List<ObjectiveCard>>() {});
        } catch (IOException e){
            System.out.println("ERROR while opening json files");
        }
    }


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

    public void shuffleResourceCardsDeck(){
        Collections.shuffle(resourceCardsDeck);
    }

    public void shuffleGoldCardsDeck(){
        Collections.shuffle(goldCardsDeck);
    }

    public void shuffleObjectiveCardsDeck(){
        Collections.shuffle(objectiveCardsDeck);
    }
}
