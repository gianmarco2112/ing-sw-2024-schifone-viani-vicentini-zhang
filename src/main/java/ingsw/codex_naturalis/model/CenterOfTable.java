package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ingsw.codex_naturalis.exceptions.EmptyDeckException;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.player.PlayerArea;

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
    private List<PlayableCard> resourceCardsDeck;

    /**
     * Gold cards deck
     */
    private List<PlayableCard> goldCardsDeck;

    /**
     * Objective cards deck
     */
    private List<ObjectiveCard> objectiveCardsDeck;

    /**
     * The two revealed resource cards
     */
    private final List<PlayableCard> revealedResourceCards;

    /**
     * The two revealed gold cards
     */
    private final List<PlayableCard> revealedGoldCards;

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
            this.resourceCardsDeck = objectMapper.readValue(new File(this.resourceCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            this.goldCardsDeck = objectMapper.readValue(new File(this.goldCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
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
    public PlayableCard removeFromResourceCardsDeck() throws EmptyDeckException {
        if(resourceCardsDeck.isEmpty()){
            throw new EmptyDeckException();
        }
        return resourceCardsDeck.removeLast();
    }

    /**
     * Removes the top card of the gold cards deck
     * @return Card
     */
    public PlayableCard removeFromGoldCardsDeck() throws EmptyDeckException{
        if(goldCardsDeck.isEmpty()){
            throw new EmptyDeckException();
        }
        return goldCardsDeck.removeLast();
    }

    /**
     * Removes the first revealed resource card and replaces it with the top card of the resource
     * cards deck
     * @return Card
     */
    public PlayableCard removeFirstFromRevealedResourceCards(){
        PlayableCard resourceCard = revealedResourceCards.removeFirst();
        revealedResourceCards.add(resourceCardsDeck.removeLast());//forse meglio chiamare removeFromResourceCardsDeck che solleva un'eccezione definita da noi
        revealedResourceCards.getLast().flip();
        return resourceCard;
    }

    /**
     * Removes the last revealed resource card and replaces it with the top card of the resource
     * cards deck
     * @return Card
     */
    public PlayableCard removeLastFromRevealedResourceCards(){
        PlayableCard resourceCard = revealedResourceCards.removeLast();
        revealedResourceCards.add(resourceCardsDeck.removeLast());//come sopra
        revealedResourceCards.getLast().flip();
        return resourceCard;
    }

    /**
     * Removes the first revealed gold card and replaces it with the top card of the gold
     * cards deck
     * @return Card
     */
    public PlayableCard removeFirstFromRevealedGoldCards(){
        PlayableCard goldCard = revealedGoldCards.removeFirst();
        revealedGoldCards.add(goldCardsDeck.removeLast());//come sopra
        revealedGoldCards.getLast().flip();
        return goldCard;
    }

    /**
     * Removes the last revealed gold card and replaces it with the top card of the gold
     * cards deck
     * @return Card
     */
    public PlayableCard removeLastFromRevealedGoldCards(){
        PlayableCard goldCard = revealedGoldCards.removeLast();
        revealedGoldCards.add(goldCardsDeck.removeLast());//come sopra
        revealedGoldCards.getLast().flip();
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
        for (PlayableCard card : revealedResourceCards){
            card.flip();
        }

        this.revealedGoldCards.add(removeFromGoldCardsDeck());
        this.revealedGoldCards.add(removeFromGoldCardsDeck());
        for (PlayableCard card : revealedGoldCards){
            card.flip();
        }
    }
    public void setCommonObjectiveCards(List<PlayerArea> playerAreas){
        Collections.shuffle(objectiveCardsDeck);
        this.commonObjectiveCards.add(removeFromObjectiveCardsDeck());
        this.commonObjectiveCards.add(removeFromObjectiveCardsDeck());
    }
    public void discardObjectiveCard(ObjectiveCard objectiveCard){
        objectiveCardsDeck.add(objectiveCard);
    }

    // TESTING METHODS
    /**
     * Testing method
     * @return Card
     */
    //only for test
    @Deprecated
    public PlayableCard testRemoveFromGoldCardsDeck(){
        return goldCardsDeck.removeFirst();
    }

    /**
     * Testing method
     * @return Card
     */
    //only for test
    @Deprecated
    public PlayableCard testRemoveFromResourceCardsDeck(){
        return resourceCardsDeck.removeFirst();
    }

    /**
     * Testing method
     * @return Card
     */
    //only for test
    @Deprecated
    public ObjectiveCard testRemoveFromObjectiveCardsDeck(){
        return objectiveCardsDeck.removeLast();
    }

    /**
     * Testing method
     * @return Card
     */
    //only for test
    @Deprecated
    public PlayableCard testGetFromGoldCardsDeck(){
        return goldCardsDeck.getFirst();
    }
    //only for test
    /**
     * Testing method
     * @return Card
     */
    @Deprecated
    public PlayableCard testGetFromResourceCardsDeck(){
        return resourceCardsDeck.getFirst();
    }
}
