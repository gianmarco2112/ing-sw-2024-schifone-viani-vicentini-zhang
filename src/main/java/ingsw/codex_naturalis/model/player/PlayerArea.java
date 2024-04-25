package ingsw.codex_naturalis.model.player;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ingsw.codex_naturalis.distributed.util.ListKeyDeserializer;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.cards.objective.SymbolsObjectiveCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * The player area
 */
public class PlayerArea {

    public record Immutable(@JsonDeserialize(keyUsing = ListKeyDeserializer.class)Map<List<Integer>,PlayableCard.Immutable> area,
                            Map<ExtremeCoordinate, Integer> extremeCoordinates,
                            Map<Symbol, Integer> numOfSymbols, ObjectiveCard.Immutable objectiveCard,
                            int points, int extraPoints) {}

    public record ImmutableHidden(@JsonDeserialize(keyUsing = ListKeyDeserializer.class)Map<List<Integer>,PlayableCard.Immutable> area,
                            Map<ExtremeCoordinate, Integer> extremeCoordinates,
                            Map<Symbol, Integer> numOfSymbols,
                            int points) {}


    public PlayerArea.Immutable getImmutablePlayerArea(){

        ObjectiveCard.Immutable immObjectiveCard = null;
        if (objectiveCard != null)
            immObjectiveCard = objectiveCard.getImmutableObjectiveCard();

        Map<List<Integer>, PlayableCard.Immutable> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            immutableArea.put(key, area.get(key).getImmutablePlayableCard());
        }

        return new PlayerArea.Immutable(immutableArea, extremeCoordinates, numOfSymbols,
                immObjectiveCard, points, extraPoints);

    }

    public PlayerArea.ImmutableHidden getImmutableHiddenPlayerArea(){

        Map<List<Integer>, PlayableCard.Immutable> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            immutableArea.put(key, area.get(key).getImmutablePlayableCard());
        }
        return new PlayerArea.ImmutableHidden(immutableArea, extremeCoordinates, numOfSymbols, points);

    }

    /**
     * This Map represents the player area with all the cards he has placed.
     * The key represents the coordinates, the value the card played.
     */
    private final Map<List<Integer>, PlayableCard> area;

    /**
     * Extreme coordinates of the player area, used from the pattern objective card strategy
     */
    private final Map<ExtremeCoordinate, Integer> extremeCoordinates;

    /**
     * The count of the symbols of the player
     */
    private final Map<Symbol, Integer> numOfSymbols;

    /**
     * The player's objective card
     */
    private ObjectiveCard objectiveCard;

    /**
     * The points the player has
     */
    private int points;

    /**
     * The extra points from the objective cards
     */
    private int extraPoints;


    /**
     * Constructor
     */
    public PlayerArea(){
        this.objectiveCard = new SymbolsObjectiveCard("aaa", 1, new HashMap<>());
        this.area = new LinkedHashMap<>();
        this.points = 0;
        this.extraPoints = 0;
        this.extremeCoordinates = new HashMap<>();
        this.numOfSymbols = new HashMap<>();
        initializeExtremeCoordinates();
        initializeNumOfSymbols();
    }
    private void initializeExtremeCoordinates() {
        this.extremeCoordinates.put(ExtremeCoordinate.MAX_X, 0);
        this.extremeCoordinates.put(ExtremeCoordinate.MAX_Y, 0);
        this.extremeCoordinates.put(ExtremeCoordinate.MIN_X, 0);
        this.extremeCoordinates.put(ExtremeCoordinate.MIN_Y, 0);
    }
    private void initializeNumOfSymbols() {
        this.numOfSymbols.put(Symbol.INSECT,0);
        this.numOfSymbols.put(Symbol.FUNGI,0);
        this.numOfSymbols.put(Symbol.ANIMAL,0);
        this.numOfSymbols.put(Symbol.PLANT,0);
        this.numOfSymbols.put(Symbol.MANUSCRIPT,0);
        this.numOfSymbols.put(Symbol.QUILL,0);
        this.numOfSymbols.put(Symbol.INKWELL,0);
        this.numOfSymbols.put(Symbol.EMPTY,0);
        this.numOfSymbols.put(Symbol.COVERED,0);
    }


    public int getExtremeCoordinate(ExtremeCoordinate extremeCoordinate) {
        return extremeCoordinates.get(extremeCoordinate);
    }
    private void setExtremeCoordinate(ExtremeCoordinate extremeCoordinate, Integer value) {
        extremeCoordinates.put(extremeCoordinate, value);
    }

    /**
     * Points getter
     * @return Points
     */
    public int getPoints() { return points; }
    /**
     * Points setter
     * @param points to add to the player
     */
    public void setPoints(int points) { this.points = points; }

    /**
     * Objective card getter
     * @return Objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }
    /**
     * Objective card setter
     * @param objectiveCard Objective card
     */
    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     * Extra points getter
     * @return Extra points
     */
    public int getExtraPoints() {
        return extraPoints;
    }
    /**
     * Extra points setter
     * @param extraPoints Extra points to set
     */
    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    /**
     * Checks if there's a card on the coordinates given
     * @param x Coordinate x
     * @param y Coordinate y
     * @return true or false
     */
    public Boolean containsCardOnCoordinates(int x, int y){
        return area.containsKey(new ArrayList<>(List.of(x,y)));
    }
    /**
     * Returns the card placed on the given coordinates
     * @param x Coordinate x
     * @param y Coordinate y
     * @return the card
     */
    public PlayableCard getCardOnCoordinates(int x, int y){
        return area.get(new ArrayList<>(List.of(x,y)));
    }
    /**
     * Returns the count of the given symbol
     * @param symbol symbol
     * @return the count
     */
    public Integer getNumOfSymbol(Symbol symbol){
        return numOfSymbols.get(symbol);
    }
    /**
     * Decreases by one the count of the given symbol
     * @param symbol Symbol
     */
    public void decrNumOfSymbol(Symbol symbol){
        numOfSymbols.put(symbol, getNumOfSymbol(symbol)-1);
    }
    /**
     * Increases by one the count of the given symbol
     * @param symbol Symbol
     */
    public void incrNumOfSymbol(Symbol symbol){
        numOfSymbols.put(symbol, getNumOfSymbol(symbol)+1);
    }
    public void setInitialCard(PlayableCard initialCard) {

        area.put(new ArrayList<>(List.of(0,0)), initialCard);
        initialCard.play(this, 0, 0);

    }
    /**
     * Places the given card on the given coordinates and adjusts the max coordinates
     * @param card the card to play
     * @param x coordinate x
     * @param y coordinate y
     */
    public void setCardOnCoordinates(PlayableCard card, int x, int y, String nickname){
        area.put(new ArrayList<>(List.of(x,y)), card);
        if(x > getExtremeCoordinate(ExtremeCoordinate.MAX_X))
            setExtremeCoordinate(ExtremeCoordinate.MAX_X, x);
        if(x < getExtremeCoordinate(ExtremeCoordinate.MIN_X))
            setExtremeCoordinate(ExtremeCoordinate.MIN_X, x);
        if(y > getExtremeCoordinate(ExtremeCoordinate.MAX_Y))
            setExtremeCoordinate(ExtremeCoordinate.MAX_Y, y);
        if(y < getExtremeCoordinate(ExtremeCoordinate.MIN_Y))
            setExtremeCoordinate(ExtremeCoordinate.MIN_Y, y);

        card.play(this, x, y);

        //notifyObservers(GameEvent.PLAYER_AREA_CHANGED, nickname);
    }
    /**
     * Method to get the coordinates of a card
     * @param card Card
     * @return The coordinates
     */
    public List<Integer> getCoordinatesOfCard(PlayableSide card){
        for (Map.Entry<List<Integer>, PlayableCard> entry : area.entrySet()) {
            if (entry.getValue().getCurrentPlayableSide() == card) {
                return entry.getKey();
            }
        }
        return new ArrayList<>();
    }
    /**
     * Method to get the player area with a boolean instead of the card, it is necessary for the pattern
     * objective card strategy
     * @return The area to mark
     */
    public Map<List<Integer>,Boolean> getAreaToMark(){
        Map<List<Integer>, Boolean> areaToMark = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            areaToMark.put(key, false);
        }
        return areaToMark;
    }

    /**
     * Area getter
     * @return Area
     */
    public Map<List<Integer>, PlayableCard> getArea(){
        return area;
    }
    public Map<Symbol, Integer> getNumOfSymbols() {
        return numOfSymbols;
    }
}
