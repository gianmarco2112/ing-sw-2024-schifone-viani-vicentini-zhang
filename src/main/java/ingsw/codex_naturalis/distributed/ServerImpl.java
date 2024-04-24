package ingsw.codex_naturalis.distributed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.distributed.util.GameObserver;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.controller.setupPhase.SetupController;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.util.GameEvent;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.util.PlayerEvent;
import ingsw.codex_naturalis.view.UI;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
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

        private void addView(Client view){
            this.views.add(view);
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
        private Map<Client, String> clientToNicknameMap = new HashMap<>();
        private Map<String, Client> nicknameToClientMap = new HashMap<>();

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
    //private final List<GameManagement<SetupController>> startingGames = new ArrayList<>();
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



    private LobbyAndStartingGameManagement getLobbyAndStartingGameManagementFromGameID(int gameID) throws NotReachableGameException{
        for (LobbyAndStartingGameManagement game : lobbyAndStartingGames) {
            if (gameID == game.getModel().getGameID())
                return game;
        }
        throw new NotReachableGameException();
    }


    private void reportLobbyUIErrorToClient(Client client, String error){
        //executorService.submit(() -> {
            try {
                client.reportLobbyUIError(error);
            } catch (RemoteException e) {
                System.err.println("Error while updating client");
            }
        //});
    }


    @Override
    public void ctsUpdateGameToAccess(Client client, int gameID, String nickname) throws NicknameAlreadyExistsException, MaxNumOfPlayersInException, NotReachableGameException{

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
                    client.stcUpdateUI(UI.GAME_STARTING);
                    client.stcUpdateGameStartingUIGameID(gameID);
                } catch (RemoteException e) {
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
                                c.stcUpdateUI(UI.SETUP);
                            } catch (RemoteException e) {
                                System.err.println("Error while trying to update client's UI to Setup");
                            }
                        }
                    }
                }

            } catch (NotReachableGameException | NicknameAlreadyExistsException | MaxNumOfPlayersInException e) {
                reportLobbyUIErrorToClient(client, e.getMessage());
            }
        }
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
            client.stcUpdateUI(UI.GAME_STARTING);
            client.stcUpdateGameStartingUIGameID(gameID);
        } catch (RemoteException e) {
            System.err.println("Error while trying to update client's UI to Game Starting");
        }

        synchronized (newClients) {
            newClients.remove(client);
            for (Client c : newClients) {
                try {
                    c.stcUpdateLobbyUIGameSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while trying to update client's lobby UI");
                }
            }
            //updateClientLobbyUIGameSpecs(newClients);
        }

    }





    private GameManagement<SetupController> findGameManagementByClient(Client client){
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
            GameManagement<SetupController> gameManagement = findGameManagementByClient(client);
            gameManagement.getController().updateReady(gameManagement.nicknameMap.getNickname(client));
        });

    }

    @Override
    public void ctsUpdateInitialCard(Client client, InitialCardEvent initialCardEvent) {
        executorService.submit( () -> {
            GameManagement<SetupController> gameManagement = findGameManagementByClient(client);
            gameManagement.getController().updateInitialCard(gameManagement.nicknameMap.getNickname(client), initialCardEvent);
        });
    }

    @Override
    public void ctsUpdateColor(Client client, Color color) {
        executorService.submit( () -> {
            GameManagement<SetupController> gameManagement = findGameManagementByClient(client);
            try {
                gameManagement.getController().updateColor(gameManagement.nicknameMap.getNickname(client), color);
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
    public void ctsUpdateFlipCard(Client client, FlipCard flipCard) {
        //this.gameplayController.updateFlipCard(client, flipCard);
    }

    @Override
    public void ctsUpdatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException {
        //this.gameplayController.updatePlayCard(client, playCard, x, y);
    }

    @Override
    public void ctsUpdateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {
       // this.gameplayController.updateDrawCard(client, drawCard);
    }

    @Override
    public void ctsUpdateText(Client client, Message message, String content, List<String> receivers) {
        //this.gameplayController.updateText(client, message, content, receivers);
    }




    @Override
    public void update(Game game, GameEvent gameEvent) {

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        List<Client> clients = gameManagement.getViews();
        for (Client client : clients) {
            Game.Immutable immGame = game.getImmutableGame(gameManagement.nicknameMap.getNickname(client));
            try {
                client.stcUpdate(immGame, gameEvent);
            } catch (RemoteException e){
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
        }
    }

    private void colorCase(Game game, Player playerWhoUpdated) {

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        Client client = gameManagement.nicknameMap.getClient(playerWhoUpdated.getNickname());
        Player player = game.getPlayerByNickname(gameManagement.nicknameMap.getNickname(client));
        try {
            client.stcUpdateColor(player.getColor());
        } catch (RemoteException e) {
            System.err.println("Error while updating client");
        }

    }


    /*private void setup1(Game game) {

        PlayableCard.Immutable topResourceCard = game.getResourceCardsDeck().getFirstCard().getImmutablePlayableCard();
        PlayableCard.Immutable topGoldCard = game.getGoldCardsDeck().getFirstCard().getImmutablePlayableCard();
        List<PlayableCard.Immutable> revealedResourceCards = game.getImmutableRevealedResourceCards();
        List<PlayableCard.Immutable> revealedGoldCards = game.getImmutableRevealedGoldCards();

        List<PlayableCard.Immutable> resourceCards = new ArrayList<>();
        resourceCards.add(topResourceCard);
        resourceCards.addAll(revealedResourceCards);

        List<PlayableCard.Immutable> goldCards = new ArrayList<>();
        goldCards.add(topGoldCard);
        goldCards.addAll(revealedGoldCards);

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        List<Client> clients = gameManagement.getViews();

        *//*for (Client client : clients) {
            PlayableCard.Immutable initialCard = game.getPlayerByNickname(gameManagement.nicknameMap.getNickname(client)).getInitialCard().getImmutablePlayableCard();
            try {
                client.stcUpdateSetup1();
            } catch (RemoteException e) {
                System.err.println("Error while updating client");
            }
        }*//*

    }*/
    private void initialCardCase(Game game, Player playerWhoUpdated, InitialCardEvent initialCardEvent) {

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        Client client;
        synchronized (gameManagement) {
            client = gameManagement.nicknameMap.getClient(playerWhoUpdated.getNickname());
        }
        Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
        try {
            client.stcUpdateInitialCard(immGame, initialCardEvent);
        } catch (RemoteException e) {
            System.err.println("Error while updating client");
        }
    }


    /*private void setup2(Game game){

        GameManagement<SetupController> gameManagement = findSetupGameManagementByGame(game);
        List<Client> clients = gameManagement.getViews();
        for (Client client : clients) {
            Game.Immutable immGame = game.getImmutableGame(gameManagement.nicknameMap.getNickname(client));
            try {
                client.stcUpdate(immGame, GameEvent.SETUP_2);
            } catch (RemoteException e){
                System.err.println("Error while updating client");
            }
        }
    }*/
}
