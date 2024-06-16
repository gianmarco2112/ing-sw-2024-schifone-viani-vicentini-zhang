//package ingsw.codex_naturalis.client.view;
//
//import ingsw.codex_naturalis.common.enumerations.ExtremeCoordinate;
//import ingsw.codex_naturalis.common.enumerations.Symbol;
//import ingsw.codex_naturalis.server.model.cards.Corner;
//import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
//import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
//
//import java.util.*;
//
//public class cardsToString {
//
//    public static String listOfPlayableCardsToString(List<PlayableCard.Immutable> hand){
//        List<List<String>> cardsAsStrings = new ArrayList<>(new ArrayList<>());
//        StringBuilder outString = new StringBuilder();
//
//        for (PlayableCard.Immutable card : hand){
//            cardsAsStrings.add(Arrays.asList(card.handCard().split("\n")));
//        }
//
//        for (int i = 0; i < cardsAsStrings.getFirst().size(); i++) {
//            for (int j = 0; j < cardsAsStrings.size(); j++) {
//                outString.append(cardsAsStrings.get(j).get(i));
//            }
//            outString.append("\n");
//        }
//
//        return outString.toString();
//    }
//
//    public static String listOfObjectiveCardsToString(List<ObjectiveCard.Immutable> cards){
//        List<List<String>> cardsAsStrings = new ArrayList<>(new ArrayList<>());
//        StringBuilder outString = new StringBuilder();
//
//        for (ObjectiveCard.Immutable card : cards){
//            cardsAsStrings.add(Arrays.asList(card.card().split("\n")));
//        }
//
//        for (int i = 0; i < cardsAsStrings.getFirst().size(); i++) {
//            for (int j = 0; j < cardsAsStrings.size(); j++) {
//                outString.append(cardsAsStrings.get(j).get(i));
//            }
//            outString.append("\n");
//        }
//
//        return outString.toString();
//    }
//
//    public static String playerAreaToString(Map<List<Integer>, PlayableCard.Immutable> area, Map<ExtremeCoordinate, Integer> extremeCoordinates){
//        LinkedHashMap<List<Integer>, List<String>> cardsAsListOfStrings = new LinkedHashMap<>();
//        LinkedHashMap<Integer, List<String>> columns = new LinkedHashMap<>();
//        StringBuilder outString = new StringBuilder();
//        StringBuilder lineToBePrune = new StringBuilder();
//        String replaceValueForPruning = "";
//        Integer CharsOfXSpacing = 3;
//
//        // creation of cards as strings
//        for (Map.Entry<List<Integer>, PlayableCard.Immutable> cardAndCordinates: area.entrySet()) {
//            cardsAsListOfStrings.put(cardAndCordinates.getKey(), Arrays.asList(cardAndCordinates.getValue().description().split("\n")));
//        }
//
//        // pruning cards
//        for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_X); i <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); i++) {
//            for (int j = extremeCoordinates.get(ExtremeCoordinate.MIN_Y); j <= extremeCoordinates.get(ExtremeCoordinate.MAX_Y); j++) {
//                if (area.containsKey(List.of(i, j))){
//                    Corner tl_corner = area.get(List.of(i, j)).currentPlayableSide().getTopLeftCorner();
//                    Corner tr_corner = area.get(List.of(i, j)).currentPlayableSide().getTopRightCorner();
//                    Corner bl_corner = area.get(List.of(i, j)).currentPlayableSide().getBottomLeftCorner();
//                    Corner br_corner = area.get(List.of(i, j)).currentPlayableSide().getBottomRightCorner();
//
//                    // top right corner
//                    if (tr_corner.isCovered() && tr_corner.getSymbol() != Symbol.COVERED) {
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getFirst());
//                        lineToBePrune.replace(8, 10, replaceValueForPruning);
//                        cardsAsListOfStrings.get(List.of(i, j)).set(0, lineToBePrune.toString());
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
//
//                        if (tl_corner.getSymbol() == Symbol.COVERED || tl_corner.getSymbol() == Symbol.EMPTY){
//                            // Case empty-empty
//                            if(tr_corner.getSymbol() == Symbol.EMPTY){
//                                lineToBePrune.replace(lineToBePrune.length() - 6, lineToBePrune.length() - 4, replaceValueForPruning);
//                            }
//                            // Case empty-symbol
//                            else{
//                                lineToBePrune.replace(8, 24, replaceValueForPruning);
//                            }
//                        }
//                        else{
//                            // Case symbol-empty
//                            if(tr_corner.getSymbol() == Symbol.EMPTY){
//                                lineToBePrune.replace(22, 24, replaceValueForPruning);
//                            }
//                            // Case symbol-symbol
//                            else{
//                                lineToBePrune.replace(22, 38, replaceValueForPruning);
//                            }
//                        }
//                        cardsAsListOfStrings.get(List.of(i, j)).set(1, lineToBePrune.toString());
//                    }
//
//                    // top left corner
//                    if (tl_corner.isCovered() && tl_corner.getSymbol() != Symbol.COVERED) {
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getFirst());
//                        lineToBePrune.replace(5, 7, replaceValueForPruning);
//                        cardsAsListOfStrings.get(List.of(i, j)).set(0, lineToBePrune.toString());
//
//                        if(tl_corner.getSymbol() == Symbol.EMPTY){
//                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
//                            lineToBePrune.replace(5, 7, replaceValueForPruning);
//                        }
//                        else{
//                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
//                            lineToBePrune.replace(5, 21, replaceValueForPruning);
//                        }
//                        cardsAsListOfStrings.get(List.of(i, j)).set(1, lineToBePrune.toString());
//                    }
//
//                    // bottom right corner
//                    if (br_corner.isCovered() && br_corner.getSymbol() != Symbol.COVERED) {
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(4));
//                        lineToBePrune.replace(8, 10, replaceValueForPruning);
//                        cardsAsListOfStrings.get(List.of(i, j)).set(4, lineToBePrune.toString());
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(3));
//
//                        if (bl_corner.getSymbol() == Symbol.COVERED || bl_corner.getSymbol() == Symbol.EMPTY){
//                            // Case empty-empty
//                            if(br_corner.getSymbol() == Symbol.EMPTY){
//                                lineToBePrune.replace(8, 10, replaceValueForPruning);
//                            }
//                            // Case empty-symbol
//                            else{
//                                lineToBePrune.replace(8, 24, replaceValueForPruning);
//                            }
//                        }
//                        else{
//                            // Case symbol-empty
//                            if(br_corner.getSymbol() == Symbol.EMPTY){
//                                lineToBePrune.replace(22, 24, replaceValueForPruning);
//                            }
//                            // Case symbol-symbol
//                            else{
//                                lineToBePrune.replace(22, 38, replaceValueForPruning);
//                            }
//                        }
//                        cardsAsListOfStrings.get(List.of(i, j)).set(3, lineToBePrune.toString());
//                    }
//
//                    // bottom left corner
//                    if (bl_corner.isCovered() && bl_corner.getSymbol() != Symbol.COVERED) {
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(3));
//                        if(bl_corner.getSymbol() == Symbol.EMPTY){
//                            lineToBePrune.replace(5, 7, replaceValueForPruning);
//                        }
//                        else{
//                            lineToBePrune.replace(5, 21, replaceValueForPruning);
//
//                        }
//                        cardsAsListOfStrings.get(List.of(i, j)).set(3, lineToBePrune.toString());
//                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getLast());
//                        lineToBePrune.replace(5, 7, replaceValueForPruning);
//                        cardsAsListOfStrings.get(List.of(i, j)).set(4, lineToBePrune.toString());
//                    }
//                }
//            }
//        }
//
//        // adding Y coordinates
//        columns.put(extremeCoordinates.get(ExtremeCoordinate.MIN_X)-1, new ArrayList<>());
//        for (int i = extremeCoordinates.get(ExtremeCoordinate.MAX_Y); i >= extremeCoordinates.get(ExtremeCoordinate.MIN_Y) - 1; i--) {
//            columns.get(extremeCoordinates.get(ExtremeCoordinate.MIN_X)-1).addAll(List.of("     ", "     ", NumIn3Char(i, false) + "  "));
//        }
//        columns.get(extremeCoordinates.get(ExtremeCoordinate.MIN_X)-1).add("   " + NumIn3Char(extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1, true));
//
//        // populating columns
//        for (int i = extremeCoordinates.get(ExtremeCoordinate.MAX_X); i >= extremeCoordinates.get(ExtremeCoordinate.MIN_X); i--) {
//            columns.put(i, new ArrayList<>());
//            for (int j = extremeCoordinates.get(ExtremeCoordinate.MAX_Y); j >= extremeCoordinates.get(ExtremeCoordinate.MIN_Y); j--) {
//
//                if (cardsAsListOfStrings.containsKey(List.of(i, j))){
//                    columns.get(i).addAll(cardsAsListOfStrings.get(List.of(i, j)));
//                }
//                else {
//                    // spacing
//                    CharsOfXSpacing = 3;
//                    if (cardsAsListOfStrings.containsKey(List.of(i-1, j)) && cardsAsListOfStrings.containsKey(List.of(i+1, j))){
//                        CharsOfXSpacing = 1;
//                    }
//
//                    if (cardsAsListOfStrings.containsKey(List.of(i, j+1))){
//                        if (!cardsAsListOfStrings.containsKey(List.of(i-1, j)) && !cardsAsListOfStrings.containsKey(List.of(i+1, j))) {
//                            if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
//                                columns.get(i).add(" ".repeat(5));
//                            }
//                            else{
//                                columns.get(i).addAll(List.of(" ".repeat(5), " ".repeat(5), " ".repeat(5)));
//                            }
//                        }
//                        else {
//                            if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
//                                columns.get(i).add(" ".repeat(CharsOfXSpacing));
//                            }
//                            else{
//                                columns.get(i).addAll(List.of(" ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing)));
//                            }
//                        }
//                    }
//                    else if ((cardsAsListOfStrings.containsKey(List.of(i+1, j+1)) && cardsAsListOfStrings.containsKey(List.of(i-1, j+1)))
//                            || cardsAsListOfStrings.containsKey(List.of(i-1, j)) || cardsAsListOfStrings.containsKey(List.of(i-1, j+1))){
//                        if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
//                            columns.get(i).addAll(List.of(" "," "," "));
//                        }
//                        else {
//                            columns.get(i).addAll(List.of(" "," "," ", " ", " "));
//                        }
//                    }
//                    else{
//                        if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
//                            columns.get(i).addAll(List.of(" ".repeat(CharsOfXSpacing)," ".repeat(CharsOfXSpacing)," ".repeat(CharsOfXSpacing)));
//                        }
//                        else{
//                            columns.get(i).addAll(List.of(" ".repeat(CharsOfXSpacing)," ".repeat(CharsOfXSpacing),
//                                    " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing)));
//                        }
//                    }
//                }
//                // adding X Coordinates
//                if (j == extremeCoordinates.get(ExtremeCoordinate.MIN_Y)){
//                    columns.get(i).addAll(List.of("   ", NumIn3Char(i, true)));
//                }
//            }
//        }
//
//        // player area assembly
//        for (int j = 0; j < 3*(extremeCoordinates.get(ExtremeCoordinate.MAX_Y)-extremeCoordinates.get(ExtremeCoordinate.MIN_Y)+1)+4; j++) {
//            for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1; i <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); i++) {
//                outString.append(columns.get(i).get(j));
//            }
//            outString.append("\n");
//        }
//
//        // adding Extreme coordinates
//        outString.insert(0, NumIn3Char(extremeCoordinates.get(ExtremeCoordinate.MAX_Y) + 1, false) + "\n");
//        outString.replace(outString.length()-1, outString.length(),
//                NumIn3Char(extremeCoordinates.get(ExtremeCoordinate.MAX_X) + 1, true) + "\n");
//        return outString.toString();
//    }
//
//
//    private static String NumIn3Char(Integer i, boolean x_coordinate){
//        String outString = "";
//        if (i>-99 && i<99){
//            if(i>=0 && i<=9){
//                if(x_coordinate){
//                    outString = " " + i.toString() + " ";
//                }
//                else{
//                    outString = "  " + i.toString();
//                }
//            }
//            else if((i<=-1 && i>=-9)){
//                if(x_coordinate){
//                    outString = i.toString() + " ";
//                }
//                else{
//                    outString = " " + i.toString();
//                }
//            } else if (i>=10) {
//                outString = " " + i.toString();
//            } else if (i<=-10) {
//                outString = i.toString();
//            }
//        }
//        return outString;
//    }
//}
