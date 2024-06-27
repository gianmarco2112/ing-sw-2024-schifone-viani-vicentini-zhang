package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.middleware.MessageToClient.*;
import ingsw.codex_naturalis.common.middleware.MessageToServer.MessageToServer;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * This class is used in socket protocol, it receives the update from the client through his server
 * stub and forwards them to the server implementation.
 */
public class ClientSkeleton implements Client {

    /**
     * Client's nickname
     */
    String nickname;

    private final ServerImpl serverImpl;

    /**
     * Client's game controller
     */
    private GameControllerImpl gameControllerImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Used to read the input
     */
    private final BufferedReader reader;

    /**
     * Used to write the output
     */
    private final PrintWriter writer;

    /**
     * ClientSkeleton's constructor
     */

    public ClientSkeleton(Socket socket, ServerImpl serverImpl) throws RemoteException{

        this.serverImpl = serverImpl;

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            this.writer = new PrintWriter(bufferedWriter);

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.reader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            throw new RemoteException("Error while creating the client skeleton\n"+e.getMessage());
        }

    }

    /**
     * Nickname's getter
     * @return nickname
     */
    @Override
    public String getNickname(){
        return nickname;
    }
    /**
     * Nickname's setter
     * @param nickname to set
     */
    @Override
    public void setNickname(String nickname) throws RemoteException {
        this.nickname = nickname;
        try {
            MessageToClient message = new STCNicknameChosen(nickname);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * GameServerImpl's getter
     * @return gameServerImpl
     */
    public ServerImpl getServerImpl(){
        return serverImpl;
    }
    /**
     * GameControllerImpl's getter
     * @return gameControllerImpl
     */
    public GameControllerImpl getGameControllerImpl() {
        return gameControllerImpl;
    }

    /**
     * GameController's setter
     * @param gameControllerImpl to set
     */
    public void setGameController(GameControllerImpl gameControllerImpl) {
        this.gameControllerImpl = gameControllerImpl;
    }
    /**
     * Update from server: Games specs updated
     * @param jsonGamesSpecs games specks
     */
    @Override
    public void updateGamesSpecs(String jsonGamesSpecs) throws RemoteException {
        try {
            MessageToClient message = new STCGamesSpecsUpdated(jsonGamesSpecs);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * Exception received
     * @param error error
     */
    @Override
    public void reportException(String error) throws RemoteException {
        try {
            MessageToClient message = new STCReportException(error);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }

    /**
     * Update from server: game joined
     */
    @Override
    public void gameJoined(int gameID) throws RemoteException {
        try {
            MessageToClient message = new STCGameJoined(gameID);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json\n"+e.getMessage());
        }
    }
    /**
     * Update from server: all players joined the game
     */
    @Override
    public void allPlayersJoined() throws RemoteException {
        try {
            MessageToClient message = new STCAllPlayersJoined();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    /**
     * Update from server: setup update
     * @param jsonImmGame immutable game
     * @param jsonGameEvent game event (setup event)
     */
    @Override
    public void setupUpdated(String jsonImmGame, String jsonGameEvent) {
        try {
            MessageToClient message = new STCSetupUpdated(jsonImmGame, jsonGameEvent);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
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
            MessageToClient message = new STCInitialCardUpdated(jsonImmGame, jsonInitialCardEvent);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: color updated
     * @param jsonColor color
     */
    @Override
    public void colorUpdated(String jsonColor) throws RemoteException {
        try {
            MessageToClient message = new STCColorUpdated(jsonColor);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: objective card chosen
     * @param jsonImmGame immutable game
     */
    @Override
    public void objectiveCardChosen(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCObjectiveCardChosen(jsonImmGame);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    /**
     * Update from server: setup ended
     * @param jsonImmGame immutable game
     */
    @Override
    public void setupEnded(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCSetupEnded(jsonImmGame);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    /**
     * Update from server: hand card flipped
     * @param jsonImmGame immutable game
     */
    @Override
    public void cardFlipped(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCCardFlipped(jsonImmGame);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: hand card played
     * @param jsonImmGame immutable game
     * @param playerNicknameWhoUpdated player's nickname who has played the card
     */
    @Override
    public void cardPlayed(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException {
        try {
            MessageToClient message = new STCCardPlayed(jsonImmGame, playerNicknameWhoUpdated);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: card drawn
     * @param jsonImmGame immutable game
     * @param playerNicknameWhoUpdated player's nickname who has draw a card
     */
    @Override
    public void cardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException {
        try {
            MessageToClient message = new STCCardDrawn(jsonImmGame, playerNicknameWhoUpdated);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: turn changed
     * @param currentPlayer current player
     */
    @Override
    public void turnChanged(String currentPlayer) throws RemoteException {
        try {
            MessageToClient message = new STCTurnChanged(currentPlayer);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: chat updated
     * @param jsonImmGame immutable game
     */
    @Override
    public void messageSent(String jsonImmGame) {
        try {
            MessageToClient message = new STCMessageSent(jsonImmGame);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: twenty points reached
     * @param jsonImmGame immutable game
     */
    @Override
    public void twentyPointsReached(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCTwentyPointsReached(jsonImmGame);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: decks are empty
     * @param jsonImmGame immutable game
     */
    @Override
    public void decksEmpty(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCDecksEmpty(jsonImmGame);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: game ended
     * @param jsonPlayers players with points and secret obj cards
     */
    @Override
    public void gameEnded(String jsonPlayers) throws RemoteException {
        try {
            MessageToClient message = new STCGameEnded(jsonPlayers);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: game has been canceled
     */
    @Override
    public void gameCanceled() throws RemoteException {
        try {
            MessageToClient message = new STCGameCanceled();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: game left
     */
    @Override
    public void gameLeft() throws RemoteException {
        try {
            MessageToClient message = new STCGameLeft();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: game rejoined
     * @param jsonImmGame immutable game
     * @param nickname nickname to set again
     */
    @Override
    public void gameRejoined(String jsonImmGame, String nickname) throws RemoteException {
        try {
            MessageToClient message = new STCGameRejoined(jsonImmGame, nickname);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
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
    public void updatePlayerInGameStatus(String jsonImmGame, String playerNickname, String jsonInGame, String jsonHasDisconnected) throws RemoteException {
        try {
            MessageToClient message = new STCUpdatePlayerInGameStatus(jsonImmGame, playerNickname, jsonInGame, jsonHasDisconnected);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: game will be cancelled if nobody joins within 10 seconds.
     */
    @Override
    public void gameToCancelLater() throws RemoteException {
        try {
            MessageToClient message = new STCGameToCancelLater();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: game resumed
     */
    @Override
    public void gameResumed() throws RemoteException {
        try {
            MessageToClient message = new STCGameResumed();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * Update from server: player is ready
     */
    @Override
    public void playerIsReady(String playerNickname) throws RemoteException {
        try {
            MessageToClient message = new STCPlayerIsReady(playerNickname);
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }
    /**
     * To receive messages from the server
     */

    public void receive() throws IOException {

        String jsonMessage;
        try {
            jsonMessage = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader\n"+e.getMessage());
        }

        MessageToServer message = objectMapper.readValue(jsonMessage, MessageToServer.class);
        message.run(this);

    }

}