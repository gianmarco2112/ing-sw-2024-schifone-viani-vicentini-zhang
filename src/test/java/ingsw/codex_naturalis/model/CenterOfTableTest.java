package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.gold.GoldCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CenterOfTableTest {
    CenterOfTable centerOfTable;
    @BeforeEach
    void setUp(){
        centerOfTable = new CenterOfTable();
    }

    /**
     * Test that checks all cards of the Resource and Gold Cards Deck are showing front at the beginning
     */
    @Test
    void testIsShowingFront() {
        //controllo che tutte le carte inizializzate nel mazzo risorsa mostrino il retro
        for(int i = 0; i<40; i++){
            assertFalse(centerOfTable.removeFromResourceCardsDeck().isShowingFront());
        }
        //controllo che tutte le carte inizializzate nel mazzo oro mostrino il retro
        for(int i = 0; i<40; i++){
            assertFalse(centerOfTable.removeFromGoldCardsDeck().isShowingFront());
        }
    }

    /**
     * Test that checks the first card of the Deck shows the front side correctly
     */
    @Test
    void testShowFront() {
        //controllo correttezza del fronte della prima carta risorsa nel mazzo
        ResourceCard c = centerOfTable.removeFromResourceCardsDeck();

        assertEquals(Symbol.INSECT, c.getFront().getKingdom());

        assertEquals(Symbol.EMPTY, c.getFront().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.INSECT, c.getFront().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getFront().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getFront().getBottomRightCorner().getSymbol());

        assertTrue(c.getFront().getTopLeftCorner().isCovered());
        assertFalse(c.getFront().getTopRightCorner().isCovered());
        assertFalse(c.getFront().getBottomLeftCorner().isCovered());
        assertFalse(c.getFront().getBottomRightCorner().isCovered());

        assertEquals(1, c.getFront().getPoints());

        //controllo correttezza del fronte della prima carta oro nel mazzo
        GoldCard f = centerOfTable.removeFromGoldCardsDeck();

        assertEquals(Symbol.INSECT, f.getFront().getKingdom());

        assertEquals(Symbol.EMPTY, f.getFront().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getFront().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getFront().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getFront().getBottomRightCorner().getSymbol());

        assertFalse(f.getFront().getTopLeftCorner().isCovered());
        assertFalse(f.getFront().getTopRightCorner().isCovered());
        assertTrue(f.getFront().getBottomLeftCorner().isCovered());
        assertTrue(f.getFront().getBottomRightCorner().isCovered());

        assertEquals(5, f.getFront().getPoints());

        assertEquals(5,f.getFront().getRequirements().get(Symbol.INSECT));
    }

    @Test
    void testShowBack() {
        //controllo correttezza del back della prima carta risorsa nel mazzo
        ResourceCard c = centerOfTable.removeFromResourceCardsDeck();

        assertEquals(Symbol.INSECT, c.getBack().getKingdom());

        assertEquals(Symbol.EMPTY, c.getBack().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBack().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBack().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBack().getBottomRightCorner().getSymbol());

        assertFalse(c.getBack().getTopLeftCorner().isCovered());
        assertFalse(c.getBack().getTopRightCorner().isCovered());
        assertFalse(c.getBack().getBottomLeftCorner().isCovered());
        assertFalse(c.getBack().getBottomRightCorner().isCovered());

        assertEquals(Symbol.INSECT, c.getBack().getResource());

        //controllo correttezza del back della prima carta oro nel mazzo
        GoldCard f = centerOfTable.removeFromGoldCardsDeck();

        assertEquals(Symbol.INSECT, f.getBack().getKingdom());

        assertEquals(Symbol.EMPTY, f.getBack().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBack().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBack().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBack().getBottomRightCorner().getSymbol());

        assertFalse(f.getBack().getTopLeftCorner().isCovered());
        assertFalse(f.getBack().getTopRightCorner().isCovered());
        assertFalse(f.getBack().getBottomLeftCorner().isCovered());
        assertFalse(f.getBack().getBottomRightCorner().isCovered());

        assertEquals(Symbol.INSECT, f.getBack().getResource());
    }
}