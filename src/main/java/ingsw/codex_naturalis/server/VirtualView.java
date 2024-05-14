package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.common.immutableModel.*;
import ingsw.codex_naturalis.common.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.common.enumerations.GameRunningStatus;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.common.enumerations.Symbol;
import ingsw.codex_naturalis.server.model.cards.Corner;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.server.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.server.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.server.model.player.PlayerArea;
import ingsw.codex_naturalis.server.util.GameObserver;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.Message;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Representation server-side of the client's view, it receives the updates from the model and
 * forwards them to the client through the network.
 */
public class VirtualView implements GameObserver {

    /**
     * Client
     */
    private final Client client;

    /**
     * Nickname
     */
    private final String nickname;

    /**
     * Object mapper
     */
    private final ObjectMapper objectMapper = new ObjectMapper();


    public VirtualView(Client client, String nickname) {
        this.client = client;
        this.nickname = nickname;
    }


    public Client getClient() {
        return client;
    }

    public String getNickname() {
        return nickname;
    }


    /**
     * A player has joined the game.
     * @param game model
     * @param nickname player's nickname who joined
     */
    @Override
    public void updatePlayerJoined(Game game, String nickname) {
        try {
            client.gameJoined(game.getGameID());
            if (game.getNumOfPlayers() == game.getPlayerOrder().size())
                client.allPlayersJoined();
        } catch (RemoteException e) {
            System.err.println("Error while updating client\n" + e.getMessage());
        }
    }


    /**
     * Generic update from the model, it includes setup phases, chat and game status.
     * @param game model
     * @param gameEvent game event
     */
    @Override
    public void update(Game game, GameEvent gameEvent) {
        switch (gameEvent) {
            case SETUP_1, SETUP_2 -> {
                try {
                    client.setupUpdated(objectMapper.writeValueAsString(getImmGame(game)), objectMapper.writeValueAsString(gameEvent));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while updating client");
                }
            }
            case GAME_STATUS_CHANGED -> gameStatusChanged(game);
            case MESSAGE -> messageCase(game);
        }
    }

    /**
     * Generic update from a player (part of the model), it includes all the main actions performed
     * by a player
     * @param game model
     * @param playerEvent player event
     * @param playerWhoUpdated player who updated
     * @param playerNicknameWhoUpdated player's nickname who updated
     */
    @Override
    public void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated, String playerNicknameWhoUpdated) {
        switch (playerEvent) {
            case INITIAL_CARD_FLIPPED -> initialCardUpdated(game, playerNicknameWhoUpdated, InitialCardEvent.FLIP);
            case INITIAL_CARD_PLAYED -> initialCardUpdated(game, playerNicknameWhoUpdated, InitialCardEvent.PLAY);
            case COLOR_SETUP -> colorChosen(playerWhoUpdated, playerNicknameWhoUpdated);
            case OBJECTIVE_CARD_CHOSEN -> objectiveCardChosen(game, playerNicknameWhoUpdated);

            case HAND_CARD_FLIPPED -> cardFlipped(game, playerNicknameWhoUpdated);
            case HAND_CARD_PLAYED -> cardPlayed(game, playerNicknameWhoUpdated);
            case CARD_DRAWN -> cardDrawn(game, playerNicknameWhoUpdated);
        }
    }

    /**
     * Exception thrown by the model
     * @param error error
     * @param playerNicknameWhoUpdated player's nickname who got the exception
     */
    @Override
    public void updateException(String error, String playerNicknameWhoUpdated) {
        if (nickname.equals(playerNicknameWhoUpdated)) {
            try {
                client.reportException(error);
            } catch (RemoteException e) {
                System.err.println("Error while updating client");
            }
        }
    }

    /**
     * The turn has changed.
     * @param playerNickname current player's nickname
     */
    @Override
    public void updateTurnChanged(String playerNickname) {
        try {
            client.turnChanged(playerNickname);
        } catch (RemoteException e) {
            System.err.println("Error while updating client");
        }
    }

    /**
     * A player has disconnected or reconnected to the game.
     * @param game model
     * @param playerNickname player's nickname
     * @param inGame disconnected or connected
     */
    @Override
    public void updatePlayerConnectionStatus(Game game, String playerNickname, boolean inGame) {
        try {
            if (inGame) {
                if (playerNickname.equals(this.nickname))
                    client.gameRejoined(objectMapper.writeValueAsString(getImmGame(game)), playerNickname);
                else
                    client.updatePlayerInGameStatus(objectMapper.writeValueAsString(getImmGame(game)),
                            playerNickname, objectMapper.writeValueAsString(true),
                            objectMapper.writeValueAsString(false));
            }
            else {
                if (!playerNickname.equals(this.nickname)) {

                    client.updatePlayerInGameStatus(objectMapper.writeValueAsString(getImmGame(game)),
                            playerNickname, objectMapper.writeValueAsString(false),
                            objectMapper.writeValueAsString(true));
                    if (game.getCurrentPlayer().getNickname().equals(playerNickname))
                        game.nextPlayer();
                }
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client\n"+e.getMessage());
        }
    }

    /**
     * A player left the game.
     * @param game model
     * @param playerNicknameWhoLeft player's nickname who left
     */
    @Override
    public void updatePlayerLeft(Game game, String playerNicknameWhoLeft) {
        try {
            if (playerNicknameWhoLeft.equals(this.nickname)) {
                client.gameLeft();
                return;
            }

            client.updatePlayerInGameStatus(objectMapper.writeValueAsString(getImmGame(game)),
                        playerNicknameWhoLeft,
                    objectMapper.writeValueAsString(false),
                    objectMapper.writeValueAsString(false));
            if ((game.getGameStatus() == GameStatus.GAMEPLAY ||
                    game.getGameStatus() == GameStatus.LAST_ROUND_DECKS_EMPTY ||
                    game.getGameStatus() == GameStatus.LAST_ROUND_20_POINTS) &&
                    (game.getCurrentPlayer().getNickname().equals(playerNicknameWhoLeft)))
                game.nextPlayer();
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client\n"+e.getMessage());
        }
    }

    /**
     * The game running status changed, it means: the game has to be canceled, the game will
     * be canceled, the game is not paused anymore.
     * @param game model
     * @param gameRunningStatus game running status
     */
    @Override
    public void updateGameRunningStatus(Game game, GameRunningStatus gameRunningStatus) {
        try {
            switch (gameRunningStatus) {
                case TO_CANCEL_NOW -> client.gameCanceled();
                case TO_CANCEL_LATER -> client.gameToCancelLater();
                case RUNNING -> client.gameResumed();
            }
        } catch (RemoteException e) {
            System.err.println("Error while updating client\n"+e.getMessage());
        }
    }


    private void initialCardUpdated(Game game, String playerNicknameWhoUpdated, InitialCardEvent initialCardEvent) {
        try {
            if (nickname.equals(playerNicknameWhoUpdated)) {
                ImmGame immGame = getImmGame(game);
                client.initialCardUpdated(objectMapper.writeValueAsString(immGame), objectMapper.writeValueAsString(initialCardEvent));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }

    private void colorChosen(Player playerWhoUpdated, String playerNicknameWhoUpdated) {
        try {
            if (nickname.equals(playerNicknameWhoUpdated))
                client.colorUpdated(objectMapper.writeValueAsString(playerWhoUpdated.getColor()));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }

    private void objectiveCardChosen(Game game, String playerNicknameWhoUpdated) {
        try {
            if (nickname.equals(playerNicknameWhoUpdated)) {
                ImmGame immGame = getImmGame(game);
                client.objectiveCardChosen(objectMapper.writeValueAsString(immGame));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }


    private void gameStatusChanged(Game game) {
        switch (game.getGameStatus()) {
            case GAMEPLAY -> {
                try {
                    ImmGame immGame = getImmGame(game);
                    client.setupEnded(objectMapper.writeValueAsString(immGame));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while updating client");
                }
            }
            case LAST_ROUND_20_POINTS -> {
                try {
                    ImmGame immGame = getImmGame(game);
                    client.twentyPointsReached(objectMapper.writeValueAsString(immGame));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while updating client");
                }
            }
            case LAST_ROUND_DECKS_EMPTY -> {
                try {
                    ImmGame immGame = getImmGame(game);
                    client.decksEmpty(objectMapper.writeValueAsString(immGame));
                } catch (RemoteException | JsonProcessingException e) {
                    System.err.println("Error while updating client");
                }
            }
            case ENDGAME -> {
                gameEnded(game);
            }
        }
    }

    private void gameEnded(Game game) {
        List<String> players = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        List<ImmObjectiveCard> secretObjectiveCards = new ArrayList<>();
        int maxPoints = 0;
        Player winner = game.getCurrentPlayer();
        for (Player p : game.getPlayerOrder()) {
            secretObjectiveCards.add(getImmObjectiveCard(p.getPlayerArea().getObjectiveCard()));
            players.add(p.getNickname());
            points.add(p.getPlayerArea().getExtraPoints() + p.getPlayerArea().getPoints());
            if (p.getPlayerArea().getExtraPoints() + p.getPlayerArea().getPoints() > maxPoints) {
                winner = p;
                maxPoints = p.getPlayerArea().getExtraPoints() + p.getPlayerArea().getPoints();
            }
        }
        try {
            client.gameEnded(winner.getNickname(), objectMapper.writeValueAsString(players),
                    objectMapper.writeValueAsString(points),
                    objectMapper.writeValueAsString(secretObjectiveCards));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }


    /**
     * It updates the client only if he is the player.
     * @param game model
     * @param playerNicknameWhoUpdated player's nickname who updated
     */
    private void cardFlipped(Game game, String playerNicknameWhoUpdated) {
        try {
            if (nickname.equals(playerNicknameWhoUpdated)) {
                ImmGame immGame = getImmGame(game);
                client.cardFlipped(objectMapper.writeValueAsString(immGame));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }

    private void cardPlayed(Game game, String playerNicknameWhoUpdated) {
        try {
            ImmGame immGame = getImmGame(game);
            client.cardPlayed(objectMapper.writeValueAsString(immGame), playerNicknameWhoUpdated);
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }

    private void cardDrawn(Game game, String playerNicknameWhoUpdated) {
        try {
            ImmGame immGame = getImmGame(game);
            client.cardDrawn(objectMapper.writeValueAsString(immGame), playerNicknameWhoUpdated);
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }
    }

    /**
     * It makes sure the client is interested in the update (he is only if
     * he is a sender or one of the receivers).
     * @param game model
     */
    private void messageCase(Game game) {
        Message message = game.getChat().getLast();
        List<String> playersInvolved = new ArrayList<>();
        playersInvolved.add(message.getSender());
        playersInvolved.addAll(message.getReceivers());

        try {
            if (playersInvolved.contains(nickname)) {
                ImmGame immGame = getImmGame(game);
                client.messageSent(objectMapper.writeValueAsString(immGame));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }


    private ImmGame getImmGame(Game model) {

        int gameID = model.getGameID();

        GameStatus gameStatus = model.getGameStatus();

        List<String> playerOrderNicknames = new ArrayList<>();

        String currentPlayerNickname;

        List<ImmOtherPlayer> immOtherPlayers = new ArrayList<>();

        ImmPlayer playerReceiver = null;

        ImmPlayableCard topResourceCard = null;

        List<ImmPlayableCard> immRevealedResourceCards = new ArrayList<>();

        ImmPlayableCard topGoldCard = null;

        List<ImmPlayableCard> immRevealedGoldCards = new ArrayList<>();

        List<ImmObjectiveCard> immCommonObjectiveCards = new ArrayList<>();

        List<ImmMessage> playerReceiverChat = new ArrayList<>();


        for (Player player : model.getPlayerOrder()) {
            playerOrderNicknames.add(player.getNickname());
            if (!player.getNickname().equals(nickname))
                immOtherPlayers.add(getImmOtherPlayer(player));
            else
                playerReceiver = getImmPlayer(player);
        }
        if (model.getCurrentPlayer() == null) {
            playerOrderNicknames.clear();
            playerOrderNicknames.add("");
            currentPlayerNickname = "";
        } else
            currentPlayerNickname = model.getCurrentPlayer().getNickname();

        if (model.getResourceCardsDeck().getFirstCard() != null)
            topResourceCard = getImmPlayableCard(model.getResourceCardsDeck().getFirstCard(), true);

        for (PlayableCard card : model.getRevealedResourceCards())
            immRevealedResourceCards.add(getImmPlayableCard(card, true));

        if (model.getGoldCardsDeck().getFirstCard() != null)
            topGoldCard = getImmPlayableCard(model.getGoldCardsDeck().getFirstCard(), true);

        for (PlayableCard card : model.getRevealedGoldCards())
            immRevealedGoldCards.add(getImmPlayableCard(card, true));

        for (ObjectiveCard card : model.getCommonObjectiveCards())
            immCommonObjectiveCards.add(getImmObjectiveCard(card));

        for (Message message : model.getChat())
            if (message.getReceivers().contains(nickname) || message.getSender().equals(nickname))
                playerReceiverChat.add(getImmMessage(message));

        return new ImmGame(gameID, gameStatus, playerOrderNicknames,
                currentPlayerNickname, immOtherPlayers, playerReceiver,
                topResourceCard, immRevealedResourceCards,
                topGoldCard, immRevealedGoldCards, immCommonObjectiveCards, playerReceiverChat);

    }

    private ImmPlayer getImmPlayer(Player player) {

        ImmPlayableCard immInitialCard = null;
        if (player.getInitialCard() != null)
            immInitialCard = getImmPlayableCard(player.getInitialCard(), true);

        List<ImmObjectiveCard> immSecretObjCards = new ArrayList<>();
        for (ObjectiveCard card : player.getSecretObjectiveCards())
            if (card != null)
                immSecretObjCards.add(getImmObjectiveCard(card));

        List<ImmPlayableCard> immHand = new ArrayList<>();
        for (PlayableCard playableCard : player.getHand())
            if (playableCard != null)
                immHand.add(getImmPlayableCard(playableCard, true));

        return new ImmPlayer(player.getNickname(),
                player.getColor(),
                player.getTurnStatus(),
                immInitialCard,
                immSecretObjCards,
                immHand,
                getImmPlayerArea(player.getPlayerArea()));

    }

    private ImmPlayerArea getImmPlayerArea(PlayerArea playerArea) {

        ImmObjectiveCard immObjectiveCard = null;
        if (playerArea.getObjectiveCard() != null)
            immObjectiveCard = getImmObjectiveCard(playerArea.getObjectiveCard());

        Map<List<Integer>, ImmPlayableCard> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : playerArea.getArea().keySet()) {
            immutableArea.put(key, getImmPlayableCard(playerArea.getArea().get(key), true));
        }

        Map<Symbol, Integer> numOfSymbols = new HashMap<>();
        for (Map.Entry<Symbol, Integer> entry : playerArea.getNumOfSymbols().entrySet())
            if (entry.getKey() != Symbol.COVERED && entry.getKey() != Symbol.EMPTY)
                numOfSymbols.put(entry.getKey(), entry.getValue());

        Map<ExtremeCoordinate, Integer> extremeCoordinates = playerArea.getExtremeCoordinates();

        return new ImmPlayerArea(immutableArea,
                playerAreaToString(playerArea, immutableArea, extremeCoordinates),
                extremeCoordinates,
                numOfSymbols,
                immObjectiveCard,
                playerArea.getPoints(),
                playerArea.getExtraPoints());

    }

    private ImmOtherPlayerArea getImmOtherPlayerArea(PlayerArea playerArea) {

        Map<List<Integer>, ImmPlayableCard> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : playerArea.getArea().keySet()) {
            immutableArea.put(key, getImmPlayableCard(playerArea.getArea().get(key), true));
        }

        Map<Symbol, Integer> numOfSymbols = new HashMap<>();
        for (Map.Entry<Symbol, Integer> entry : playerArea.getNumOfSymbols().entrySet())
            if (entry.getKey() != Symbol.COVERED && entry.getKey() != Symbol.EMPTY)
                numOfSymbols.put(entry.getKey(), entry.getValue());

        Map<ExtremeCoordinate, Integer> extremeCoordinates = playerArea.getExtremeCoordinates();

        return new ImmOtherPlayerArea(immutableArea,
                playerAreaToString(playerArea, immutableArea, extremeCoordinates),
                extremeCoordinates,
                numOfSymbols,
                playerArea.getPoints());

    }

    private String playerAreaToString(PlayerArea playerArea, Map<List<Integer>, ImmPlayableCard> area, Map<ExtremeCoordinate, Integer> extremeCoordinates) {

        LinkedHashMap<List<Integer>, List<String>> cardsAsListOfStrings = new LinkedHashMap<>();
        LinkedHashMap<Integer, List<String>> columns = new LinkedHashMap<>();
        StringBuilder outString = new StringBuilder();
        StringBuilder lineToBePrune = new StringBuilder();
        String replaceValueForPruning = "";
        int CharsOfXSpacing = 3;

        // creation of cards as strings
        for (Map.Entry<List<Integer>, ImmPlayableCard> cardAndCoordinates : area.entrySet()) {
            cardsAsListOfStrings.put(cardAndCoordinates.getKey(), Arrays.asList(cardAndCoordinates.getValue().areaCard().split("\n")));
        }

        // pruning cards
        for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_X); i <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); i++) {
            for (int j = extremeCoordinates.get(ExtremeCoordinate.MIN_Y); j <= extremeCoordinates.get(ExtremeCoordinate.MAX_Y); j++) {
                if (area.containsKey(List.of(i, j))) {
                    Corner tl_corner = playerArea.getArea().get(List.of(i, j)).getCurrentPlayableSide().getTopLeftCorner();
                    Corner tr_corner = playerArea.getArea().get(List.of(i, j)).getCurrentPlayableSide().getTopRightCorner();
                    Corner bl_corner = playerArea.getArea().get(List.of(i, j)).getCurrentPlayableSide().getBottomLeftCorner();
                    Corner br_corner = playerArea.getArea().get(List.of(i, j)).getCurrentPlayableSide().getBottomRightCorner();

                    // top right corner
                    if (tr_corner.isCovered() && tr_corner.getSymbol() != Symbol.COVERED) {
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getFirst());
                        lineToBePrune.replace(8, 10, replaceValueForPruning);
                        cardsAsListOfStrings.get(List.of(i, j)).set(0, lineToBePrune.toString());
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));

                        if (tl_corner.getSymbol() == Symbol.COVERED || tl_corner.getSymbol() == Symbol.EMPTY) {
                            // Case empty-empty
                            if (tr_corner.getSymbol() == Symbol.EMPTY) {
                                lineToBePrune.replace(lineToBePrune.length() - 6, lineToBePrune.length() - 4, replaceValueForPruning);
                            }
                            // Case empty-symbol
                            else {
                                lineToBePrune.replace(8, 24, replaceValueForPruning);
                            }
                        } else {
                            // Case symbol-empty
                            if (tr_corner.getSymbol() == Symbol.EMPTY) {
                                lineToBePrune.replace(22, 24, replaceValueForPruning);
                            }
                            // Case symbol-symbol
                            else {
                                lineToBePrune.replace(22, 38, replaceValueForPruning);
                            }
                        }
                        cardsAsListOfStrings.get(List.of(i, j)).set(1, lineToBePrune.toString());
                    }

                    // top left corner
                    if (tl_corner.isCovered() && tl_corner.getSymbol() != Symbol.COVERED) {
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getFirst());
                        lineToBePrune.replace(5, 7, replaceValueForPruning);
                        cardsAsListOfStrings.get(List.of(i, j)).set(0, lineToBePrune.toString());

                        if (tl_corner.getSymbol() == Symbol.EMPTY) {
                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
                            lineToBePrune.replace(5, 7, replaceValueForPruning);
                        } else {
                            lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(1));
                            lineToBePrune.replace(5, 21, replaceValueForPruning);
                        }
                        cardsAsListOfStrings.get(List.of(i, j)).set(1, lineToBePrune.toString());
                    }

                    // bottom right corner
                    if (br_corner.isCovered() && br_corner.getSymbol() != Symbol.COVERED) {
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(4));
                        lineToBePrune.replace(8, 10, replaceValueForPruning);
                        cardsAsListOfStrings.get(List.of(i, j)).set(4, lineToBePrune.toString());
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(3));

                        if (bl_corner.getSymbol() == Symbol.COVERED || bl_corner.getSymbol() == Symbol.EMPTY) {
                            // Case empty-empty
                            if (br_corner.getSymbol() == Symbol.EMPTY) {
                                lineToBePrune.replace(8, 10, replaceValueForPruning);
                            }
                            // Case empty-symbol
                            else {
                                lineToBePrune.replace(8, 24, replaceValueForPruning);
                            }
                        } else {
                            // Case symbol-empty
                            if (br_corner.getSymbol() == Symbol.EMPTY) {
                                lineToBePrune.replace(22, 24, replaceValueForPruning);
                            }
                            // Case symbol-symbol
                            else {
                                lineToBePrune.replace(22, 38, replaceValueForPruning);
                            }
                        }
                        cardsAsListOfStrings.get(List.of(i, j)).set(3, lineToBePrune.toString());
                    }

                    // bottom left corner
                    if (bl_corner.isCovered() && bl_corner.getSymbol() != Symbol.COVERED) {
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).get(3));
                        if (bl_corner.getSymbol() == Symbol.EMPTY) {
                            lineToBePrune.replace(5, 7, replaceValueForPruning);
                        } else {
                            lineToBePrune.replace(5, 21, replaceValueForPruning);

                        }
                        cardsAsListOfStrings.get(List.of(i, j)).set(3, lineToBePrune.toString());
                        lineToBePrune = new StringBuilder(cardsAsListOfStrings.get(List.of(i, j)).getLast());
                        lineToBePrune.replace(5, 7, replaceValueForPruning);
                        cardsAsListOfStrings.get(List.of(i, j)).set(4, lineToBePrune.toString());
                    }
                }
            }
        }

        // adding Y coordinates
        columns.put(extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1, new ArrayList<>());
        for (int i = extremeCoordinates.get(ExtremeCoordinate.MAX_Y); i >= extremeCoordinates.get(ExtremeCoordinate.MIN_Y) - 1; i--) {
            columns.get(extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1).addAll(List.of("     ", "     ", NumIn3Char(i, false) + "  "));
        }
        columns.get(extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1).add("   " + NumIn3Char(extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1, true));

        // populating columns
        for (int i = extremeCoordinates.get(ExtremeCoordinate.MAX_X); i >= extremeCoordinates.get(ExtremeCoordinate.MIN_X); i--) {
            columns.put(i, new ArrayList<>());
            for (int j = extremeCoordinates.get(ExtremeCoordinate.MAX_Y); j >= extremeCoordinates.get(ExtremeCoordinate.MIN_Y); j--) {

                if (cardsAsListOfStrings.containsKey(List.of(i, j))) {
                    columns.get(i).addAll(cardsAsListOfStrings.get(List.of(i, j)));
                } else {
                    // spacing
                    CharsOfXSpacing = 3;
                    if (cardsAsListOfStrings.containsKey(List.of(i - 1, j)) && cardsAsListOfStrings.containsKey(List.of(i + 1, j))) {
                        CharsOfXSpacing = 1;
                    }

                    if (cardsAsListOfStrings.containsKey(List.of(i, j + 1))) {
                        if (!cardsAsListOfStrings.containsKey(List.of(i - 1, j)) && !cardsAsListOfStrings.containsKey(List.of(i + 1, j))) {
                            if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
                                columns.get(i).add(" ".repeat(5));
                            } else {
                                columns.get(i).addAll(List.of(" ".repeat(5), " ".repeat(5), " ".repeat(5)));
                            }
                        } else {
                            if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
                                columns.get(i).add(" ".repeat(CharsOfXSpacing));
                            } else {
                                columns.get(i).addAll(List.of(" ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing)));
                            }
                        }
                    } else if ((cardsAsListOfStrings.containsKey(List.of(i + 1, j + 1)) && cardsAsListOfStrings.containsKey(List.of(i - 1, j + 1)))
                            || cardsAsListOfStrings.containsKey(List.of(i - 1, j)) || cardsAsListOfStrings.containsKey(List.of(i - 1, j + 1))) {
                        if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
                            columns.get(i).addAll(List.of(" ", " ", " "));
                        } else {
                            columns.get(i).addAll(List.of(" ", " ", " ", " ", " "));
                        }
                    } else {
                        if (j != extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
                            columns.get(i).addAll(List.of(" ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing)));
                        } else {
                            columns.get(i).addAll(List.of(" ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing),
                                    " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing), " ".repeat(CharsOfXSpacing)));
                        }
                    }
                }
                // adding X Coordinates
                if (j == extremeCoordinates.get(ExtremeCoordinate.MIN_Y)) {
                    columns.get(i).addAll(List.of("   ", NumIn3Char(i, true)));
                }
            }
        }

        // player area assembly
        for (int j = 0; j < 3 * (extremeCoordinates.get(ExtremeCoordinate.MAX_Y) - extremeCoordinates.get(ExtremeCoordinate.MIN_Y) + 1) + 4; j++) {
            for (int i = extremeCoordinates.get(ExtremeCoordinate.MIN_X) - 1; i <= extremeCoordinates.get(ExtremeCoordinate.MAX_X); i++) {
                outString.append(columns.get(i).get(j));
            }
            outString.append("\n");
        }

        // adding Extreme coordinates
        outString.insert(0, NumIn3Char(extremeCoordinates.get(ExtremeCoordinate.MAX_Y) + 1, false) + "\n");
        outString.replace(outString.length() - 1, outString.length(),
                NumIn3Char(extremeCoordinates.get(ExtremeCoordinate.MAX_X) + 1, true) + "\n");
        return outString.toString();
    }

    private String NumIn3Char(Integer i, boolean x_coordinate) {
        String outString = "";
        if (i > -99 && i < 99) {
            if (i >= 0 && i <= 9) {
                if (x_coordinate) {
                    outString = " " + i.toString() + " ";
                } else {
                    outString = "  " + i.toString();
                }
            } else if ((i <= -1 && i >= -9)) {
                if (x_coordinate) {
                    outString = i.toString() + " ";
                } else {
                    outString = " " + i.toString();
                }
            } else if (i >= 10) {
                outString = " " + i.toString();
            } else {
                outString = i.toString();
            }
        }
        return outString;
    }

    private ImmOtherPlayer getImmOtherPlayer(Player player) {

        List<ImmPlayableCard> immHand = new ArrayList<>();
        for (PlayableCard playableCard : player.getHand())
            if (playableCard != null)
                immHand.add(getImmPlayableCard(playableCard, false));

        return new ImmOtherPlayer(player.getNickname(),
                player.getColor(),
                player.getTurnStatus(),
                immHand,
                getImmOtherPlayerArea(player.getPlayerArea()));

    }

    private ImmPlayableCard getImmPlayableCard(PlayableCard card, boolean canShowFront) {
        boolean showingFront;
        PlayableSide playableSide;
        if (!canShowFront) {
            showingFront = false;
            playableSide = card.getBack();
        } else {
            showingFront = card.isShowingFront();
            playableSide = card.getCurrentPlayableSide();
        }
        return new ImmPlayableCard(card.getCardID(),
                showingFront,
                playableSide.handCardToString(card.getKingdom()),
                playableSide.playerAreaCardToString(card.getKingdom()));
    }

    private ImmObjectiveCard getImmObjectiveCard(ObjectiveCard card) {
        return new ImmObjectiveCard(card.getCardID(),
                card.isShowingFront(),
                card.cardToString());
    }

    private ImmMessage getImmMessage(Message message) {
        return new ImmMessage(message.getContent(),
                message.getSentTime(),
                message.getSender(),
                message.getReceivers());
    }

}
