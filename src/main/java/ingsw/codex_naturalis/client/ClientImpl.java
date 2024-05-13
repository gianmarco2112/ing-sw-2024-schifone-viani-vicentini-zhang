package ingsw.codex_naturalis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.util.UIObservableItem;
import ingsw.codex_naturalis.client.view.util.ViewObserver;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.server.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.server.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

public class ClientImpl implements Client, ViewObserver {

    private String nickname;

    ObjectMapper objectMapper = new ObjectMapper();

    private UI view;

    private final Server server;
    private GameController gameController;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    public ClientImpl(Server server, NetworkProtocol networkProtocol) throws RemoteException {
        this.server = server;
        switch (networkProtocol) {
            case RMI -> server.register((Client) UnicastRemoteObject.exportObject(this, 0));
            case SOCKET -> server.register(this);
        }

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                server.imAlive(this);
            } catch (RemoteException e) {
                System.err.println("Error while sending heart beat");
            }
        }, 0, 5, TimeUnit.SECONDS);
    }


    private UIChoice askUIChoice() {

        Scanner s = new Scanner(System.in);

        System.out.print("\n\nWelcome to ");

        //red, green, blue, purple
        String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[34m", "\u001B[35m"};
        String text = "Codex Naturalis!";

        //print fancy codex naturalis text
        for (int i = 0; i < text.length(); i++) {
            int colorIndex = i % colors.length;
            String color = colors[colorIndex];
            System.out.print(color + text.charAt(i));
        }

        //color reset
        System.out.println("\u001B[0m");

        System.out.println("""
                                
                --------------------------------------------------------
                Please choose your preferred user interface (UI) option:
                                
                (1) Textual User interface - TUI
                (2) Graphical User Interface - GUI
                --------------------------------------------------------
                                
                                
                """);

        Map<Integer, UIChoice> uiChoices = new LinkedHashMap<>();

        String input;
        while (true) {
            input = s.next();
            try {
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1 -> {
                        return UIChoice.TUI;
                    }
                    case 2 -> {
                        return UIChoice.GUI;
                    }
                    default -> System.err.println("Invalid option");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            }
        }

    }


    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        view.setNickname(nickname);
    }


    @Override
    public void updateGamesSpecs(String jsonGameSpecs) {
        try {
            List<GameSpecs> gamesSpecs = objectMapper.readValue(jsonGameSpecs, new TypeReference<List<GameSpecs>>() {
            });
            view.updateGamesSpecs(gamesSpecs);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json value");
        }
    }


    @Override
    public void reportException(String error) {
        view.reportError(error);
    }


    @Override
    public void gameJoined(int gameID) {
        view.updateGameID(gameID);
    }


    @Override
    public void allPlayersJoined() {
        view.allPlayersJoined();
    }


    @Override
    public void setupUpdated(String jsonImmGame, String jsonGameEvent) {

        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            GameEvent gameEvent = objectMapper.readValue(jsonGameEvent, GameEvent.class);
            view.updateSetup(immGame, gameEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }

    }

    @Override
    public void initialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) {
        try {
            ImmGame game = objectMapper.readValue(jsonImmGame, ImmGame.class);
            InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
            view.updateInitialCard(game, initialCardEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void colorUpdated(String jsonColor) {
        try {
            Color color = objectMapper.readValue(jsonColor, Color.class);
            view.updateColor(color);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void objectiveCardChosen(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.updateObjectiveCardChoice(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void setupEnded(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.endSetup(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void cardFlipped(String jsonGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonGame, ImmGame.class);
            view.cardFlipped(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void cardPlayed(String jsonImmGame, String playerNicknameWhoUpdated) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.cardPlayed(immGame, playerNicknameWhoUpdated);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void cardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.cardDrawn(immGame, playerNicknameWhoUpdated);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void turnChanged(String currentPlayer) throws RemoteException {
        view.turnChanged(currentPlayer);
    }

    @Override
    public void messageSent(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.messageSent(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void twentyPointsReached(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.twentyPointsReached(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void decksEmpty(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.decksEmpty(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void gameEnded(String winner, String jsonPlayers, String jsonPoints, String jsonSecretObjectiveCards) {
        try {
            List<String> players = objectMapper.readValue(jsonPlayers, new TypeReference<>() {
            });
            List<Integer> points = objectMapper.readValue(jsonPoints, new TypeReference<>() {
            });
            List<ImmObjectiveCard> secretObjectiveCards = objectMapper.readValue(jsonPlayers, new TypeReference<>() {
            });
            view.gameEnded(winner, players, points, secretObjectiveCards);
            gameController = null;
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void gameCanceled() {
        view.gameCanceled();
        gameController = null;
    }

    @Override
    public void gameLeft() {
        view.gameLeft();
        gameController = null;
    }

    @Override
    public void gameRejoined(String jsonImmGame, String nickname) {
        this.nickname = nickname;
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.gameRejoined(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    @Override
    public void updatePlayerInGameStatus(String jsonImmGame, String playerNickname,
                                         String jsonInGame, String jsonHasDisconnected) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            boolean inGame = objectMapper.readValue(jsonInGame, Boolean.class);
            boolean hasDisconected = objectMapper.readValue(jsonHasDisconnected, Boolean.class);
            view.updatePlayerInGameStatus(immGame, playerNickname, inGame, hasDisconected);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void gameToCancelLater()  {
        view.gamePaused();
    }

    @Override
    public void gameResumed()  {
        view.gameResumed();
    }


    @Override
    public void ctsUpdateNickname(String nickname) {
        try {
            server.chooseNickname(this, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateGameToAccess(int gameID) {
        try {
            server.accessExistingGame(this, gameID);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateNewGame(int numOfPlayers) {
        try {
            server.accessNewGame(this, numOfPlayers);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }


    @Override
    public void ctsUpdateReady() {
        try {
            gameController = server.getGameController(this);
            gameController.readyToPlay();
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateInitialCard(InitialCardEvent initialCardEvent) {
        try {
            gameController.updateInitialCard(nickname, objectMapper.writeValueAsString(initialCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateColor(Color color) {
        try {
            gameController.chooseColor(nickname, objectMapper.writeValueAsString(color));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateObjectiveCardChoice(int index) {
        try {
            gameController.chooseSecretObjectiveCard(nickname, index);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }


    @Override
    public void ctsUpdateFlipCard(int index) {
        try {
            gameController.flipCard(nickname, index);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdatePlayCard(int index, int x, int y) throws NotYourTurnException {
        try {
            gameController.playCard(nickname, index, x, y);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {
        try {
            gameController.drawCard(nickname, objectMapper.writeValueAsString(drawCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void ctsUpdateSendMessage(String receiver, String content) {
        try {
            gameController.sendMessage(nickname, receiver, content);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void updateLeaveGame() {
        try {
            server.leaveGame(this, false);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server");
        }
    }

    @Override
    public void updateGetGameController() {
        try {
            this.gameController = server.getGameController(this);
        } catch (RemoteException e) {
            System.err.println("Error while getting game controller\n"+e.getMessage());
        }
    }


    public void runView() {

        UIObservableItem uiObservableItem = new UIObservableItem();
        uiObservableItem.addObserver(this);
        view = askUIChoice().createView(uiObservableItem);

        try {
            server.viewIsReady(this);
        } catch (RemoteException e) {
            System.err.println("Error while updating server");
        }

        view.run();

    }


}
