package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.CardNotInHandException;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayableCardTest {

    Player player;
    CenterOfTable centerOfTable;
    PlayableCard initialCard;

    @BeforeEach
    void setup(){
        player = new Player("P1", Color.BLUE, 1);
        centerOfTable = new CenterOfTable();
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

    /*@Test
    void testFlipPlayableCard(){
        player.drawFromGoldCardsDeck();
        PlayableCard goldCard = player.getHand().getFirst();
        if (goldCard.isShowingFront()){
            player.flip(goldCard);
            assertEquals(Boolean.FALSE, goldCard.isShowingFront());
        }
        else{
            player.flip(goldCard);
            assertEquals(Boolean.TRUE, goldCard.isShowingFront());
        }
        player.drawFromResourceCardsDeck();
        PlayableCard resourceCard = player.getHand().getLast();
        if (resourceCard.isShowingFront()){
            player.flip(resourceCard);
            assertEquals(Boolean.FALSE, resourceCard.isShowingFront());
        }
        else{
            player.flip(resourceCard);
            assertEquals(Boolean.TRUE, resourceCard.isShowingFront());
        }
    }*/

    @Test
    void testFlipPlayedCard(){
        player.drawFromResourceCardsDeck();
        PlayableCard card = player.getHand().getFirst();
        player.playCard(card, 1, 1);

        assertThrows(CardNotInHandException.class, () -> {
            player.flip(card);
        });
    }
}
