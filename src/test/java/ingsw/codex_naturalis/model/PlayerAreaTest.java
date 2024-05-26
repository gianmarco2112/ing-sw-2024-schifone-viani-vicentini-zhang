package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.common.enumerations.PlayableCardType;
import ingsw.codex_naturalis.common.enumerations.Symbol;
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
}
