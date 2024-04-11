package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.Observable;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Game class
 */
public class Game extends Observable<Event> {


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
    private List<PlayableCard> revealedResourceCards;

    /**
     * The two revealed gold cards
     */
    private List<PlayableCard> revealedGoldCards;

    /**
     * The two common objective cards
     */
    private List<ObjectiveCard> commonObjectiveCards;

    private List<Message> messages;



    /**
     * Constructor
     */
    public Game(int gameID, int maxNumOfPlayers) {
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
        this.maxNumOfPlayers = maxNumOfPlayers;
        this.messages = new ArrayList<>();
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
        switch (gameStatus) {
            case SETUP -> notifyObservers(Event.SETUP_STATUS, "");
            case GAMEPLAY -> notifyObservers(Event.GAMEPLAY_STATUS, "");
        }
    }

    public int getMaxNumOfPlayers() {
        return maxNumOfPlayers;
    }

    public List<Player> getPlayerOrder() {
        return new ArrayList<>(playerOrder);
    }
    public void setPlayerOrder(List<Player> playerOrder){
        this.playerOrder = playerOrder;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer, String nickname){
        this.currentPlayer = currentPlayer;
        notifyObservers(Event.TURN_CHANGED, nickname);
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }
    public void setMessages(List<Message> messages, String nickname){
        this.messages = messages;
        notifyObservers(Event.MESSAGE_SENT, nickname);
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
        notifyObservers(Event.REVEALED_RESOURCE_CARDS_CHANGED, nickname);
    }

    public List<PlayableCard> getRevealedGoldCards() {
        return new ArrayList<>(revealedGoldCards);
    }
    public void setRevealedGoldCards(List<PlayableCard> revealedGoldCards, String nickname){
        this.revealedGoldCards = revealedGoldCards;
        notifyObservers(Event.REVEALED_GOLD_CARDS_CHANGED, nickname);
    }






    @Deprecated
    public void seTCommonObjectiveCards(List<PlayerArea> playerAreas){
        objectiveCardsDeck.shuffle();
        /*this.commonObjectiveCards.add(objectiveCardsDeck.drawACard());
        this.commonObjectiveCards.add(objectiveCardsDeck.drawACard());*/
    }

    @Deprecated
    public void shufflePlayerList(){
        Collections.shuffle(this.playerOrder);
    }

    @Deprecated
    public void setRevealedCards(){
        resourceCardsDeck.shuffle();
       /* this.revealedResourceCards.add(resourceCardsDeck.drawACard());
        this.revealedResourceCards.add(resourceCardsDeck.drawACard());
        for (PlayableCard card : revealedResourceCards){
            card.flip();
        }

        goldCardsDeck.shuffle();
        this.revealedGoldCards.add(goldCardsDeck.drawACard());
        this.revealedGoldCards.add(goldCardsDeck.drawACard());
        for (PlayableCard card : revealedGoldCards){
            card.flip();
        }*/
    }

    /**
     * Deals an initial card to each player
     */
    @Deprecated
    public void dealInitialCard(){
        /*initialCardsDeck.shuffle();
        for (Player player : playerOrder){
            player.setInitialCard(initialCardsDeck.drawACard());
        }*/
    }

    /**
     * Adds a player to the game
     * @param player Player
     */
    @Deprecated
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
}
