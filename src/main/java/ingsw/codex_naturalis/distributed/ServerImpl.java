package ingsw.codex_naturalis.distributed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.util.GameObserver;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.controller.setupPhase.SetupController;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.util.PlayerEvent;
import ingsw.codex_naturalis.view.UI;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements Server, GameObserver {

    private static class GameManagement <Controller>{
        private final Game model;
        private final Controller controller;
        private final List<Client> views;

        private NicknameMap nicknameMap = new NicknameMap();

        private GameManagement(Game model, Controller controller, List<Client> views){
            this.model = model;
            this.controller = controller;
            this.views = views;
        }


        private Game getModel() {
            return model;
        }

        private Controller getController() {
            return controller;
        }

        private List<Client> getViews() {
            return views;
        }

    }

    private static class LobbyAndStartingGameManagement {
        private final Game model;
        private final List<Client> views = new ArrayList<>();

        private final NicknameMap nicknameMap = new NicknameMap();

        private LobbyAndStartingGameManagement(Game model, Client view){
            this.model = model;
            this.views.add(view);
        }

        private Game getModel(){
            return model;
        }

        private List<Client> getViews() {
            return views;
        }

        private void addView(Client view){
            this.views.add(view);
        }
    }

    private static class NicknameMap {
        private final Map<Client, String> clientToNicknameMap = new HashMap<>();
        private final Map<String, Client> nicknameToClientMap = new HashMap<>();

        private void addClient(Client client, String nickname) {
            clientToNicknameMap.put(client, nickname);
            nicknameToClientMap.put(nickname, client);
        }

        private String getNickname(Client client) {
            return clientToNicknameMap.get(client);
        }

        private Client getClient(String nickname) {
            return nicknameToClientMap.get(nickname);
        }
    }



    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Client> newClients = new ArrayList<>();

    private final List<LobbyAndStartingGameManagement> lobbyAndStartingGames = new ArrayList<>();
    private final List<GameManagement<SetupController>> setupGames = new ArrayList<>();
    private final List<GameManagement<GameplayController>> gameplayGames = new ArrayList<>();


    ExecutorService executorService = Executors.newCachedThreadPool();




    @Override
    public void register(Client client) {

        synchronized (newClients) {
            newClients.add(client);
        }

        updateClientLobbyUIGameSpecs(new ArrayList<>(List.of(client)));

    }


    private void updateClientLobbyUIGameSpecs(List<Client> clients){

        for (Client client : clients) {
            executorService.execute(() -> {
                try {
                    client.stcUpdateLobbyUIGameSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while trying to update client's lobby UI");
                }
            });
        }

    }
    private List<GameSpecs> getGamesSpecs() {

        synchronized (lobbyAndStartingGames) {
            List<GameSpecs> gamesSpecs = new ArrayList<>();
            for (LobbyAndStartingGameManagement gameManagement : lobbyAndStartingGames) {
                int gameID = gameManagement.getModel().getGameID();
                int numOfPlayers = gameManagement.getModel().getNumOfPlayers();
                int currentNumOfPlayers = gameManagement.getModel().getPlayerOrder().size();
                GameSpecs gameSpecs = new GameSpecs(gameID, currentNumOfPlayers, numOfPlayers);
                gamesSpecs.add(gameSpecs);
            }

            return gamesSpecs;
        }
    }



    private LobbyAndStartingGameManagement getLobbyAndStartingGameManagementFromGameID(int gameID) throws NotReachableGameException{
        for (LobbyAndStartingGameManagement game : lobbyAndStartingGames) {
            if (gameID == game.getModel().getGameID())
                return game;
        }
        throw new NotReachableGameException();
    }


    private void reportLobbyUIErrorToClient(Client client, String error){
        executorService.submit(() -> {
            try {
                client.reportLobbyUIError(error);
            } catch (RemoteException e) {
                System.err.println("Error while updating client");
            }
        });
    }


    @Override
    public void ctsUpdateGameToAccess(Client client, int gameID, String nickname) throws NicknameAlreadyExistsException, MaxNumOfPlayersInException, NotReachableGameException{

        executorService.submit( () -> {
            synchronized (lobbyAndStartingGames) {
                try {

                    LobbyAndStartingGameManagement lobbyAndStartingGameManagement = getLobbyAndStartingGameManagementFromGameID(gameID);
                    lobbyAndStartingGameManagement.getModel().addPlayer(new Player(nickname));
                    lobbyAndStartingGameManagement.addView(client);
                    lobbyAndStartingGameManagement.nicknameMap.addClient(client, nickname);

                    synchronized (newClients) {
                        newClients.remove(client);
                    }

                    if (lobbyAndStartingGameManagement.getModel().getNumOfPlayers() > lobbyAndStartingGameManagement.getModel().getPlayerOrder().size())
                        try {
                            client.stcUpdateUI(objectMapper.writeValueAsString(UI.GAME_STARTING));
                            client.stcUpdateGameStartingUIGameID(gameID);
                        } catch (RemoteException | JsonProcessingException e) {
                            System.err.println("Error while trying to update client's UI to Game Starting");
                        }

                    else {
                        lobbyAndStartingGames.remove(lobbyAndStartingGameManagement);
                        SetupController setupController = new SetupController(lobbyAndStartingGameManagement.getModel(), lobbyAndStartingGameManagement.getViews());
                        Game model = lobbyAndStartingGameManagement.getModel();
                        List<Client> views = lobbyAndStartingGameManagement.getViews();
                        GameManagement<SetupController> gameManagement = new GameManagement<>(model, setupController, views);
                        gameManagement.nicknameMap = lobbyAndStartingGameManagement.nicknameMap;
                        synchronized (setupGames) {
                            setupGames.add(gameManagement);
                            List<Client> clients = gameManagement.getViews();
                            for (Client c : clients) {
                                try {
                                    c.stcUpdateUI(objectMapper.writeValueAsString(UI.SETUP));
                                } catch (RemoteException | JsonProcessingException e) {
                                    System.err.println("Error while trying to update client's UI to Setup");
                                }
                            }
                        }
                    }

                } catch (NotReachableGameException | NicknameAlreadyExistsException | MaxNumOfPlayersInException e) {
                    reportLobbyUIErrorToClient(client, e.getMessage());
                }
            }
        });

    }

    private int createGameID(){
        int gameID;
        List<Integer> gameIDs = new ArrayList<>();
        for (LobbyAndStartingGameManagement gameManagement : lobbyAndStartingGames)
            gameIDs.add(gameManagement.getModel().getGameID());
        do {
            gameID = new Random().nextInt(100) + 1;
        } while (gameIDs.contains(gameID));
        return gameID;
    }

    @Override
    public void ctsUpdateNewGame(Client client, int numOfPlayers, String nickname) {

        executorService.submit( () -> {
            int gameID = createGameID();
            Game game = new Game(gameID, numOfPlayers);
            game.addObserver(this);
            Player player = new Player(nickname);
            game.addPlayer(player);

            LobbyAndStartingGameManagement lobbyAndStartingGameManagement = new LobbyAndStartingGameManagement(game, client);
            lobbyAndStartingGameManagement.nicknameMap.addClient(client, nickname);

            synchronized (lobbyAndStartingGames) {
                lobbyAndStartingGames.add(lobbyAndStartingGameManagement);
            }

            try {
                client.stcUpdateUI(objectMapper.writeValueAsString(UI.GAME_STARTING));
                client.stcUpdateGameStartingUIGameID(gameID);
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while trying to update client's UI to Game Starting");
            }

            synchronized (newClients) {
                newClients.remove(client);
                updateClientLobbyUIGameSpecs(newClients);
            }
        });

    }



    private GameManagement<GameplayController> findGameplayGameManagementByClient(Client client){
        synchronized (gameplayGames) {
            for (GameManagement<GameplayController> gameManagement : gameplayGames) {
                if (gameManagement.getViews().contains(client))
                    return gameManagement;
            }
        }
        return null;
    }

    private GameManagement<GameplayController> findGameplayGameManagementByGame(Game game){
        synchronized (gameplayGames) {
            for (GameManagement<GameplayController> gameManagement : gameplayGames) {
                if (gameManagement.getModel().equals(game))
                    return gameManagement;
            }
        }
        return null;
    }

    private GameManagement<SetupController> findSetupGameManagementByClient(Client client){
        synchronized (setupGames) {
            for (GameManagement<SetupController> gameManagement : setupGames) {
                if (gameManagement.getViews().contains(client))
                    return gameManagement;
            }
        }
        return null;
    }

    private GameManagement<SetupController> findSetupGameManagementByGame(Game game){
        synchronized (setupGames) {
            for (GameManagement<SetupController> gameManagement : setupGames) {
                if (gameManagement.getModel().equals(game))
                    return gameManagement;
            }
        }
        return null;
    }

    @Override
    public void ctsUpdateReady(Client client) {
       executorService.submit( () -> {
            GameManagement<SetupController> gameManagement = findSetupGameManagementByClient(client);
            gameManagement.getController().updateReady(gameManagement.nicknameMap.getNickname(client));
        });

    }

    @Override
    public void ctsUpdateInitialCard(Client client, String jsonInitialCardEvent) {
        executorService.submit( () -> {
            try {
                InitialCardEvent initialCardEvent = objectMapper.readValue(jsonInitialCardEvent, InitialCardEvent.class);
                GameManagement<SetupController> gameManagement = findSetupGameManagementByClient(client);
                gameManagement.getController().updateInitialCard(gameManagement.nicknameMap.getNickname(client), initialCardEvent);
            } catch (JsonProcessingException e){
                System.err.println("Error while processing json");
            }
        });
    }

    @Override
    public void ctsUpdateColor(Client client, String jsonColor) {
        executorService.submit( () -> {
            try {
                Color color = objectMapper.readValue(jsonColor, Color.class);
                GameManagement<SetupController> gameManagement = findSetupGameManagementByClient(client);
                gameManagement.getController().updateColor(gameManagement.nicknameMap.getNickname(client), color);
            } catch (JsonProcessingException e) {
                System.err.println("Error while processing json");
            } catch (ColorAlreadyChosenException e){
                try {
                    client.reportSetupUIError(e.getMessage());
                } catch (RemoteException e1) {
                    System.err.println("Error while updating client");
                }
            }
        });
    }

    @Override
    public void ctsUpdateObjectiveCardChoice(Client client, String jsonObjectiveCardChoice) throws RemoteException {
        executorService.submit( () -> {
            try {
                ObjectiveCardChoice objectiveCardChoice = objectMapper.readValue(jsonObjectiveCardChoice, ObjectiveCardChoice.class);
                GameManagement<SetupController> gameManagement = findSetupGameManagementByClient(client);
                gameManagement.getController().updateObjectiveCard(gameManagement.nicknameMap.getNickname(client), objectiveCardChoice);
            } catch (JsonProcessingException e){
                System.err.println("Error while processing json");
            }
        });
    }


    @Override
    public void ctsUpdateFlipCard(Client client, String jsonFlipCardEvent) {
        executorService.submit( () -> {
            try {
                FlipCardEvent flipCardEvent = objectMapper.readValue(jsonFlipCardEvent, FlipCardEvent.class);
                GameManagement<GameplayController> gameManagement = findGameplayGameManagementByClient(client);
                gameManagement.getController().updateFlipCard(gameManagement.nicknameMap.getNickname(client), flipCardEvent);
            } catch (JsonProcessingException e){
                System.err.println("Error while processing json");
            }
        });
    }

    @Override
    public void ctsUpdatePlayCard(Client client, String jsonPlayCardEvent, int x, int y) throws NotYourTurnException {
        executorService.submit( () -> {
            try {
                PlayCardEvent playCardEvent = objectMapper.readValue(jsonPlayCardEvent, PlayCardEvent.class);
                GameManagement<GameplayController> gameManagement = findGameplayGameManagementByClient(client);
                try {
                    gameManagement.getController().updatePlayCard(gameManagement.nicknameMap.getNickname(client), playCardEvent, x, y);
                } catch (NotYourTurnException | NotYourPlayTurnStatusException | NotPlayableException e){
                    try {
                        client.reportGameplayUIError(e.getMessage());
                    } catch (RemoteException ex) {
                        System.err.println("Error while updating client");
                    }
                }
            } catch (JsonProcessingException e){
                System.err.println("Error while processing json");
            }
        });
    }

    @Override
    public void ctsUpdateDrawCard(Client client, String jsonDrawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {
       // this.gameplayController.updateDrawCard(client, drawCardEvent);
    }

    @Override
    public void ctsUpdateText(Client client, Message message, String content, List<String> receivers) {
        //this.gameplayController.updateText(client, message, content, receivers);
    }






    @Override
    public void update(Game game, GameEvent gameEvent) {

        GameManagement<SetupController> setupGameManagement = findSetupGameManagementByGame(game);
        List<Client> clients = setupGameManagement.getViews();

        if (gameEvent == GameEvent.GAME_STATUS_CHANGED)
            switch (game.getGameStatus()) {
                case GAMEPLAY -> {
                    changeFromSetupToGameplay(game, setupGameManagement, clients);
                    return;
                }
                case LAST_ROUND_20_POINTS -> {

                }
            }

        for (Client client : clients) {
            Game.Immutable immGame = game.getImmutableGame(setupGameManagement.nicknameMap.getNickname(client));
            try {
                client.stcUpdateSetupUI(objectMapper.writeValueAsString(immGame), objectMapper.writeValueAsString(gameEvent));
            } catch (RemoteException | JsonProcessingException e){
                System.err.println("Error while updating client");
            }
        }

    }

    private void changeFromSetupToGameplay(Game game, GameManagement<SetupController> setupGameManagement, List<Client> clients) {
        synchronized (setupGames) {
            setupGames.remove(setupGameManagement);
        }
        Game model = setupGameManagement.getModel();
        List<Client> views = setupGameManagement.getViews();
        GameplayController gameplayController = new GameplayController(model, views);
        GameManagement<GameplayController> gameplayGameManagement = new GameManagement<>(model, gameplayController, views);
        gameplayGameManagement.nicknameMap = setupGameManagement.nicknameMap;
        synchronized (gameplayGames) {
            gameplayGames.add(gameplayGameManagement);
        }
        for (Client client : clients) {
            Game.Immutable immGame = game.getImmutableGame(setupGameManagement.nicknameMap.getNickname(client));
            try {
                client.stcUpdateUI(objectMapper.writeValueAsString(UI.GAMEPLAY));
                client.stcUpdateGameplayUIPlayerOrder(objectMapper.writeValueAsString(immGame));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while updating client");
            }
        }
    }

    @Override
    public void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated) {
        switch (playerEvent) {
            case INITIAL_CARD_FLIPPED -> initialCardCase(game, playerWhoUpdated, InitialCardEvent.FLIP);
            case INITIAL_CARD_PLAYED -> initialCardCase(game, playerWhoUpdated, InitialCardEvent.PLAY);
            case COLOR_SETUP -> colorCase(game, playerWhoUpdated);
            case OBJECTIVE_CARD_CHOSEN -> objectiveCardCase(game, playerWhoUpdated);
            case HAND_CARD_FLIPPED -> privateCase(game, playerWhoUpdated);
            case HAND_CARD_PLAYED -> publicCase(game, playerWhoUpdated);
        }
    }

    private void publicCase(Game game, Player playerWhoUpdated) {
        GameManagement<GameplayController> gameplayGameManagement = findGameplayGameManagementByGame(game);
        List<Client> clients = gameplayGameManagement.getViews();
        for (Client client : clients) {
            Game.Immutable immGame = game.getImmutableGame(gameplayGameManagement.nicknameMap.getNickname(client));
            try {
                client.stcUpdateGameplayUI(objectMapper.writeValueAsString(immGame));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while updating client");
            }
        }
    }

    private void privateCase(Game game, Player playerWhoUpdated) {

        GameManagement<GameplayController> gameplayGameManagement = findGameplayGameManagementByGame(game);
        Client client = gameplayGameManagement.nicknameMap.getClient(playerWhoUpdated.getNickname());
        Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
        try {
            client.stcUpdateGameplayUI(objectMapper.writeValueAsString(immGame));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }


    private void objectiveCardCase(Game game, Player playerWhoUpdated) {

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        Client client = gameManagement.nicknameMap.getClient(playerWhoUpdated.getNickname());
        Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
        try {
            client.stcUpdateSetupUIObjectiveCardChoice(objectMapper.writeValueAsString(immGame));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }

    private void colorCase(Game game, Player playerWhoUpdated) {

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        Client client = gameManagement.nicknameMap.getClient(playerWhoUpdated.getNickname());
        Player player = game.getPlayerByNickname(gameManagement.nicknameMap.getNickname(client));
        try {
            client.stcUpdateSetupUIColor(objectMapper.writeValueAsString(player.getColor()));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }

    private void initialCardCase(Game game, Player playerWhoUpdated, InitialCardEvent initialCardEvent) {

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        Client client;
        synchronized (gameManagement) {
            client = gameManagement.nicknameMap.getClient(playerWhoUpdated.getNickname());
        }
        Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
        try {
            client.stcUpdateSetupUIInitialCard(objectMapper.writeValueAsString(immGame), objectMapper.writeValueAsString(initialCardEvent));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }

}
