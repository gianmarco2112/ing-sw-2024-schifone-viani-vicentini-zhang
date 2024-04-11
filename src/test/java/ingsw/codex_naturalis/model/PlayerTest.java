package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.exceptions.CardNotInHandException;
import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiver;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies.StandardPointsStrategy;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.enumerations.TurnStatus;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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