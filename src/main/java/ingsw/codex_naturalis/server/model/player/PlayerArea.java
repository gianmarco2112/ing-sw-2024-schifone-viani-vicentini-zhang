package ingsw.codex_naturalis.server.model.player;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ingsw.codex_naturalis.common.util.ListKeyDeserializer;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.common.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.common.enumerations.Symbol;

import java.util.*;

/**
 * The player's area
 */
public class PlayerArea {

    /**
     * Part of the model's view: immutable overview of the Player's Area
     * (intended for the Controller in order to manage the game)
     */

    public record Immutable(@JsonDeserialize(keyUsing = ListKeyDeserializer.class)Map<List<Integer>,PlayableCard.Immutable> area,
                            Map<ExtremeCoordinate, Integer> extremeCoordinates,
                            Map<Symbol, Integer> numOfSymbols, ObjectiveCard.Immutable objectiveCard,
                            int points, int extraPoints) {}
    /**
     * Part of the model's view: immutable overview of the Player's Area
     * (intended for the View -> the player's secret objective card and the points it gives are hidden)
     */

    public record ImmutableHidden(@JsonDeserialize(keyUsing = ListKeyDeserializer.class)Map<List<Integer>,PlayableCard.Immutable> area,
                            Map<ExtremeCoordinate, Integer> extremeCoordinates,
                            Map<Symbol, Integer> numOfSymbols,
                            int points) {}

    /**
     * Getter of the immutable Player's Area
     */
    public PlayerArea.Immutable getImmutablePlayerArea(){

        ObjectiveCard.Immutable immObjectiveCard = null;
        if (objectiveCard != null)
            immObjectiveCard = objectiveCard.getImmutableObjectiveCard();

        Map<List<Integer>, PlayableCard.Immutable> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            immutableArea.put(key, area.get(key).getImmutablePlayableCard());
        }

        Map<Symbol,Integer> noCornerNumOfSymbols = new HashMap<>();
        for (Map.Entry<Symbol,Integer> entry : numOfSymbols.entrySet())
            if (entry.getKey() != Symbol.COVERED)
                noCornerNumOfSymbols.put(entry.getKey(), entry.getValue());

        return new PlayerArea.Immutable(immutableArea, new HashMap<>(extremeCoordinates), noCornerNumOfSymbols,
                immObjectiveCard, points, extraPoints);

    }
    /**
     * Getter of the immutable Player's Area
     * (without the secret objective cards and the relative points)
     */
    public PlayerArea.ImmutableHidden getImmutableHiddenPlayerArea(){

        Map<List<Integer>, PlayableCard.Immutable> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet())
            immutableArea.put(key, area.get(key).getImmutablePlayableCard());

        Map<Symbol,Integer> noCornerNumOfSymbols = new HashMap<>();
        for (Map.Entry<Symbol,Integer> entry : numOfSymbols.entrySet())
            if (entry.getKey() != Symbol.COVERED)
                noCornerNumOfSymbols.put(entry.getKey(), entry.getValue());

        return new PlayerArea.ImmutableHidden(immutableArea, new HashMap<>(extremeCoordinates), noCornerNumOfSymbols, points);

    }

    /**
     * This Map represents the player's area with all the cards he has placed
     * The key represents the coordinates, the value represents the card played
     */
    private final Map<List<Integer>, PlayableCard> area;

    /**
     * Extreme (maximum and minimum) coordinates of the cards in the Player's area
     * (used from the pattern objective card strategy)
     */
    private final Map<ExtremeCoordinate, Integer> extremeCoordinates;

    /**
     * Counter of the Player's Symbols
     */
    private final Map<Symbol, Integer> numOfSymbols;

    /**
     * The Player's secret objective card
     */
    private ObjectiveCard objectiveCard;

    /**
     * The points the Player has
     */
    private int points;

    /**
     * Counts the total number of extra points from the fulfilment of the
     * objectives on the Objective cards.
     *
     */
    private int extraPoints;

    /**
     * Constructor
     */
    public PlayerArea(){
        this.objectiveCard = null;
        this.area = new LinkedHashMap<>();
        this.points = 0;
        this.extraPoints = 0;
        this.extremeCoordinates = new HashMap<>();
        this.numOfSymbols = new HashMap<>();
        initializeExtremeCoordinates();
        initializeNumOfSymbols();
    }
    /**
     * To initialize each of the Extreme Coordinates to 0
     */
    private void initializeExtremeCoordinates() {
        this.extremeCoordinates.put(ExtremeCoordinate.MAX_X, 0);
        this.extremeCoordinates.put(ExtremeCoordinate.MAX_Y, 0);
        this.extremeCoordinates.put(ExtremeCoordinate.MIN_X, 0);
        this.extremeCoordinates.put(ExtremeCoordinate.MIN_Y, 0);
    }
    /**
     * To initialize the Player's counters (of each Symbol) to 0
     */
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
    /**
     * Getter of the Extreme Coordinates
     * @return extremeCoordinates
     */
    public int getExtremeCoordinate(ExtremeCoordinate extremeCoordinate) {
        return extremeCoordinates.get(extremeCoordinate);
    }
    /**
     * Setter of the Extreme Coordinates
     * @param extremeCoordinate: list of the previous extreme coordinates
     * @param value : the value of the new extreme coordinates
     */
    private void setExtremeCoordinate(ExtremeCoordinate extremeCoordinate, Integer value) {
        extremeCoordinates.put(extremeCoordinate, value);
    }

    /**
     * Getter of the Player's points
     * @return Points
     */
    public int getPoints() { return points; }
    /**
     * Setter of the Player's point
     * @param points to add to the player
     */
    public void setPoints(int points) { this.points = points; }

    /**
     * Getter of the Player's objective card
     * @return Objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }
    /**
     * Setter of the Player's objective card
     * @param objectiveCard Objective card
     */
    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     *  Getter of the Player's extra points
     * @return Extra points
     */
    public int getExtraPoints() {
        return extraPoints;
    }
    /**
     * Setter of the Player's extra points
     * @param extraPoints Extra points to set
     */
    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    /**
     * Checks if there's a card on the specified coordinates
     * @param x Coordinate x
     * @param y Coordinate y
     * @return true (if there is a card on the specified coordinates) or false (otherwise)
     */
    public Boolean containsCardOnCoordinates(int x, int y){
        return area.containsKey(new ArrayList<>(List.of(x,y)));
    }
    /**
     * Returns the card placed on the specified coordinates
     * @param x Coordinate x
     * @param y Coordinate y
     * @return the card
     */
    public PlayableCard getCardOnCoordinates(int x, int y){
        return area.get(new ArrayList<>(List.of(x,y)));
    }
    /**
     * Returns the count of the specified Symbol
     * @param symbol
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
    /**
     * Put the initial card (on the side chosen by the Player) at the origin of the Player's Area
     * @param initialCard
     */
    public void setInitialCard(PlayableCard initialCard) {

        area.put(new ArrayList<>(List.of(0,0)), initialCard);
        initialCard.play(this, 0, 0);

    }
    /**
     * Places the given card at the specified coordinates and adjusts the max coordinates
     * @param card the card to play
     * @param x    coordinate x
     * @param y    coordinate y
     */
    public void setCardOnCoordinates(PlayableCard card, int x, int y){
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

    }
    /**
     * Getter of the coordinates of a card
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
     * This method returns a copy of the Player's Area where cards are substituted
     * by a boolean, initialized to False
     * (it is necessary for the pattern objective card strategy)
     * @return the boolean copy of the Player's Area
     */
    public Map<List<Integer>,Boolean> getAreaToMark(){
        Map<List<Integer>, Boolean> areaToMark = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            areaToMark.put(key, false);
        }
        return areaToMark;
    }

    /**
     * Getter of the Player's Area
     * @return Area
     */
    public Map<List<Integer>, PlayableCard> getArea(){
        return area;
    }
    /**
     * Getter of the Symbols' counters
     * @return numOfSymbols
     */
    public Map<Symbol, Integer> getNumOfSymbols() {
        return numOfSymbols;
    }
}
