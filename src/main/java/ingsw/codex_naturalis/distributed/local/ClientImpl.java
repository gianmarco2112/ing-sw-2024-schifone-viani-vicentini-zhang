package ingsw.codex_naturalis.distributed.local;

import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;
import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.controller.setupPhase.SetupObserver;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourTurnStatusException;
import ingsw.codex_naturalis.distributed.UIChoice;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.view.GameUI;
import ingsw.codex_naturalis.view.gameplayPhase.GameplayUI;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.view.lobbyPhase.LobbyUI;
import ingsw.codex_naturalis.view.setupPhase.SetupUI;

import java.util.*;

public class ClientImpl implements Client, LobbyObserver, SetupObserver, GameplayObserver, Runnable {

    private String nickname;

    private final Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();
    private final UIChoice uiChoice;

    private GameUI currentGameView;

    private final LobbyUI lobbyView;
    private SetupUI setupView;
    private GameplayUI gameplayView;

    private Server server;

    public ClientImpl(Server server){

        uiChoice = askUIChoice();

        lobbyView = uiChoice.createLobbyUI();
        lobbyView.addObserver(this);

        this.server = server;
        server.register(this);

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
        for (int key = 0; key < UIChoice.values().length; key++) {
            uiChoices.put(key+1, UIChoice.values()[key]);
        }
        for (Map.Entry<Integer, UIChoice> entry : uiChoices.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }

        while (true) {
            String input = s.next();
            try{
                Integer number = Integer.parseInt(input);
                if (uiChoices.containsKey(number))
                    if (uiChoices.get(number) == UIChoice.GUI)
                        System.err.println("We're working on it, please choose an other option.");
                    else
                        return uiChoices.get(number);
                else
                    System.err.println("This is not a command: " + number);
            } catch (NumberFormatException e) {
                System.err.println("This is not a command: "+ input);
            }
        }
    }



    @Override
    public void updateGameUI(Game.Immutable o, Event arg, String playerWhoUpdated) {
        this.currentGameView.update(o, arg, nickname, playerWhoUpdated);
    }

    @Override
    public void updateLobbyUI(List<ServerImpl.GameSpecs> gamesSpecs) {
        lobbyView.updateGamesSpecs(gamesSpecs);
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void updateView(GameStatus gameStatus, PlayersConnectedStatus playersConnectedStatus) {
        switch (gameStatus) {
            case SETUP -> {
                lobbyView.stop();
                lobbyView.deleteObservers();
                currentGameView = uiChoice.createSetupUI(playersConnectedStatus);
            }
            case GAMEPLAY -> {
                currentGameView.stop();
                currentGameView = uiChoice.createGameplayUI();
            }
        }
        currentGameView.run();
    }

    @Override
    public void updatePlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus) {
        currentGameView.setPlayersConnectedStatus(playersConnectedStatus);
    }


    @Override
    public void updateNetworkProtocol(NetworkProtocol networkProtocol) {
        server.updateNetworkProtocol(this, networkProtocol);
    }
    @Override
    public void updateGameToAccess(int gameID, String nickname) {
        server.updateGameToAccess(this, gameID, nickname);
    }
    @Override
    public void updateNewGame(int numOfPlayers, String nickname) {
        server.updateNewGame(this, numOfPlayers, nickname);
    }




    @Override
    public void updateReady() {
        server.updateReady(this);
    }





    @Override
    public void updateFlipCard(FlipCard flipCard) {
        server.updateFlipCard(this, flipCard);
    }
    @Override
    public void updatePlayCard(PlayCard playCard, int x, int y) throws NotYourTurnException {
        server.updatePlayCard(this, playCard, x, y);
    }
    @Override
    public void updateDrawCard(DrawCard drawCard) throws NotYourTurnException, NotYourTurnStatusException {
        server.updateDrawCard(this, drawCard);
    }
    @Override
    public void updateText(Message message, String content, List<String> receivers) {
        server.updateText(this, message, content, receivers);
    }





    @Override
    public void run() {

        lobbyView.run();

    }

}
