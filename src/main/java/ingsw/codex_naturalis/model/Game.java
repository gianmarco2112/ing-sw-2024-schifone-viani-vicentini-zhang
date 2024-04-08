package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.model.cards.Card;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.GameStatus;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Game class
 */
public class Game {
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
     * JSON (Initial cards)
     */
    public static final String initialCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/initialCards.json";






    /**
     * Game ID is necessary in order to have multiple game
     */
    private final int gameID;

    /**
     * Game status
     */
    private GameStatus gameStatus;

    /**
     * Max number of players of the game, the game creator decides this parameter
     */
    private final int maxNumOfPlayers;

    /**
     * Contains all the players of the game, ordered by the turn they play
     */
    private final List<Player> playerOrder;

    /**
     * Current player
     */
    private Player currentPlayer;

    /**
     * Initial cards deck
     */
    private Deck<PlayableCard> initialCardsDeck;

    /**
     * Resource cards deck
     */
    private Deck<PlayableCard> resourceCardsDeck;

    /**
     * Gold cards deck
     */
    private Deck<PlayableCard> goldCardsDeck;

    /**
     * Objective cards deck
     */
    private Deck<ObjectiveCard> objectiveCardsDeck;

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
    public Game(int gameID, int maxNumOfPlayers) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.initialCardsDeck = objectMapper.readValue(new File(initialCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
        } catch (IOException e){
            System.err.println("ERROR while opening json file");
        }
        this.playerOrder = new ArrayList<>();
        this.gameID = gameID;
        this.gameStatus = GameStatus.WAITING;
        this.maxNumOfPlayers = maxNumOfPlayers;
    }



    public int getGameID() {
        return gameID;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }

    public int getMaxNumOfPlayers() {
        return maxNumOfPlayers;
    }
    public List<Player> getPlayerOrder() {
        return playerOrder;
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
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
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
        initialCardsDeck.shuffle();
        for (Player player : playerOrder){
            player.setInitialCard(initialCardsDeck.drawACard());
        }
    }

    public void setCommonObjectiveCards(List<PlayerArea> playerAreas){
        objectiveCardsDeck.shuffle();
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard());
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard());
    }

    /**
     * Gold and resource cards set up
     * Shuffles the gold and resource decks, draws 2 cards from each deck and
     * places them faceup
     */
    public void setRevealedCards(){
        resourceCardsDeck.shuffle();
        this.revealedResourceCards.add(resourceCardsDeck.drawACard());
        this.revealedResourceCards.add(resourceCardsDeck.drawACard());
        for (PlayableCard card : revealedResourceCards){
            card.flip();
        }

        goldCardsDeck.shuffle();
        this.revealedGoldCards.add(goldCardsDeck.drawACard());
        this.revealedGoldCards.add(goldCardsDeck.drawACard());
        for (PlayableCard card : revealedGoldCards){
            card.flip();
        }
    }
}
