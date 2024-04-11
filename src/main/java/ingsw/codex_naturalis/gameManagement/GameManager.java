package ingsw.codex_naturalis.gameManagement;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.controller.lobbyPhase.LobbyController;
import ingsw.codex_naturalis.controller.setupPhase.SetupController;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.view.gameplayPhase.Observer;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class GameManager implements Observer<Game, Event> {

    private final Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();
    private UIChoice uiChoice;

    private final Game model;

    private LobbyUI lobbyView;
    private LobbyController lobbyController;

    private SetupUI setupView;
    private SetupController setupController;

    private GameplayUI gameplayView;
    private GameplayController gameplayController;


    public GameManager(Game model){
        this.model = model;
        for (int key = 0; key < UIChoice.values().length; key++) {
            uiChoices.put(key, UIChoice.values()[key]);
        }
    }


    public void startGame(){
        uiChoice = askUIChoice();
        lobbyView = uiChoice.createLobbyUI();
        lobbyController = new LobbyController(model, lobbyView);
        lobbyView.run();
    }

    private UIChoice askUIChoice() {
        Scanner s = new Scanner(System.in);
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_GREEN = "\u001B[32m";
        String[] colors = {ANSI_RED, ANSI_GREEN, ANSI_BLUE, ANSI_PURPLE};
        String text = "Codex Naturalis!";
        System.out.print("Welcome to ");
        for (int i = 0; i < text.length(); i++) {
            int colorIndex = i % colors.length;
            String color = colors[colorIndex];
            System.out.print(color + text.charAt(i));
        }
        System.out.println(ANSI_RESET);
        System.out.println();
        System.out.println("Before we begin, please choose your preferred user interface (UI) option:");
        for (Map.Entry<Integer, UIChoice> entry : uiChoices.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
        System.out.println("--- Insert the character associated to the action ---");

        while (true) {
            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (uiChoices.containsKey(number))
                    return uiChoices.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }
    }

    @Override
    public void update(Game o, Event arg, String nickname) {
        switch (arg) {
            case SETUP_STATUS -> setupPhase();
            case GAMEPLAY_STATUS -> gameplayPhase();
        }
    }

    private void setupPhase(){
        setupView = uiChoice.createSetupUI();
        setupController = new SetupController(model, setupView);
    }

    private void gameplayPhase() {
    }
}
