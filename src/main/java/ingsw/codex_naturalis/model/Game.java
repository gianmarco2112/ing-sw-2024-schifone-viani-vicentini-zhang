package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.model.enumerations.Color;

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
    private final List<Player> playerOrder;

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
    private final int gameID;


    /**
     * Constructor
     */
    public Game(int gameID) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.initialCardsDeck = objectMapper.readValue(new File(this.initialCardsJsonFilePath), new TypeReference<List<InitialCard>>() {});
        } catch (IOException e){
            System.out.println("ERROR while opening json file");
        }
        this.centerOfTable = new CenterOfTable();
        this.playerOrder = new ArrayList<>();
        this.gameID=gameID;
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
        List<String> nicknames = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        for(Player p : playerOrder){
            nicknames.add(p.getNickname());
            colors.add(p.getColor());
        }
        if(nicknames.contains(player.getNickname())||colors.contains(player.getColor())){
            if(nicknames.contains(player.getNickname())){
                System.out.println("This nickname already exists, please choose another nickname");
            }
            if(colors.contains(player.getColor())){
                System.out.println("This color has already been chosen by another player, please choose another one");
            }
        }else{
            playerOrder.add(player);
            for(Player p : playerOrder){
                p.setPossibleMessageReceivers(player);
            }
        }
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
    private Player getNextElement(List<Player> playerList, Player player){
        int index = playerList.indexOf(player);
        if(index < playerList.size() -1 ){
            return playerList.get(index+1);
        }else{
            return playerList.getFirst();
        }
    }
    public Player getNextPlayer(){
        return getNextElement(playerOrder,getCurrentPlayer());
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getGameID() {
        return gameID;
    }
    public void setCurrentPlayer(){
        currentPlayer = playerOrder.getFirst();
    }
}
