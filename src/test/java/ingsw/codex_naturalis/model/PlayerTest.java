package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.common.enumerations.TurnStatus;
import ingsw.codex_naturalis.server.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * it tests player's properties (nickname, color, id, initial card ect) and actions (draw, play a card)
 */
class PlayerTest {
    Player player = new Player("Test");
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
    @Test
    void simplePlayerTest(){
        PlayableCard initialCard = initialCard();
        player.setInitialCard(initialCard);
        assertEquals(initialCard,player.getInitialCard());
        player.setTurnStatus(TurnStatus.DRAW);
        assertEquals(TurnStatus.DRAW,player.getTurnStatus());
        player.setColor(Color.RED);
        assertEquals(Color.RED,player.getColor());
        assertEquals("Test",player.getNickname());
        PlayableCard resourceCard = insectResourceCard(); //showing back initially
        List<PlayableCard> hand = new ArrayList<>();
        hand.add(resourceCard);
        player.setHand(hand);
        assertEquals(resourceCard,player.getHand().getFirst());
    }
}