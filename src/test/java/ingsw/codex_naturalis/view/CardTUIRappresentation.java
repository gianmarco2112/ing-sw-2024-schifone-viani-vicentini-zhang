package ingsw.codex_naturalis.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.Deck;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CardTUIRappresentation {

    @Test
    void objectiveCardRappresentationTest(){
        Deck<ObjectiveCard> objectiveCardsDeck;
        ObjectiveCard card;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String objectiveCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/objectiveCards.json";

            List<ObjectiveCard> objectiveCards = objectMapper.readValue(new File(objectiveCardsJsonFilePath), new TypeReference<List<ObjectiveCard>>() {});
            objectiveCardsDeck = new Deck<>(objectiveCards);
            for (int i = 0; i < 16; i++) {
                card = objectiveCardsDeck.drawACard("test");
                System.out.println(card.cardToString());
            }

        }
        catch (IOException e){
            System.err.println("ERROR while opening json file");
        }
    }
}
