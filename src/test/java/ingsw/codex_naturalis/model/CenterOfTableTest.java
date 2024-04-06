package ingsw.codex_naturalis.model;


import ingsw.codex_naturalis.exceptions.EmptyDeckException;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CenterOfTableTest {
    CenterOfTable centerOfTable;

    /**
     * setUp before each test
     */
    @BeforeEach
    void setUp(){
        centerOfTable = new CenterOfTable();
    }

    /**
     * Test that checks the first card of the Deck shows the front side correctly
     */
    @Test
    void testDeckFirstCard() {
        //controllo correttezza del fronte della prima carta risorsa nel mazzo
        PlayableCard c = centerOfTable.removeFromResourceCardsDeck();

        c.flip();

        assertEquals(Symbol.INSECT, c.getKingdom());

        assertEquals(Symbol.EMPTY, c.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.INSECT, c.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomRightCorner().getSymbol());

        assertTrue(c.getTopLeftCorner().isCovered());
        assertFalse(c.getTopRightCorner().isCovered());
        assertFalse(c.getBottomLeftCorner().isCovered());
        assertFalse(c.getBottomRightCorner().isCovered());

        //controllo correttezza del fronte della prima carta oro nel mazzo
        PlayableCard f = centerOfTable.removeFromGoldCardsDeck();

        f.flip();

        assertEquals(Symbol.INSECT, f.getKingdom());

        assertEquals(Symbol.EMPTY, f.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomRightCorner().getSymbol());

        assertFalse(f.getTopLeftCorner().isCovered());
        assertFalse(f.getTopRightCorner().isCovered());
        assertTrue(f.getBottomLeftCorner().isCovered());
        assertTrue(f.getBottomRightCorner().isCovered());

        //controllo correttezza del back della prima carta risorsa nel mazzo

        c.flip();
        assertEquals(Symbol.INSECT, c.getKingdom());

        assertEquals(Symbol.EMPTY, c.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomRightCorner().getSymbol());

        assertFalse(c.getTopLeftCorner().isCovered());
        assertFalse(c.getTopRightCorner().isCovered());
        assertFalse(c.getBottomLeftCorner().isCovered());
        assertFalse(c.getBottomRightCorner().isCovered());

        //controllo correttezza del back della prima carta oro nel mazzo

        f.flip();
        assertEquals(Symbol.INSECT, f.getKingdom());

        assertEquals(Symbol.EMPTY, f.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomRightCorner().getSymbol());

        assertFalse(f.getTopLeftCorner().isCovered());
        assertFalse(f.getTopRightCorner().isCovered());
        assertFalse(f.getBottomLeftCorner().isCovered());
        assertFalse(f.getBottomRightCorner().isCovered());
    }

    @Test
    void testDeckLastCard(){
        //controllo correttezza del fronte dell'ultima carta risorsa nel mazzo
        PlayableCard c = centerOfTable.testRemoveFromResourceCardsDeck();

        c.flip();

        assertEquals(Symbol.FUNGI, c.getKingdom());

        assertEquals(Symbol.FUNGI, c.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getTopRightCorner().getSymbol());
        assertEquals(Symbol.FUNGI, c.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomRightCorner().getSymbol());

        assertFalse(c.getTopLeftCorner().isCovered());
        assertFalse(c.getTopRightCorner().isCovered());
        assertFalse(c.getBottomLeftCorner().isCovered());
        assertTrue(c.getBottomRightCorner().isCovered());

        //controllo correttezza del fronte dell'ultima carta oro nel mazzo
        PlayableCard f = centerOfTable.testRemoveFromGoldCardsDeck();

        f.flip();

        assertEquals(Symbol.FUNGI, f.getKingdom());

        assertEquals(Symbol.EMPTY, f.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.QUILL, f.getBottomRightCorner().getSymbol());

        assertTrue(f.getTopLeftCorner().isCovered());
        assertFalse(f.getTopRightCorner().isCovered());
        assertFalse(f.getBottomLeftCorner().isCovered());
        assertFalse(f.getBottomRightCorner().isCovered());

        //controllo correttezza del back dell'ultima carta risorsa nel mazzo

        c.flip();
        assertEquals(Symbol.FUNGI, c.getKingdom());

        assertEquals(Symbol.EMPTY, c.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getBottomRightCorner().getSymbol());

        assertFalse(c.getTopLeftCorner().isCovered());
        assertFalse(c.getTopRightCorner().isCovered());
        assertFalse(c.getBottomLeftCorner().isCovered());
        assertFalse(c.getBottomRightCorner().isCovered());

        //controllo correttezza del back dell'ultima carta oro nel mazzo

        f.flip();
        assertEquals(Symbol.FUNGI, f.getKingdom());

        assertEquals(Symbol.EMPTY, f.getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getBottomRightCorner().getSymbol());

        assertFalse(f.getTopLeftCorner().isCovered());
        assertFalse(f.getTopRightCorner().isCovered());
        assertFalse(f.getBottomLeftCorner().isCovered());
        assertFalse(f.getBottomRightCorner().isCovered());
    }

    /**
     * it tests if shuffle works correctly
     */
    @Test
    void testShuffle(){
        PlayableCard resourceCard = centerOfTable.testGetFromResourceCardsDeck();
        PlayableCard goldCard = centerOfTable.testGetFromGoldCardsDeck();

        //shuffle gold and resource decks
        centerOfTable.setRevealedCards();

        PlayableCard resouceCard1 = centerOfTable.testGetFromResourceCardsDeck();
        assertNotEquals(resourceCard,resouceCard1);

        PlayableCard goldCard1 = centerOfTable.testGetFromGoldCardsDeck();
        assertNotEquals(goldCard,goldCard1);


    }

    @Test
    void testEmptyDeckException() {
        for (int i = 0; i < 40; i++) {
            centerOfTable.removeFromResourceCardsDeck();
        }
        assertThrows(EmptyDeckException.class, () -> {
            centerOfTable.removeFromResourceCardsDeck();
        });
    }
}