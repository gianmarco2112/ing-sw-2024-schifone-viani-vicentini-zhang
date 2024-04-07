
package ingsw.codex_naturalis.model;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.cards.objective.SymbolsObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SymbolsCalcExtraPointsStrategyTest {
    Game game;
    CenterOfTable centerOfTable = new CenterOfTable();
    Player player = new Player("Test", Color.RED,0);
    ObjectiveCard objectiveCard;
    PlayableCard initialCard;
    @BeforeEach
    void setUp(){
        game = new Game(0);
        game.addPlayer(player);
        player.setCenterOfTable(centerOfTable);
        initialCard = new PlayableCard(
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
                2,
                new HashMap<>(Map.of(Symbol.QUILL,2))
        );
    }
    @Test
    void noExtraPoints(){
        objectiveCard = twoObjecctObjectiveCard();
        player.setObjectiveCards(objectiveCard,objectiveCard);
        player.chooseObjectiveCard(objectiveCard);

        objectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(0,player.getExtraPoints());
    }
    @Test
    void testTwoObject(){
        objectiveCard = twoObjecctObjectiveCard();
        player.setObjectiveCards(objectiveCard,objectiveCard);
        player.chooseObjectiveCard(objectiveCard);

        int i = 1;
        while(i<6){
            player.drawFromResourceCardsDeck();
            player.getHand().getFirst().flip();
            player.playCard(player.getHand().getFirst(),i,i );
            i++;
        }
        player.drawFromResourceCardsDeck();
        player.playCard(player.getHand().getFirst(),i,i );
        int j = -1;
        while(i<13){
            player.drawFromResourceCardsDeck();
            player.getHand().getFirst().flip();
            player.playCard(player.getHand().getFirst(),j,j );
            i++;
            j--;
        }
        player.drawFromResourceCardsDeck();
        player.playCard(player.getHand().getFirst(),j,j );

        objectiveCard.gainPoints(List.of(player.getPlayerArea()));
        assertEquals(2,player.getExtraPoints());
    }
}
