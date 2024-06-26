package ingsw.codex_naturalis.server.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import ingsw.codex_naturalis.common.enumerations.Color;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.server.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.server.exceptions.InvalidNumOfPlayersException;
import ingsw.codex_naturalis.server.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.server.model.util.GameObservable;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;
import ingsw.codex_naturalis.server.model.util.PlayerObserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Game's class
 */
public class Game extends GameObservable implements PlayerObserver {

    /**
     * Game ID 8is necessary in order to have multiple game)
     */
    private final int gameID;

    /**
     * Game's status
     */
    private GameStatus gameStatus;

    /**
     * Max number of players in the game, the game creator decides this parameter when creating a new game
     */
    private int numOfPlayers;

    /**
     * Contains a list of all the players in the game, sorted by the turn they are playing
     */
    private List<Player> playerOrder;
    /**
     * Hashmap that links the player with his nickname
     */
    private final Map<String, Player> nicknameToPlayer = new HashMap<>();

    /**
     * Current player
     */
    private Player currentPlayer;

    /**
     * Deck of initial cards
     */
    private Deck<PlayableCard> initialCardsDeck;

    /**
     * Deck of resource cards
     */
    private Deck<PlayableCard> resourceCardsDeck;

    /**
     * Deck of gold cards
     */
    private Deck<PlayableCard> goldCardsDeck;

    /**
     * Deck of objective cards
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
     * The game chat (contains all the messages sent by the players during the game)
     */
    private final List<Message> chat;
    /**
     * The status of the game (when the game is created, the status is initialized to "RUNNING")
     */
    private GameRunningStatus gameRunningStatus = GameRunningStatus.RUNNING;

    /**
     * Game's constructor
     * @param gameID : the ID of the game
     * @param numOfPlayers : number of players in the game
     */
    public Game(int gameID, int numOfPlayers) {

        if (numOfPlayers > 4 || numOfPlayers < 2)
            throw new InvalidNumOfPlayersException();

        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream in = getClass().getResourceAsStream("/jsonCards/initialCards.json")){
            JsonNode jsonNode = objectMapper.readValue(in, JsonNode.class);
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            List<PlayableCard> initialCards = objectMapper.readValue(jsonString, new TypeReference<List<PlayableCard>>() {});
            this.initialCardsDeck = new Deck<>(initialCards);
        } catch (IOException e) {
            System.err.println("Error while reading JSON file\n"+e.getMessage());
        }
        try (InputStream in = getClass().getResourceAsStream("/jsonCards/resourceCards.json")){
            JsonNode jsonNode = objectMapper.readValue(in, JsonNode.class);
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            List<PlayableCard> resourceCards = objectMapper.readValue(jsonString, new TypeReference<List<PlayableCard>>() {});
            this.resourceCardsDeck = new Deck<>(resourceCards);
        } catch (IOException e) {
            System.err.println("Error while reading JSON file\n"+e.getMessage());
        }
        try (InputStream in = getClass().getResourceAsStream("/jsonCards/goldCards.json")){
            JsonNode jsonNode = objectMapper.readValue(in, JsonNode.class);
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            List<PlayableCard> goldCards = objectMapper.readValue(jsonString, new TypeReference<List<PlayableCard>>() {});
            this.goldCardsDeck = new Deck<>(goldCards);
        } catch (IOException e) {
            System.err.println("Error while reading JSON file\n"+e.getMessage());
        }
        try (InputStream in = getClass().getResourceAsStream("/jsonCards/objectiveCards.json")){
            JsonNode jsonNode = objectMapper.readValue(in, JsonNode.class);
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            List<ObjectiveCard> objectiveCards = objectMapper.readValue(jsonString, new TypeReference<List<ObjectiveCard>>() {});
            this.objectiveCardsDeck = new Deck<>(objectiveCards);
        } catch (IOException e) {
            System.err.println("Error while reading JSON file\n"+e.getMessage());
        }

        this.playerOrder = new ArrayList<>();
        this.gameID = gameID;
        this.gameStatus = GameStatus.WAITING_FOR_PLAYERS;
        this.numOfPlayers = numOfPlayers;
        this.chat = new ArrayList<>();
        this.revealedResourceCards = new ArrayList<>();
        this.revealedGoldCards = new ArrayList<>();
        this.commonObjectiveCards = new ArrayList<>();
    }

    /**
     * Getter of the common objective cards
     * @return commonObjectiveCards
     */

    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }
    /**
     * Getter of the game ID
     * @return gameID
     */
    public int getGameID() {
        return gameID;
    }
    /**
     * Getter of the current status of the game
     * @return gameStatus
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    /**
     * Setter of the current status of the game
     * @param gameStatus : the status I want to set
     */
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
        notifyGameEvent(this, GameEvent.GAME_STATUS_CHANGED);
    }
    /**
     * Getter of the number of players in the game
     * @return numOfPlayers
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    /**
     * Getter of the Players' order
     * @return playerOrder : list of the players in the order they're playing
     */
    public List<Player> getPlayerOrder() {
        return new ArrayList<>(playerOrder);
    }
    /**
     * This method removes the specified player from the game (without the possibility of
     * reconnecting later) without notifying the other clients (if the player quits before
     * the game starts, the other players do not need to know)
     * @param player : the player I want to remove
     */
    public void silentlyRemovePlayer(Player player) {
        this.playerOrder.remove(player);
    }
    /**
     * This method removes the specified player from the game (without the possibility of
     * reconnecting later) notifying other clients (if the player leaves during the game,
     * the other clients need to know that, so that they don't wait for him)
     * @param player : the player I want to remove
     */
    public void removePlayer(Player player) {
        this.playerOrder.remove(player);
        if (gameStatus != GameStatus.WAITING_FOR_PLAYERS)
            numOfPlayers--;
        if (playerOrder.isEmpty())
            gameRunningStatus = GameRunningStatus.TO_CANCEL_NOW;
        notifyPlayerLeft(this, player.getNickname());
    }
    /**
     * Getter of the Player (giving his nickname)
     * @param nickname: the nickname of the desired player
     * @return nicknameToPlayer : the Player
     */
    public Player getPlayerByNickname(String nickname){
        return nicknameToPlayer.get(nickname);
    }
    /**
     * Getter of the current Player
     * @return currentPlayer
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    /**
     * Setter of the current Player
     * @param currentPlayer: the player to set as the current one
     */
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    /**
     * Getter of the game's chat
     * @return chat
     */
    public List<Message> getChat() {
        return new ArrayList<>(chat);
    }
    /**
     * This method add a message to the game's chat
     */
    public void addMessageToChat(Message message) {
        chat.add(message);
        notifyGameEvent(this, GameEvent.MESSAGE);
    }
    /**
     * Getter of the resource cards' deck
     * @return resourceCardDeck
     */
    public Deck<PlayableCard> getResourceCardsDeck() {
        return resourceCardsDeck;
    }
    /**
     * Getter of the gold cards' deck
     * @return goldCardDeck
     */
    public Deck<PlayableCard> getGoldCardsDeck() {
        return goldCardsDeck;
    }
    /**
     * Getter of the objective cards' deck
     * @return objectiveCardDeck
     */
    public Deck<ObjectiveCard> getObjectiveCardsDeck() {
        return objectiveCardsDeck;
    }
    /**
     * This method adds a resource card to the 2 revealed resource cards in the centre
     */
    public void addRevealedResourceCard(PlayableCard card) {
        revealedResourceCards.add(card);
    }
    /**
     * This method adds a gold card to the 2 revealed gold cards in the centre
     */
    public void addRevealedGoldCard(PlayableCard card) {
        revealedGoldCards.add(card);
    }
    /**
     * This method removes a resource card to the 2 revealed resource cards in the centre
     */
    public PlayableCard removeRevealedResourceCard(int index) {
        return revealedResourceCards.remove(index);
    }
    /**
     * This method adds a gold card to the 2 revealed gold cards in the centre
     */
    public PlayableCard removeRevealedGoldCard(int index) {
        return revealedGoldCards.remove(index);
    }
    /**
     * Getter of the 2 revealed resource cards in the centre of the board
     * @return revealedResourceCards: an arraylist with all the revealed resource cards
     */
    public List<PlayableCard> getRevealedResourceCards() {
        return new ArrayList<>(revealedResourceCards);
    }
    /**
     * Getter of the 2 revealed gold cards in the centre of the board
     * @return revealedGoldCards: an arraylist with all the revealed gold cards
     */
    public List<PlayableCard> getRevealedGoldCards() {
        return new ArrayList<>(revealedGoldCards);
    }
    /**
     * Adds a player to the game
     * @param player Player
     */
    public boolean addPlayer(Player player) throws MaxNumOfPlayersInException {
        if (playerOrder.size() >= numOfPlayers)
            throw new MaxNumOfPlayersInException();

        playerOrder.add(player);
        player.addObserver(this);
        nicknameToPlayer.put(player.getNickname(), player);
        notifyPlayerJoined(this, player.getNickname());
        if (playerOrder.size() == numOfPlayers) {
            gameStatus = GameStatus.READY;
            return true;
        }
        return false;
    }
    /**
     * Setter of player's color
     * @param player : the player
     * @param color : the color
     */
    public void setPlayerColor(Player player, Color color) throws ColorAlreadyChosenException {
        for (Player p : playerOrder)
            if (p.getColor() == color)
                throw new ColorAlreadyChosenException();
        player.setColor(color);
    }
    /**
     * This method notifies the observers that an exception has been thrown
     * @param player: the player that has thrown the exception
     * @param error: the error that has been thrown
     */
    public void exceptionThrown(Player player, String error) {
        notifyException(error, player.getNickname());
    }
    /**
     * This method sets up gold and resource cards by shuffling the decks and revealing two resource cards and 2 gold cards
     */
    public void setupResourceAndGoldCards(){
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
    /**
     * Deals an initial card to each player
     */
    public void dealInitialCards(){
        initialCardsDeck.shuffle();
        for (Player player : playerOrder){
            player.setInitialCard(initialCardsDeck.drawACard());
        }

        gameStatus = GameStatus.SETUP_1;
        notifyGameEvent(this, GameEvent.SETUP_1);
    }
    /**
     * This method sets up a player's hand by drawing 2 resource cards and 1 objective cards
     */
    public void setupHands() {

        for (Player player : playerOrder) {
            List<PlayableCard> hand = new ArrayList<>();
            hand.add(resourceCardsDeck.drawACard());
            hand.add(resourceCardsDeck.drawACard());
            hand.add(goldCardsDeck.drawACard());
            player.setupHand(hand);
            for (PlayableCard card : hand)
                card.flip();
        }
    }
    /**
     * This method sets up the 2 common objective cards by drawing and adding them to the centre of the board
     */
    public void setupCommonObjectiveCards(){
        objectiveCardsDeck.shuffle();
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard());
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard());
    }
    /**
     * This method sets up the secret objective card of each player,
     * giving them 2 objective cards to choose from
     */
    public void setupSecretObjectiveCards(){
        for (Player player : playerOrder) {
            List<ObjectiveCard> secretObjectiveCards = new ArrayList<>();
            secretObjectiveCards.add(objectiveCardsDeck.drawACard());
            secretObjectiveCards.add(objectiveCardsDeck.drawACard());
            for (ObjectiveCard objectiveCard : secretObjectiveCards)
                objectiveCard.flip();
            player.setupSecretObjectiveCards(secretObjectiveCards);
        }
        gameStatus = GameStatus.SETUP_2;
        notifyGameEvent(this, GameEvent.SETUP_2);
    }
    /**
     * This method shuffle the player's list placing them in a random order
     */
    public void shufflePlayerList(){
        Collections.shuffle(this.playerOrder);
        currentPlayer = playerOrder.getFirst();
    }

    /**
     * Setter of the new current player (at the end of each turn,
     * the current player becomes the next one in the player's list)
     */
    public boolean nextPlayer() {
        Player nextPlayer = currentPlayer;
        boolean newRound = false;
        do {
            int index = getPlayerOrder().indexOf(nextPlayer);
            if (index < getPlayerOrder().size() - 1) {
                nextPlayer = getPlayerOrder().get(index + 1);
            } else {
                nextPlayer = getPlayerOrder().getFirst();
                newRound = true;
            }
        } while (!nextPlayer.isInGame());
        setCurrentPlayer(nextPlayer);
        if (gameStatus != GameStatus.LAST_ROUND || !newRound)
            notifyTurnChanged(nextPlayer.getNickname());
        return newRound;
    }
    /**
     * Getter of the GameRunningStatus
     * @return gameRunningStatus
     */
    public GameRunningStatus getGameRunningStatus() {
        return gameRunningStatus;
    }
    /**
     * Setter of the GameRunnigStatus
     * @param gameRunningStatus : the gameRunningStatus I want to set
     */
    public void setGameRunningStatus(GameRunningStatus gameRunningStatus) {
        this.gameRunningStatus = gameRunningStatus;
        notifyGameRunningStatus(this, gameRunningStatus);
    }
    /**
     * This method is used to notify observers that an event has occurred to the player
     * @param player : the player who generated the event
     * @param playerEvent : the event that the player has generated
     */
    @Override
    public void update(Player player, PlayerEvent playerEvent) {
        notifyPlayerEvent(this, playerEvent, player, player.getNickname());
    }
    /**
     * This method is used to update the connection status of a player
     * @param player : the player who generated the event
     * @param inGame : true if the player is still in game, false otherwise
     */
    @Override
    public void updatePlayerConnectionStatus(Player player, boolean inGame) {
        notifyPlayerConnectionStatus(this, player.getNickname(), inGame);
    }
    /**
     * This method is used to update the status of the player to ready (in the initial setup of the game)
     * @param player : the player who is ready to play
     */
    @Override
    public void updateReady(Player player) {
        notifyPlayerReady(player.getNickname());
    }

}
