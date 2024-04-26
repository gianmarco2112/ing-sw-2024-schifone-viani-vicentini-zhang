package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.enumerations.Symbol;
import ingsw.codex_naturalis.events.gameplayPhase.*;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.player.PlayerArea;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.view.cardsToString;

import java.util.*;


public class GameplayTextualUI extends GameplayUI {

    private final Scanner s = new Scanner(System.in);

    private Game.Immutable game;


    private enum State {
        RUNNING,
        WAITING_FOR_UPDATE,
        STOPPING_THE_VIEW
    }

    private GameplayTextualUI.State state = State.WAITING_FOR_UPDATE;

    private final Object lock = new Object();

    private GameplayTextualUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(GameplayTextualUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }




    @Override
    public void run() {

        waitForUpdate();
        playing();

    }

    private void playing() {

        while (true) {
            askCommand();
            getCommand();
        }

    }

    @Override
    public void stop() {
        setState(State.STOPPING_THE_VIEW);
    }


    private void printErrInvalidOption(){
        System.err.println("Invalid option");
    }

    private void waitForUpdate() {
        while (getState() == GameplayTextualUI.State.WAITING_FOR_UPDATE) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Error while waiting for server update");
                }
            }
        }
    }


    private void askCommand() {
        System.out.println("""
                
                
                ----------------------------------------
                Commands list:
                
                (1) Flip a card
                (2) Play a card
                (3) Draw a card
                (4) Send a message
                ----------------------------------------
                
                
                """);
    }
    private void getCommand() {
        String input;
        input = s.next();
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> flippingCard();
                case 2 -> playingCard();
                case 3 -> drawingCard();
                case 4 -> sendingMessage();
                default -> {
                    printErrInvalidOption();
                    getCommand();
                }
            }
        } catch (NumberFormatException e) {
            printErrInvalidOption();
        }
    }

    private void flippingCard() {

        askFlipCardOption();
        getFlipCardOption();

    }
    private void askFlipCardOption() {
        System.out.println("""
                
                
                -------------------------------
                Which card do you want to flip?
                
                (/) Back
                """);
        for (int i=0; i<game.player().hand().size(); i++)
            System.out.println("("+i+1+") Card "+i+1);
        System.out.println("\n");
    }
    private void getFlipCardOption() {
        String input;
        input = s.next();
        if (input.equals("/"))
            return;
        try {
            int option = Integer.parseInt(input);
            if (game.player().hand().size() <= option) {
                switch (option) {
                    case 1 -> notifyFlipCard(FlipCard.FLIP_CARD_1);
                    case 2 -> notifyFlipCard(FlipCard.FLIP_CARD_2);
                    case 3 -> notifyFlipCard(FlipCard.FLIP_CARD_3);
                    default -> {
                        printErrInvalidOption();
                        getFlipCardOption();
                    }
                }
            }
            else {
                printErrInvalidOption();
                getFlipCardOption();
            }
            setState(State.WAITING_FOR_UPDATE);
        } catch (NumberFormatException e) {
            printErrInvalidOption();
        }
    }

    private void playingCard() {
        askPlayCardOption();
        PlayCard playCard = getPlayCardOption();
        if (playCard == PlayCard.BACK)
            return;

        askCoordinate("x");
        int x = getCoordinate();
        if (x == 0)
            return;

        askCoordinate("y");
        int y = getCoordinate();
        if (y == 0)
            return;

        notifyPlayCard(playCard, x, y);
    }
    private void askPlayCardOption() {
        System.out.println("""
                
                
                -------------------------------
                Which card do you want to play?
                
                (/) Back
                """);
        for (int i=0; i<game.player().hand().size(); i++)
            System.out.println("("+i+1+") Card "+i+1);
        System.out.println("\n");
    }
    private PlayCard getPlayCardOption() {
        String input;
        while (true) {
            input = s.next();
            if (input.equals("/"))
                return PlayCard.BACK;
            try {
                int option = Integer.parseInt(input);
                if (game.player().hand().size() <= option) {
                    switch (option) {
                        case 1 -> {
                            return PlayCard.PLAY_CARD_1;
                        }
                        case 2 -> {
                            return PlayCard.PLAY_CARD_2;
                        }
                        case 3 -> {
                            return PlayCard.PLAY_CARD_3;
                        }
                        default -> { printErrInvalidOption(); }
                    }
                } else { printErrInvalidOption(); }
            } catch (NumberFormatException e) {
                printErrInvalidOption();
            }
        }
    }
    private void askCoordinate(String coordinate) {
        System.out.println("""
                
                
                ----------------------------------------""");
        System.out.println("Please write the " + coordinate + " coordinate");
        System.out.println("\n(/) Back");
        System.out.println("\n");
    }
    private int getCoordinate() {
        String input;
        while (true) {
            input = s.next();
            if (input.equals("/"))
                return 0;
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printErrInvalidOption();
            }
        }
    }

    private void drawingCard() {
    }

    private void sendingMessage() {
    }






    /*private void drawCardCase(){
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
    }*/





    public void update(Game.Immutable o, GameEvent arg, String nickname, String playerWhoUpdated) {
        try {
            switch (arg) {
                //o.player().playerArea().area().get(new ArrayList<>(List.of(0,0)));
            }
        } catch (NoSuchNicknameException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void updatePlayerOrder(Game.Immutable immGame) {

        this.game = immGame;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        show();
        System.out.println("The player order is:");
        for (int i=0; i<game.playerOrderNicknames().size(); i++)
            System.out.println(i+1 + ". " + game.playerOrderNicknames().get(i));
        setState(State.RUNNING);

    }


    private void show() {
        showOtherPlayers();

        showResourceAndGoldDecks();

        showCommonObjectiveCards();

        showYourPlayArea();

        showYourHand();
    }

    private void showOtherPlayers(){
        for (Player.ImmutableHidden player : game.hiddenPlayers()) {
            Color color = player.color();
            System.out.println(color.getColorCode() + player.nickname() +"'s play area" + "\u001B[0m");
            System.out.println("Points: " + player.playerArea().points());
            System.out.println("Resources and objects: " + player.playerArea().numOfSymbols().toString());
            System.out.println(player.playerArea().area().get(List.of(0,0)).description());
            System.out.println(cardsToString.listOfPlayableCardsToString(player.hand()));
            System.out.println("////////////////////////////////////////////////////////////////");
        }
    }

    private void showYourPlayArea(){
        System.out.println("////////////////////////////////////////////////////////////////");
        Color color = game.player().color();
        System.out.println(color.getColorCode() + "\nYour play area" + "\u001B[0m");
        System.out.println("Points: " + game.player().playerArea().points());
        System.out.println("Resources and objects: " + game.player().playerArea().numOfSymbols().toString());
        System.out.println(game.player().playerArea().area().get(List.of(0,0)).description());
    }

    private void showResourceAndGoldDecks() {
        System.out.println("\n\nResource cards");
        System.out.println(cardsToString.listOfPlayableCardsToString(game.resourceCards()));

        System.out.println("\nGold cards");
        System.out.println(cardsToString.listOfPlayableCardsToString(game.goldCards()));
    }

    private void showCommonObjectiveCards() {

        System.out.println("\nCommon objective cards");
        System.out.println(cardsToString.listOfObjectiveCardsToString(game.commonObjectiveCards()));

    }

    private void showYourHand() {
        System.out.println(cardsToString.listOfPlayableCardsToString(game.player().hand()));
    }

}
