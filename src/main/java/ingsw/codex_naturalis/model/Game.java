package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.CenterOfTable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Game class
 */
public class Game {

    /**
     * Contains all the players of the game, ordered by the turn they play
     */
    private List<Player> playerOrder;

    /**
     * Current player
     */
    private Player currentPlayer;

    /**
     * JSON (Initial cards)
     */
    public static final String initialCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/initialCards.json";
    /**
     * Initial cards deck
     */
    private List<InitialCard> initialCardsDeck;

    /**
     * Center of table
     */
    private final CenterOfTable centerOfTable;
    /**
     * Game ID is necessary in order to have multiple game
     */
    private int gameID;


    /**
     * Constructor
     */
    public Game() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.initialCardsDeck = objectMapper.readValue(new File(this.initialCardsJsonFilePath), new TypeReference<List<InitialCard>>() {});
        } catch (IOException e){
            System.out.println("ERROR while opening json file");
        }
        this.centerOfTable = new CenterOfTable();
        this.playerOrder = new ArrayList<>();
    }

    /**
     * Shuffles all the decks
     */
    public void shuffleAll(){
        Collections.shuffle(this.initialCardsDeck);
        centerOfTable.shuffleAll();
    }

    /**
     * Adds a player to the game
     * @param player Player
     */
    public void addPlayer(Player player){
        playerOrder.add(player);
    }

    /**
     * Deals an initial card to each player
     */
    public void dealInitialCard(){
        for (Player player : playerOrder){
            player.setInitialCard(initialCardsDeck.removeFirst());
        }
    }

    public Player getFirstPlayer(){
        return playerOrder.getFirst();
    }
    public void shufflePlayerList(){
        Collections.shuffle(this.playerOrder);
    }

}
