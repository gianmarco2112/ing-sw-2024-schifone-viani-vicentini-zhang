package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandPlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.SymbolsObjectiveCard;
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
     * Test that checks all cards of the Resource and Gold Cards Deck are showing back at the beginning
     * At the same time, it tests removeFromResource/Gold/ObjectiveCardsDeck
     */
    @Test
    void testRemoveFromDecks() {
        //controllo che tutte le carte inizializzate nel mazzo risorsa mostrino il retro
        for(int i = 0; i<40; i++){
            assertFalse(centerOfTable.removeFromResourceCardsDeck().isShowingFront());
        }
        //controllo che tutte le carte inizializzate nel mazzo oro mostrino il retro
        for(int i = 0; i<40; i++){
            assertFalse(centerOfTable.removeFromGoldCardsDeck().isShowingFront());
        }
        //controllo che siano state inizializzate tutte le carte obiettivo
        for(int i = 0; i<16; i++){
            assertNotNull(centerOfTable.removeFromObjectiveCardsDeck());
        }
    }
    @Test
    void testRemoveFromRevealedCards(){
        centerOfTable.setRevealedCards();
        //rimuovo una carta rivelata ed essendo rivelata deve mostrare il fronte
        assertTrue(centerOfTable.removeFirstFromRevealedResourceCards().isShowingFront());
        assertTrue(centerOfTable.removeLastFromRevealedResourceCards().isShowingFront());
        assertTrue(centerOfTable.removeFirstFromRevealedGoldCards().isShowingFront());
        assertTrue(centerOfTable.removeLastFromRevealedGoldCards().isShowingFront());
    }

    /**
     * Test that checks the first card of the Deck shows the front side correctly
     */
    @Test
    void testDeckFirstCard() {
        //controllo correttezza del fronte della prima carta risorsa nel mazzo
        HandPlayableCard c = centerOfTable.removeFromResourceCardsDeck();


        assertEquals(Symbol.INSECT, c.getHandPlayableFront().getKingdom());

        assertEquals(Symbol.EMPTY, c.getHandPlayableFront().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.INSECT, c.getHandPlayableFront().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableFront().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableFront().getBottomRightCorner().getSymbol());

        assertTrue(c.getHandPlayableFront().getTopLeftCorner().isCovered());
        assertFalse(c.getHandPlayableFront().getTopRightCorner().isCovered());
        assertFalse(c.getHandPlayableFront().getBottomLeftCorner().isCovered());
        assertFalse(c.getHandPlayableFront().getBottomRightCorner().isCovered());

        //controllo correttezza del fronte della prima carta oro nel mazzo
        HandPlayableCard f = centerOfTable.removeFromGoldCardsDeck();

        assertEquals(Symbol.INSECT, f.getHandPlayableFront().getKingdom());

        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getBottomRightCorner().getSymbol());

        assertFalse(f.getHandPlayableFront().getTopLeftCorner().isCovered());
        assertFalse(f.getHandPlayableFront().getTopRightCorner().isCovered());
        assertTrue(f.getHandPlayableFront().getBottomLeftCorner().isCovered());
        assertTrue(f.getHandPlayableFront().getBottomRightCorner().isCovered());

        //controllo correttezza del back della prima carta risorsa nel mazzo

        assertEquals(Symbol.INSECT, c.getHandPlayableBack().getKingdom());

        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getBottomRightCorner().getSymbol());

        assertFalse(c.getHandPlayableBack().getTopLeftCorner().isCovered());
        assertFalse(c.getHandPlayableBack().getTopRightCorner().isCovered());
        assertFalse(c.getHandPlayableBack().getBottomLeftCorner().isCovered());
        assertFalse(c.getHandPlayableBack().getBottomRightCorner().isCovered());

        //controllo correttezza del back della prima carta oro nel mazzo

        assertEquals(Symbol.INSECT, f.getHandPlayableBack().getKingdom());

        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getBottomRightCorner().getSymbol());

        assertFalse(f.getHandPlayableBack().getTopLeftCorner().isCovered());
        assertFalse(f.getHandPlayableBack().getTopRightCorner().isCovered());
        assertFalse(f.getHandPlayableBack().getBottomLeftCorner().isCovered());
        assertFalse(f.getHandPlayableBack().getBottomRightCorner().isCovered());


        //controllo correttezza della prima carta obiettivo
        ObjectiveCard objectiveCard = centerOfTable.removeFromObjectiveCardsDeck();
        PatternObjectiveCard patternObjectiveCard = (PatternObjectiveCard) objectiveCard;

        assertEquals(2,patternObjectiveCard.getPoints());
        assertEquals(Symbol.FUNGI,patternObjectiveCard.getSymbolAt(0,0));
        assertEquals(Symbol.FUNGI,patternObjectiveCard.getSymbolAt(1,1));
        assertEquals(Symbol.FUNGI,patternObjectiveCard.getSymbolAt(2,2));
        assertEquals(2,patternObjectiveCard.getMaxX());
        assertEquals(2,patternObjectiveCard.getMaxY());
        assertEquals(0,patternObjectiveCard.getMinX());
        assertEquals(0,patternObjectiveCard.getMinY());
    }

    @Test
    void testDeckLastCard(){
        //controllo correttezza del fronte dell'ultima carta risorsa nel mazzo
        HandPlayableCard c = centerOfTable.testRemoveFromResourceCardsDeck();


        assertEquals(Symbol.FUNGI, c.getHandPlayableFront().getKingdom());

        assertEquals(Symbol.FUNGI, c.getHandPlayableFront().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableFront().getTopRightCorner().getSymbol());
        assertEquals(Symbol.FUNGI, c.getHandPlayableFront().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableFront().getBottomRightCorner().getSymbol());

        assertFalse(c.getHandPlayableFront().getTopLeftCorner().isCovered());
        assertFalse(c.getHandPlayableFront().getTopRightCorner().isCovered());
        assertFalse(c.getHandPlayableFront().getBottomLeftCorner().isCovered());
        assertTrue(c.getHandPlayableFront().getBottomRightCorner().isCovered());

        //controllo correttezza del fronte dell'ultima carta oro nel mazzo
        HandPlayableCard f = centerOfTable.testRemoveFromGoldCardsDeck();

        assertEquals(Symbol.FUNGI, f.getHandPlayableFront().getKingdom());

        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableFront().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.QUILL, f.getHandPlayableFront().getBottomRightCorner().getSymbol());

        assertTrue(f.getHandPlayableFront().getTopLeftCorner().isCovered());
        assertFalse(f.getHandPlayableFront().getTopRightCorner().isCovered());
        assertFalse(f.getHandPlayableFront().getBottomLeftCorner().isCovered());
        assertFalse(f.getHandPlayableFront().getBottomRightCorner().isCovered());

        //controllo correttezza del back dell'ultima carta risorsa nel mazzo

        assertEquals(Symbol.FUNGI, c.getHandPlayableBack().getKingdom());

        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, c.getHandPlayableBack().getBottomRightCorner().getSymbol());

        assertFalse(c.getHandPlayableBack().getTopLeftCorner().isCovered());
        assertFalse(c.getHandPlayableBack().getTopRightCorner().isCovered());
        assertFalse(c.getHandPlayableBack().getBottomLeftCorner().isCovered());
        assertFalse(c.getHandPlayableBack().getBottomRightCorner().isCovered());

        //controllo correttezza del back dell'ultima carta oro nel mazzo

        assertEquals(Symbol.FUNGI, f.getHandPlayableBack().getKingdom());

        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getTopLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getTopRightCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getBottomLeftCorner().getSymbol());
        assertEquals(Symbol.EMPTY, f.getHandPlayableBack().getBottomRightCorner().getSymbol());

        assertFalse(f.getHandPlayableBack().getTopLeftCorner().isCovered());
        assertFalse(f.getHandPlayableBack().getTopRightCorner().isCovered());
        assertFalse(f.getHandPlayableBack().getBottomLeftCorner().isCovered());
        assertFalse(f.getHandPlayableBack().getBottomRightCorner().isCovered());

        //controllo correttezza dell'ultima carta obiettivo
        ObjectiveCard objectiveCard = centerOfTable.testRemoveFromObjectiveCardsDeck();
        SymbolsObjectiveCard symbolsObjectiveCard = (SymbolsObjectiveCard) objectiveCard;

        assertEquals(2,symbolsObjectiveCard.getPoints());
        assertEquals(2,symbolsObjectiveCard.getNumOfSymbol(Symbol.QUILL));
    }

    @Test
    void testShuffle(){
        //TODO
    }
}