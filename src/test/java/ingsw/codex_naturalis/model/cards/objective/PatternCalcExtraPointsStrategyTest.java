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

        HashMap<List<Integer>, Symbol> pattern = new HashMap<>(){{put(List.of(0,2),Symbol.INSECT);
                                                                  put(List.of(1,1),Symbol.INSECT);
                                                                  put(List.of(2,0),Symbol.INSECT);}};
        PatternObjectiveCard patternObjectiveCard = new PatternObjectiveCard(2,pattern,1,1,0,0);
        patternObjectiveCard.setCalcExtraPointsStrategy(new PatternCalcExtraPointsStrategy(patternObjectiveCard,List.of(player.getPlayerArea())));
        patternObjectiveCard.chosen(player.getPlayerArea());
        patternObjectiveCard.execute();
        //assertEquals(2,player.getPlayerArea().getExtraPoints());
        //PlayableSide cx = player.getPlayerArea().getCardOnCoordinates(1,-1);
        //assertEquals(Symbol.);
        assertEquals(Symbol.INSECT,pattern.get(List.of(0,2)));
    }


}