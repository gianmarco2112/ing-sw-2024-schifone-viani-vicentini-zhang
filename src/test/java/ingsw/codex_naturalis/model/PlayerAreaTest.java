package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ingsw.codex_naturalis.server.model.DefaultValue;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.Needy;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiver;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverForObject;
import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.server.model.cards.objective.PatternObjectiveCard;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class PlayerAreaTest {

    private PlayableCard fungiResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.FUNGI,
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
                        List.of(Symbol.FUNGI))
        );
        return(resourceCard);
    }
    private PlayableCard fungiGoldCard(){
        PlayableCard goldCard;
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverForObject(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1,
                        new HashMap<>(),
                        Symbol.QUILL
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.FUNGI))
        );
        return(goldCard);
    }

    private PlayableCard fungiTopRightNotCoveredResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.FUNGI,
                new PlayableSide(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true)),
                new Back(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        List.of(Symbol.FUNGI))
        );
        return(resourceCard);
    }
    private PlayableCard fungiFullyCoveredResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.FUNGI,
                new PlayableSide(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true)),
                new Back(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        List.of(Symbol.FUNGI))
        );
        return(resourceCard);
    }
    private PlayableCard fungiBottomRightNotCoveredResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.FUNGI,
                new PlayableSide(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false)),
                new Back(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,true),
                        List.of(Symbol.FUNGI))
        );
        return(resourceCard);
    }
    private PlayableCard fungiBottomLeftCoveredGoldCard(){
        PlayableCard goldCard;
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverForObject(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false),
                        1,
                        new HashMap<>(),
                        Symbol.QUILL
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.FUNGI))
        );
        return(goldCard);
    }
    private PlayableCard fungiBottomRightCoveredGoldCard(){
        PlayableCard goldCard;
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverForObject(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        1,
                        new HashMap<>(),
                        Symbol.QUILL
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.FUNGI))
        );
        return(goldCard);
    }
    private PlayableCard fungiTopLeftCoveredGoldCard(){
        PlayableCard goldCard;
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverForObject(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1,
                        new HashMap<>(),
                        Symbol.QUILL
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.FUNGI))
        );
        return(goldCard);
    }
    private PlayableCard fungiTopRightCoveredGoldCard(){
        PlayableCard goldCard;
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverForObject(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        1,
                        new HashMap<>(),
                        Symbol.QUILL
                ),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.FUNGI))
        );
        return(goldCard);
    }
    private PlayableCard initialCard() {
        PlayableCard initialCard;
        initialCard = new PlayableCard(
                "I00",
                PlayableCardType.INITIAL,
                Symbol.EMPTY,
                new PlayableSide(
                        new Corner(Symbol.EMPTY, false),
                        new Corner(Symbol.EMPTY, false),
                        new Corner(Symbol.EMPTY, false),
                        new Corner(Symbol.EMPTY, false)),
                new Back(
                        new Corner(Symbol.EMPTY, false),
                        new Corner(Symbol.EMPTY, false),
                        new Corner(Symbol.EMPTY, false),
                        new Corner(Symbol.EMPTY, false),
                        List.of(Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY)));
        return (initialCard);
    }

    private PatternObjectiveCard patternObjectiveCard(){
        List<List<Integer>> positions = new ArrayList <> ();
        List <Symbol> kingdom = new ArrayList <> ();
        positions.add(Arrays.asList(0, 0));
        positions.add(Arrays.asList(1, 1));
        positions.add(Arrays.asList(2, 2));
        kingdom.add (Symbol.FUNGI);
        kingdom.add (Symbol.FUNGI);
        kingdom.add (Symbol.FUNGI);
        PatternObjectiveCard patternObjectiveCard = new PatternObjectiveCard(
                "Pattern Test - card 001",
                2,
                positions,
                kingdom,
                2,
                2,
                0,
                0
        );
        return (patternObjectiveCard);
    }
    private PlayerArea playerArea() {
        PlayerArea playerArea1 = new PlayerArea();
        ObjectiveCard objectiveCard = patternObjectiveCard();
        playerArea1.setObjectiveCard(objectiveCard);
        PlayableCard initialCard = initialCard();
        playerArea1.setCardOnCoordinates(initialCard, 0,0);
        PlayableCard card1 = fungiResourceCard();
        PlayableCard card2 = fungiResourceCard();
        PlayableCard card3 = fungiResourceCard();
        PlayableCard card4 = fungiResourceCard();
        PlayableCard card5 = fungiResourceCard();
        playerArea1.setCardOnCoordinates(card1, 1,1);
        playerArea1.setCardOnCoordinates(card2, -1,-1);
        playerArea1.setCardOnCoordinates(card3, 2,2);
        playerArea1.setCardOnCoordinates(card4, 3, 3);
        playerArea1.setCardOnCoordinates(card5, 4,4);
        return (playerArea1);
    }
    private PlayerArea emptyPlayerArea() {
        PlayerArea playerArea1 = new PlayerArea();
        ObjectiveCard objectiveCard = patternObjectiveCard();
        playerArea1.setObjectiveCard(objectiveCard);
        PlayableCard initialCard = initialCard();
        playerArea1.setCardOnCoordinates(initialCard, 0,0);
        return (playerArea1);
    }

    @Test
    void getImmutablePlayerArea() {
        PlayerArea playerArea = playerArea();
        PlayerArea.Immutable immutablePlayerArea = playerArea.getImmutablePlayerArea();
        assertNotNull(immutablePlayerArea);
    }

    @Test
    void getImmutableHiddenPlayerArea() {
        PlayerArea playerArea = playerArea();
        PlayerArea.ImmutableHidden immutableHidden = playerArea.getImmutableHiddenPlayerArea();
        assertNotNull(immutableHidden);
    }

    @Test
    void isPlayableTest () {
        PlayerArea playerArea = playerArea ();
        ObjectiveCard.Immutable immutableHiddenObjectiveCard = playerArea.getObjectiveCard().getImmutableHiddenPlayableCard();
        assertNotNull(immutableHiddenObjectiveCard);
        PlayableCard card1 = fungiResourceCard();
        assertTrue (card1.isPlayable(playerArea, 5,5));
/*      PlayableCard coveredCard = fungiFullyCoveredResourceCard();
        assertFalse (coveredCard.isPlayable(playerArea, -2,-2));
        coveredCard.play(playerArea, -2,-2);
        assertEquals (coveredCard, playerArea.getCardOnCoordinates(-2,-2)); */
        PlayableCard topRightNotCovered = fungiTopRightNotCoveredResourceCard();
        topRightNotCovered.play(playerArea, -2,-2);
        assertFalse (card1.isPlayable(playerArea, -3,-3));
        assertFalse (card1.isPlayable(playerArea, -2,-3));
        assertFalse (card1.isPlayable(playerArea, -3,-2));
        assertFalse (card1.isPlayable(playerArea, -1,-1));
        PlayerArea playerArea1 = playerArea();
        PlayableCard bottomRightNotCovered = fungiBottomRightNotCoveredResourceCard();
        bottomRightNotCovered.play(playerArea1, -2,-1);
        assertFalse (card1.isPlayable(playerArea1, -2,1));

    }
    @Test
    void NeedyTest () {
        PlayerArea playerArea = emptyPlayerArea ();
        PlayableCard card = fungiGoldCard();
        card.flip();
        PlayableCard cardBottomRight = fungiBottomRightCoveredGoldCard();
        cardBottomRight.flip();
        PlayableCard cardTopRight = fungiTopRightCoveredGoldCard();
        cardTopRight.flip();
        PlayableCard cardTopLeft = fungiTopLeftCoveredGoldCard();
        cardTopLeft.flip();
        PlayableCard cardBottomLeft = fungiBottomLeftCoveredGoldCard();
        cardBottomLeft.flip();
        playerArea.setCardOnCoordinates(card,1,1);
        // controllo di non poter piazzare una carta sopra a una già presente
        assertFalse(card.isPlayable(playerArea, 1,1));
        // controllo di non poter piazzare una carta fuori dall'area di gioco
        assertFalse(card.isPlayable(playerArea, -2,-2));
        playerArea.setCardOnCoordinates(card,2,1);
        playerArea.setCardOnCoordinates(cardBottomRight,2,2);
        assertFalse (card.isPlayable(playerArea, 3,2));
        playerArea.setCardOnCoordinates(cardTopRight, 3, -1);
        assertFalse (card.isPlayable(playerArea, 4,-1));
        playerArea.setCardOnCoordinates(cardTopLeft, 3, 3);
        assertFalse (card.isPlayable(playerArea, 3,4));
        playerArea.setCardOnCoordinates(cardBottomLeft, 1, -1);
        assertFalse (card.isPlayable(playerArea, 1,-2));
        assertTrue (card.isPlayable (playerArea, -1,-1));
    }
}