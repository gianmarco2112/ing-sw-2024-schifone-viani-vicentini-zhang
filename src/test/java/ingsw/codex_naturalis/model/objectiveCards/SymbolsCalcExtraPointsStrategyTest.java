
package ingsw.codex_naturalis.model.objectiveCards;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.SymbolsObjectiveCard;
import ingsw.codex_naturalis.enumerations.PlayableCardType;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SymbolsCalcExtraPointsStrategyTest {
    Game game;
    Player player = new Player("Test");
    ObjectiveCard objectiveCard;
    PlayableCard initialCard;
    @BeforeEach
    void setUp(){
        game = new Game(0,4);
        game.addPlayer(player);
        initialCard = new PlayableCard(
                "ITest",
                PlayableCardType.INITIAL,
                Symbol.EMPTY,
                new PlayableSide(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false)),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.EMPTY,Symbol.EMPTY,Symbol.EMPTY)));
        player.setInitialCard(initialCard);
        player.playInitialCard();
    }
    private ObjectiveCard twoObjecctObjectiveCard(){
        return new SymbolsObjectiveCard(
                "OTest",
                2,
                new HashMap<>(Map.of(Symbol.QUILL,2))
        );
    }
    @Test
    void noExtraPoints(){
        objectiveCard = twoObjecctObjectiveCard();

        objectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(0,player.getPlayerArea().getExtraPoints());
    }
}
