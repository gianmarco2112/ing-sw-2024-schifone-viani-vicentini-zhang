package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.cards.HandPlayableCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playCard() {
    }

    @Test
    void drawFromResourceCardsDeck() {
        Player player = new Player("Bob", Color.RED,1);

        CenterOfTable centerOfTable = new CenterOfTable();
        player.setCenterOfTable(centerOfTable);
        Game game = new Game(1);
        game.addPlayer(player);
        game.dealInitialCard();
        /*List<HandPlayableCard> hand = player.getList();
        assertTrue(hand.isEmpty());
        player.playInitialCard();



        player.drawFromResourceCardsDeck();
        assertFalse(hand.isEmpty());
        player.playCard(hand.getFirst(), 0, 0);
        assertTrue(hand.isEmpty());*/
    }
}