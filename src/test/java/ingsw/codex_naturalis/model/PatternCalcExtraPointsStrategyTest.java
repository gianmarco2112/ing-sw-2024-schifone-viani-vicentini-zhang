package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.CenterOfTable;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Player;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternCalcExtraPointsStrategyTest {
    /*@Test
    void patternCalcExtraPointsStrategy(){
        Player player = new Player("Andrea", Color.BLUE,1);
        CenterOfTable centerOfTable = new CenterOfTable();
        Game game = new Game(1);

        game.addPlayer(player);

        game.dealInitialCard();
        player.playInitialCard();

        player.setCenterOfTable(centerOfTable);

        ResourceCard c1 = centerOfTable.removeFromResourceCardsDeck();
        ResourceCard c2 = centerOfTable.removeFromResourceCardsDeck();
        ResourceCard c3 = centerOfTable.removeFromResourceCardsDeck();

        c1.drawn(player.getPlayerArea());
        c2.drawn(player.getPlayerArea());
        c3.drawn(player.getPlayerArea());

        //player.playCard(c1, 1,-1);
        //player.playCard(c2,2,-2);
        //player.playCard(c3,3,-3);

        ObjectiveCard c = centerOfTable.removeFromObjectiveCardsDeck();
        //PatternObjectiveCard cx = (PatternObjectiveCard) c;
        //assertEquals(Symbol.FUNGI,cx.getSymbolAt(0,0));

        SymbolsObjectiveCard cx = (SymbolsObjectiveCard) c;
        //assertNull(c);
        assertEquals(2,cx.getNumOfSymbol(Symbol.MANUSCRIPT));
        assertEquals(2,cx.getPoints());

    }*/


}