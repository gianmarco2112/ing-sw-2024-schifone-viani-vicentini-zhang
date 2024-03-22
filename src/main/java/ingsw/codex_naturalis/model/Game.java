package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.cards.gold.GoldCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.cards.initial.InitialCardBack;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.model.enumerations.Symbol;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Game {

    private List<Player> playerOrder;

    private Player currentPlayer;


    public static final String resourceCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/resourceCards.json";
    public static final String goldCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/goldCards.json";
    public static final String initialCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/initialCards.json";
    public static final String objectiveCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/objectiveCards.json";
    private List<InitialCard> initialCardsDeck;

    private CenterOfTable centerOfTable;

    // A fini di test terra terra
    public static void main(String[] args) throws IOException {
        Game gioco_test = new Game();
        //InitialCard carta = gioco_test.drawInitialCardsDeck();
        //System.out.println(carta.getBack().getClass());
        //List<Symbol> risorse = carta.getBack().getResources();
        //risorse.removeFirst();
        //System.out.println(gioco_test.resourceCardsDeck.getFirst().getFront().getKingdom());
        //System.out.println(gioco_test.initialCardsDeck.getFirst().getBack().getResources().getFirst());
        //System.out.println(gioco_test.goldCardsDeck.get(3).getFront().getClass());
        //System.out.println(gioco_test.goldCardsDeck.get(1).getFront().getClass());
    }

    public Game() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.initialCardsDeck = objectMapper.readValue(new File(this.initialCardsJsonFilePath), new TypeReference<List<InitialCard>>() {});
        } catch (IOException e){
            System.out.println("ERROR while opening json file");
        }

    }

    public void shuffleInitialCardDeck(){
        Collections.shuffle(this.initialCardsDeck);
    }

    public InitialCard drawInitialCardsDeck(){
        InitialCard initialCard = this.initialCardsDeck.getFirst();
        this.initialCardsDeck.removeFirst();
        return initialCard;
    }
}
