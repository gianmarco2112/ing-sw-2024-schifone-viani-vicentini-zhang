package ingsw.codex_naturalis.view.lobbyPhase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.events.lobbyPhase.GameAccess;
import ingsw.codex_naturalis.distributed.ServerImpl;
import ingsw.codex_naturalis.exceptions.*;

import java.util.*;

public class LobbyTextualUI extends LobbyUI {

    private boolean running;

    private final Scanner s = new Scanner(System.in);

    Map<Integer, ServerImpl.GameSpecs>  gameSpecsMap = new LinkedHashMap<>();

    private boolean askingWhichGameToAccess = false;


    public LobbyTextualUI(){
        running = true;
    }



    @Override
    public void run() {

        gameAccess();

    }



    private void gameAccess() {

        while (running) {
            int option = askGameAccess();
            switch (option) {
                case 1 -> newGameAccess();
                case 2 -> existingGameAccess();
                default -> System.err.println("Invalid option");
            }
        }

    }

    private <Event extends Enum<Event>> void printOptions(Class<Event> event){
        for (int i = 0; i < event.getEnumConstants().length; i++) {
            System.out.println(i+1 + " - " + event.getEnumConstants()[i]);
        }
    }

    private int askGameAccess() {

        while (true) {
            System.out.println("\n--------------------");
            System.out.println("Please choose an option for game access: ");
            printOptions(GameAccess.class);
            System.out.println("--------------------");
            String input = s.next();
            try{
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }

    }

    private void newGameAccess() {

        int numOfPlayers = askNumOfPlayers();
        if (numOfPlayers == 0)
            gameAccess();

        String nickname = askNickname();
        if (nickname.equals("/"))
            newGameAccess();

        notifyNewGame(numOfPlayers, nickname);

    }
    private void existingGameAccess() {
        int gameID = 0;
        try {
            gameID = askWhichGameToAccess();
            askingWhichGameToAccess = false;
            if (gameID == 0)
                gameAccess();
        } catch (NoExistingGamesAvailable e){
            System.err.println(e.getMessage());
            gameAccess();
        }
        String nickname = askNickname();
        if (nickname.equals("/"))
            existingGameAccess();

        notifyGameToAccess(gameID, nickname);

    }
    private int askNumOfPlayers() {

        while (true) {
            System.out.println("\n--------------------");
            System.out.println("Please specify the number of players for the game   (Min: 2, Max: 4)");
            System.out.println("/ - Back");
            System.out.println("--------------------");
            String input = s.next();
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
                System.err.println("Invalid option");
            }
        }

    }
    private void printGamesToAccess(Map<Integer, ServerImpl.GameSpecs>  gameSpecsMap) {

        System.out.println("\n--------------------");
        System.out.println("Which game do you want to access?");
        System.out.println("/ - Back");
        for (Map.Entry<Integer, ServerImpl.GameSpecs> entry : gameSpecsMap.entrySet()) {
            System.out.println(entry.getKey() + " - "
                    + "Game ID: " + entry.getValue().ID()
                    + "    Current number of players connected: " + entry.getValue().currentNumOfPlayers()
                    + "    Max number of players: " + entry.getValue().maxNumOfPlayers());
        }
        System.out.println("--------------------");

    }



    private int askWhichGameToAccess() throws NoExistingGamesAvailable {

        if (gameSpecsMap.isEmpty())
            throw new NoExistingGamesAvailable();

        printGamesToAccess(gameSpecsMap);

        askingWhichGameToAccess = true;

        while (true) {

            String input = null;
            if (s.hasNext())
                input = s.next();
            else
                printGamesToAccess(gameSpecsMap);

            if (input.equals("/"))
                return 0;

            try{
                Integer number = Integer.parseInt(input);
                if (gameSpecsMap.containsKey(number))
                    return gameSpecsMap.get(number).ID();
                else
                    System.err.println("Invalid option");
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }

    }
    private String askNickname()  {

        while (true) {
            System.out.println("\n--------------------");
            System.out.println("Choose your nickname");
            System.out.println("--------------------");
            String input = s.next();
            if (input.length() > 20)
                System.err.println("Too many characters!");
            else
                return input;
        }

    }




    @Override
    public void updateGamesSpecs(String jsonGamesSpecs){

        ObjectMapper objectMapper = new ObjectMapper();

        List<ServerImpl.GameSpecs> gamesSpecs = new ArrayList<>();

        try {
            gamesSpecs = objectMapper.readValue(jsonGamesSpecs, new TypeReference<List<ServerImpl.GameSpecs>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (int key = 0; key < gamesSpecs.size(); key++)
            gameSpecsMap.put(key+1, gamesSpecs.get(key));

        if (askingWhichGameToAccess) {
            gameSpecsMap.clear();
            for (int key = 0; key < gamesSpecs.size(); key++)
                gameSpecsMap.put(key+1, gamesSpecs.get(key));

            printGamesToAccess(gameSpecsMap);
        }
    }


    @Override
    public void stop() {
        running = false;
    }


}
