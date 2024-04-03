package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;
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
     * Adds a player to the game
     * @param player Player
     */
    //NOTA: l'eccezione viene catturata poi dal controller che gestisce la logica del gioco
    public void addPlayer(Player player) throws NicknameAlreadyExistsException, ColorAlreadyChosenException, MaxNumOfPlayersInException {
        if(playerOrder.size() >= DefaultValue.maxNumOfPlayer)
            throw new MaxNumOfPlayersInException();

        for(Player p : playerOrder){
            if(player.getNickname().equals(p.getNickname())){
                throw new NicknameAlreadyExistsException();
            }
            if(player.getColor() == p.getColor()){
                throw new ColorAlreadyChosenException();
            }
        }
        playerOrder.add(player);
        for(Player p : playerOrder){
            p.setPossibleMessageReceivers(player);
        }
    }

    /**
     * Deals an initial card to each player
     */
    public void dealInitialCard(){
        Collections.shuffle(initialCardsDeck);
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

    public void setAndDealObjectiveCards(){
        List<PlayerArea> playerAreas = new ArrayList<>();
        for (Player player : playerOrder){
            playerAreas.add(player.getPlayerArea());
        }
        centerOfTable.setCommonObjectiveCards(new ArrayList<>(playerAreas));
        for (Player player : playerOrder){
            player.setObjectiveCards(centerOfTable.removeFromObjectiveCardsDeck(), centerOfTable.removeFromObjectiveCardsDeck());
        }
    }
}
