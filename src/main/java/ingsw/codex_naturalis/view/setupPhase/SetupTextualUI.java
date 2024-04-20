package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.observerObservable.Event;

import java.io.IOException;
import java.util.*;

public class SetupTextualUI extends SetupUI {

    private boolean running;

    private final Scanner s = new Scanner(System.in);





    public SetupTextualUI(){
        running = true;
    }







    @Override
    public void run() {

        //the players tells when he's ready to play
        ready();

        //the player plays the initial card
        playingInitialCard();

        //the player chooses a color
        choosingColor();

        //the player chooses his objective card
        //choosingObjectiveCard();

    }




    private void ready() {

        System.out.println();
        System.out.println("Press enter if you're ready to play");
        try {
            System.in.read();
        } catch (IOException e) { }
        notifyReady();

    }

    private void playingInitialCard() {

        while (true) {

            InitialCardEvent initialCardEvent = askInitialCardEvent();

            switch (initialCardEvent) {
                case FLIP -> notifyInitialCard(InitialCardEvent.FLIP);
                case PLAY -> {
                    notifyInitialCard(InitialCardEvent.PLAY);
                    return; }
            }
        }

    }
    private InitialCardEvent askInitialCardEvent() {

        Map<Integer, InitialCardEvent> initialCardEventMap = new LinkedHashMap<>();
        for (int key = 0; key < InitialCardEvent.values().length; key++)
            initialCardEventMap.put(key+1, InitialCardEvent.values()[key]);

        System.out.println("Please play the initial card on your preferred side");
        printInitialCardEventCommands(initialCardEventMap);

        while (true) {

            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (initialCardEventMap.containsKey(number))
                    return initialCardEventMap.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }

        }
    }
    private void printInitialCardEventCommands(Map<Integer, InitialCardEvent> initialCardEventMap) {
        for (Map.Entry<Integer, InitialCardEvent> entry : initialCardEventMap.entrySet())
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
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
        running = false;
    }






    public void update(Game.Immutable o, Event arg, String nickname, String playerWhoUpdated) {
        switch (arg) {
            case CARDS_SETUP -> showCardsSetup(o);
            case INITIAL_CARDS_SETUP -> showInitialCardsSetup(o);
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
