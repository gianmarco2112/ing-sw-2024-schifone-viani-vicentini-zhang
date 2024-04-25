package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.events.gameplayPhase.*;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;

import java.util.*;

import static ingsw.codex_naturalis.events.gameplayPhase.UtilityCommand.CANCEL;

public class GameplayTextualUI extends GameplayUI {

    private boolean running;

    String nickname;

    private final Scanner s = new Scanner(System.in);

    private final Map<Integer, String> playerNicknames = new LinkedHashMap<>();

    private final Map<Integer, Command> commands = new LinkedHashMap<>();
    private final Map<Integer, FlipCard> flipCardCommands = new LinkedHashMap<>();
    private final Map<Integer, PlayCard> playCardCommands = new LinkedHashMap<>();
    private final Map<Integer, DrawCard> drawCardCommands = new LinkedHashMap<>();
    private final Map<Integer, Message> textCommands = new LinkedHashMap<>();

    private final Map<String, UtilityCommand> utilityCommands = new LinkedHashMap<>();


    public GameplayTextualUI(String nickname, List<String> playerNicknames){

        running = true;

        this.nickname = nickname;

        for (int key = 0; key < playerNicknames.size(); key++) {
            this.playerNicknames.put(key+1, playerNicknames.get(key));
        }

        for (int key = 0; key < Command.values().length; key++) {
            commands.put(key, Command.values()[key]);
        }
        for (int key = 0; key < FlipCard.values().length; key++) {
            flipCardCommands.put(key+1, FlipCard.values()[key]);
        }
        for (int key = 0; key < PlayCard.values().length; key++) {
            playCardCommands.put(key+1, PlayCard.values()[key]);
        }
        for (int key = 0; key < DrawCard.values().length; key++) {
            drawCardCommands.put(key+1, DrawCard.values()[key]);
        }
        for (int key = 0; key < Message.values().length; key++) {
            textCommands.put(key+1, Message.values()[key]);
        }
        utilityCommands.put("/", CANCEL);
    }


    @Override
    public void run() {
        while (running) {
            Command command = askCommandToPlayer();
            try {
                switch (command) {
                    case FLIP_CARD -> flipCardCase();
                    case PLAY_CARD -> playCardCase();
                    case DRAW_CARD -> drawCardCase();
                    case TEXT -> textCase();
                }
            } catch (NoSuchNicknameException e){
                System.err.println(e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
    }


    private void printUtilityCommands(){
        for (Map.Entry<String, UtilityCommand> entry : utilityCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printFlipCardCommands(){
        for (Map.Entry<Integer, FlipCard> entry : flipCardCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printPlayCardCommands(){
        for (Map.Entry<Integer, PlayCard> entry : playCardCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printDrawCardCommands(){
        for (Map.Entry<Integer, DrawCard> entry : drawCardCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printTextCommands(){
        for (Map.Entry<Integer, Message> entry : textCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }

    private void utilityCommandCase(UtilityCommand utilityCommand){
        switch (utilityCommand) {
            case CANCEL -> {
                System.err.println("Request canceled");
                System.out.println();
            }
        }
    }

    private void flipCardCase(){
        System.out.println("--- Which card do you want to flip? ---");
        printUtilityCommands();
        printFlipCardCommands();
        try {
            FlipCard flipCard = askGenericCommandToPlayer(flipCardCommands);
            notifyFlipCard(flipCard);
        } catch (UtilityCommandException e){
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        }
    }

    private void playCardCase(){
        System.out.println("--- Which card do you want to play? ---");
        printUtilityCommands();
        printPlayCardCommands();
        try {
            PlayCard playCard = askGenericCommandToPlayer(playCardCommands);
            int x = askCoordinateToPlayer("coordinate x");
            int y = askCoordinateToPlayer("coordinate y");
            try {
                notifyPlayCard(playCard, x, y);
                removeCardFromTheOptions();
            } catch (NotPlayableException | NotYourTurnException e) {
                System.err.println(e.getMessage());
            }
        } catch (UtilityCommandException e) {
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        }
    }

    private void removeCardFromTheOptions(){
        flipCardCommands.remove(3);
        playCardCommands.remove(3);
    }

    private void drawCardCase(){
        System.out.println("--- Which card do you want to draw? ---");
        printUtilityCommands();
        printDrawCardCommands();
        try {
            DrawCard drawCard = askGenericCommandToPlayer(drawCardCommands);
            notifyDrawCard(drawCard);
            addCardToTheOptions();
        } catch (UtilityCommandException e){
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        } catch (NotYourDrawTurnStatusException | NotYourTurnException | EmptyDeckException | NoMoreRevealedCardHereException e){
            System.err.println(e.getMessage());
        }
    }

    private void addCardToTheOptions() {
        flipCardCommands.put(3, FlipCard.values()[2]);
        playCardCommands.put(3, PlayCard.values()[2]);
    }

    private void textCase(){
        System.out.println("--- Who do you want to send a message to? ---");
        printUtilityCommands();
        printTextCommands();
        try {
            Message textCommand = askGenericCommandToPlayer(textCommands);
            List<String> playersToText = new ArrayList<>();
            switch (textCommand) {
                case TEXT_A_PLAYER -> {
                    playersToText.add(askWhichPlayerToPlayer());
                }
                case TEXT_ALL_PLAYERS ->
                        playersToText = new ArrayList<>(playerNicknames.values());
            }
            String message = askMessageContentToPlayer();
            notifyText(textCommand, message, playersToText);
        } catch (UtilityCommandException e) {
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        }
    }

    private String askWhichPlayerToPlayer() throws UtilityCommandException{
        System.out.println("--- Which player do you want to text? ---");
        printUtilityCommands();
        for (Map.Entry<Integer, String> entry : playerNicknames.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        while (true) {
            String input = s.next();
            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            try{
                Integer number = Integer.parseInt(input);
                if (playerNicknames.containsKey(number))
                    return playerNicknames.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }
    }

    private String askMessageContentToPlayer() throws  UtilityCommandException{
        System.out.println("--- Write your message ---");
        printUtilityCommands();
        while (true) {
            String input = s.next();
            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            if (input.length() > 150)
                System.err.println("These are too many characters...");
            else if (input.isEmpty())
                System.err.println("You wrote nothing!");
            else
                return input;
        }
    }

    private int askCoordinateToPlayer(String info) throws UtilityCommandException {
        System.out.println("--- Write the " + info + " ---");
        printUtilityCommands();
        while (true) {
            String input = s.next();
            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            try{
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("That was not a valid coordinate!");
            }
        }
    }

    private <T extends Enum<T>> T askGenericCommandToPlayer(Map<Integer, T> genericCommands) throws UtilityCommandException {
        while (true) {
            String input = s.next();
            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            try{
                int number = Integer.parseInt(input);
                if (genericCommands.containsKey(number))
                    return genericCommands.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: " + input);
            }
        }
    }

    private Command askCommandToPlayer() {
        System.out.println("--- Commands list: ---");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
        System.out.println("--- Insert the character associated to the action ---");
        while (true) {
            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (commands.containsKey(number))
                    return commands.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }
    }





    public void update(Game.Immutable o, Event arg, String nickname, String playerWhoUpdated) {
        try {
            switch (arg) {
                //o.player().playerArea().area().get(new ArrayList<>(List.of(0,0)));
            }
        } catch (NoSuchNicknameException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setPlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus) {
    }

    public String getPlayerAreaToString(){
        return null;
    }


    public static String getHandCardsToString(List<PlayableCard.Immutable> hand){
        List<List<String>> cardsFrontsAsStrings = new ArrayList<>(new ArrayList<>());
        StringBuilder outString = new StringBuilder();

        for (PlayableCard.Immutable card : hand){
            cardsFrontsAsStrings.add(Arrays.asList(card.handCard().split("\n")));
        }

        for (int i = 0; i < cardsFrontsAsStrings.getFirst().size(); i++) {
            for (int j = 0; j < cardsFrontsAsStrings.size(); j++) {
                outString.append(cardsFrontsAsStrings.get(j).get(i));
            }
            outString.append("\n");
        }

        return outString.toString();
    }

    public static String playerAreaToString(PlayerArea.Immutable playerArea){
        LinkedHashMap<List<Integer>, List<String>> cardsAsListOfStrings = new LinkedHashMap<>();
        LinkedHashMap<Integer, List<String>> columns = new LinkedHashMap<>();
        StringBuilder outString = new StringBuilder();
        StringBuilder lineToBePrune = new StringBuilder();

        // creation of cards as string
        for (Map.Entry<List<Integer>, PlayableCard.Immutable> cardAndCordinates: playerArea.area().entrySet()) {
            cardsAsListOfStrings.put(cardAndCordinates.getKey(), Arrays.asList(cardAndCordinates.getValue().description().split("\n")));
        }

        // pruning cards
        for (int i = playerArea.extremeCoordinates().get(ExtremeCoordinate.MIN_X); i <= playerArea.extremeCoordinates().get(ExtremeCoordinate.MAX_X); i++) {
            for (int j = playerArea.extremeCoordinates().get(ExtremeCoordinate.MIN_Y); j <= playerArea.extremeCoordinates().get(ExtremeCoordinate.MAX_Y); j++) {
                if (playerArea.area().containsKey(List.of(i, j))){

                    // top left angle
                    if (playerArea.area().get(List.of(i, j)).currentPlayableSide().getTopLeftCorner().isCovered() && playerArea.area().get(List.of(i, j)).currentPlayableSide().getTopLeftCorner().getSymbol() != Symbol.COVERED) {
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getFirst());
                        lineToBePrune.replace(5, 7, "  ");
                        cardsAsListOfStrings.get(List.of(i, j)).set(0, lineToBePrune.toString());
                        if(playerArea.area().get(List.of(i, j)).currentPlayableSide().getTopLeftCorner().getSymbol() == Symbol.EMPTY){
                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
                            lineToBePrune.replace(5, 7, "  ");
                            cardsAsListOfStrings.get(List.of(i, j)).set(1, lineToBePrune.toString());
                        }
                        else{
                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
                            lineToBePrune.replace(5, 21, "  ");
                            cardsAsListOfStrings.get(List.of(i, j)).set(1, lineToBePrune.toString());
                        }
                    }
                    if (playerArea.area().get(List.of(i, j)).currentPlayableSide().getTopRightCorner().isCovered()) {
                    }

                    // bottom left angle
                    if (playerArea.area().get(List.of(i, j)).currentPlayableSide().getBottomLeftCorner().isCovered() && playerArea.area().get(List.of(i, j)).currentPlayableSide().getBottomLeftCorner().getSymbol() != Symbol.COVERED) {

                        if(playerArea.area().get(List.of(i, j)).currentPlayableSide().getTopLeftCorner().getSymbol() == Symbol.EMPTY){
                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(3));
                            lineToBePrune.replace(5, 7, "  ");
                            cardsAsListOfStrings.get(List.of(i, j)).set(3, lineToBePrune.toString());
                        }
                        else{
                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(3));
                            lineToBePrune.replace(5, 21, "  ");
                            cardsAsListOfStrings.get(List.of(i, j)).set(3, lineToBePrune.toString());
                        }
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getLast());
                        lineToBePrune.replace(5, 7, "  ");
                        cardsAsListOfStrings.get(List.of(i, j)).set(4, lineToBePrune.toString());
                    }
                    if (playerArea.area().get(List.of(i, j)).currentPlayableSide().getBottomRightCorner().isCovered()) {
                    }
                }
            }
        }

        for (int i = playerArea.extremeCoordinates().get(ExtremeCoordinate.MAX_X); i >= playerArea.extremeCoordinates().get(ExtremeCoordinate.MIN_X); i--) {
            columns.put(i, new ArrayList<>());
            for (int j = playerArea.extremeCoordinates().get(ExtremeCoordinate.MAX_Y); j >= playerArea.extremeCoordinates().get(ExtremeCoordinate.MIN_Y); j--) {

                if (cardsAsListOfStrings.containsKey(List.of(i, j))){
                    columns.get(i).addAll(cardsAsListOfStrings.get(List.of(i, j)));
                }
                else {
                    columns.get(i).addAll(List.of("     ", "     ", "     ", "     ", "     "));
                }
            }
        }

        for (int j = 0; j < 5*(playerArea.extremeCoordinates().get(ExtremeCoordinate.MAX_Y)-playerArea.extremeCoordinates().get(ExtremeCoordinate.MIN_Y)+1); j++) {
            for (int i = playerArea.extremeCoordinates().get(ExtremeCoordinate.MIN_X); i <= playerArea.extremeCoordinates().get(ExtremeCoordinate.MAX_X); i++) {
                outString.append(columns.get(i).get(j));
            }
            outString.append("\n");
        }
        return outString.toString();
    }
}
