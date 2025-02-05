package ingsw.codex_naturalis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.GameController;
import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.common.NetworkProtocol;
import ingsw.codex_naturalis.common.Server;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.immutableModel.ImmPlayer;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

/**
 * Client implementation
 */
public class ClientImpl implements Client {

    /**
     * Nickname
     */
    private String nickname;

    /**
     * Object mapper
     */
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * View
     */
    private final UI view;

    /**
     * Server reference
     */
    private final Server server;

    /**
     * Game controller reference (null if the client's not connected to a game)
     */
    private GameController gameController;

    /**
     * Scheduled executor service
     */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * Client's creator
     */
    public ClientImpl(Server server, NetworkProtocol networkProtocol, UI view) throws RemoteException {
        this.view = view;
        this.server = server;
        switch (networkProtocol) {
            case RMI -> server.register((Client) UnicastRemoteObject.exportObject(this, 0));
            case SOCKET -> server.register(this);
        }

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                server.imAlive(this);
            } catch (RemoteException e) {
                System.err.println("Error while sending heart beat\n" + e.getMessage());
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Nickname's getter
     */
    @Override
    public String getNickname() {
        return nickname;
    }

    /**
     * Nickname's setter
     */

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        view.setNickname(nickname);
    }

    /**
     * Update from server: Games specs updated
     * @param jsonGameSpecs games specks
     */
    @Override
    public void updateGamesSpecs(String jsonGameSpecs) {
        try {
            List<GameSpecs> gamesSpecs = objectMapper.readValue(jsonGameSpecs, new TypeReference<List<GameSpecs>>() {
            });
            view.updateGamesSpecs(gamesSpecs);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json value\n"+e.getMessage());
        }
    }


    /**
     * Exception received
     * @param error error
     */
    @Override
    public void reportException(String error) {
        view.reportError(error);
    }

    /**
     * Update from server: game joined
     * @param gameID game id
     */
    @Override
    public void gameJoined(int gameID) {
        view.updateGameID(gameID);
    }

    /**
     * Update from server: all players joined the game
     */
    @Override
    public void allPlayersJoined() {
        view.allPlayersJoined();
    }

    /**
     * Update from server: setup update
     * @param jsonImmGame immutable game
     * @param jsonGameEvent game event (setup event)
     */
    @Override
    public void setupUpdated(String jsonImmGame, String jsonGameEvent) {

        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            GameEvent gameEvent = objectMapper.readValue(jsonGameEvent, GameEvent.class);
            view.updateSetup(immGame, gameEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }

    }

    /**
     * Update from server: initial card played or flipped
     * @param jsonImmGame immutable game
     * @param jsonInitialCardEvent flipped or played
     */
    @Override
    public void initialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) {
        try {
            ImmGame game = objectMapper.readValue(jsonImmGame, ImmGame.class);
            InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
            view.updateInitialCard(game, initialCardEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: color updated
     * @param jsonColor color
     */
    @Override
    public void colorUpdated(String jsonColor) {
        try {
            Color color = objectMapper.readValue(jsonColor, Color.class);
            view.updateColor(color);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: objective card chosen
     * @param jsonImmGame immutable game
     */
    @Override
    public void objectiveCardChosen(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.updateObjectiveCardChoice(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: setup ended
     * @param jsonImmGame immutable game
     */
    @Override
    public void setupEnded(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.endSetup(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: hand card flipped
     * @param jsonGame immutable game
     */
    @Override
    public void cardFlipped(String jsonGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonGame, ImmGame.class);
            view.cardFlipped(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: hand card played
     * @param jsonImmGame immutable game
     * @param playerNicknameWhoUpdated player's nickname who has played the card
     */
    @Override
    public void cardPlayed(String jsonImmGame, String playerNicknameWhoUpdated) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.cardPlayed(immGame, playerNicknameWhoUpdated);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: card drawn
     * @param jsonImmGame immutable game
     * @param playerNicknameWhoUpdated player's nickname who has draw a card
     */
    @Override
    public void cardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.cardDrawn(immGame, playerNicknameWhoUpdated);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: turn changed
     * @param currentPlayer current player
     */
    @Override
    public void turnChanged(String currentPlayer) {
        view.turnChanged(currentPlayer);
    }

    /**
     * Update from server: chat updated
     * @param jsonImmGame immutable game
     */
    @Override
    public void messageSent(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.messageSent(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: twenty points reached
     * @param jsonImmGame immutable game
     */
    @Override
    public void twentyPointsReached(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.twentyPointsReached(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: decks are empty
     * @param jsonImmGame immutable game
     */
    @Override
    public void decksEmpty(String jsonImmGame) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            view.decksEmpty(immGame);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: game ended
     * @param jsonPlayers players with points and secret obj cards
     */
    @Override
    public void gameEnded(String jsonPlayers) {
        try {
            List<ImmPlayer> players = objectMapper.readValue(jsonPlayers, new TypeReference<>() {
            });
            view.gameEnded(players);
            gameController = null;
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: game has been canceled
     */
    @Override
    public void gameCanceled() {
        view.gameCanceled();
        gameController = null;
    }

    /**
     * Update from server: game left
     */
    @Override
    public void gameLeft() {
        view.gameLeft();
        gameController = null;
    }

    /**
     * Update from server: game rejoined
     * @param jsonImmGame immutable game
     * @param nickname nickname to set again
     */
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

    /**
     * Update from server: a player in game status changed (disconnected, reconnected, or left the game)
     * @param jsonImmGame immutable game
     * @param playerNickname player
     * @param jsonInGame boolean in game
     * @param jsonHasDisconnected boolean disconnected
     */
    @Override
    public void updatePlayerInGameStatus(String jsonImmGame, String playerNickname,
                                         String jsonInGame, String jsonHasDisconnected) {
        try {
            ImmGame immGame = objectMapper.readValue(jsonImmGame, ImmGame.class);
            boolean inGame = objectMapper.readValue(jsonInGame, Boolean.class);
            boolean hasDisconnected = objectMapper.readValue(jsonHasDisconnected, Boolean.class);
            view.updatePlayerInGameStatus(immGame, playerNickname, inGame, hasDisconnected);
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: game will be cancelled if nobody joins within 10 seconds.
     */
    @Override
    public void gameToCancelLater()  {
        view.gamePaused();
    }

    /**
     * Update from server: game resumed
     */
    @Override
    public void gameResumed()  {
        view.gameResumed();
    }

    /**
     * Update from server: player is ready
     */

    @Override
    public void playerIsReady(String playerNickname) {
        view.playerIsReady(playerNickname);
    }


    /**
     * Called to choose a nickname
     * @param nickname nickname
     */
    public void ctsUpdateNickname(String nickname) {
        try {
            server.chooseNickname(this, nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to join an existing game
     * @param gameID game id
     */
    public void ctsUpdateGameToAccess(int gameID) {
        try {
            server.accessExistingGame(this, gameID);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to create a new game
     * @param numOfPlayers number of players for the game
     */
    public void ctsUpdateNewGame(int numOfPlayers) {
        try {
            server.accessNewGame(this, numOfPlayers);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }


    /**
     * Called if the client's ready
     */
    public void ctsUpdateReady() {
        try {
            gameController = server.getGameController(this);
            gameController.readyToPlay(nickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to update the initial card (play or flip)
     * @param initialCardEvent play or flip
     */
    public void ctsUpdateInitialCard(InitialCardEvent initialCardEvent) {
        try {
            gameController.updateInitialCard(nickname, objectMapper.writeValueAsString(initialCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to choose a color
     * @param color color
     */
    public void ctsUpdateColor(Color color) {
        try {
            gameController.chooseColor(nickname, objectMapper.writeValueAsString(color));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to choose a secret objective card
     * @param index card index
     */
    public void ctsUpdateObjectiveCardChoice(int index) {
        try {
            gameController.chooseSecretObjectiveCard(nickname, index);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }


    /**
     * Called to flip a hand card
     * @param index hand card index
     */
    public void ctsUpdateFlipCard(int index) {
        try {
            gameController.flipCard(nickname, index);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to play a hand card
     * @param index hand card index
     * @param x coordinate x of the play area
     * @param y coordinate y of the play area
     */
    public void ctsUpdatePlayCard(int index, int x, int y) {
        try {
            gameController.playCard(nickname, index, x, y);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to draw a card
     * @param drawCardEvent card to draw
     */
    public void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) {
        try {
            gameController.drawCard(nickname, objectMapper.writeValueAsString(drawCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to send a chat message
     * @param receiver message receiver (null if it's a global message)
     * @param content message content
     */
    public void ctsUpdateSendMessage(String receiver, String content) {
        try {
            gameController.sendMessage(nickname, receiver, content);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to leave the current game.
     */
    public void updateLeaveGame() {
        try {
            server.leaveGame(this);
        } catch (RemoteException e) {
            System.err.println("Error while updating the server\n"+e.getMessage());
        }
    }

    /**
     * Called to get his game controller.
     */
    public void updateGetGameController() {
        try {
            this.gameController = server.getGameController(this);
        } catch (RemoteException e) {
            System.err.println("Error while getting game controller\n"+e.getMessage());
        }
    }

}
