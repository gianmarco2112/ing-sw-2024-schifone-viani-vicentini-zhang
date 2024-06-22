package ingsw.codex_naturalis.server.model;

import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.common.enumerations.Symbol;
/**
 * This class contains all the default values
 */
public class DefaultValue {
    /**
     * Max number of player in a game
     */
    public final static int maxNumOfPlayer = 4;
    /**
     * Min number of player in a game
     */
    public final static int minNumOfPlayer = 2;
    /**
     * Total number of Resource cards in the game
     */
    public final static int totNumOfResourceCards = 40;
    /**
     * Total number of Gold cards in the game
     */
    public final static int totNumOfGoldCards = 40;
    /**
     * Total number of Objective cards in the game
     */
    public final static int totNumOfObjectiveCards = 16;
    /**
     * Total number of Initial cards in the game
     */
    public final static int totNumOfInitialCards = 6;
    /**
     * To reset the text's color to default
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * To set the text's color to red (color of resource "FUNGI")
     */
    public static final String FungiColor = "\u001B[31m";
    /**
     * To set the text's color to green (color of resource "PLANT")
     */
    public static final String PlantColor = "\u001B[32m";
    /**
     * To set the text's color to gold
     */
    public static final String GoldColor = "\u001B[33m";
    /**
     * To set the text's color to light-blue (color of resource "ANIMAL")
     */
    public static final String AnimalColor = "\u001B[34m";
    /**
     * To set the text's color to purple (color of resource "INSECT")
     */
    public static final String InsectColor = "\u001B[35m";
    /**
     * To set the text's color to white
     */
    public static final String WHITE = "\u001B[37m";
    /**
     * To draw the cards in TUI (with the view used to draw the cards in player's hand)
     * @param side : the side (of the card) to draw
     * @param kingdom : the central symbol of the card (used to know the color to use to draw the card)
     */
    public static String getTUIHandCardSideTemplate(PlayableSide side, Symbol kingdom){
        String cardTemplate = "";
        boolean tl_is_covered = side.getTopLeftCorner().isCovered();
        boolean tr_is_covered = side.getTopRightCorner().isCovered();
        boolean bl_is_covered = side.getBottomLeftCorner().isCovered();
        boolean br_is_covered = side.getBottomRightCorner().isCovered();

        if (tl_is_covered && tr_is_covered && bl_is_covered && br_is_covered){
            cardTemplate =
                    kingdom.getColor() + "╭───────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───────────╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───────────╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭────────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───────────╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬─────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯     ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───────────╯" + ANSI_RESET;
        } else if (tl_is_covered && tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭───────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴────────╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴────────╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭────────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴────────╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬─────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯     ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴────────╯" + ANSI_RESET;
        } else if (tl_is_covered && tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭───────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰────────┴──╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰────────┴──╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭────────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰────────┴──╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬─────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯     ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰────────┴──╯" + ANSI_RESET;
        } else if (tl_is_covered && tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭───────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│           │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮     ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴─────┴──╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬────────╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯        │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮     ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴─────┴──╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭────────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│        ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮     ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴─────┴──╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭──┬─────┬──╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╯     ╰──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├──╮     ╭──┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│  │     │  │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰──┴─────┴──╯" + ANSI_RESET;
        }
        StringBuilder cardTemplateBuilder = new StringBuilder(cardTemplate);

        if (side.getBottomRightCorner().getSymbol() != Symbol.EMPTY && side.getBottomRightCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(108, 109, side.getBottomRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getBottomLeftCorner().getSymbol() != Symbol.EMPTY && side.getBottomLeftCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(98, 99, side.getBottomLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopRightCorner().getSymbol() != Symbol.EMPTY && side.getTopRightCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(39, 40, side.getTopRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopLeftCorner().getSymbol() != Symbol.EMPTY && side.getTopLeftCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(29, 30, side.getTopLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }

        cardTemplate = cardTemplateBuilder.toString();

        return cardTemplate;
    }
    /**
     * To draw the cards in TUI (with the view used to draw the cards into the player's area)
     * @param side : the side (of the card) to draw
     * @param kingdom : the central symbol of the card (used to know the color to use to draw the card)
     */
    public static String getTUIPlayerAreaCardSideTemplate(PlayableSide side, Symbol kingdom){
        String cardTemplate = "";
        boolean tl_is_covered = (side.getTopLeftCorner().getSymbol() == Symbol.COVERED);
        boolean tr_is_covered = (side.getTopRightCorner().getSymbol() == Symbol.COVERED);
        boolean bl_is_covered = (side.getBottomLeftCorner().getSymbol()  == Symbol.COVERED);
        boolean br_is_covered = (side.getBottomRightCorner().getSymbol() == Symbol.COVERED);

        if (tl_is_covered && tr_is_covered && bl_is_covered && br_is_covered){
            cardTemplate =
                    kingdom.getColor() + "╭───╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─┘ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ └─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─╥─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─╨─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰───╯" + ANSI_RESET;
        } else if (tl_is_covered && tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭───╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─┐ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╞═╡ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─▞─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && !bl_is_covered && br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─╥─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╞═╃─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (tl_is_covered && tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭───╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ┌─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─▚─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ╞═╡" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─╥─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─╄═╡" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─┴─╯" + ANSI_RESET;
        } else if (tl_is_covered && tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭───╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│   │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─╥─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─╨─╯" + ANSI_RESET;
        } else if (!tl_is_covered && tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╞═╅─┤" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─╨─╯" + ANSI_RESET;
        } else if (tl_is_covered && !tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─┬─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ │ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "├─╆═╡" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─╨─╯" + ANSI_RESET;
        } else if (!tl_is_covered && !tr_is_covered && !bl_is_covered && !br_is_covered) {
            cardTemplate =
                    kingdom.getColor() + "╭─╥─╮" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╞═╬═╡" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "│ ║ │" + ANSI_RESET + "\n" +
                    kingdom.getColor() + "╰─╨─╯" + ANSI_RESET;
        }
        StringBuilder cardTemplateBuilder = new StringBuilder(cardTemplate);

        if (side.getBottomRightCorner().getSymbol() != Symbol.EMPTY && side.getBottomRightCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(53, 54, side.getBottomRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getBottomLeftCorner().getSymbol() != Symbol.EMPTY && side.getBottomLeftCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(51, 52, side.getBottomLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopRightCorner().getSymbol() != Symbol.EMPTY && side.getTopRightCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(23, 24, side.getTopRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopLeftCorner().getSymbol() != Symbol.EMPTY && side.getTopLeftCorner().getSymbol() != Symbol.COVERED){
            cardTemplateBuilder.replace(21, 22, side.getTopLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        cardTemplate = cardTemplateBuilder.toString();

        return cardTemplate;
    }
}
