package ingsw.codex_naturalis.client.view.tui;

import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.util.UIObservableItem;
import ingsw.codex_naturalis.common.immutableModel.*;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.ExtremeCoordinate;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.exceptions.NoExistingGamesAvailable;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.server.model.util.GameEvent;

import java.util.*;

/**
 * Textual User Interface
 */
public class TextualUI implements UI {

    /**
     * View state
     */
    private enum State {
        LOGIN,
        LOBBY,
        GAME,
        REJOINED
    }

    /**
     * Used to update the not started games in lobby while the user is in that state.
     */
    private boolean askingWhichGameToAccess = false;

    /**
     * Game states
     */
    private enum GameState {
        WAITING_FOR_PLAYERS,
        READY,
        SETUP_INITIAL_CARD,
        SETUP_COLOR,
        SETUP_OBJECTIVE_CARD,
        PLAYING,
    }

    /**
     * Running states
     */
    private enum RunningState {
        RUNNING,
        WAITING_FOR_UPDATE,
        STOP
    }

    private State state = State.LOGIN;
    private RunningState runningState = RunningState.RUNNING;
    private GameState gameState = null;

    private final Object lock = new Object();

    private State getState() {
        synchronized (lock) {
            return state;
        }
    }
    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    private GameState getGameState() {
        synchronized (lock) {
            return gameState;
        }
    }
    private void setGameState(GameState gameState) {
        synchronized (lock) {
            this.gameState = gameState;
            lock.notifyAll();
        }
    }

    private RunningState getRunningState() {
        synchronized (lock) {
            return runningState;
        }
    }
    private void setRunningState(RunningState runningState) {
        synchronized (lock) {
            this.runningState = runningState;
            lock.notifyAll();
        }
    }

    private boolean isAskingWhichGameToAccess() {
        synchronized (lock) {
            return askingWhichGameToAccess;
        }
    }
    private void setAskingWhichGameToAccess(boolean askingWhichGameToAccess) {
        synchronized (lock) {
            this.askingWhichGameToAccess = askingWhichGameToAccess;
        }
    }

    /**
     * Used to notify the client
     */
    private final UIObservableItem uiObservableItem;

    private final InputRequesterTUI inputRequesterTUI = new InputRequesterTUI();
    private final Scanner scanner = new Scanner(System.in);

    private List<GameSpecs> gamesSpecs = null;
    private int gameID;
    private ImmGame game = null;


    
    public TextualUI(UIObservableItem uiObservableItem) {
        this.uiObservableItem = uiObservableItem;
    }
    
    
    
    @Override
    public void run() {

        while (true) {
            switch (getRunningState()) {
                case RUNNING -> running();
                case WAITING_FOR_UPDATE -> waitForUpdate();
                case STOP -> { setRunningState(RunningState.RUNNING); }
            }

        }

    }

    private void running() {

        switch (getState()) {
            case LOGIN -> loginView();
            case LOBBY -> lobbyView();
            case GAME -> gameView();
            case REJOINED -> {
                uiObservableItem.notifyGetGameController();
                setState(State.GAME);
                setGameState(GameState.PLAYING);
            }
        }

    }

    private void printErrInvalidOption(){
        System.err.println("Invalid option");
    }
    private void waitForUpdate() {
        while (getRunningState() == RunningState.WAITING_FOR_UPDATE) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Error while waiting for server update");
                }
            }
        }
    }


    /**
     * Asks for nickname
     */
    private void loginView() {
        inputRequesterTUI.nickname();
        String nickname = askNickname();
        setRunningState(RunningState.WAITING_FOR_UPDATE);
        uiObservableItem.notifyNickname(nickname);
    }


    private void lobbyView() {
        gameAccessOption();
    }

    /**
     * Asks if he wants to join an existing game or create a new one
     */
    private void gameAccessOption() {
        inputRequesterTUI.gameAccessOption();
        int option = askForOptionInput(1, 2, false);
        switch (option) {
            case 1 -> newGameAccess();
            case 2 -> existingGameAccess();
        }
    }

    /**
     * For creating a new game
     */
    private void newGameAccess() {

        inputRequesterTUI.newGameAccess();
        int numOfPlayers = askForOptionInput(2, 4, true);
        if (numOfPlayers == 0)
            return;

        setRunningState(RunningState.WAITING_FOR_UPDATE);
        uiObservableItem.notifyNewGame(numOfPlayers);

    }

    /**
     * For joining an existing game
     */
    private void existingGameAccess() {

        try {
            inputRequesterTUI.existingGameAccess(gamesSpecs);
        } catch (NoExistingGamesAvailable e) {
            System.err.println(e.getMessage());
            return;
        }
        setAskingWhichGameToAccess(true);
        int option = askForOptionInput(1, gamesSpecs.size(), true);
        setAskingWhichGameToAccess(false);
        if (option == 0)
            return;
        int gameID = gamesSpecs.get(option-1).ID();

        setRunningState(RunningState.WAITING_FOR_UPDATE);
        uiObservableItem.notifyGameToAccess(gameID);

    }

    private String askNickname()  {
        while (true) {
            String input = scanner.nextLine();
            if (input.contains(" ")) {
                System.err.println("You can't include a white space!");
            } else if (input.length() > 20) {
                System.err.println("Too many characters!");
            }
            else if (!input.isEmpty()) {
                return input;
            }
        }
    }


    private void gameView() {

        switch (gameState) {
            case WAITING_FOR_PLAYERS -> {
                System.out.println("\nGameID: " + gameID);
                System.out.println("Waiting for players...");
                System.out.println("Press any key and enter if you want to leave the game");
                scanner.next();
                if (getRunningState() == RunningState.STOP)
                    return;
                setRunningState(RunningState.WAITING_FOR_UPDATE);
                switch (getGameState()) {
                    case WAITING_FOR_PLAYERS -> uiObservableItem.notifyLeaveGame();
                    case READY -> {
                        uiObservableItem.notifyReady();
                        System.out.println("You are ready to play. Please wait for the other players to be ready");
                    }
                }
            }
            case SETUP_INITIAL_CARD -> playingInitialCard();
            case SETUP_COLOR -> choosingColor();
            case SETUP_OBJECTIVE_CARD -> choosingObjectiveCard();
            case PLAYING -> playing();
        }

    }

    private void playingInitialCard() {
        inputRequesterTUI.initialCardOption();
        int option = askForOptionInput(1, 2, false);
        if (option == 0)
            return;
        setRunningState(RunningState.WAITING_FOR_UPDATE);
        switch (option) {
            case 1 -> uiObservableItem.notifyInitialCard(InitialCardEvent.FLIP);
            case 2 -> uiObservableItem.notifyInitialCard(InitialCardEvent.PLAY);
        }
    }

    private void choosingColor() {
        inputRequesterTUI.colorOption();
        int option = askForOptionInput(1, 4, false);
        if (option == 0)
            return;
        setRunningState(RunningState.WAITING_FOR_UPDATE);
        switch (option) {
            case 1 -> uiObservableItem.notifyColor(Color.RED);
            case 2 -> uiObservableItem.notifyColor(Color.BLUE);
            case 3 -> uiObservableItem.notifyColor(Color.GREEN);
            case 4 -> uiObservableItem.notifyColor(Color.YELLOW);
        }
    }

    private void choosingObjectiveCard() {
        inputRequesterTUI.objectiveCardOption(listOfObjectiveCardsToString(game.player().secretObjectiveCards()));
        int option = askForOptionInput(1, 2, false);
        if (option == 0)
            return;
        setRunningState(RunningState.WAITING_FOR_UPDATE);
        uiObservableItem.notifyObjectiveCardChoice(option-1);
    }

    private void playing() {
        inputRequesterTUI.playing();
        int option = askForOptionInput(1, 5, false);
        switch (option) {
            case 1 -> flippingCard();
            case 2 -> playingCard();
            case 3 -> drawingCard();
            case 4 -> sendingMessage();
            case 5 -> leaveGame();
        }
    }

    private void flippingCard() {
        inputRequesterTUI.flippingCardOption(game.player().hand());
        int option = askForOptionInput(1, game.player().hand().size(), true);
        if (option == 0)
            return;
        uiObservableItem.notifyFlipCard(option-1);
    }

    private void playingCard() {
        inputRequesterTUI.playingCardOption(game.player().hand());
        int option = askForOptionInput(1, game.player().hand().size(), true);
        if (option == 0)
            return;

        inputRequesterTUI.coordinate("x");
        int x = askForCoordinate(
                game.player().playerArea().extremeCoordinates().get(ExtremeCoordinate.MIN_X)-1,
                game.player().playerArea().extremeCoordinates().get(ExtremeCoordinate.MAX_X)+1);
        if (x == -100)
            return;

        inputRequesterTUI.coordinate("y");
        int y = askForCoordinate(
                game.player().playerArea().extremeCoordinates().get(ExtremeCoordinate.MIN_Y)-1,
                game.player().playerArea().extremeCoordinates().get(ExtremeCoordinate.MAX_Y)+1);
        if (y == -100)
            return;

        uiObservableItem.notifyPlayCard(option-1, x, y);
    }

    private void drawingCard() {
        inputRequesterTUI.drawingCardOption();
        int option = askForOptionInput(1, 6, true);
        switch (option) {
            case 1 -> uiObservableItem.notifyDrawCard(DrawCardEvent.DRAW_FROM_RESOURCE_CARDS_DECK);
            case 2 -> uiObservableItem.notifyDrawCard(DrawCardEvent.DRAW_FROM_GOLD_CARDS_DECK);
            case 3 -> uiObservableItem.notifyDrawCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_1);
            case 4 -> uiObservableItem.notifyDrawCard(DrawCardEvent.DRAW_REVEALED_RESOURCE_CARD_2);
            case 5 -> uiObservableItem.notifyDrawCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_1);
            case 6 -> uiObservableItem.notifyDrawCard(DrawCardEvent.DRAW_REVEALED_GOLD_CARD_2);
        }
    }

    private void sendingMessage() {
        List<String> playersToText = new ArrayList<>(game.playerOrderNicknames());
        playersToText.remove(game.player().nickname());
        inputRequesterTUI.sendingMessage(playersToText);
        int option;
        if (playersToText.size() > 1)
            option = askForOptionInput(1, playersToText.size()+1, true);
        else
            option = askForOptionInput(1, 1, true);
        String receiver;
        switch (option) {
            case 0 -> { return; }
            case 1 -> receiver = null;
            default -> receiver = playersToText.get(option-2);
        }

        inputRequesterTUI.messageContent();
        String content = askForMessageContent();
        if (content.equals("/"))
            return;
        uiObservableItem.notifySendMessage(receiver, content);
    }

    private void leaveGame() {
        inputRequesterTUI.leaveGame();
        int option = askForOptionInput(1, 1, true);
        if (option == 0)
            return;
        uiObservableItem.notifyLeaveGame();
    }



    private int askForOptionInput(int min, int max, boolean canGoBack) {
        String input;
        while (true) {
            input = scanner.next();
            if (getRunningState() == RunningState.STOP || (canGoBack && input.equals("/")))
                return 0;
            try {
                int option = Integer.parseInt(input);
                if (option >= min && option <= max)
                    return option;
            } catch (NumberFormatException ignored) {}
            printErrInvalidOption();
        }
    }

    private int askForCoordinate(int min, int max) {
        String input;
        while (true) {
            input = scanner.next();
            if (input.equals("/"))
                return -100;
            try {
                int option = Integer.parseInt(input);
                if (option >= min && option <= max)
                    return option;
            } catch (NumberFormatException ignored) {}
            printErrInvalidOption();
        }
    }

    private String askForMessageContent() {
        while (true) {
            String input = scanner.nextLine();
            if (input.length() > 100)
                System.err.println("Too many characters!");
            else if (!input.isEmpty())
                return input;
        }
    }


    @Override
    public void reportError(String error) {
        System.err.println(error);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void setNickname(String nickname) {
        System.out.println("Welcome "+ nickname + "!");
        setState(State.LOBBY);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {
        this.gamesSpecs = gamesSpecs;
        if (isAskingWhichGameToAccess())
            inputRequesterTUI.existingGameAccess(this.gamesSpecs);
    }


    @Override
    public void updateGameID(int gameID) {

        this.gameID = gameID;
        setState(State.GAME);
        setGameState(GameState.WAITING_FOR_PLAYERS);
        setRunningState(RunningState.RUNNING);

    }

    @Override
    public void allPlayersJoined() {
        System.out.println("\nPress any key and enter if you're ready to play");
        setGameState(GameState.READY);
        //setRunningState(RunningState.RUNNING);
    }

    @Override
    public void updateSetup(ImmGame game, GameEvent gameEvent) {
        this.game = game;
        switch (gameEvent) {
            case SETUP_1 -> firstSetup();
            case SETUP_2 -> secondSetup();
        }
    }

    @Override
    public void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent) {
        this.game = game;
        printSpace();
        showCommonCards();
        switch (initialCardEvent) {
            case FLIP -> {
                System.out.println("\nYour initial card\n" + game.player().initialCard().handCard());
                setRunningState(RunningState.RUNNING);
            }
            case PLAY -> {
                showYou();
                setGameState(GameState.SETUP_COLOR);
                setRunningState(RunningState.RUNNING);
            }
        }
    }

    @Override
    public void updateColor(Color color) {
        System.out.println(color.getColorCode() + "You chose " + color.getDescription() + "!" + "\u001B[0m");
        System.out.println("Please wait for the other players to make their choices.\n");
    }

    @Override
    public void updateObjectiveCardChoice(ImmGame immGame) {
        this.game = immGame;
        printSpace();
        showOtherPlayers();
        showCommonCards();
        showYou();
        System.out.println("Please wait for the other players to make their choice");
    }

    @Override
    public void endSetup(ImmGame game) {
        this.game = game;
        System.out.println("""
                
                The game setup ended!
                
                
                The player order is:""");
        for (int i=0; i<game.playerOrderNicknames().size(); i++)
            System.out.println(i+1 + ". " + game.playerOrderNicknames().get(i));

        if (game.playerOrderNicknames().getFirst().equals(game.player().nickname()))
            System.out.println("\nIt's your turn!");

        setGameState(GameState.PLAYING);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void cardFlipped(ImmGame game) {
        this.game = game;
        printSpace();
        showOtherPlayers();
        showChat();
        showCommonCards();
        showYou();
        inputRequesterTUI.playing();
    }

    @Override
    public void cardPlayed(ImmGame game, String playerNicknameWhoUpdated) {
        this.game  = game;
        printSpace();
        showOtherPlayers();
        showChat();
        showCommonCards();
        showYou();
        if (playerNicknameWhoUpdated.equals(this.game.player().nickname()))
            System.out.println("Card played!");
        else
            System.out.println(playerNicknameWhoUpdated + " has played a card!");
        inputRequesterTUI.playing();
    }

    @Override
    public void cardDrawn(ImmGame game, String playerNicknameWhoUpdated) {
        this.game  = game;
        printSpace();
        showOtherPlayers();
        showChat();
        showCommonCards();
        showYou();
        if (playerNicknameWhoUpdated.equals(this.game.player().nickname()))
            System.out.println("Card drawn!");
        else
            System.out.println(playerNicknameWhoUpdated + " has drawn a card!");
        inputRequesterTUI.playing();
    }

    @Override
    public void turnChanged(String currentPlayer) {
        if (currentPlayer.equals(game.player().nickname()))
            System.out.println("\nIt's your turn!");
        else
            System.out.println("\nIt's " + currentPlayer + "'s turn!");
    }

    @Override
    public void messageSent(ImmGame game) {
        this.game = game;
        printSpace();
        showOtherPlayers();
        showChat();
        showCommonCards();
        showYou();
        System.out.println("==== Chat update! ====");
        inputRequesterTUI.playing();
    }

    @Override
    public void twentyPointsReached(ImmGame game) {
        this.game = game;
        System.out.println("======== 20 Points reached! ========");
    }

    @Override
    public void decksEmpty(ImmGame game) {
        this.game = game;
        System.out.println("======== All decks are empty! ========");
    }

    @Override
    public void gameEnded(String winner, List<String> players, List<Integer> points, List<ImmObjectiveCard> secretObjectiveCards) {
        System.err.println("\n\n||||||||||| Game ended!. |||||||||||");
        System.out.println("The winner is " + winner + "!");
        for (int i = 0; i < players.size(); i++)
            System.out.println(players.get(i) + "   " + points.get(i) + " points   " + secretObjectiveCards.get(i).tui());
        System.err.println("\nPress any key and enter to go back to lobby...");
        setState(State.LOBBY);
        setGameState(null);
        setRunningState(RunningState.STOP);
    }

    @Override
    public void gameCanceled() {
        System.err.println("\n||||||||||| You won! |||||||||||\n\nPress any key and enter to go back to lobby...");
        setState(State.LOBBY);
        setGameState(null);
        setRunningState(RunningState.STOP);
    }

    @Override
    public void gameLeft() {
        System.err.println("\n||||||||||| You left the game. |||||||||||\n\nPress any key and enter to go back to lobby...");
        setState(State.LOBBY);
        setGameState(null);
        setRunningState(RunningState.STOP);
    }

    @Override
    public void gameRejoined(ImmGame game) {
        this.game = game;
        printSpace();
        showOtherPlayers();
        showChat();
        showCommonCards();
        showYou();
        System.out.println("\nGame rejoined!");
        setState(State.REJOINED);
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void updatePlayerInGameStatus(ImmGame immGame, String playerNickname, boolean inGame, boolean hasDisconnected) {
        this.game = immGame;
        if (inGame)
            System.err.println(playerNickname + " has rejoined the game!");
        else {
            if (hasDisconnected)
                System.err.println(playerNickname + " has disconnected!");
            else
                System.err.println(playerNickname + " has left the game!");
        }
    }

    @Override
    public void gamePaused() {
        System.err.println("\n||||||||||| Currently, you're the only player connected. Please wait for another player to rejoin within 10 seconds.  |||||||||||");
        setRunningState(RunningState.STOP);
    }

    @Override
    public void gameResumed() {
        System.err.println("\n||||||||||| Game resumed!  |||||||||||");
        setRunningState(RunningState.RUNNING);
    }

    @Override
    public void playerIsReady(String playerNickname) {
        System.out.println(playerNickname+" is ready!");
    }


    private void firstSetup() {

        printSpace();
        showCommonCards();
        System.out.println("\nYour initial card\n" + game.player().initialCard().handCard());

        setGameState(GameState.SETUP_INITIAL_CARD);
        setRunningState(RunningState.RUNNING);

    }

    private void secondSetup() {
        printSpace();
        showOtherPlayers();
        showCommonCards();
        showYou();
        setGameState(GameState.SETUP_OBJECTIVE_CARD);
        setRunningState(RunningState.RUNNING);
    }


    private void showOtherPlayers() {
        for (ImmOtherPlayer player : game.otherPlayers()) {
            Color color = player.color();
            System.out.println(color.getColorCode() + player.nickname() +"'s play area" + "\u001B[0m");
            System.out.println("Points: " + player.playerArea().points());
            System.out.println("Resources and objects: " + player.playerArea().numOfSymbols().toString());
            System.out.println(player.playerArea().areaTUI());

            if (!player.hand().isEmpty())
                System.out.println(listOfPlayableCardsToString(player.hand()));
            printSplitter();
        }
    }

    private void showChat() {
        System.out.println("GAME CHAT");
        for (ImmMessage message : game.chat()) {
            System.out.println("......................................");
            System.out.print("From: ");
            if (message.sender().equals(game.player().nickname()))
                System.out.print("you     ");
            else
                System.out.print(message.sender()+"     ");
            System.out.print("To: ");
            if (message.receivers().size() == game.playerOrderNicknames().size()-1)
                System.out.print("all the players     ");
            else if (message.receivers().contains(game.player().nickname()))
                System.out.print("you     ");
            else
                System.out.println(message.receivers().get(0)+"     ");
            System.out.println("Sent time: " + message.sentTime());
            System.out.println(message.content());
        }
        printSplitter();
    }

    private void showCommonCards() {

        List<ImmPlayableCard> resourceCards = new ArrayList<>();
        resourceCards.add(game.topResourceCard());
        resourceCards.addAll(game.revealedResourceCards());

        System.out.println("\n\nResource cards");
        System.out.println(listOfPlayableCardsToString(resourceCards));

        List<ImmPlayableCard> goldCards = new ArrayList<>();
        goldCards.add(game.topGoldCard());
        goldCards.addAll(game.revealedGoldCards());

        System.out.println("\nGold cards");
        System.out.println(listOfPlayableCardsToString(goldCards));

        if (!game.commonObjectiveCards().isEmpty()) {
            System.out.println("\nCommon objective cards");
            System.out.println(listOfObjectiveCardsToString(game.commonObjectiveCards()));
        }

    }

    private void showYou(){
        printSplitter();
        Color color = game.player().color();
        if (color != null)
            System.out.println(color.getColorCode() + "\nYour play area" + "\u001B[0m");
        else
            System.out.println("\nYour play area");
        System.out.println("Points: " + game.player().playerArea().points());
        System.out.println("Resources and objects: " + game.player().playerArea().numOfSymbols().toString());
        System.out.println(game.player().playerArea().areaTUI());

        if (game.player().playerArea().objectiveCard() != null)
            System.out.println(game.player().playerArea().objectiveCard().tui());

        if (!game.player().hand().isEmpty())
            System.out.println(listOfPlayableCardsToString(game.player().hand()));
    }


    private String listOfPlayableCardsToString(List<ImmPlayableCard> hand){

        List<List<String>> cardsAsStrings = new ArrayList<>(new ArrayList<>());
        StringBuilder outString = new StringBuilder();

        for (ImmPlayableCard card : hand){
            cardsAsStrings.add(Arrays.asList(card.handCard().split("\n")));
        }

        for (int i = 0; i < cardsAsStrings.getFirst().size(); i++) {
            for (List<String> cardsAsString : cardsAsStrings)
                outString.append(cardsAsString.get(i));
            outString.append("\n");
        }

        return outString.toString();

    }

    private String listOfObjectiveCardsToString(List<ImmObjectiveCard> cards){

        List<List<String>> cardsAsStrings = new ArrayList<>(new ArrayList<>());
        StringBuilder outString = new StringBuilder();

        for (ImmObjectiveCard card : cards){
            cardsAsStrings.add(Arrays.asList(card.tui().split("\n")));
        }

        for (int i = 0; i < cardsAsStrings.getFirst().size(); i++) {
            for (List<String> cardsAsString : cardsAsStrings)
                outString.append(cardsAsString.get(i));
            outString.append("\n");
        }

        return outString.toString();

    }

    private void printSpace() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    private void printSplitter() {
        System.out.println("////////////////////////////////////////////////////////////////////////");
    }

}
