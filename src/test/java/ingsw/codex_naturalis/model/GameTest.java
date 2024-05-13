package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
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

    @Test
    void simpleGameTest(){
        assertEquals(0,game.getGameID());
        game.setGameStatus(GameStatus.SETUP_1);
        game.setGameStatus(GameStatus.GAMEPLAY);
        assertEquals(GameStatus.GAMEPLAY,game.getGameStatus());
        assertEquals(4,game.getNumOfPlayers());

    }
}