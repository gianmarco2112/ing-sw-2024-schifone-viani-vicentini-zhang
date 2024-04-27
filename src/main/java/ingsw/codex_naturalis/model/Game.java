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
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.model.util.GameObservable;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.util.PlayerEvent;
import ingsw.codex_naturalis.model.util.PlayerObserver;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Game class
 */
public class Game extends GameObservable implements PlayerObserver {

    public record Immutable(int gameID, GameStatus gameStatus,
                            List<String> playerOrderNicknames,
                            String currentPlayerNickname,
                            List<Player.ImmutableHidden> hiddenPlayers,
                            Player.Immutable player,
                            PlayableCard.Immutable topResourceCard,
                            List<PlayableCard.Immutable> revealedResourceCards,
                            PlayableCard.Immutable topGoldCard,
                            List<PlayableCard.Immutable> revealedGoldCards,
                            List<ObjectiveCard.Immutable> commonObjectiveCards,
                            List<Message> chat) {}

    public Game.Immutable getImmutableGame(String playerNicknameReceiver) {

        int gameID = this.gameID;

        GameStatus gameStatus = this.gameStatus;

        List<String> playerOrderNicknames = new ArrayList<>();

        String currentPlayerNickname;

        List<Player.ImmutableHidden> immHiddenPlayers = new ArrayList<>();

        Player.Immutable playerReceiver = null;

        PlayableCard.Immutable topResourceCard = null;

        List<PlayableCard.Immutable> immRevealedResourceCards = new ArrayList<>();

        PlayableCard.Immutable topGoldCard = null;

        List<PlayableCard.Immutable> immRevealedGoldCards = new ArrayList<>();

        List<ObjectiveCard.Immutable> immCommonObjectiveCards = new ArrayList<>();

        List<Message> playerReceiverChat = new ArrayList<>();


        for (Player player : playerOrder) {
            playerOrderNicknames.add(player.getNickname());
            if (!player.getNickname().equals(playerNicknameReceiver))
                immHiddenPlayers.add(player.getImmutableHiddenPlayer());
            else
                playerReceiver = player.getImmutablePlayer();
        }
        if (currentPlayer == null) {
            playerOrderNicknames.clear();
            playerOrderNicknames.add("");
            currentPlayerNickname = "";
        }
        else
            currentPlayerNickname = currentPlayer.getNickname();

        if (resourceCardsDeck.getFirstCard() != null)
            topResourceCard = resourceCardsDeck.getFirstCard().getImmutablePlayableCard();

        for (PlayableCard card : revealedResourceCards)
            immRevealedResourceCards.add(card.getImmutablePlayableCard());

        if (goldCardsDeck.getFirstCard() != null)
            topGoldCard = goldCardsDeck.getFirstCard().getImmutablePlayableCard();

        for (PlayableCard card : revealedGoldCards)
            immRevealedGoldCards.add(card.getImmutablePlayableCard());

        for (ObjectiveCard card : commonObjectiveCards)
            immCommonObjectiveCards.add(card.getImmutableObjectiveCard());

        for (Message message : chat)
            if (message.getReceivers().contains(playerNicknameReceiver) || message.getSender().equals(playerNicknameReceiver))
                playerReceiverChat.add(message);

        return new Immutable(gameID, gameStatus, playerOrderNicknames,
                currentPlayerNickname, immHiddenPlayers, playerReceiver,
                topResourceCard, immRevealedResourceCards,
                topGoldCard, immRevealedGoldCards, immCommonObjectiveCards, playerReceiverChat);

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
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
        notifyObservers(this, GameEvent.GAME_STATUS_CHANGED);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public List<Player> getPlayerOrder() {
        return new ArrayList<>(playerOrder);
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
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public List<Message> getChat() {
        return new ArrayList<>(chat);
    }

    public void addMessageToChat(Message message) {
        chat.add(message);
        notifyObservers(this, GameEvent.MESSAGE);
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
    public void addPlayer(Player player) throws NicknameAlreadyExistsException, MaxNumOfPlayersInException {
        if (playerOrder.size() >= numOfPlayers)
            throw new MaxNumOfPlayersInException();

        for (Player p : playerOrder){
            if(player.getNickname().equals(p.getNickname())){
                throw new NicknameAlreadyExistsException();
            }
        }
        playerOrder.add(player);
        player.addObserver(this);
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

        notifyObservers(this, GameEvent.SETUP_1);
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
        notifyObservers(this, GameEvent.SETUP_2);
    }

    public void shufflePlayerList(){
        Collections.shuffle(this.playerOrder);
        currentPlayer = playerOrder.getFirst();
    }



    @Override
    public void update(Player player, PlayerEvent playerEvent) {
        notifyObservers(this, playerEvent, player);
    }

}
