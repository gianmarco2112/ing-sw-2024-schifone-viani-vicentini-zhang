package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.enumerations.GameStatus;

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
    private List<PlayableCard> initialCardsDeck;

    /**
     * Center of table
     */
    private final CenterOfTable centerOfTable;
    /**
     * Game ID is necessary in order to have multiple game
     */
    private final int gameID;

    private GameStatus gameStatus;

    private final int maxNumOfPlayers;


    /**
     * Constructor
     */
    public Game(int gameID, int maxNumOfPlayers) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.initialCardsDeck = objectMapper.readValue(new File(initialCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
        } catch (IOException e){
            System.err.println("ERROR while opening json file");
        }
        this.centerOfTable = new CenterOfTable();
        this.playerOrder = new ArrayList<>();
        this.gameID = gameID;
        this.gameStatus = GameStatus.WAITING;
        this.maxNumOfPlayers = maxNumOfPlayers;
    }


    public int getMaxNumOfPlayers() {
        return maxNumOfPlayers;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Adds a player to the game
     * @param player Player
     */
    public void addPlayer(Player player) throws NicknameAlreadyExistsException, ColorAlreadyChosenException, MaxNumOfPlayersInException {
        if(playerOrder.size() >= maxNumOfPlayers)
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

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getGameID() {
        return gameID;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void setAndDealObjectiveCards(){
        List<PlayerArea> playerAreas = new ArrayList<>();
        for (Player player : playerOrder){
            playerAreas.add(player.getPlayerArea());
        }
        centerOfTable.setCommonObjectiveCards(new ArrayList<>(playerAreas));
        /*for (Player player : playerOrder){
            player.setObjectiveCards(centerOfTable.removeFromObjectiveCardsDeck(), centerOfTable.removeFromObjectiveCardsDeck());
        }*/
    }

    public CenterOfTable getCenterOfTable() {
        return centerOfTable;
    }

    public List<Player> getPlayerOrder() {
        return playerOrder;
    }
}
