package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;

public class DefaultValue {
    public final static int maxNumOfPlayer = 4;
    public final static int minNumOfPlayer = 2;
    public final static int totNumOfResourceCards = 40;
    public final static int totNumOfGoldCards = 40;
    public final static int totNumOfObjectiveCards = 16;
    public final static int totNumOfInitialCards = 6;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String FungiColor = "\u001B[31m";
    public static final String PlantColor = "\u001B[32m";
    public static final String GoldColor = "\u001B[33m";
    public static final String AnimalColor = "\u001B[34m";
    public static final String InsectColor = "\u001B[35m";
    public static final String WHITE = "\u001B[37m";

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

        if (side.getBottomRightCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(108, 109, side.getBottomRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getBottomLeftCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(98, 99, side.getBottomLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopRightCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(39, 40, side.getTopRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopLeftCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(29, 30, side.getTopLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }

        cardTemplate = cardTemplateBuilder.toString();

        return cardTemplate;
    }

    public static String getTUIPlayerAreaCardSideTemplate(PlayableSide side, Symbol kingdom){
        String cardTemplate = "";
        boolean tl_is_covered = side.getTopLeftCorner().isCovered();
        boolean tr_is_covered = side.getTopRightCorner().isCovered();
        boolean bl_is_covered = side.getBottomLeftCorner().isCovered();
        boolean br_is_covered = side.getBottomRightCorner().isCovered();

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

        if (side.getBottomRightCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(53, 54, side.getBottomRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getBottomLeftCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(51, 52, side.getBottomLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopRightCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(23, 24, side.getTopRightCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        if (side.getTopLeftCorner().getSymbol() != Symbol.EMPTY){
            cardTemplateBuilder.replace(21, 22, side.getTopLeftCorner().getSymbol().getColoredChar() + kingdom.getColor());
        }
        cardTemplate = cardTemplateBuilder.toString();

        return cardTemplate;
    }
}
