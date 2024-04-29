package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetupControllerTest {
    private final Game model = new Game(1,2);

    private final SetupController setupController = new SetupController(model);

    @BeforeEach
    void setUp(){
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        model.addPlayer(player1);
        model.addPlayer(player2);
    }
    @Test
    void updateReadyTest(){
        assertTrue(model.getRevealedGoldCards().isEmpty());
        assertTrue(model.getRevealedResourceCards().isEmpty());
        assertNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertNull(model.getPlayerOrder().getLast().getInitialCard());

        setupController.updateReady();
        setupController.updateReady();

        assertEquals(2,model.getRevealedResourceCards().size());
        assertEquals(2,model.getRevealedGoldCards().size());
        assertNotNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertNotNull(model.getPlayerOrder().getLast().getInitialCard());

        assertEquals(model,setupController.getModel());
    }

    @Test
    void updateInitialCard(){
        //set initialCards
        updateReadyTest();

        assertFalse(model.getPlayerOrder().getFirst().getInitialCard().getImmutablePlayableCard().showingFront());

        setupController.updateInitialCard("player1", InitialCardEvent.FLIP);

        assertTrue(model.getPlayerOrder().getFirst().getInitialCard().getImmutablePlayableCard().showingFront());

        assertFalse(model.getPlayerOrder().getFirst().getPlayerArea().containsCardOnCoordinates(0,0));
        setupController.updateInitialCard("player1", InitialCardEvent.PLAY);
        assertNull(model.getPlayerOrder().getFirst().getInitialCard());
        assertTrue(model.getPlayerOrder().getFirst().getPlayerArea().containsCardOnCoordinates(0,0));

    }

    @Test
    void updateColor(){
        assertTrue(model.getPlayerOrder().getFirst().getHand().isEmpty());
        assertTrue(model.getImmutableGame("player1").commonObjectiveCards().isEmpty());
        assertTrue(model.getPlayerOrder().getFirst().getSecretObjectiveCards().isEmpty());

        setupController.updateColor("player1", Color.BLUE);
        assertThrows(ColorAlreadyChosenException.class,()->{setupController.updateColor("player1", Color.BLUE);});

        setupController.updateColor("player2", Color.RED);

        assertEquals(3,model.getPlayerOrder().getFirst().getHand().size());
        assertFalse(model.getImmutableGame("player1").commonObjectiveCards().isEmpty());
        assertFalse(model.getPlayerOrder().getFirst().getSecretObjectiveCards().isEmpty());

    }

    @Test
    void updateObjectiveCard(){
        updateColor();

        assertNull(model.getPlayerOrder().getFirst().getPlayerArea().getObjectiveCard());
        setupController.updateObjectiveCard("player1", ObjectiveCardChoice.CHOICE_1);
        assertEquals(0,model.getPlayerOrder().getFirst().getSecretObjectiveCards().size());
        assertNotNull(model.getPlayerOrder().getFirst().getPlayerArea().getObjectiveCard());


        assertNull(model.getPlayerOrder().getLast().getPlayerArea().getObjectiveCard());
        setupController.updateObjectiveCard("player2", ObjectiveCardChoice.CHOICE_2);
        assertEquals(0,model.getPlayerOrder().getLast().getSecretObjectiveCards().size());
        assertNotNull(model.getPlayerOrder().getLast().getPlayerArea().getObjectiveCard());

        assertEquals(model.getGameStatus(), GameStatus.GAMEPLAY);
    }
}