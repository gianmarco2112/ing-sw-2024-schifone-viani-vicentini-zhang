package ingsw.codex_naturalis.client.view.tui;

import ingsw.codex_naturalis.common.immutableModel.ImmPlayableCard;
import ingsw.codex_naturalis.server.exceptions.NoExistingGamesAvailable;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;

import java.io.IOException;
import java.util.List;

/**
 * Class used by the TUI to ask for players' inputs.
 */
public class InputRequesterTUI {
    /**
     * To clear all previous inputs
     */
    private void clearPreviousInputs() {

        try {
            while (System.in.available() > 0) {
                System.in.read(new byte[System.in.available()]);
            }
        } catch (IOException e){
            System.err.println("Error [System.in.available]");
        }

    }

    /**
     * Prints the game's access options
     */
    public void gameAccessOption() {

        clearPreviousInputs();
        System.out.println("""
                
                
                ----------------------------------------
                Please choose an option for game access:
                
                (1) Create a new game
                (2) Access an existing game
                ----------------------------------------
                
                
                """);

    }

    /**
     * Asks for the number of players in the new game
     */
    public void newGameAccess() {

        clearPreviousInputs();
        System.out.println("""
                
                
                --------------------------------------------------------------------
                Please specify the number of players for the game   (Min: 2, Max: 4)
                
                (/) Back
                --------------------------------------------------------------------
                
                
                """);

    }

    /**
     * Asks for a nickname
     */
    public void nickname() {

        clearPreviousInputs();
        System.out.println("""
                
                
                --------------------
                Choose your nickname
                
                --------------------
                
                
                """);

    }

    /**
     * Asks which game to access
     * @param gamesSpecs available games
     * @throws NoExistingGamesAvailable no existing games available
     */
    public void existingGameAccess(List<GameSpecs> gamesSpecs) throws NoExistingGamesAvailable {

        if (gamesSpecs.isEmpty())
            throw new NoExistingGamesAvailable();

        clearPreviousInputs();
        System.out.println("""
                
                
                ------------------------------------------------------------------------------
                Which game do you want to access?
                
                (/) Back""");
        for (int i = 0; i < gamesSpecs.size(); i++) {
            System.out.println((i+1) + " - "
            + "Game ID: " + gamesSpecs.get(i).ID()
            + "     Current number of players connected: " + gamesSpecs.get(i).currentNumOfPlayers()
            + "     Max number of players: " + gamesSpecs.get(i).maxNumOfPlayers());
        }
        System.out.println("------------------------------------------------------------------------------\n");

    }

    /**
     * Asks to play or flip the initial card
     */
    public void initialCardOption() {

        clearPreviousInputs();
        System.out.println("""
                
                
                
                ---------------------------------------------------
                Please play the initial card on your preferred side
                
                (1) Flip
                (2) Play
                ---------------------------------------------------
                
                """);

    }

    /**
     * Asks to choose a color
     */
    public void colorOption() {

        clearPreviousInputs();
        System.out.println("""
                
                
                
                ------------------------
                Please choose your color
                
                (1) Red
                (2) Blue
                (3) Green
                (4) Yellow
                ------------------------
                
                """);

    }

    /**
     * Asks to choose a secret objective card
     * @param objectiveCards secret objective cards from which you can choose
     */
    public void objectiveCardOption(String objectiveCards) {

        clearPreviousInputs();
        System.out.println("""
                
                
                
                ----------------------------------------
                Please choose your secret objective card
                
                (1) Objective card 1
                (2) Objective card 2
                """);
        System.out.println(objectiveCards);
        System.out.println("----------------------------------------\n");

    }

    /**
     * Asks the action to do while in gameplay phase
     */
    public void playing() {
        System.out.println("""
                
                ------------------
                Commands list:
                
                (1) Flip a card
                (2) Play a card
                (3) Draw a card
                (4) Send a message
                (5) LEAVE THE GAME
                ------------------
                """);
    }

    /**
     * Asks which card to flip
     * @param hand hand cards
     */
    public void flippingCardOption(List<ImmPlayableCard> hand) {
        clearPreviousInputs();
        System.out.println("""
                
                -------------------------------
                Which card do you want to flip?
                
                (/) Back""");
        for (int i = 0; i < hand.size(); i++)
            System.out.println("(" + (i+1) + ") Card " + (i+1));
        System.out.println("-------------------------------");
    }

    /**
     * Asks which card to play
     * @param hand hand cards
     */
    public void playingCardOption(List<ImmPlayableCard> hand) {
        clearPreviousInputs();
        System.out.println("""
                
                -------------------------------
                Which card do you want to play?
                
                (/) Back""");
        for (int i = 0; i < hand.size(); i++)
            System.out.println("(" + (i+1) + ") Card " + (i+1));
        System.out.println("-------------------------------");
    }

    /**
     * Asks which coordinate to play in
     * @param coordinate where to play the card
     */
    public void coordinate(String coordinate) {
        clearPreviousInputs();
        System.out.println("""
                
                -----------------------------""");
        System.out.println("Please write the " + coordinate + " coordinate");
        System.out.println("\n(/) Back");
        System.out.println("-----------------------------\n");
    }

    /**
     * Asks which card to draw
     */
    public void drawingCardOption() {
        System.out.println("""
                
                -------------------------------
                Which card do you want to draw?
                
                (/) Back
                (1) Resource card from deck
                (2) Gold card from deck
                (3) Revealed resource card 1
                (4) Revealed resource card 2
                (5) Revealed gold card 1
                (6) Revealed gold card 2
                -------------------------------
                """);
    }

    /**
     * Asks which player/s to text
     * @param playersToText possible players to text
     */
    public void sendingMessage(List<String> playersToText) {
        clearPreviousInputs();
        System.out.println("""
                
                
                -------------------------------------
                Who do you want to send e message to?
                
                (/) Back
                (1) {To all the players}""");
        if (playersToText.size() > 1) {
            for (int i = 0; i < playersToText.size(); i++)
                System.out.println("(" + (i + 2) + ") " + playersToText.get(i));
        }
        System.out.println("-------------------------------------\n");
    }

    /**
     * Asks for the chat message content
     */
    public void messageContent() {
        clearPreviousInputs();
        System.out.println("""
                
                
                ------------------
                Write your message
                
                (/) Back
                ------------------
                
                
                """);
    }


    /**
     * Asks confirmation before leaving the game
     */
    public void leaveGame() {
        clearPreviousInputs();
        System.out.println("""
                
                ----------------------------------------
                Are you sure you want to leave the game?
                
                (/) Back
                (1) Yes
                ----------------------------------------
                """);
    }

}
