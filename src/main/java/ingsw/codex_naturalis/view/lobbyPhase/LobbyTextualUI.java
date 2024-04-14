package ingsw.codex_naturalis.view.lobbyPhase;

import ingsw.codex_naturalis.events.lobbyPhase.GameAccess;
import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;
import ingsw.codex_naturalis.distributed.local.ServerImpl;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.events.gameplayPhase.UtilityCommand;

import java.util.*;

import static ingsw.codex_naturalis.events.gameplayPhase.UtilityCommand.CANCEL;

public class LobbyTextualUI extends LobbyUI {

    private String nickname;

    private boolean running;

    private final Scanner s = new Scanner(System.in);

    private boolean gamesSpecsChanged;
    private List<ServerImpl.GameSpecs> gamesSpecs = new ArrayList<>();

    private final Map<String, UtilityCommand> utilityCommands = new LinkedHashMap<>();


    public LobbyTextualUI(){
        running = true;
        utilityCommands.put("/", CANCEL);
    }


    @Override
    public void run() {

        NetworkProtocol networkProtocol = askNetworkProtocol();
        notifyNetworkProtocol(networkProtocol);

        gameAccess();

    }


    private void printUtilityCommands(){
        for (Map.Entry<String, UtilityCommand> entry : utilityCommands.entrySet())
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

    private NetworkProtocol askNetworkProtocol() {

        Map<Integer, NetworkProtocol> networkProtocolMap = new LinkedHashMap<>();
        for (int key = 0; key < NetworkProtocol.values().length; key++) {
            networkProtocolMap.put(key+1, NetworkProtocol.values()[key]);
        }
        System.out.println("Please choose a network protocol from the following options: ");
        for (Map.Entry<Integer, NetworkProtocol> entry : networkProtocolMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }

        while (true) {
            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (networkProtocolMap.containsKey(number))
                    return networkProtocolMap.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }

    }

    private void gameAccess() {

        while (running) {
            GameAccess gameAccess = askGameAccess();
            switch (gameAccess) {
                case NEW_GAME -> newGameAccess();
                case EXISTING_GAME -> existingGameAccess();
            }
        }
        System.out.println();
        System.out.println("Waiting for players to join");

    }
    private GameAccess askGameAccess() {

        Map<Integer, GameAccess> gameAccessMap = new LinkedHashMap<>();
        for (int key = 0; key < GameAccess.values().length; key++) {
            gameAccessMap.put(key+1, GameAccess.values()[key]);
        }
        System.out.println("Please choose an option for game access: ");
        for (Map.Entry<Integer, GameAccess> entry : gameAccessMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }

        while (true) {
            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (gameAccessMap.containsKey(number))
                    return gameAccessMap.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }

    }
    private void newGameAccess() {

        try {
            int numOfPlayers = askNumOfPlayers();
            String nickname = askNickname();
            notifyNewGame(numOfPlayers, nickname);
        } catch (UtilityCommandException e) {
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        }

    }
    private void existingGameAccess() {

        try {
            int gameID = askWhichGameToAccess();
            String nickname = askNickname();
            notifyGameToAccess(gameID, nickname);
        } catch (UtilityCommandException e) {
            UtilityCommand utilityCommand = utilityCommands.get(e.getMessage());
            utilityCommandCase(utilityCommand);
        } catch (NicknameAlreadyExistsException | NoExistingGamesAvailable | MaxNumOfPlayersInException |
                 NotReachableGameException e) {
            System.err.println(e.getMessage());
        }

    }
    private int askNumOfPlayers() throws UtilityCommandException {

        System.out.println("Please specify the number of players for the game   (Min: 2, Max: 4)");

        while (true) {
            String input = s.next();
            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            try {
                int numOfPlayers = Integer.parseInt(input);
                if (numOfPlayers < 2)
                    System.err.println("The minimum number of players is 2");
                else if (numOfPlayers > 4)
                    System.err.println("The maximum number of players is 4");
                else
                    return numOfPlayers;
            } catch (NumberFormatException e) {
                System.err.println("This is not a number: " + input);
            }
        }

    }
    private void printGamesToAccess(Map<Integer, ServerImpl.GameSpecs>  gameSpecsMap) {

        System.out.println("Which game do you want to access?");
        printUtilityCommands();
        for (Map.Entry<Integer, ServerImpl.GameSpecs> entry : gameSpecsMap.entrySet()) {
            System.out.println(entry.getKey() + " - "
                    + "Game ID: " + entry.getValue().ID()
                    + "    Current number of players connected: " + entry.getValue().currentNumOfPlayers()
                    + "    Max number of players: " + entry.getValue().maxNumOfPlayers());
        }

    }
    private int askWhichGameToAccess() throws UtilityCommandException, NoExistingGamesAvailable {

        if (gamesSpecs.isEmpty())
            throw new NoExistingGamesAvailable();

        Map<Integer, ServerImpl.GameSpecs>  gameSpecsMap = new LinkedHashMap<>();
        for (int key = 0; key < gamesSpecs.size(); key++)
            gameSpecsMap.put(key+1, gamesSpecs.get(key));
        printGamesToAccess(gameSpecsMap);
        gamesSpecsChanged = false;


        while (true) {
            String input = null;
            while (input == null) {
                if (gamesSpecsChanged) {
                    gameSpecsMap.clear();
                    for (int key = 0; key < gamesSpecs.size(); key++)
                        gameSpecsMap.put(key+1, gamesSpecs.get(key));
                    printGamesToAccess(gameSpecsMap);
                    gamesSpecsChanged = false;
                }
                if (s.hasNext())
                    input = s.next();
            }

            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            try{
                Integer number = Integer.parseInt(input);
                if (gameSpecsMap.containsKey(number))
                    return gameSpecsMap.get(number).ID();
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }

    }
    private String askNickname() throws UtilityCommandException {

        System.out.println("Choose your nickname");

        while (true) {
            String input = s.next();
            if (utilityCommands.containsKey(input))
                throw new UtilityCommandException(input);
            if (input.length() > 20)
                System.err.println("Too many characters!");
            else
                return input;
        }

    }


    @Override
    public void stop() {
        running = false;
    }


    @Override
    public void updateGamesSpecs(List<ServerImpl.GameSpecs> gamesSpecs){
        this.gamesSpecs = gamesSpecs;
        gamesSpecsChanged = true;
    }

}
