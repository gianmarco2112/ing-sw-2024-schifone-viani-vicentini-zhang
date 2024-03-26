package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.CenterOfTable;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Player;
import ingsw.codex_naturalis.model.cards.PlayableSide;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternCalcExtraPointsStrategyTest {
    @Test
    void patternCalcExtraPointsStrategy(){
        Player player = new Player("Andrea", Color.BLUE);
        CenterOfTable centerOfTable = new CenterOfTable();
        Game game = new Game();

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

        player.playCard(c1, 1,-1);
        player.playCard(c2,2,-2);
        player.playCard(c3,3,-3);



    }


}