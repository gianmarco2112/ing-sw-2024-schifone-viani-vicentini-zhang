package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;
import ingsw.codex_naturalis.model.enumerations.GameStatus;
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

    @Test
    void simpleGameTest(){
        assertEquals(0,game.getGameID());
        game.setGameStatus(GameStatus.SETUP);
        game.setGameStatus(GameStatus.GAMEPLAY);
        assertEquals(GameStatus.GAMEPLAY,game.getGameStatus());
        assertEquals(4,game.getMaxNumOfPlayers());

    }
}