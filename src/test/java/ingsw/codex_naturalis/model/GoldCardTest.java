package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.PlayableCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoldCardTest {

    Player player;
    CenterOfTable centerOfTable;

    @BeforeEach
    void setup(){
        player = new Player("P1", Color.BLUE, 1);
        centerOfTable = new CenterOfTable();
    }

    @Test
    void testFlipGoldCard(){
        PlayableCard card = centerOfTable.removeFromGoldCardsDeck();
        if (card.isShowingFront()){
            player.flip(card);
            assertEquals(Boolean.FALSE, card.isShowingFront());
        }
        else{
            player.flip(card);
            assertEquals(Boolean.TRUE, card.isShowingFront());
        }
    }
}
