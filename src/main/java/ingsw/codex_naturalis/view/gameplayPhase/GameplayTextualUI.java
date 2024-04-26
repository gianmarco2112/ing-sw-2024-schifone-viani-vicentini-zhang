package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.*;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.cardsToString;

import java.util.*;


public class GameplayTextualUI extends GameplayUI {

    private final Scanner s = new Scanner(System.in);

    private Game.Immutable game;
    private final List<PlayableCard.Immutable> resourceCards = new ArrayList<>();
    private final List<PlayableCard.Immutable> goldCards = new ArrayList<>();
    private final List<List<PlayableCard.Immutable>> cardsToDraw = new ArrayList<>();


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

    @Override
    public void run() {

        waitForUpdate();
        playing();

    }

    private void playing() {

        while (true) {
            askCommand();
            getCommand();
            waitForUpdate();
        }

    }


    private void askCommand() {
        System.out.println("""
                
                
                ------------------
                Commands list:
                
                (1) Flip a card
                (2) Play a card
                (3) Draw a card
                (4) Send a message
                ------------------
                
                
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
            getCommand();
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
                
                (/) Back""");
        for (int i=0; i<game.player().hand().size(); i++)
            System.out.println("(" + (i+1) + ") Card " + (i+1));
        System.out.println("-------------------------------");
        System.out.println("\n");
    }
    private void getFlipCardOption() {
        String input;
        input = s.next();
        if (input.equals("/"))
            return;
        try {
            int option = Integer.parseInt(input);
            if (game.player().hand().size() >= option) {
                switch (option) {
                    case 1 -> notifyFlipCard(FlipCardEvent.FLIP_CARD_1);
                    case 2 -> notifyFlipCard(FlipCardEvent.FLIP_CARD_2);
                    case 3 -> notifyFlipCard(FlipCardEvent.FLIP_CARD_3);
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
            getFlipCardOption();
        }
    }

    private void playingCard() {
        askPlayCardOption();
        PlayCardEvent playCardEvent = getPlayCardOption();
        if (playCardEvent == PlayCardEvent.BACK)
            return;

        askCoordinate("x");
        int x = getCoordinate();
        if (x == 0)
            return;

        askCoordinate("y");
        int y = getCoordinate();
        if (y == 0)
            return;

        notifyPlayCard(playCardEvent, x, y);
        setState(State.WAITING_FOR_UPDATE);
    }
    private void askPlayCardOption() {
        System.out.println("""
                
                
                -------------------------------
                Which card do you want to play?
                
                (/) Back""");
        for (int i=0; i<game.player().hand().size(); i++)
            System.out.println("(" + (i+1) + ") Card " + (i+1));
        System.out.println("-------------------------------");
        System.out.println("\n");
    }
    private PlayCardEvent getPlayCardOption() {
        String input;
        while (true) {
            input = s.next();
            if (input.equals("/"))
                return PlayCardEvent.BACK;
            try {
                int option = Integer.parseInt(input);
                if (game.player().hand().size() >= option) {
                    switch (option) {
                        case 1 -> {
                            return PlayCardEvent.PLAY_CARD_1;
                        }
                        case 2 -> {
                            return PlayCardEvent.PLAY_CARD_2;
                        }
                        case 3 -> {
                            return PlayCardEvent.PLAY_CARD_3;
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
                
                
                -----------------------------""");
        System.out.println("Please write the " + coordinate + " coordinate");
        System.out.println("\n(/) Back");
        System.out.println("-----------------------------");
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

        try {
            askDrawCardOption();
        } catch (ZeroCardsLeftException e) {
            System.err.println(e.getMessage());
            return;
        }
        getDrawCardOption();

    }
    private void askDrawCardOption() throws ZeroCardsLeftException{

        if (cardsToDraw.isEmpty())
            throw new ZeroCardsLeftException();

        System.out.println("""
                
                
                ---------------------------------------
                Which type of card do you want to draw?
                
                (/) Back""");

        switch (cardsToDraw.size()) {
            case 1 -> { System.out.println("(1) Gold card"); }
            case 2 -> {
                System.out.println("(1) Resource card");
                System.out.println("(2) Gold card");
            }
        }

        System.out.println("---------------------------------------\n\n");
    }
    private void getDrawCardOption() {
        String input;
        input = s.next();
        if (input.equals("/"))
            return;
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> {
                    if (resourceCards.isEmpty())
                        drawingGoldCard();
                    else drawingResourceCard();
                }
                case 2 -> {
                    if (goldCards.isEmpty()) {
                        printErrInvalidOption();
                        getDrawCardOption();
                    }
                    else
                        drawingGoldCard();
                }
                default -> {
                    printErrInvalidOption();
                    getDrawCardOption();
                }
            }
        } catch (NumberFormatException e) {
            printErrInvalidOption();
            getDrawCardOption();
        }
    }
    private void drawingResourceCard() {

        try {
            askDrawResourceCardOption();
        } catch (EmptyResourceCardsDeckException e){
            System.out.println(e.getMessage());
            return;
        }
        getDrawResourceCardOption();

    }
    private void askDrawResourceCardOption() throws EmptyResourceCardsDeckException{

        if (resourceCards.isEmpty())
            throw new EmptyResourceCardsDeckException();

        System.out.println("""
                
                
                ----------------------------------------
                Which resource card do you want to draw?
                
                (/) Back""");
        if (game.topResourceCard() != null) {
            System.out.println("(1) Deck card");
            for (int i=0; i<game.revealedResourceCards().size(); i++)
                System.out.println("("+(i+2)+") Revealed card "+(i+1));
        }
        else {
            for (int i = 0; i < game.revealedResourceCards().size(); i++)
                System.out.println("(" + (i + 1) + ") Revealed card " + (i + 1));
        }
        System.out.println("----------------------------------------\n\n");

    }
    private void getDrawResourceCardOption() {
        String input;
        input = s.next();
        if (input.equals("/"))
            return;
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> {
                    if (game.topResourceCard() != null)
                        notifyDrawCard(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK);
                    else
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
                }
                case 2 -> {
                    if (game.topResourceCard() != null)
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
                    else
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2);
                }
                case 3 -> {
                    if (game.revealedResourceCards().size() == 2)
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2);
                    else {
                        printErrInvalidOption();
                        getDrawResourceCardOption();
                    }
                }
                default -> {
                    printErrInvalidOption();
                    getDrawResourceCardOption();
                }
            }
        } catch (NumberFormatException e) {
            printErrInvalidOption();
            getDrawCardOption();
        }
    }
    private void drawingGoldCard() {

        try {
            askDrawGoldCardOption();
        } catch (EmptyGoldCardsDeckException e){
            System.out.println(e.getMessage());
            return;
        }
        getDrawGoldCardOption();

    }
    private void askDrawGoldCardOption() throws EmptyGoldCardsDeckException{

        if (goldCards.isEmpty())
            throw new EmptyGoldCardsDeckException();

        System.out.println("""
                
                
                ------------------------------------
                Which gold card do you want to draw?
                
                (/) Back""");
        if (game.topGoldCard() != null) {
            System.out.println("(1) Deck card");
            for (int i=0; i<game.revealedGoldCards().size(); i++)
                System.out.println("("+(i+2)+") Revealed card "+(i+1));
        }
        else {
            for (int i = 0; i < game.revealedGoldCards().size(); i++)
                System.out.println("(" + (i + 1) + ") Revealed card " + (i + 1));
        }
        System.out.println("------------------------------------\n\n");

    }
    private void getDrawGoldCardOption() {
        String input;
        input = s.next();
        if (input.equals("/"))
            return;
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> {
                    if (game.topGoldCard() != null)
                        notifyDrawCard(DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK);
                    else
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);
                }
                case 2 -> {
                    if (game.topGoldCard() != null)
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);
                    else
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2);
                }
                case 3 -> {
                    if (game.revealedGoldCards().size() == 2)
                        notifyDrawCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2);
                    else {
                        printErrInvalidOption();
                        getDrawGoldCardOption();
                    }
                }
                default -> {
                    printErrInvalidOption();
                    getDrawGoldCardOption();
                }
            }
        } catch (NumberFormatException e) {
            printErrInvalidOption();
            getDrawCardOption();
        }
    }

    private void sendingMessage() {
    }






    /*
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
    }*/



    private void initCenterOfTable() {
        if (game.topResourceCard() != null)
            resourceCards.add(game.topResourceCard());
        resourceCards.addAll(game.revealedResourceCards());

        if (game.topGoldCard() != null)
            goldCards.add(game.topGoldCard());
        goldCards.addAll(game.revealedGoldCards());

        if (!resourceCards.isEmpty())
            cardsToDraw.add(resourceCards);

        if (!goldCards.isEmpty())
            cardsToDraw.add(goldCards);
    }

    @Override
    public void updatePlayerOrder(Game.Immutable immGame) {

        this.game = immGame;

        initCenterOfTable();

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        show();
        System.out.println("The player order is:");
        for (int i=0; i<game.playerOrderNicknames().size(); i++)
            System.out.println(i+1 + ". " + game.playerOrderNicknames().get(i));
        setState(State.RUNNING);

    }

    @Override
    public void update(Game.Immutable immGame) {

        String lastCurrentPlayerNickname = game.currentPlayerNickname();
        game = immGame;
        initCenterOfTable();

        show();
        if (!lastCurrentPlayerNickname.equals(game.currentPlayerNickname())) {
            if (game.player().nickname().equals(game.currentPlayerNickname())) {
                System.out.println("=================== It's your turn! ===================");
            }
            else {
                System.out.println("=================== It's " + game.currentPlayerNickname() + "'s turn! ===================");
            }
        }

        setState(State.RUNNING);
    }

    @Override
    public void reportError(String error) {
        System.err.println(error);
        setState(State.RUNNING);
    }


    private void show() {

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        showOtherPlayers();

        showResourceAndGoldDecks();

        showCommonObjectiveCards();

        showYourPlayArea();

        System.out.println(game.player().playerArea().objectiveCard().card());

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
        System.out.println(cardsToString.playerAreaToString(game.player().playerArea()));
    }

    private void showResourceAndGoldDecks() {

        if (!resourceCards.isEmpty()) {
            System.out.println("\n\nResource cards");
            System.out.println(cardsToString.listOfPlayableCardsToString(resourceCards));
        }

        if (!goldCards.isEmpty()) {
            System.out.println("\nGold cards");
            System.out.println(cardsToString.listOfPlayableCardsToString(goldCards));
        }
    }

    private void showCommonObjectiveCards() {

        System.out.println("\nCommon objective cards");
        System.out.println(cardsToString.listOfObjectiveCardsToString(game.commonObjectiveCards()));

    }

    private void showYourHand() {
        System.out.println(cardsToString.listOfPlayableCardsToString(game.player().hand()));
    }

}
