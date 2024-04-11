package ingsw.codex_naturalis.controller;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.exceptions.NoSuchNicknameException;
import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.view.gameplayPhase.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.FlipCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.PlayCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.TextCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameplayControllerTest {
    GameplayController gameplayController;
    Game model;
    GameplayUI view;
    Player player;
    @BeforeEach
    void setUp(){
        model = new Game(0,4);
        player = new Player("Test");
        model.addPlayer(player);
        model.setCurrentPlayer(player,"Test");
        gameplayController = new GameplayController(model,view);
    }
    private PlayableCard insectResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
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
                        List.of(Symbol.INSECT))
        );
        return(resourceCard);
    }
    private PlayableCard initialCard(){
        PlayableCard initialCard;
        initialCard = new PlayableCard(
                "I00",
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
        return(initialCard);
    }
    @Test
    void updateFlipCard() {
        PlayableCard resourceCard = insectResourceCard(); //showing back initially
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);

        gameplayController.updateFlipCard("Test", FlipCardCommand.FLIP_CARD_1);

        assertTrue(player.getHand().getFirst().showingFront);
    }

    @Test
    void updatePlayCard() {
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);

        player.setInitialCard(initialCard());
        player.playInitialCard();

        gameplayController.updatePlayCard("Test", PlayCardCommand.PLAY_CARD_1,1,1);

        assertEquals(resourceCard,player.getPlayerArea().getCardOnCoordinates(1,1));
    }

    @Test
    void notYourTurnException(){
        assertThrows(NotYourTurnException.class,()->{gameplayController.updatePlayCard("Test2",PlayCardCommand.PLAY_CARD_1,1,1);});
    }

    @Test
    void updateDrawCard() {
        updatePlayCard();
        gameplayController.updateDrawCard("Test", DrawCardCommand.DRAW_FROM_RESOURCE_CARDS_DECK);//remove first
        PlayableCard card = player.getHand().getFirst();
        assertEquals("R01",card.getCardID());
    }

    @Test
    void updateText() {
        Player player1 = new Player("Test1");

        gameplayController.updateText("Test", TextCommand.TEXT_A_PLAYER,"Prova",List.of("Test1"));

        assertEquals("Prova",model.getMessages().getFirst().getContent());
        assertEquals("Test",model.getMessages().getFirst().getSender());
        assertEquals("Test1",model.getMessages().getFirst().getReceivers().getFirst());
    }

    @Test
    void noSuchNicknameException(){
        assertThrows(NoSuchNicknameException.class,()->{gameplayController.updateFlipCard("Test1",FlipCardCommand.FLIP_CARD_1);});
    }
    @Test
    void notPlayableException(){
        PlayableCard resourceCard = insectResourceCard();
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);

        Player player = model.getPlayerOrder().getFirst();
        player.setHand(hand);
        assertThrows(NotPlayableException.class,()->{gameplayController.updatePlayCard("Test",PlayCardCommand.PLAY_CARD_1,1,1);});
    }
}