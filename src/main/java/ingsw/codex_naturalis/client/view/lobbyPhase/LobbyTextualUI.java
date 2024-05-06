package ingsw.codex_naturalis.client.view.lobbyPhase;

import ingsw.codex_naturalis.common.exceptions.NoExistingGamesAvailable;
import ingsw.codex_naturalis.server.model.GameSpecs;

import java.io.IOException;
import java.util.*;

public class LobbyTextualUI extends LobbyUI {

    private final Scanner s = new Scanner(System.in);


    private enum State {
        RUNNING,
        ASKING_WHICH_GAME_TO_ACCESS,
        WAITING_FOR_UPDATE,
        STOPPING_THE_VIEW
    }

    private State state = State.RUNNING;

    private final Object lock = new Object();

    private final Map<Integer, GameSpecs>  gameSpecsMap = new LinkedHashMap<>();




    private State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }


    @Override
    public void run() {

        while (true) {
            while (getState() == State.WAITING_FOR_UPDATE) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Error while waiting for server update");
                    }
                }
            }

            if (getState() == State.STOPPING_THE_VIEW)
                return;

            askGameAccessOption();
            getGameAccessOption();

        }

    }


    private void printErrInvalidOption(){
        System.err.println("Invalid option");
    }
    private void clearPreviousInputs() {
        try {
            while (System.in.available() > 0) {
                System.in.read(new byte[System.in.available()]);
            }
        } catch (IOException e){
            System.err.println("Error [System.in.available]");
        }
    }


    private void askGameAccessOption() {

        clearPreviousInputs();
        System.out.println("""
                
                
                ----------------------------------------
                Please choose an option for game access:
                
                (1) Create a new game
                (2) Access an existing game
                ----------------------------------------
                
                
                """);

    }
    private void getGameAccessOption() {

        String input;
        input = s.next();
        try {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1 -> newGameAccess();
                case 2 -> existingGameAccess();
                default -> {
                    printErrInvalidOption();
                    getGameAccessOption();
                }
            }
        } catch (NumberFormatException e) {
            printErrInvalidOption();
            getGameAccessOption();
        }

    }

    private void newGameAccess() {

        askNumOfPlayers();
        int numOfPlayers = getNumOfPlayers();
        if (numOfPlayers == 0)
            return;

        askNickname();
        String nickname = getNickname();
        if (nickname.equals("/"))
            return;

        setState(State.WAITING_FOR_UPDATE);
        notifyNewGame(numOfPlayers, nickname);

    }
    private void askNumOfPlayers() {

        clearPreviousInputs();
        System.out.println("""
                
                
                --------------------------------------------------------------------
                Please specify the number of players for the game   (Min: 2, Max: 4)
                
                (/) Back
                --------------------------------------------------------------------
                
                
                """);

    }
    private int getNumOfPlayers() {

        String input;
        while (true) {
            input = s.next();
            if (input.equals("/"))
                return 0;
            try {
                int numOfPlayers = Integer.parseInt(input);
                if (numOfPlayers < 2)
                    System.err.println("The minimum number of players is 2");
                else if (numOfPlayers > 4)
                    System.err.println("The maximum number of players is 4");
                else
                    return numOfPlayers;
            } catch (NumberFormatException e) {
                printErrInvalidOption();
            }
        }

    }

    private void existingGameAccess() {

        try {
            askGameToAccess();
        } catch (NoExistingGamesAvailable e) {
            System.err.println(e.getMessage());
            return;
        }

        int gameID = getGameToAccess();
        setState(State.RUNNING);
        if (gameID == 0)
            return;

        askNickname();
        String nickname = getNickname();
        if (nickname.equals("/"))
            return;

        setState(State.WAITING_FOR_UPDATE);
        notifyGameToAccess(gameID, nickname);

    }
    private void askGameToAccess() throws NoExistingGamesAvailable{

        if (gameSpecsMap.isEmpty())
            throw new NoExistingGamesAvailable();

        clearPreviousInputs();
        System.out.println("""
                
                
                -----------------------------------------------------
                Which game do you want to access?
                
                (/) Back
                """);
        for (Map.Entry<Integer, GameSpecs> entry : gameSpecsMap.entrySet()) {
            System.out.println(entry.getKey() + " - "
                    + "Game ID: " + entry.getValue().ID()
                    + "    Current number of players connected: " + entry.getValue().currentNumOfPlayers()
                    + "    Max number of players: " + entry.getValue().maxNumOfPlayers());
        }
        System.out.println("-----------------------------------------------------\n\n");

    }
    private int getGameToAccess() {

        setState(State.ASKING_WHICH_GAME_TO_ACCESS);
        String input;

        while (true) {
            input = s.next();
            if (input.equals("/"))
                return 0;

            try{
                Integer number = Integer.parseInt(input);
                if (gameSpecsMap.containsKey(number))
                    return gameSpecsMap.get(number).ID();
                else
                    printErrInvalidOption();
            } catch (NumberFormatException e) {
                printErrInvalidOption();
            }
        }

    }

    private void askNickname() {

        clearPreviousInputs();
        System.out.println("""
                
                
                --------------------
                Choose your nickname
                
                (/) Back
                --------------------
                
                
                """);

    }
    private String getNickname()  {

        while (true) {
            String input = s.nextLine();
            if (input.contains(" ")) {
                System.err.println("You can't include a white space!");
            } else if (input.length() > 20) {
                System.err.println("Too many characters!");
            }
            else if (!input.isEmpty()) {
                return input;
            }
        }

    }




    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs){

        for (int key = 0; key < gamesSpecs.size(); key++)
            gameSpecsMap.put(key+1, gamesSpecs.get(key));

        if (getState() == State.ASKING_WHICH_GAME_TO_ACCESS) {
            gameSpecsMap.clear();
            for (int key = 0; key < gamesSpecs.size(); key++)
                gameSpecsMap.put(key+1, gamesSpecs.get(key));

            askGameToAccess();
        }
    }

    @Override
    public void reportError(String error) {
        System.err.println(error);
        setState(State.RUNNING);
    }


    @Override
    public void stop() {
        setState(State.STOPPING_THE_VIEW);
    }


}
