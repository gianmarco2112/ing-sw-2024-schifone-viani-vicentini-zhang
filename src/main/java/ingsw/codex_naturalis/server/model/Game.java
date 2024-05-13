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

    private final Map<String, Player> nicknameToPlayer = new HashMap<>();

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

    private final List<Message> chat;

    private GameRunningStatus gameRunningStatus = GameRunningStatus.RUNNING;



    /**
     * Constructor
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



    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    public int getGameID() {
        return gameID;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
        notifyGameEvent(this, GameEvent.GAME_STATUS_CHANGED);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public List<Player> getPlayerOrder() {
        return new ArrayList<>(playerOrder);
    }

    public void silentlyRemovePlayer(Player player) {
        this.playerOrder.remove(player);
    }

    public void removePlayer(Player player) {
        this.playerOrder.remove(player);
        numOfPlayers--;
        notifyPlayerLeft(this, player.getNickname());
    }

    public Player getPlayerByNickname(String nickname){
        return nicknameToPlayer.get(nickname);
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public List<Message> getChat() {
        return new ArrayList<>(chat);
    }

    public void addMessageToChat(Message message) {
        chat.add(message);
        notifyGameEvent(this, GameEvent.MESSAGE);
    }

    public Deck<PlayableCard> getResourceCardsDeck() {
        return resourceCardsDeck;
    }

    public Deck<PlayableCard> getGoldCardsDeck() {
        return goldCardsDeck;
    }

    public Deck<ObjectiveCard> getObjectiveCardsDeck() {
        return objectiveCardsDeck;
    }

    public void addRevealedResourceCard(PlayableCard card) {
        revealedResourceCards.add(card);
    }
    public void addRevealedGoldCard(PlayableCard card) {
        revealedGoldCards.add(card);
    }
    public PlayableCard removeRevealedResourceCard(int index) {
        return revealedResourceCards.remove(index);
    }
    public PlayableCard removeRevealedGoldCard(int index) {
        return revealedGoldCards.remove(index);
    }
    public List<PlayableCard> getRevealedResourceCards() {
        return new ArrayList<>(revealedResourceCards);
    }
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

    public void setPlayerColor(Player player, Color color) throws ColorAlreadyChosenException {
        for (Player p : playerOrder)
            if (p.getColor() == color)
                throw new ColorAlreadyChosenException();
        player.setColor(color);
    }

    public void exceptionThrown(Player player, String error) {
        notifyException(error, player.getNickname());
    }

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
