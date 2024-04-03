package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.PlayableCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayableCardTest {

    Player player;
    CenterOfTable centerOfTable;

    @BeforeEach
    void setup(){
        player = new Player("P1", Color.BLUE, 1);
        centerOfTable = new CenterOfTable();
        player.setCenterOfTable(centerOfTable);
    }

    @Test
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

    }
}
