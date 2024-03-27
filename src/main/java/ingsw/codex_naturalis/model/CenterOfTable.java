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
    private final List<ResourceCard> revealedResourceCards;

    /**
     * The two revealed gold cards
     */
    private final List<GoldCard> revealedGoldCards;

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
            System.out.println("ERROR while opening json files");
        }
        // le liste delle carte rivelate le inizializziamo qui? TODO
        //shuffleAll(); //può causa problemi nei test mentre si instanzia il centro del tavolo
        this.revealedResourceCards = new ArrayList<>();
        this.revealedResourceCards.add(removeFromResourceCardsDeck());//prima carta risorsa rivelata
        this.revealedResourceCards.add(removeFromResourceCardsDeck());//seconda carta risorsa rivelata

        this.revealedGoldCards = new ArrayList<>();
        this.revealedGoldCards.add(removeFromGoldCardsDeck());//prima carta oro rivelata
        this.revealedGoldCards.add(removeFromGoldCardsDeck());//seconda carta oro rivelata

        this.commonObjectiveCards = new ArrayList<>();
        this.commonObjectiveCards.add(removeFromObjectiveCardsDeck());//prima carta obiettivo rivelata
        this.commonObjectiveCards.add(removeFromObjectiveCardsDeck());//seconda carta obiettivo rivelata
    }

// ritorniamo una copia delle liste TODO
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
    public ResourceCard removeFromResourceCardsDeck(){
        return resourceCardsDeck.removeLast();
    }

    /**
     * Removes the top card of the gold cards deck
     * @return Card
     */
    public GoldCard removeFromGoldCardsDeck(){
        return goldCardsDeck.removeLast();
    }

    /**
     * Removes the first revealed resource card and replaces it with the top card of the resource
     * cards deck
     * @return Card
     */
    public ResourceCard removeFirstFromRevealedResourceCards(){
        ResourceCard resourceCard = revealedResourceCards.removeFirst();
        revealedResourceCards.add(resourceCardsDeck.removeLast());
        return resourceCard;
    }

    /**
     * Removes the last revealed resource card and replaces it with the top card of the resource
     * cards deck
     * @return Card
     */
    public ResourceCard removeLastFromRevealedResourceCards(){
        ResourceCard resourceCard = revealedResourceCards.removeLast();
        revealedResourceCards.add(resourceCardsDeck.removeLast());
        return resourceCard;
    }

    /**
     * Removes the first revealed gold card and replaces it with the top card of the gold
     * cards deck
     * @return Card
     */
    public GoldCard removeFirstFromRevealedGoldCards(){
        GoldCard goldCard = revealedGoldCards.removeFirst();
        revealedGoldCards.add(goldCardsDeck.removeLast());
        return goldCard;
    }

    /**
     * Removes the last revealed gold card and replaces it with the top card of the gold
     * cards deck
     * @return Card
     */
    public GoldCard removeLastFromRevealedGoldCards(){
        GoldCard goldCard = revealedGoldCards.removeLast();
        revealedGoldCards.add(goldCardsDeck.removeLast());
        return goldCard;
    }

    /**
     * Method to shuffle the center of table decks
     */
    public void shuffleAll(){
        Collections.shuffle(resourceCardsDeck);
        Collections.shuffle(goldCardsDeck);
        Collections.shuffle(objectiveCardsDeck);
    }

    //creato solo per il test, ma forse è necessario pescare le carte obiettivo in comune,
    // dato che solo centerOfTable può usare il deck delle carte obiettivo
    public ObjectiveCard removeFromObjectiveCardsDeck(){
        return objectiveCardsDeck.removeLast();
    }
}
