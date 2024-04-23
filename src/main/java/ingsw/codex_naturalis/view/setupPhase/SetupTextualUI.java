package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayTextualUI;

import java.io.IOException;
import java.util.*;

public class SetupTextualUI extends SetupUI {

    private final Scanner s = new Scanner(System.in);


    private enum State {
        RUNNING,
        WAITING_FOR_UPDATE,
        PLAYING_INITIAL_CARD,
        STOPPING_THE_VIEW
    }

    private SetupTextualUI.State state = SetupTextualUI.State.RUNNING;

    private final Object lock = new Object();





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
        //choosingObjectiveCard();

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

        System.out.println();
        System.out.println("Press enter if you're ready to play");
        try {
            System.in.read();
        } catch (IOException e) { }
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

            Color color = askColor();

            try {
                notifyColor(color);
                return;
            } catch (ColorAlreadyChosenException e) {
                System.err.println(e.getMessage());
            }

        }

    }
    private Color askColor() {

        while (true) {

            Map<Integer, Color> colorMap = new LinkedHashMap<>();
            for (int key = 0; key < Color.values().length; key++)
                colorMap.put(key+1, Color.values()[key]);

            System.out.println();
            System.out.println("Please choose your color");
            printColors(colorMap);

            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (colorMap.containsKey(number))
                    return colorMap.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }

        }

    }
    private void printColors(Map<Integer, Color> colorMap) {

        for (Map.Entry<Integer, Color> entry : colorMap.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());

    }

   /* private void choosingObjectiveCard() {

        while (true) {

            ObjectiveCardChoice objectiveCardChoice = askObjectiveCardChoice();
            notifyObjectiveCard(objectiveCardChoice);
            return;

        }
    }*/



    @Override
    public void stop() {
        setState(State.STOPPING_THE_VIEW);
    }

    @Override
    public void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards) {
        System.out.println("\nResource cards");
        System.out.println(GameplayTextualUI.getHandCardsToString(resourceCards));

        System.out.println("\nGold cards");
        System.out.println(GameplayTextualUI.getHandCardsToString(goldCards));

        System.out.println("\nYour initial card\n" + initialCard.handCard());

        setState(State.RUNNING);
    }


    public void update(Game.Immutable o, Event arg, String nickname, String playerWhoUpdated) {
        switch (arg) {
            //case RESOURCE_AND_GOLD_DECKS_SETUP_1 -> showCardsSetup(o);
            case SETUP_1 -> showInitialCardsSetup(o);
            case COLOR_SETUP -> showColorSetup();
        }
    }

    private void showColorSetup() {
    }

    private void showCardsSetup(Game.Immutable o) {
    }

    private void showInitialCardsSetup(Game.Immutable o) {



    }

}
