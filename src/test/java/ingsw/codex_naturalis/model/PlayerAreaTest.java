package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.PointsGiverAndPointsGiverForCorner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies.StandardStrategy;
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
    private PlayableCard fungiGoldCardBottomRightCornerCovered(){
        PlayableCard goldCard;
        HashMap<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 2);
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        2,
                        requirements,
                        new StandardStrategy()
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
    private PlayableCard fungiGoldCardBottomLeftCornerCovered(){
        PlayableCard goldCard;
        HashMap<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 2);
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        requirements,
                        new StandardStrategy()
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
    private PlayableCard fungiGoldCardTopRightCornerCovered(){
        PlayableCard goldCard;
        HashMap<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 2);
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        requirements,
                        new StandardStrategy()
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
    private PlayableCard fungiGoldCardTopLeftCornerCovered(){
        PlayableCard goldCard;
        HashMap<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 2);
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.EMPTY,true),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        2,
                        requirements,
                        new StandardStrategy()
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
    private PlayableCard fungiGoldCardInsectRequirements(){
        PlayableCard goldCard;
        HashMap<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.INSECT, 2);
        goldCard= new PlayableCard(
                "RTest",
                PlayableCardType.GOLD,
                Symbol.FUNGI,
                new PointsGiverAndPointsGiverForCorner(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,true),
                        2,
                        requirements,
                        new StandardStrategy()
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

//    @Test
//    void getImmutablePlayerArea() {
//        PlayerArea playerArea = playerArea();
//        PlayerArea.Immutable immutablePlayerArea = playerArea.getImmutablePlayerArea();
//        assertNotNull(immutablePlayerArea);
//    }
//
//    @Test
//    void getImmutableHiddenPlayerArea() {
//        PlayerArea playerArea = playerArea();
//        PlayerArea.ImmutableHidden immutableHidden = playerArea.getImmutableHiddenPlayerArea();
//        assertNotNull(immutableHidden);
//    }
//
//    @Test
//    void isPlayableTest () {
//        PlayerArea playerArea = playerArea ();
//        ObjectiveCard.Immutable immutableHiddenObjectiveCard = playerArea.getObjectiveCard().getImmutableHiddenPlayableCard();
//        assertNotNull(immutableHiddenObjectiveCard);
//        PlayableCard card1 = fungiResourceCard();
//        assertTrue (card1.isPlayable(playerArea, 5,5));
///*      PlayableCard coveredCard = fungiFullyCoveredResourceCard();
//        assertFalse (coveredCard.isPlayable(playerArea, -2,-2));
//        coveredCard.play(playerArea, -2,-2);
//        assertEquals (coveredCard, playerArea.getCardOnCoordinates(-2,-2)); */
//        PlayableCard topRightNotCovered = fungiTopRightNotCoveredResourceCard();
//        topRightNotCovered.play(playerArea, -2,-2);
//        assertFalse (card1.isPlayable(playerArea, -3,-3));
//        assertFalse (card1.isPlayable(playerArea, -2,-3));
//        assertFalse (card1.isPlayable(playerArea, -3,-2));
//        assertFalse (card1.isPlayable(playerArea, -1,-1));
//        PlayerArea playerArea1 = playerArea();
//        PlayableCard bottomRightNotCovered = fungiBottomRightNotCoveredResourceCard();
//        bottomRightNotCovered.play(playerArea1, -2,-1);
//        assertFalse (card1.isPlayable(playerArea1, -2,1));
//
//    }

    @Test
    void NeedyTest () {
        PlayerArea playerArea = playerArea ();
        PlayableCard card1 = fungiGoldCardBottomRightCornerCovered();
        PlayableCard card2 = fungiGoldCardInsectRequirements();
        card1.flip();
        card2.flip();
        assertTrue(card1.isShowingFront());
        assertTrue(card2.isShowingFront());
        assertFalse(card2.isPlayable(playerArea, -2,-2)); //missing requirements
        playerArea.setCardOnCoordinates(card1, -1, -2);
        assertFalse (card1.isPlayable(playerArea,1,-3)); //card1 has the bottomRightCorner covered
        PlayableCard card3 = fungiGoldCardBottomLeftCornerCovered();
        PlayableCard card4 = fungiGoldCardTopLeftCornerCovered();
        PlayableCard card5 = fungiGoldCardTopRightCornerCovered();
        card3.flip();
        card4.flip();
        card5.flip();
        assertTrue(card3.isShowingFront());
        assertTrue(card4.isShowingFront());
        assertTrue(card5.isShowingFront());
        playerArea.setCardOnCoordinates(card4, 4,5);
        assertFalse(card5.isPlayable(playerArea, 2,6));
        playerArea.setCardOnCoordinates(card5, 4,6);
        assertFalse(card4.isPlayable(playerArea,5,7));
        playerArea.setCardOnCoordinates(card3,-2,-2);
        assertFalse(card4.isPlayable(playerArea, -3,-3));
    }
}