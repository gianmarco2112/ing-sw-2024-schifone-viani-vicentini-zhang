package ingsw.codex_naturalis.view;

import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.player.PlayerArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cardsToString {
    public static String listOfPlayableCardsToString(List<PlayableCard.Immutable> hand){
        List<List<String>> cardsAsStrings = new ArrayList<>(new ArrayList<>());
        StringBuilder outString = new StringBuilder();

        for (PlayableCard.Immutable card : hand){
            cardsAsStrings.add(Arrays.asList(card.handCard().split("\n")));
        }

        for (int i = 0; i < cardsAsStrings.getFirst().size(); i++) {
            for (int j = 0; j < cardsAsStrings.size(); j++) {
                outString.append(cardsAsStrings.get(j).get(i));
            }
            outString.append("\n");
        }

        return outString.toString();
    }

    public static String listOfObjectiveCardsToString(List<ObjectiveCard.Immutable> cards){
        List<List<String>> cardsAsStrings = new ArrayList<>(new ArrayList<>());
        StringBuilder outString = new StringBuilder();

        for (ObjectiveCard.Immutable card : cards){
            cardsAsStrings.add(Arrays.asList(card.card().split("\n")));
        }

        for (int i = 0; i < cardsAsStrings.getFirst().size(); i++) {
            for (int j = 0; j < cardsAsStrings.size(); j++) {
                outString.append(cardsAsStrings.get(j).get(i));
            }
            outString.append("\n");
        }

        return outString.toString();
    }

    public static String playerAreaToString(PlayerArea.Immutable playerArea){
        return null;
    }
}
