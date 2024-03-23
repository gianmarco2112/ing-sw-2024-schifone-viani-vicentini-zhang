package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.CenterOfTable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Game {

    private List<Player> playerOrder;

    private Player currentPlayer;

    public static final String initialCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/initialCards.json";
    private List<InitialCard> initialCardsDeck;

    private CenterOfTable centerOfTable; //ogni Game ha un solo centro del tavolo

    public Game() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.initialCardsDeck = objectMapper.readValue(new File(this.initialCardsJsonFilePath), new TypeReference<List<InitialCard>>() {});
        } catch (IOException e){
            System.out.println("ERROR while opening json file");
        }
        this.centerOfTable = new CenterOfTable();
    }

    public void shuffleInitialCardsDeck(){
        Collections.shuffle(this.initialCardsDeck);
    }

    //Da valutare, bisogna capire come gestire il centro del tavolo che è comune a tutti i Player e a Game TODO
    //public CenterOfTable getCenterOfTable(){
        //return centerOfTable;
    //}
}
