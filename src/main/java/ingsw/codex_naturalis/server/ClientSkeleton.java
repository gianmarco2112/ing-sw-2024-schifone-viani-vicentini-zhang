package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.middleware.MessageToClient.*;
import ingsw.codex_naturalis.common.middleware.MessageToServer.MessageToServer;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {

    String nickname;

    private final ServerImpl serverImpl;
    private GameControllerImpl gameControllerImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    private final BufferedReader reader;
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
            throw new RemoteException("Error while creating the client skeleton");
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
            MessageToClient message = new STCNicknameChosen();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(nickname);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    public ServerImpl getServerImpl(){
        return serverImpl;
    }

    public BufferedReader getReader() {
        return reader;
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
            MessageToClient message = new STCGamesSpecsUpdated();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonGamesSpecs);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void reportException(String error) throws RemoteException {
        try {
            MessageToClient message = new STCReportException();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(error);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void gameJoined(int gameID) throws RemoteException {
        try {
            MessageToClient message = new STCGameJoined();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(gameID);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
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
            MessageToClient message = new STCSetupUpdated();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(jsonGameEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void initialCardUpdated(String jsonImmGame, String jsonInitialCardEvent) {
        try {
            MessageToClient message = new STCInitialCardUpdated();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(jsonInitialCardEvent);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void colorUpdated(String jsonColor) throws RemoteException {
        try {
            MessageToClient message = new STCColorUpdated();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonColor);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void objectiveCardChosen(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCObjectiveCardChosen();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void setupEnded(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCSetupEnded();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }


    @Override
    public void cardFlipped(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCCardFlipped();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void cardPlayed(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException {
        try {
            MessageToClient message = new STCCardPlayed();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(playerNicknameWhoUpdated);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void cardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) throws RemoteException {
        try {
            MessageToClient message = new STCCardDrawn();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(playerNicknameWhoUpdated);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void turnChanged(String currentPlayer) throws RemoteException {
        try {
            MessageToClient message = new STCTurnChanged();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(currentPlayer);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void messageSent(String jsonImmGame) {
        try {
            MessageToClient message = new STCMessageSent();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void twentyPointsReached(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCTwentyPointsReached();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void decksEmpty(String jsonImmGame) throws RemoteException {
        try {
            MessageToClient message = new STCDecksEmpty();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void gameEnded(String winner, String jsonPlayers, String jsonPoints, String jsonSecretObjectiveCards) throws RemoteException {
        try {
            MessageToClient message = new STCGameEnded();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(winner);
            writer.println(jsonPlayers);
            writer.println(jsonPoints);
            writer.println(jsonSecretObjectiveCards);
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
            MessageToClient message = new STCGameRejoined();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(nickname);
            writer.flush();
        } catch (JsonProcessingException e) {
            System.err.println("Error while processing json");
        }
    }

    @Override
    public void updatePlayerInGameStatus(String jsonImmGame, String playerNickname, String jsonInGame, String jsonHasDisconnected) throws RemoteException {
        try {
            MessageToClient message = new STCUpdatePlayerInGameStatus();
            String jsonMessage = objectMapper.writeValueAsString(message);
            writer.println(jsonMessage);
            writer.println(jsonImmGame);
            writer.println(playerNickname);
            writer.println(jsonInGame);
            writer.println(jsonHasDisconnected);
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


    public void receive() throws IOException {

        String jsonMessage;
        try {
            jsonMessage = reader.readLine();
        } catch (IOException e) {
            throw new RemoteException("Error while reading from the buffered reader");
        }

        MessageToServer message = objectMapper.readValue(jsonMessage, MessageToServer.class);
        message.run(this);

    }

}