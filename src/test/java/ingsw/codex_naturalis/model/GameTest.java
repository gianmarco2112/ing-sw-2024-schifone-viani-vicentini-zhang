package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    /**
     * setUp before each test
     */
    @BeforeEach
    void setup(){
        game = new Game(0,4);
    }

    /*@Test da testare con il controller
    void testMaxPlayersNumber(){
        Player player1 = new Player("P1");
        Player player2 = new Player("P2");
        Player player3 = new Player("P3");
        Player player4 = new Player("P4");

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        assertThrows(MaxNumOfPlayersInException.class, () -> {
            game.addPlayer(new Player("P5"));
        });
    }

    @Test
    void testPlayerColor(){
        Player player1 = new Player("P1");
        game.addPlayer(player1);

        Player player2 = new Player("P2");

        assertThrows(ColorAlreadyChosenException.class, () -> {
            game.addPlayer(player2);
        });
    }

    @Test
    void testPlayerNickname(){
        Player player1 = new Player("P1");
        game.addPlayer(player1);

        Player player2 = new Player("P1");

        assertThrows(NicknameAlreadyExistsException.class, () -> {
            game.addPlayer(player2);
        });
    }*/
}