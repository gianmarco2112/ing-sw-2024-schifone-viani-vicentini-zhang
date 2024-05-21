package ingsw.codex_naturalis.server.model;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.*;

/**
 * Game class
 */
public class Game extends GameObservable implements PlayerObserver {

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
    private int numOfPlayers;

    /**
     * Contains all the players of the game, ordered by the turn they play
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
     * Initial cards deck1
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
     * The game chat (contains all the messages sent by the players during the game)
     */
    private final List<Message> chat;
    /**
     * The status of the game (when the game is created is initialized to running)
     */
    private GameRunningStatus gameRunningStatus = GameRunningStatus.RUNNING;

    /**
     * Constructor
     * @param gameID : the ID of the game
     * @param numOfPlayers : number of players in the game
     */
    public Game(int gameID, int numOfPlayers) {

        if (numOfPlayers > 4 || numOfPlayers < 2)
            throw new InvalidNumOfPlayersException();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String initialCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/initialCards.json";
            String resourceCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/resourceCards.json";
            String goldCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/goldCards.json";
            String objectiveCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/objectiveCards.json";

            List<PlayableCard> initialCards = objectMapper.readValue(new File(initialCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            List<PlayableCard> resourceCards = objectMapper.readValue(new File(resourceCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            List<PlayableCard> goldCards = objectMapper.readValue(new File(goldCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            List<ObjectiveCard> objectiveCards = objectMapper.readValue(new File(objectiveCardsJsonFilePath), new TypeReference<List<ObjectiveCard>>() {});

            this.initialCardsDeck = new Deck<>(initialCards);
            this.resourceCardsDeck = new Deck<>(resourceCards);
            this.goldCardsDeck = new Deck<>(goldCards);
            this.objectiveCardsDeck = new Deck<>(objectiveCards);
        } catch (IOException e){
            System.err.println("ERROR while opening json file");
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
     * Setter if the current status of the game
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
     * @return playerOrder : a list of the players in the order they're playing
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
     * Getter of the 2 revealed resource card in the centre of the board
     * @return revealedResourceCards: an arraylist with all the revealed resource cards
     */
    public List<PlayableCard> getRevealedResourceCards() {
        return new ArrayList<>(revealedResourceCards);
    }
    /**
     * Getter of the 2 revealed gold card in the centre of the board
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

    public void shufflePlayerList(){
        Collections.shuffle(this.playerOrder);
        currentPlayer = playerOrder.getFirst();
    }

    /**
     * Sets the new current player
     */
    public void nextPlayer() {
        Player nextPlayer;
        do {
            int index = getPlayerOrder().indexOf(getCurrentPlayer());
            if (index < getPlayerOrder().size() - 1) {
                nextPlayer = getPlayerOrder().get(index + 1);
            } else {
                nextPlayer = getPlayerOrder().getFirst();
            }
        } while (!nextPlayer.isInGame());
        setCurrentPlayer(nextPlayer);
        notifyTurnChanged(nextPlayer.getNickname());
    }

    public GameRunningStatus getGameRunningStatus() {
        return gameRunningStatus;
    }

    public void setGameRunningStatus(GameRunningStatus gameRunningStatus) {
        this.gameRunningStatus = gameRunningStatus;
        notifyGameRunningStatus(this, gameRunningStatus);
    }

    @Override
    public void update(Player player, PlayerEvent playerEvent) {
        notifyPlayerEvent(this, playerEvent, player, player.getNickname());
    }

    @Override
    public void updatePlayerConnectionStatus(Player player, boolean inGame) {
        notifyPlayerConnectionStatus(this, player.getNickname(), inGame);
    }

}
