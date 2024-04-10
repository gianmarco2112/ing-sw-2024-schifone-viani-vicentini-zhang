package ingsw.codex_naturalis.view.playing;

import ingsw.codex_naturalis.exceptions.NotPlayableException;
import ingsw.codex_naturalis.exceptions.UtilityCommandException;
import ingsw.codex_naturalis.view.playing.events.MessageEvent;
import ingsw.codex_naturalis.view.playing.events.PlayCardEvent;
import ingsw.codex_naturalis.view.playing.events.commands.*;

import java.util.*;

import static ingsw.codex_naturalis.view.playing.events.commands.UtilityCommand.CANCEL;

public class TextualUI extends ObservableView implements Runnable{

    String nickname;

    private final Scanner s = new Scanner(System.in);

    private final Map<Integer, String> playerNicknames = new LinkedHashMap<>();

    private final Map<Integer, Command> commands = new LinkedHashMap<>();
    private final Map<Integer, FlipCardCommand> flipCardCommands = new LinkedHashMap<>();
    private final Map<Integer, PlayCardCommand> playCardCommands = new LinkedHashMap<>();
    private final Map<Integer, DrawCardCommand> drawCardCommands = new LinkedHashMap<>();
    private final Map<Integer, TextCommand> textCommands = new LinkedHashMap<>();

    private final Map<String, UtilityCommand> utilityCommands = new LinkedHashMap<>();


    public TextualUI(String nickname, List<String> playerNicknames){

        for (int key = 0; key < playerNicknames.size(); key++) {
            this.playerNicknames.put(key+1, playerNicknames.get(key));
        }

        for (int key = 0; key < Command.values().length; key++) {
            commands.put(key, Command.values()[key]);
        }
        for (int key = 0; key < FlipCardCommand.values().length; key++) {
            flipCardCommands.put(key+1, FlipCardCommand.values()[key]);
        }
        for (int key = 0; key < PlayCardCommand.values().length; key++) {
            playCardCommands.put(key+1, PlayCardCommand.values()[key]);
        }
        for (int key = 0; key < DrawCardCommand.values().length; key++) {
            drawCardCommands.put(key+1, DrawCardCommand.values()[key]);
        }
        for (int key = 0; key < TextCommand.values().length; key++) {
            textCommands.put(key+1, TextCommand.values()[key]);
        }
        utilityCommands.put("/", CANCEL);
    }


    @Override
    public void run() {
        while (true) {
            Command command = askCommandToPlayer();
            switch (command) {
                case FLIP_CARD -> flipCardCase();
                case PLAY_CARD -> playCardCase();
                case DRAW_CARD -> drawCardCase();
                case TEXT -> textCase();
            }
        }
    }


    public void printUtilityCommands(){
        for (Map.Entry<String, UtilityCommand> entry : utilityCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printFlipCardCommands(){
        for (Map.Entry<Integer, FlipCardCommand> entry : flipCardCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printPlayCardCommands(){
        for (Map.Entry<Integer, PlayCardCommand> entry : playCardCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printDrawCardCommands(){
        for (Map.Entry<Integer, DrawCardCommand> entry : drawCardCommands.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
    }
    private void printTextCommands(){
        for (Map.Entry<Integer, TextCommand> entry : textCommands.entrySet())
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
            FlipCardCommand flipCardCommand = askGenericCommandToPlayer(flipCardCommands);
            notifyObservers(flipCardCommand);
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
            PlayCardCommand playCardCommand = askGenericCommandToPlayer(playCardCommands);
            int x = askCoordinateToPlayer("coordinate x");
            int y = askCoordinateToPlayer("coordinate y");
            try {
                notifyObservers(new PlayCardEvent(playCardCommand, x, y));
                removeCardFromTheOptions();
            } catch (NotPlayableException e) {
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
            DrawCardCommand drawCardCommand = askGenericCommandToPlayer(drawCardCommands);
            notifyObservers(drawCardCommand);
            addCardToTheOptions();
        } catch (UtilityCommandException e){
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        }
    }

    private void addCardToTheOptions() {
        flipCardCommands.put(3, FlipCardCommand.values()[2]);
        playCardCommands.put(3, PlayCardCommand.values()[2]);
    }

    private void textCase(){
        System.out.println("--- Who do you want to send a message to? ---");
        printUtilityCommands();
        printTextCommands();
        try {
            TextCommand textCommand = askGenericCommandToPlayer(textCommands);
            List<String> playersToText = new ArrayList<>();
            switch (textCommand) {
                case TEXT_A_PLAYER -> {
                    playersToText.add(askWhichPlayerToPlayer());
                }
                case TEXT_ALL_PLAYERS ->
                        playersToText = new ArrayList<>(playerNicknames.values());
            }
            String message = askMessageContentToPlayer();
            notifyObservers(new MessageEvent(message, nickname, playersToText));
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
}
