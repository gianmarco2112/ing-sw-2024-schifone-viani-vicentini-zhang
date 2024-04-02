package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.exceptions.NicknameAlreadyExistsException;
import ingsw.codex_naturalis.model.enumerations.Color;
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
        game = new Game(0);
    }

    @Test
    void testMaxPlayersNumber(){
        Player player1 = new Player("P1", Color.BLUE, 1);
        Player player2 = new Player("P2", Color.YELLOW, 2);
        Player player3 = new Player("P3", Color.GREEN, 3);
        Player player4 = new Player("P4", Color.RED, 4);

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        assertThrows(MaxNumOfPlayersInException.class, () -> {
            game.addPlayer(new Player("P5", Color.BLUE, 5));
        });
    }

    @Test
    void testPlayerColor(){
        Player player1 = new Player("P1", Color.BLUE, 1);
        game.addPlayer(player1);

        Player player2 = new Player("P2", Color.BLUE, 2);

        assertThrows(ColorAlreadyChosenException.class, () -> {
            game.addPlayer(player2);
        });
    }

    @Test
    void testPlayerNickname(){
        Player player1 = new Player("P1", Color.BLUE, 1);
        game.addPlayer(player1);

        Player player2 = new Player("P1", Color.RED, 2);

        assertThrows(NicknameAlreadyExistsException.class, () -> {
            game.addPlayer(player2);
        });
    }

    @Test
    void testNextPlayer(){
        Player player1 = new Player("P1", Color.BLUE, 1);
        Player player2 = new Player("P2", Color.RED, 2);
        game.addPlayer(player1);
        game.addPlayer(player2);

        game.shufflePlayerList();
        game.setCurrentPlayer();

        if(game.getCurrentPlayer().getPlayerID() == 1){
            assertEquals(player2.getPlayerID(), game.getNextPlayer().getPlayerID());
        }
        else{
            assertEquals(player1.getPlayerID(), game.getNextPlayer().getPlayerID());
        }

    }
}