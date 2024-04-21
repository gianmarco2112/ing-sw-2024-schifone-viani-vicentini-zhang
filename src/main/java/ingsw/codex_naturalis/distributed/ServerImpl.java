package ingsw.codex_naturalis.distributed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.controller.setupPhase.SetupController;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.UI;
import ingsw.codex_naturalis.view.gameplayPhase.Observer;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.view.setupPhase.InitialCardEvent;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements Server, Observer {

    public static class NicknameMap {
        private Map<Client, String> clientToNicknameMap = new HashMap<>();
        private Map<String, Client> nicknameToClientMap = new HashMap<>();

        public void addClient(Client client, String nickname) {
            clientToNicknameMap.put(client, nickname);
            nicknameToClientMap.put(nickname, client);
        }

        public String getNickname(Client client) {
            return clientToNicknameMap.get(client);
        }

        public Client getClient(String nickname) {
            return nicknameToClientMap.get(nickname);
        }
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public record GameSpecs(int ID, int currentNumOfPlayers, int maxNumOfPlayers) {}

    private final List<Client> newClients = new ArrayList<>();

    private final NicknameMap nicknameMap = new NicknameMap();

    private final List<GameManagement<SetupController>> startingGames = new ArrayList<>();
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
                    client.updateLobbyUIGameSpecs(objectMapper.writeValueAsString(getGamesSpecs()));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while trying to update client's lobby UI");
                }
            });
        }

    }
    private List<ServerImpl.GameSpecs> getGamesSpecs() {

        List<ServerImpl.GameSpecs> gamesSpecs = new ArrayList<>();
        for (GameManagement<SetupController> gameManagement : startingGames) {
            int gameID = gameManagement.getModel().getGameID();
            int numOfPlayers = gameManagement.getModel().getNumOfPlayers();
            int currentNumOfPlayers = gameManagement.getModel().getPlayerOrder().size();
            GameSpecs gameSpecs = new GameSpecs(gameID, currentNumOfPlayers, numOfPlayers);
            gamesSpecs.add(gameSpecs);
        }

        return gamesSpecs;

    }



    private GameManagement<SetupController> getGameManagementFromGameID(int gameID) throws NotReachableGameException{
        for (GameManagement<SetupController> game : startingGames) {
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
    public void updateGameToAccess(Client client, int gameID, String nickname) throws NicknameAlreadyExistsException, MaxNumOfPlayersInException, NotReachableGameException{

        synchronized (startingGames) {
            try {

                GameManagement<SetupController> gameManagement = getGameManagementFromGameID(gameID);
                gameManagement.getModel().addPlayer(new Player(nickname));
                gameManagement.addView(client);

                synchronized (newClients) {
                    newClients.remove(client);
                }


                if (gameManagement.getModel().getNumOfPlayers() > gameManagement.getModel().getPlayerOrder().size())
                try {
                    client.updateUI(UI.GAME_STARTING);
                    client.updateGameStartingUIGameID(gameID);
                    nicknameMap.addClient(client, nickname);
                } catch (RemoteException e) {
                    System.err.println("Error while trying to update client's UI to Game Starting");
                }

                else {
                    nicknameMap.addClient(client, nickname);
                    startingGames.remove(gameManagement);
                    synchronized (setupGames) {
                        setupGames.add(gameManagement);
                        List<Client> clients = gameManagement.getViews();
                        for (Client c : clients) {
                            try {
                                c.updateUI(UI.SETUP);
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
        for (GameManagement<SetupController> gameManagement : startingGames)
            gameIDs.add(gameManagement.getModel().getGameID());
        do {
            gameID = new Random().nextInt(100) + 1;
        } while (gameIDs.contains(gameID));
        return gameID;
    }

    @Override
    public void updateNewGame(Client client, int numOfPlayers, String nickname) {

        int gameID = createGameID();
        Game game = new Game(gameID, numOfPlayers);
        game.addObserver(this);
        Player player = new Player(nickname);
        game.addPlayer(player);

        SetupController setupController = new SetupController(game, client);

        synchronized (startingGames) {
            startingGames.add(new GameManagement<>(game, setupController, client));
        }

        try {
            client.updateUI(UI.GAME_STARTING);
            client.updateGameStartingUIGameID(gameID);
        } catch (RemoteException e) {
            System.err.println("Error while trying to update client's UI to Game Starting");
        }

        synchronized (newClients) {
            newClients.remove(client);
            updateClientLobbyUIGameSpecs(newClients);
        }

        nicknameMap.addClient(client, nickname);

    }




    private SetupController findSetupControllerByClient(Client client) {

        for (GameManagement<SetupController> gameManagement : setupGames){
            if (gameManagement.getViews().contains(client))
                return gameManagement.getController();
        }

        return null;

    }

    @Override
    public void updateReady(Client client) {
        findSetupControllerByClient(client).updateReady(nicknameMap.getNickname(client));
    }

    @Override
    public void updateInitialCard(Client client, InitialCardEvent initialCardEvent) {
        findSetupControllerByClient(client).updateInitialCard(client, initialCardEvent);
    }

    @Override
    public void updateColor(Client client, Color color) {
        findSetupControllerByClient(client).updateColor(client, color);
    }





    @Override
    public void updateFlipCard(Client client, FlipCard flipCard) {
        //this.gameplayController.updateFlipCard(client, flipCard);
    }

    @Override
    public void updatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException {
        //this.gameplayController.updatePlayCard(client, playCard, x, y);
    }

    @Override
    public void updateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {
       // this.gameplayController.updateDrawCard(client, drawCard);
    }

    @Override
    public void updateText(Client client, Message message, String content, List<String> receivers) {
        //this.gameplayController.updateText(client, message, content, receivers);
    }




    @Override
    public void update(Game o, Event arg, String playerWhoUpdated) {

        List<Client> clients = findClientsFromGame(o);

        switch (arg) {
            case SETUP_1 -> setup1(clients, o);
        }

    }

    private void setup1(List<Client> clients, Game game) {

        PlayableCard.Immutable topResourceCard = game.getResourceCardsDeck().getFirstCard().getImmutablePlayableCard();
        PlayableCard.Immutable topGoldCard = game.getGoldCardsDeck().getFirstCard().getImmutablePlayableCard();
        List<PlayableCard.Immutable> revealedResourceCards = game.getImmutableRevealedResourceCards();
        List<PlayableCard.Immutable> revealedGoldCards = game.getImmutableRevealedGoldCards();

        executorService.submit(() -> {
            for (Client client : clients) {
                PlayableCard.Immutable initialCard = game.getPlayerByNickname(nicknameMap.getNickname(client)).getInitialCard().getImmutablePlayableCard();
                try {
                    System.out.println("TESTING\n" + initialCard.handCard());
                    client.updateSetup1(initialCard, topResourceCard, topGoldCard, revealedResourceCards, revealedGoldCards);
                } catch (RemoteException e) {
                    System.err.println("Error while updating client");
                }
            }
        });

    }

    private List<Client> findClientsFromGame(Game game) {

        for (GameManagement<SetupController> gameManagement : setupGames){
            if (gameManagement.getModel().equals(game))
                return gameManagement.getViews();
        }

        return null;

    }
}
