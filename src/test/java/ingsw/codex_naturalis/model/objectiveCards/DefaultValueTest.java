package ingsw.codex_naturalis.model.objectiveCards;

import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultValueTest {
    PlayableSide side1;
    PlayableSide side2;
    PlayableSide side3;
    PlayableSide side4;
    PlayableSide side5;
    PlayableSide side6;
    PlayableSide side7;
    PlayableSide side8;
    PlayableSide side9;
    PlayableSide side10;
    PlayableSide side11;
    PlayableSide side12;
    PlayableSide side13;
    PlayableSide side14;
    PlayableSide side15;
    PlayableSide side16;

    @BeforeEach
    void setup(){
        side1 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true)
        );
        side2 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true)
        );
        side3 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true)
        );
        side4 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true)
        );
        side5 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true)
        );
        side6 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true)
        );
        side7 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true)
        );
        side8 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true)
        );
        side9 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false)
        );
        side10 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false)
        );
        side11 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false)
        );
        side12 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false)
        );
        side13 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false)
        );
        side14 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false)
        );
        side15 = new PlayableSide(
                new Corner(Symbol.COVERED, true),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false)
        );
        side16 = new PlayableSide(
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false),
                new Corner(Symbol.EMPTY, false)
        );
    }

    @Test
    void TUIHandCardSiteTemplateTest(){
        assertEquals(
                DefaultValue.WHITE + "╭───────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰───────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side1, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰───────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side2, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭────────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰───────────╯" + DefaultValue.ANSI_RESET,
                DefaultValue.getTUIHandCardSideTemplate(side3, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬─────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯     ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰───────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side4, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭───────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side5, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side6, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭────────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side7, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬─────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯     ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴────────╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side8, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭───────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰────────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side9, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰────────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side10, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭────────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰────────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side11, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬─────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯     ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE+ "│        ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰────────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side12, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭───────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│           │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮     ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴─────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side13, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬────────╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯        │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮     ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴─────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side14, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭────────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│        ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮     ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴─────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side15, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭──┬─────┬──╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╯     ╰──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├──╮     ╭──┤" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│  │     │  │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰──┴─────┴──╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIHandCardSideTemplate(side16, Symbol.EMPTY));
    }

    @Test
    void TUIPlayerAreaCardSiteTemplateTest(){
        assertEquals(
                DefaultValue.WHITE + "╭───╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰───╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side1, Symbol.EMPTY));
        assertEquals(
                DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "├─┘ │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                        DefaultValue.WHITE + "╰───╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side2, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ └─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰───╯" + DefaultValue.ANSI_RESET,
                DefaultValue.getTUIPlayerAreaCardSideTemplate(side3, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─╥─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─╨─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰───╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side4, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭───╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─┐ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side5, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╞═╡ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side6, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─▞─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side7, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─╥─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╞═╃─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side8, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭───╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ┌─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side9, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─▚─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side10, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ╞═╡" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side11, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─╥─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─╄═╡" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─┴─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side12, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭───╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│   │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─╥─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─╨─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side13, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╞═╅─┤" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─╨─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side14, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─┬─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ │ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "├─╆═╡" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─╨─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side15, Symbol.EMPTY));
        assertEquals(
        DefaultValue.WHITE + "╭─╥─╮" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╞═╬═╡" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "│ ║ │" + DefaultValue.ANSI_RESET + "\n" +
                DefaultValue.WHITE + "╰─╨─╯" + DefaultValue.ANSI_RESET
                , DefaultValue.getTUIPlayerAreaCardSideTemplate(side16, Symbol.EMPTY));
    }
}
