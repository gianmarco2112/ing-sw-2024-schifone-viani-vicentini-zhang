package ingsw.codex_naturalis.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.Deck;
import ingsw.codex_naturalis.model.DefaultValue;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayTextualUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardTUIRappresentationTest {

    Deck<PlayableCard> initialCardsDeck;
    Deck<PlayableCard> resourceCardsDeck;
    Deck<PlayableCard> goldCardsDeck;
    Deck<ObjectiveCard> objectiveCardsDeck;

    @BeforeEach
    void setup(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String initialCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/initialCards.json";
            String resourceCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/resourceCards.json";
            String goldCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/goldCards.json";
            String objectiveCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/objectiveCards.json";

            List<PlayableCard> initialCards = objectMapper.readValue(new File(initialCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            List<PlayableCard> resourceCards = objectMapper.readValue(new File(resourceCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            List<PlayableCard> goldCards = objectMapper.readValue(new File(goldCardsJsonFilePath), new TypeReference<List<PlayableCard>>() {});
            List<ObjectiveCard> objectiveCards = objectMapper.readValue(new File(objectiveCardsJsonFilePath), new TypeReference<List<ObjectiveCard>>() {});

            initialCardsDeck = new Deck<>(initialCards);
            resourceCardsDeck = new Deck<>(resourceCards);
            goldCardsDeck = new Deck<>(goldCards);
            objectiveCardsDeck = new Deck<>(objectiveCards);
        } catch (IOException e){
            System.err.println("ERROR while opening json file");
        }
    }

    @Test
    void objectiveCardRappresentationTest(){
        ObjectiveCard card;
        for (int i = 0; i < 16; i++) {
            card = objectiveCardsDeck.drawACard("test");
            System.out.println(card.cardToString());
        }
    }

    @Test
    void playableCardRappresentationTest(){
        PlayableCard card;
        for (int i = 0; i < 40; i++) {
            card = resourceCardsDeck.drawACard("test");
            System.out.println(card.getFront().handCardToString(card.getKingdom()));
            System.out.println(card.getBack().handCardToString(card.getKingdom()));
            System.out.println(card.getFront().playerAreaCardToString(card.getKingdom()));
            System.out.println(card.getBack().playerAreaCardToString(card.getKingdom()));
        }
        for (int i = 0; i < 6; i++) {
            card = initialCardsDeck.drawACard("test");
            System.out.println(card.getFront().handCardToString(card.getKingdom()));
            System.out.println(card.getBack().handCardToString(card.getKingdom()));
            System.out.println(card.getFront().playerAreaCardToString(card.getKingdom()));
            System.out.println(card.getBack().playerAreaCardToString(card.getKingdom()));
        }
        for (int i = 0; i < 40; i++) {
            card = goldCardsDeck.drawACard("test");
            System.out.println(card.getFront().handCardToString(card.getKingdom()));
            System.out.println(card.getBack().handCardToString(card.getKingdom()));
            System.out.println(card.getFront().playerAreaCardToString(card.getKingdom()));
            System.out.println(card.getBack().playerAreaCardToString(card.getKingdom()));
        }
    }

    @Test
    void defaultValueCardTemplatingTest(){
        PlayableCard card;
        for (int i = 0; i < 6; i++) {
            card = initialCardsDeck.drawACard("test");
            System.out.println(DefaultValue.getTUIHandCardSideTemplate(card.getFront(), card.getKingdom()));
            System.out.println(DefaultValue.getTUIHandCardSideTemplate(card.getBack(), card.getKingdom()));
            System.out.println(DefaultValue.getTUIPlayerAreaCardSideTemplate(card.getFront(), card.getKingdom()));
            System.out.println(DefaultValue.getTUIPlayerAreaCardSideTemplate(card.getBack(), card.getKingdom()));
        }
        for (int i = 0; i < 40; i++) {
            card = resourceCardsDeck.drawACard("test");
            System.out.println(DefaultValue.getTUIHandCardSideTemplate(card.getBack(), card.getKingdom()));
            System.out.println(DefaultValue.getTUIPlayerAreaCardSideTemplate(card.getBack(), card.getKingdom()));
        }
        for (int i = 0; i < 40; i++) {
            card = goldCardsDeck.drawACard("test");
            System.out.println(DefaultValue.getTUIHandCardSideTemplate(card.getBack(), card.getKingdom()));
            System.out.println(DefaultValue.getTUIPlayerAreaCardSideTemplate(card.getBack(), card.getKingdom()));
        }
    }

    @Test
    void getHandCadsToStringTest(){
        List<PlayableCard.Immutable> cards = new ArrayList<>();
        resourceCardsDeck.shuffle();
        goldCardsDeck.shuffle();
        initialCardsDeck.shuffle();
        cards.add(resourceCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(resourceCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(resourceCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(resourceCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(goldCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(goldCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(goldCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(goldCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(initialCardsDeck.drawACard("test").getImmutablePlayableCard());
        cards.add(initialCardsDeck.drawACard("test").getImmutablePlayableCard());
        System.out.println(GameplayTextualUI.getHandCardsToString(cards));
    }

    @Test
    void playerAreaToStringTest(){
        PlayerArea playerArea = new PlayerArea();
        playerArea.setObjectiveCard(objectiveCardsDeck.drawACard("test"));
        PlayableCard card = initialCardsDeck.drawACard("test");
        card.flip();
        playerArea.setCardOnCoordinates(card,0,0,"test");
        playerArea.setCardOnCoordinates(resourceCardsDeck.drawACard("test"),-1,-1,"test");
        playerArea.setCardOnCoordinates(resourceCardsDeck.drawACard("test"),1,1,"test");
        card = resourceCardsDeck.drawACard("test");
        card.flip();
        playerArea.setCardOnCoordinates(card,2,0,"test");
        playerArea.setCardOnCoordinates(resourceCardsDeck.drawACard("test"),3,1,"test");
        playerArea.setCardOnCoordinates(resourceCardsDeck.drawACard("test"),2,2,"test");
        card = resourceCardsDeck.drawACard("test");
        card.flip();
        playerArea.setCardOnCoordinates(card,1,3,"test");
        playerArea.setCardOnCoordinates(resourceCardsDeck.drawACard("test"),0,4,"test");

        System.out.println(GameplayTextualUI.playerAreaToString(playerArea.getImmutablePlayerArea()));
    }
}
