package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.view.cardsToString;

import java.io.IOException;
import java.util.*;

public class SetupTextualUI extends SetupUI {

    private final Scanner s = new Scanner(System.in);


    private enum State {
        RUNNING,
        WAITING_FOR_UPDATE,
        PLAYING_INITIAL_CARD,
        CHOOSING_COLOR,
        STOPPING_THE_VIEW
    }

    private SetupTextualUI.State state = SetupTextualUI.State.RUNNING;

    private final Object lock = new Object();


    private Game.Immutable game;



    private SetupTextualUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(SetupTextualUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    @Override
    public void run() {

        //the players tells when he's ready to play
        ready();
        waitForUpdate();

        //the player plays the initial card
        playingInitialCard();
        waitForUpdate();

        //the player chooses a color
        choosingColor();

        //the player chooses his objective card
        choosingObjectiveCard();
        waitForUpdate();
        System.out.println("Please wait for the other players to make their choice");

    }


    private void printErrInvalidOption(){
        System.err.println("Invalid option");
    }

    private void waitForUpdate() {
        while (getState() == SetupTextualUI.State.WAITING_FOR_UPDATE) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Error while waiting for server update");
                }
            }
        }
    }


    private void ready() {

        //clear possible previous inputs
        try {
            while (System.in.available() > 0) {
                System.in.read(new byte[System.in.available()]);
            }
        } catch (IOException e){
            System.err.println("Error [System.in.available]");
        }

        System.out.println();
        System.out.println("Press enter if you're ready to play");
        s.nextLine();
        notifyReady();
        System.out.println("You are ready to play. Please wait for the other players to be ready");
        setState(State.WAITING_FOR_UPDATE);

    }

    private void playingInitialCard() {

        while (true) {

            askInitialCardOption();

            getInitialCardOption();

            while (getState() == State.PLAYING_INITIAL_CARD) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Error while waiting for server update");
                    }
                }
            }

            if (getState() == State.WAITING_FOR_UPDATE)
                return;

        }

    }
    private void getInitialCardOption() {

        String input = s.next();
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> {
                    notifyInitialCard(InitialCardEvent.FLIP);
                    setState(State.PLAYING_INITIAL_CARD);
                }
                case 2 -> {
                    notifyInitialCard(InitialCardEvent.PLAY);
                    setState(State.WAITING_FOR_UPDATE);
                }
                default -> {
                    printErrInvalidOption();
                    getInitialCardOption();
                }
            }
        } catch (NumberFormatException e) {
            printErrInvalidOption();
        }

    }
    private void askInitialCardOption() {
        System.out.println("""
                
                
                
                ---------------------------------------------------
                Please play the initial card on your preferred side
                
                (1) Flip
                (2) Play
                ---------------------------------------------------
                
                
                
                """);
    }

    private void choosingColor() {

        while (true) {

            askColorOption();

            getColorOption();

            while (getState() == State.WAITING_FOR_UPDATE) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Error while waiting for server update");
                    }
                }
            }

            if (getState() == State.RUNNING)
                return;

        }

    }
    private void askColorOption() {

        System.out.println("""
                
                
                
                ------------------------
                Please choose your color
                
                (1) Red
                (2) Blue
                (3) Green
                (4) Yellow
                ------------------------
                
                
                
                """);

    }
    private void getColorOption() {

        String input = s.next();
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> notifyColor(Color.RED);
                case 2 -> notifyColor(Color.BLUE);
                case 3 -> notifyColor(Color.GREEN);
                case 4 -> notifyColor(Color.YELLOW);
                default -> {
                    printErrInvalidOption();
                    getColorOption();
                }
            }
            setState(State.WAITING_FOR_UPDATE);
        } catch (NumberFormatException e) {
            printErrInvalidOption();
        }

    }

    private void choosingObjectiveCard() {

        askObjectiveCardOption();
        getObjectiveCardOption();

    }
    private void askObjectiveCardOption() {

        System.out.println("""
                
                
                
                ----------------------------------------
                Please choose your secret objective card
                
                (1) Objective card 1
                (2) Objective card 2
                """);
        System.out.println(cardsToString.listOfObjectiveCardsToString(game.player().secretObjectiveCards()));
        System.out.println("----------------------------------------");

    }
    private void getObjectiveCardOption() {

        String input = s.next();
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> notifyObjectiveCardChoice(ObjectiveCardChoice.CHOICE_1);
                case 2 -> notifyObjectiveCardChoice(ObjectiveCardChoice.CHOICE_2);
                default -> {
                    printErrInvalidOption();
                    getObjectiveCardOption();
                }
            }
            setState(State.WAITING_FOR_UPDATE);
        } catch (NumberFormatException e) {
            printErrInvalidOption();
        }

    }


    @Override
    public void stop() {
        setState(State.STOPPING_THE_VIEW);
    }



    @Override
    public void updateInitialCard(Game.Immutable game, InitialCardEvent initialCardEvent) {
        this.game = game;
        showResourceAndGoldDecks();
        switch (initialCardEvent) {
            case FLIP -> {
                System.out.println("\nYour initial card\n" + game.player().initialCard().handCard());
            }
            case PLAY -> {
                System.out.println("\nYour play area");
                System.out.println("Points: " + game.player().playerArea().points());
                System.out.println("Resources and objects: " + game.player().playerArea().numOfSymbols().toString());
                System.out.println(game.player().playerArea().area().get(List.of(0,0)).description());
            }
        }
        setState(State.RUNNING);
    }

    @Override
    public void updateColor(Color color) {
        System.out.println(color.getColorCode() + "Your chose " + color.getDescription() + "!" + "\u001B[0m");
        System.out.println("Please wait for the other players to make their choices.\n");
    }

    @Override
    public void reportError(String message) {
        System.err.println(message);
        setState(State.CHOOSING_COLOR);
    }

    @Override
    public void update(Game.Immutable immGame, GameEvent gameEvent) {
        this.game = immGame;
        switch (gameEvent) {
            case SETUP_1 -> showSetup1();
            case SETUP_2 -> showSetup2();
        }
    }

    @Override
    public void updateObjectiveCardChoice(Game.Immutable immGame) {
        this.game = immGame;

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

        showOtherPlayers();

        showResourceAndGoldDecks();

        showCommonObjectiveCards();

        showYourPlayArea();

        System.out.println(game.player().playerArea().objectiveCard().card());

        showYourHand();

        setState(State.RUNNING);
    }



    private void showSetup1() {

        showResourceAndGoldDecks();
        System.out.println("\nYour initial card\n" + game.player().initialCard().handCard());

        setState(State.RUNNING);

    }

    private void showSetup2() {

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

        showOtherPlayers();

        showResourceAndGoldDecks();

        showCommonObjectiveCards();

        showYourPlayArea();

        showYourHand();

        setState(State.RUNNING);

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
