package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ingsw.codex_naturalis.model.cards.HandPlayableCard;
import ingsw.codex_naturalis.model.cards.gold.GoldCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CenterOfTable {

    /**
     * JSON (resource cards)
     */
    public static final String resourceCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/resourceCards.json";

    /**
     * JSON (gold cards)
     */
    public static final String goldCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/goldCards.json";

    /**
     * JSON (objective cards)
     */
    public static final String objectiveCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/objectiveCards.json";

    /**
     * Resource cards deck
     */
    private List<ResourceCard> resourceCardsDeck;

    /**
     * Gold cards deck
     */
    private List<GoldCard> goldCardsDeck;

    /**
     * Objective cards deck
     */
    private List<ObjectiveCard> objectiveCardsDeck;

    /**
     * The two revealed resource cards
     */
    private final List<HandPlayableCard> revealedResourceCards;

    /**
     * The two revealed gold cards
     */
    private final List<HandPlayableCard> revealedGoldCards;

    /**
     * The two common objective cards
     */
    private final List<ObjectiveCard> commonObjectiveCards;


    /**
     * Constructor
     */
    public CenterOfTable(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.resourceCardsDeck = objectMapper.readValue(new File(this.resourceCardsJsonFilePath), new TypeReference<List<ResourceCard>>() {});
            this.goldCardsDeck = objectMapper.readValue(new File(this.goldCardsJsonFilePath), new TypeReference<List<GoldCard>>() {});
            this.objectiveCardsDeck = objectMapper.readValue(new File(this.objectiveCardsJsonFilePath), new TypeReference<List<ObjectiveCard>>() {});
        } catch (IOException e){
            System.err.println("ERROR while opening json files");
        }
        // le liste delle carte rivelate le inizializziamo qui? TODO
        //shuffleAll(); //può causa problemi nei test mentre si instanzia il centro del tavolo
        this.revealedResourceCards = new ArrayList<>();

        this.revealedGoldCards = new ArrayList<>();

        this.commonObjectiveCards = new ArrayList<>();
    }


    /**
     * Common objective cards getter
     * @return Common objective cards
     */
    public List<ObjectiveCard> getCommonObjectiveCards() {
        return new ArrayList<>(commonObjectiveCards);
    }

//prima di rimuovere dobbiamo controllare che il deck non sia vuoto oppure lanciamo un'eccezione da fare gestire al Player
    /**
     * Removes the top card of the resource cards deck
     * @return Card
     */
    public HandPlayableCard removeFromResourceCardsDeck(){
        return resourceCardsDeck.removeLast();
    }

    /**
     * Removes the top card of the gold cards deck
     * @return Card
     */
    public HandPlayableCard removeFromGoldCardsDeck(){
        return goldCardsDeck.removeLast();
    }

    /**
     * Removes the first revealed resource card and replaces it with the top card of the resource
     * cards deck
     * @return Card
     */
    public HandPlayableCard removeFirstFromRevealedResourceCards(){
        HandPlayableCard resourceCard = revealedResourceCards.removeFirst();
        revealedResourceCards.add(resourceCardsDeck.removeLast());
        revealedResourceCards.getLast().showFront();
        return resourceCard;
    }

    /**
     * Removes the last revealed resource card and replaces it with the top card of the resource
     * cards deck
     * @return Card
     */
    public HandPlayableCard removeLastFromRevealedResourceCards(){
        HandPlayableCard resourceCard = revealedResourceCards.removeLast();
        revealedResourceCards.add(resourceCardsDeck.removeLast());
        revealedResourceCards.getLast().showFront();
        return resourceCard;
    }

    /**
     * Removes the first revealed gold card and replaces it with the top card of the gold
     * cards deck
     * @return Card
     */
    public HandPlayableCard removeFirstFromRevealedGoldCards(){
        HandPlayableCard goldCard = revealedGoldCards.removeFirst();
        revealedGoldCards.add(goldCardsDeck.removeLast());
        revealedGoldCards.getLast().showFront();
        return goldCard;
    }

    /**
     * Removes the last revealed gold card and replaces it with the top card of the gold
     * cards deck
     * @return Card
     */
    public HandPlayableCard removeLastFromRevealedGoldCards(){
        HandPlayableCard goldCard = revealedGoldCards.removeLast();
        revealedGoldCards.add(goldCardsDeck.removeLast());
        revealedGoldCards.getLast().showFront();
        return goldCard;
    }

    public ObjectiveCard removeFromObjectiveCardsDeck(){
        return objectiveCardsDeck.removeFirst();
    }

    /**
     * Gold and resource cards set up
     * Shuffles the gold and resource decks, draws 2 cards from each deck and
     * places them faceup
     */
    public void setRevealedCards(){
        Collections.shuffle(resourceCardsDeck);
        Collections.shuffle(goldCardsDeck);
        this.revealedResourceCards.add(removeFromResourceCardsDeck());
        this.revealedResourceCards.add(removeFromResourceCardsDeck());
        for (HandPlayableCard card : revealedResourceCards){
            card.showFront();
        }

        this.revealedGoldCards.add(removeFromGoldCardsDeck());
        this.revealedGoldCards.add(removeFromGoldCardsDeck());
        for (HandPlayableCard card : revealedGoldCards){
            card.showFront();
        }
    }
    public void setCommonObjectiveCards(List<PlayerArea> playerAreas){
        Collections.shuffle(objectiveCardsDeck);
        this.commonObjectiveCards.add(removeFromObjectiveCardsDeck());
        this.commonObjectiveCards.add(removeFromObjectiveCardsDeck());
        for (ObjectiveCard objectiveCard : commonObjectiveCards){
            objectiveCard.commonCardDrawn(playerAreas);
        }
    }
    public void discardObjectiveCard(ObjectiveCard objectiveCard){
        objectiveCardsDeck.add(objectiveCard);
    }

    //only for test
    public HandPlayableCard testRemoveFromGoldCardsDeck(){
        return goldCardsDeck.removeFirst();
    }
    //only for test
    public HandPlayableCard testRemoveFromResourceCardsDeck(){
        return resourceCardsDeck.removeFirst();
    }
    //only for test
    public ObjectiveCard testRemoveFromObjectiveCardsDeck(){
        return objectiveCardsDeck.removeLast();
    }
}
