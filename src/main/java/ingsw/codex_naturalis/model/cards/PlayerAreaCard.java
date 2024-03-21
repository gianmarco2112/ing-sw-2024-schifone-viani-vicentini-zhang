package ingsw.codex_naturalis.model.cards;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.PlayerArea;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.calcPoints.CalcPointsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.coverCorners.CoverCornersStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.getSymbols.GetSymbolsStrategy;
import ingsw.codex_naturalis.model.cards.playerAreaCardStrategy.isPlayable.IsPlayableStrategy;
import ingsw.codex_naturalis.model.enumerations.Symbol;

/**
 * PlayerAreaCard's class
 * This is an abstract class that represents the common methods and attributes of the cards
 * that a player could use in his PlayerArea (each player would have in it 1 initialCard
 * and an undefined number of resourceCards and goldCards) or have in his hand (in this
 * case just resourceCards and/or goldCards).
 * The attribute "kingdom" represents the color of the card: it could be PLANT -> green,
 * ANIMAL -> blue, FUNGI ->red, INSECT ->purple, it couldn't be any of the Objects (QUll,
 * INKWELL, MANUSCRIPT) and it couldn't be EMPTY
 * The attribute "points" represents the number of points that the card gives to the player
 * when it is used.
 *
 */
public abstract class PlayerAreaCard {

    /**
     * Kingdom
     */
    private Symbol kingdom;

    /**
     * Top left corner
     */
    private Corner topLeftCorner;
    /**
     * Top right corenr
     */
    private Corner topRightCorner;
    /**
     * Bottom left corner
     */
    private Corner bottomLeftCorner;
    /**
     * Bottom right corner
     */
    private Corner bottomRightCorner;


    private IsPlayableStrategy isPlayableStrategy;
    private CoverCornersStrategy coverCornersStrategy;
    private GetSymbolsStrategy getSymbolsStrategy;
    private CalcPointsStrategy calcPointsStrategy;


    /**
     * Constructor
     * @param kingdom represents the color of the card
     * @param topLeftCorner of the card
     * @param topRightCorner of the card
     * @param bottomRightCorner of the card
     * @param bottomLeftCorner of the card
     */
    public PlayerAreaCard(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner){
        this.kingdom = kingdom;
        this.topLeftCorner = topLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }


    /**
     *
     * @return
     */
    public Symbol getKingdom() { return kingdom; }
    public Corner getTopLeftCorner(){ return topLeftCorner; }
    public Corner getTopRightCorner() { return topRightCorner; }
    public Corner getBottomLeftCorner() { return bottomLeftCorner; }
    public Corner getBottomRightCorner() { return bottomRightCorner; }

    public void setIsPlayableStrategy(IsPlayableStrategy isPlayableStrategy) { this.isPlayableStrategy = isPlayableStrategy; }
    public void setCoverCornersStrategy(CoverCornersStrategy coverCornersStrategy) { this.coverCornersStrategy = coverCornersStrategy; }
    public void setGetSymbolsStrategy(GetSymbolsStrategy getSymbolsStrategy) { this.getSymbolsStrategy = getSymbolsStrategy; }
    public void setCalcPointsStrategy(CalcPointsStrategy calcPointsStrategy) { this.calcPointsStrategy = calcPointsStrategy; }


    /**
     * This method is called when a player wants to play a card in a certain position into his PlayerArea.
     * It verifies if the pointed PlayerAreaCard is placeable in the area on coordinates x and y.
     * It checks that there isn't a card already placed in those coordinates and that there's at least
     * one card on which the pointed card will be placed.
     * It also checks that every corner the PlayableAreaCard is going to cover, is a visible corner.
     * @param x (cartesian coordinate x of the position where the player wants to place the card)
     * @param y (cartesian coordinate y of the position where the player wants to place the card)
     * @return true (if the card is playable in that position),
     * false (if the card isn't playable in that position)
     */
    public Boolean isPlayable(int x, int y){
        return isPlayableStrategy.run(x,y);
    }

    /**
     * This method is called when a player wants to play a card into his PlayerArea.
     * It changes the attributes of the corners of the card is going to cover in order to
     * make them actually covered and to remove the symbols from the symbol count of the player.
     * @param x (cartesian coordinate x of the position where the player wants to place the card)
     * @param y (cartesian coordinate y of the position where the player wants to place the card)
     */
    public void coverCorners(int x, int y){
        coverCornersStrategy.run(x,y);
    }

    /**
     * This method is called when a player successfully plays a card into his PlayerArea.
     * It is aimed to get the list of all the resources (present on the played card)
     * to add into the player's resources count.
     */
    public void getSymbols(){
        getSymbolsStrategy.run();
    }

    /**
     * This method calculates the number of points given by the card
     * */
    public void calcPoints(){
        calcPointsStrategy.run();
    }

    public abstract void played(PlayerArea playerArea, int x, int y);
}
