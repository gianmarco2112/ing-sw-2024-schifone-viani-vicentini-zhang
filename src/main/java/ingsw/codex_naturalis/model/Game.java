package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.Observable;
import ingsw.codex_naturalis.model.player.Player;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Game class
 */
public class Game extends Observable {

    public record Immutable(int gameID, GameStatus gameStatus, List<String> playerOrder,
                            String currentPlayer, List<Player.ImmutableHidden> hiddenPlayers,
                            Player.Immutable player, PlayableCard.Immutable topResourceCardDeck,
                            PlayableCard.Immutable topGoldCardDeck, List<PlayableCard.Immutable> revealedResourceCards,
                            List<PlayableCard.Immutable> revealedGoldCards, List<ObjectiveCard.Immutable> commonObjectiveCards,
                            List<Message> chat) implements Serializable {
        @Serial
        private static final long serialVersionUID = 4L; }

    public Game.Immutable getImmutableGame(String playerNicknameReceiver) {

        List<String> playerOrderString = new ArrayList<>();
        Player.Immutable playerReceiver = null;
        List<Player.ImmutableHidden> immutableHiddenPlayers = new ArrayList<>();
        for (Player player : playerOrder) {
            playerOrderString.add(player.getNickname());
            if (!player.getNickname().equals(playerNicknameReceiver))
                immutableHiddenPlayers.add(player.getImmutableHiddenPlayer());
            else
                playerReceiver = player.getImmutablePlayer();
        }

        List<PlayableCard.Immutable> immutableRevealedResourceCards = getImmutableRevealedResourceCards();

        List<PlayableCard.Immutable> immutableRevealedGoldCards = getImmutableRevealedGoldCards();

        List<ObjectiveCard.Immutable> immutableCommonObjectiveCards = new ArrayList<>();
        for (ObjectiveCard card : commonObjectiveCards)
            immutableCommonObjectiveCards.add(card.getImmutableObjectiveCard());

        return new Immutable(gameID, gameStatus, playerOrderString,
                currentPlayer.getNickname(), immutableHiddenPlayers, playerReceiver,
                getResourceCardsDeck().getFirstCard().getImmutablePlayableCard(),
                getGoldCardsDeck().getFirstCard().getImmutablePlayableCard(), immutableRevealedResourceCards,
                immutableRevealedGoldCards, immutableCommonObjectiveCards, chat);

    }

    public List<PlayableCard.Immutable> getImmutableRevealedResourceCards() {
        List<PlayableCard.Immutable> immutableRevealedResourceCards = new ArrayList<>();
        for (PlayableCard card : revealedResourceCards)
            immutableRevealedResourceCards.add(card.getImmutablePlayableCard());
        return immutableRevealedResourceCards;
    }

    public List<PlayableCard.Immutable> getImmutableRevealedGoldCards() {
        List<PlayableCard.Immutable> immutableRevealedGoldCards = new ArrayList<>();
        for (PlayableCard card : revealedGoldCards)
            immutableRevealedGoldCards.add(card.getImmutablePlayableCard());
        return immutableRevealedGoldCards;
    }


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
    private final int numOfPlayers;

    /**
     * Contains all the players of the game, ordered by the turn they play
     */
    private List<Player> playerOrder;

    public void setColorToPlayer(Color color, Player player) throws ColorAlreadyChosenException{
        for (Player p : playerOrder) {
            if (p.getColor() == color)
                throw new ColorAlreadyChosenException();
        }
        player.setColor(color);
    }

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
    private List<PlayableCard> revealedResourceCards;

    /**
     * The two revealed gold cards
     */
    private List<PlayableCard> revealedGoldCards;

    /**
     * The two common objective cards
     */
    private List<ObjectiveCard> commonObjectiveCards;

    private List<Message> chat;



    /**
     * Constructor
     */
    public Game(int gameID, int numOfPlayers) {

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
        this.gameStatus = GameStatus.LOBBY;
        this.numOfPlayers = numOfPlayers;
        this.chat = new ArrayList<>();
        this.revealedResourceCards = new ArrayList<>();
        this.revealedGoldCards = new ArrayList<>();
        this.commonObjectiveCards = new ArrayList<>();
    }



    public int getGameID() {
        return gameID;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(GameStatus gameStatus, String nickname){
        this.gameStatus = gameStatus;
        notifyObservers(this, Event.GAME_STATUS_CHANGED, nickname);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public List<Player> getPlayerOrder() {
        return new ArrayList<>(playerOrder);
    }
    public void setPlayerOrder(List<Player> playerOrder){
        this.playerOrder = playerOrder;
    }

    public Player getPlayerByNickname(String nickname){
        for (Player player : playerOrder)
            if (player.getNickname().equals(nickname))
                return player;
        return null;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer, String nickname){
        this.currentPlayer = currentPlayer;
        notifyObservers(this, Event.TURN_CHANGED, nickname);
    }

    public List<Message> getChat() {
        return new ArrayList<>(chat);
    }
    public void setMessages(List<Message> messages, String nickname){
        this.chat = messages;
        notifyObservers(this, Event.MESSAGE_SENT, nickname);
    }

    public List<ObjectiveCard> getCommonObjectiveCards(){
        return new ArrayList<>(commonObjectiveCards);
    }
    public void setCommonObjectiveCards(List<ObjectiveCard> commonObjectiveCards){
        this.commonObjectiveCards = commonObjectiveCards;
    }

    public Deck<PlayableCard> getInitialCardsDeck() {
        return initialCardsDeck;
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

    public List<PlayableCard> getRevealedResourceCards() {
        return new ArrayList<>(revealedResourceCards);
    }
    public void setRevealedResourceCards(List<PlayableCard> revealedResourceCards, String nickname){
        this.revealedResourceCards = revealedResourceCards;
        notifyObservers(this, Event.REVEALED_RESOURCE_CARDS_CHANGED, nickname);
    }

    public List<PlayableCard> getRevealedGoldCards() {
        return new ArrayList<>(revealedGoldCards);
    }
    public void setRevealedGoldCards(List<PlayableCard> revealedGoldCards, String nickname){
        this.revealedGoldCards = revealedGoldCards;
        notifyObservers(this, Event.REVEALED_GOLD_CARDS_CHANGED, nickname);
    }

    /**
     * Adds a player to the game
     * @param player Player
     */
    public void addPlayer(Player player) throws NicknameAlreadyExistsException, MaxNumOfPlayersInException {
        if(playerOrder.size() >= numOfPlayers)
            throw new MaxNumOfPlayersInException();

        for(Player p : playerOrder){
            if(player.getNickname().equals(p.getNickname())){
                throw new NicknameAlreadyExistsException();
            }
        }
        playerOrder.add(player);
        notifyObservers(this, Event.PLAYER, player.getNickname());
    }




    public void setupResourceAndGoldCards(){
        resourceCardsDeck.shuffle();
        this.revealedResourceCards.add(resourceCardsDeck.drawACard(""));
        this.revealedResourceCards.add(resourceCardsDeck.drawACard(""));
        for (PlayableCard card : revealedResourceCards){
            card.flip("");
        }

        goldCardsDeck.shuffle();
        this.revealedGoldCards.add(goldCardsDeck.drawACard(""));
        this.revealedGoldCards.add(goldCardsDeck.drawACard(""));
        for (PlayableCard card : revealedGoldCards){
            card.flip("");
        }

    }

    /**
     * Deals an initial card to each player
     */
    public void dealInitialCards(){
        initialCardsDeck.shuffle();
        for (Player player : playerOrder){
            player.setInitialCard(initialCardsDeck.drawACard(""));
        }

        notifyObservers(this, Event.SETUP_1, "");
    }

    public void setupHands() {

        for (Player player : playerOrder) {
            List<PlayableCard> hand = new ArrayList<>();
            hand.add(resourceCardsDeck.drawACard(""));
            hand.add(resourceCardsDeck.drawACard(""));
            hand.add(goldCardsDeck.drawACard(""));
            player.setupHand(hand);
        }

        notifyObservers(this, Event.HANDS_SETUP, "");
    }

    public void setupCommonObjectiveCards(){
        objectiveCardsDeck.shuffle();
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard(""));
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard(""));
    }

    @Deprecated
    public void shufflePlayerList(){
        Collections.shuffle(this.playerOrder);
    }


}
