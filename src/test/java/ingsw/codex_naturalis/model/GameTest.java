package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.server.exceptions.InvalidNumOfPlayersException;
import ingsw.codex_naturalis.server.exceptions.MaxNumOfPlayersInException;
import ingsw.codex_naturalis.server.exceptions.NotYourPlayTurnStatusException;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.server.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    /**
     * setUp before each test
     */

    @Test
    void simpleGameTest(){
        assertThrows(InvalidNumOfPlayersException.class,()->{Game game1 = new Game(1,5);});
        Game game = new Game (0,3);
        assertEquals(0,game.getGameID());
        game.setGameStatus(GameStatus.SETUP_1);
        game.setGameStatus(GameStatus.GAMEPLAY);
        assertEquals(GameStatus.GAMEPLAY,game.getGameStatus());
        assertEquals(3,game.getNumOfPlayers());
        Player player1 = new Player ("Player 1");
        Player player2 = new Player ("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.setGameRunningStatus(GameRunningStatus.RUNNING);
        assertEquals (GameRunningStatus.RUNNING, game.getGameRunningStatus());
        assertThrows(MaxNumOfPlayersInException.class, () -> {game.addPlayer(player4);});
        game.setCurrentPlayer(player3);
        assertEquals (player3, game.getCurrentPlayer());
        game.nextPlayer();
        assertEquals(player1, game.getCurrentPlayer());
        game.setGameStatus(GameStatus.GAMEPLAY);
        game.removePlayer(player2);
        game.nextPlayer();
        assertEquals (player3, game.getCurrentPlayer());
        game.removePlayer(player1);
        game.removePlayer(player3);
        assertEquals (GameRunningStatus.TO_CANCEL_NOW, game.getGameRunningStatus());
        Game game1 = new Game (1,3);
        game1.addPlayer(player1);
        game1.addPlayer(player2);
        game1.addPlayer(player3);
        game1.setCurrentPlayer(player1);
        game1.updatePlayerConnectionStatus(player2, false);
        game1.silentlyRemovePlayer(player2);
        game1.nextPlayer();
        assertEquals (player3, game1.getCurrentPlayer());
    }

}