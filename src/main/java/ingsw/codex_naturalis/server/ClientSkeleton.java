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


    @Override
    public String getNickname(){
        return nickname;
    }

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

    public ServerImpl getServerImpl(){
        return serverImpl;
    }

    public GameControllerImpl getGameControllerImpl() {
        return gameControllerImpl;
    }

    public void setGameController(GameControllerImpl gameControllerImpl) {
        this.gameControllerImpl = gameControllerImpl;
    }



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