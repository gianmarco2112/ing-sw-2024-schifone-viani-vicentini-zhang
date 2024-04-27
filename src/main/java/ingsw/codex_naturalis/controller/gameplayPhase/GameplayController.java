package ingsw.codex_naturalis.controller.gameplayPhase;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.enumerations.TurnStatus;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.exceptions.*;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Message;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;

import java.util.ArrayList;
import java.util.List;


public class GameplayController {

    private final Game model;
    private final List<Client> views;

    //including the player who gets to 20 points
    private int turnsLeftInSecondToLastRound;
    private int turnsLeftInLastRound;

    //--------------------------------------------------------------------------------------
    public GameplayController(Game model, List<Client> views) {
        this.model = model;
        this.views = views;
        turnsLeftInLastRound = model.getNumOfPlayers();
        turnsLeftInSecondToLastRound = model.getNumOfPlayers();
    }

    //-----------------------------------------------------------------------------------------

    public synchronized void updateFlipCard(String nickname, FlipCardEvent flipCardEvent) {

        Player player = model.getPlayerByNickname(nickname);
        switch (flipCardEvent) {
            case FLIP_CARD_1 -> player.flip(player.getHand().get(0));
            case FLIP_CARD_2 -> player.flip(player.getHand().get(1));
            case FLIP_CARD_3 -> player.flip(player.getHand().get(2));
        }

    }


    public synchronized void updatePlayCard(String nickname, PlayCardEvent playCardEvent, int x, int y) throws NotYourTurnException, NotYourPlayTurnStatusException, NotPlayableException {

        Player player = model.getPlayerByNickname(nickname);

        if (!nickname.equals(model.getCurrentPlayer().getNickname()))
            throw new NotYourTurnException();

        if (!player.getTurnStatus().equals(TurnStatus.PLAY))
            throw new NotYourPlayTurnStatusException();

        PlayableCard cardToPlay;
        switch (playCardEvent) {
            case PLAY_CARD_1 -> cardToPlay = player.getHand().get(0);
            case PLAY_CARD_2 -> cardToPlay = player.getHand().get(1);
            case PLAY_CARD_3 -> cardToPlay = player.getHand().get(2);
            default -> { return; }
        }

        if(!cardToPlay.isPlayable(player.getPlayerArea(), x, y))
            throw new NotPlayableException();

        player.playCard(cardToPlay, x, y);

        checkGameStatus(player);
    }

    private void checkGameStatus(Player player) {

        if (player.getPlayerArea().getPoints() >= 20 && model.getGameStatus() == GameStatus.GAMEPLAY) {
            turnsLeftInSecondToLastRound = model.getNumOfPlayers() - model.getPlayerOrder().indexOf(player);
            model.setGameStatus(GameStatus.LAST_ROUND_20_POINTS);
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() != GameStatus.LAST_ROUND_DECKS_EMPTY)
            player.setTurnStatus(TurnStatus.DRAW);

        if (turnsLeftInSecondToLastRound == 0) {
            turnsLeftInLastRound--;
            nextPlayer();
        }

        if (turnsLeftInSecondToLastRound > 0 && model.getGameStatus() == GameStatus.LAST_ROUND_DECKS_EMPTY) {
            turnsLeftInSecondToLastRound--;
            nextPlayer();
        }

        if (turnsLeftInLastRound == 0) {
            model.setGameStatus(GameStatus.ENDGAME);
        }

    }



    public synchronized void updateDrawCard(String nickname, DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException {

        Player player = model.getPlayerByNickname(nickname);

        if (!nickname.equals(model.getCurrentPlayer().getNickname()))
            throw new NotYourTurnException();

        if (!player.getTurnStatus().equals(TurnStatus.DRAW))
            throw new NotYourDrawTurnStatusException();

        PlayableCard drawnCard;
        switch (drawCardEvent) {
            case DRAW_FROM_RESOURCE_CARDS_DECK -> {
                drawnCard = model.getResourceCardsDeck().drawACard();
                drawnCard.flip();
            }
            case DRAW_FROM_GOLD_CARDS_DECK -> {
                drawnCard = model.getGoldCardsDeck().drawACard();
                drawnCard.flip();
            }
            case DRAW_REVEALED_RESOURCE_CARD_1 -> {
                drawnCard = model.removeRevealedResourceCard(0);
                if (!model.getResourceCardsDeck().isEmpty()) {
                    PlayableCard cardToReveal = model.getResourceCardsDeck().drawACard();
                    cardToReveal.flip();
                    model.addRevealedResourceCard(cardToReveal);
                }
            }
            case DRAW_REVEALED_RESOURCE_CARD_2 -> {
                drawnCard = model.removeRevealedResourceCard(1);
                if (!model.getResourceCardsDeck().isEmpty()) {
                    PlayableCard cardToReveal = model.getResourceCardsDeck().drawACard();
                    cardToReveal.flip();
                    model.addRevealedResourceCard(cardToReveal);
                }
            }
            case DRAW_REVEALED_GOLD_CARD_1 -> {
                drawnCard = model.removeRevealedGoldCard(0);
                if (!model.getGoldCardsDeck().isEmpty()) {
                    PlayableCard cardToReveal = model.getGoldCardsDeck().drawACard();
                    cardToReveal.flip();
                    model.addRevealedGoldCard(cardToReveal);
                }
            }
            case DRAW_REVEALED_GOLD_CARD_2 -> {
                drawnCard = model.removeRevealedGoldCard(1);
                if (!model.getGoldCardsDeck().isEmpty()) {
                    PlayableCard cardToReveal = model.getGoldCardsDeck().drawACard();
                    cardToReveal.flip();
                    model.addRevealedGoldCard(cardToReveal);
                }
            }
            default -> { return; }
        }

        nextPlayer();

        player.drawCard(drawnCard);

        checkTurnsLeftInSecondToLastRound(player);

    }

    private void checkTurnsLeftInSecondToLastRound(Player player) {
    if (model.getResourceCardsDeck().isEmpty() &&
        model.getGoldCardsDeck().isEmpty() &&
        model.getRevealedResourceCards().isEmpty() &&
        model.getRevealedGoldCards().isEmpty()) {
            turnsLeftInSecondToLastRound = model.getNumOfPlayers() - model.getPlayerOrder().indexOf(player) - 1;
            model.setGameStatus(GameStatus.LAST_ROUND_DECKS_EMPTY);
        }

        if (model.getGameStatus() == GameStatus.LAST_ROUND_20_POINTS)
            turnsLeftInSecondToLastRound--;
    }

    /**
     * Sets the new current player
     */
    private void nextPlayer() {
        Player nextPlayer;
        int index = model.getPlayerOrder().indexOf(model.getCurrentPlayer());
        if (index < model.getPlayerOrder().size() -1 ) {
            nextPlayer = model.getPlayerOrder().get(index+1);
        } else {
            nextPlayer = model.getPlayerOrder().getFirst();
        }
        model.setCurrentPlayer(nextPlayer);
    }



    public synchronized void updateSendMessage(String nickname, String receiver, String content) {

        List<String> receivers = new ArrayList<>();
        if (receiver != null)
            receivers.add(receiver);
        else {
            for (Player player : model.getPlayerOrder())
                if (!player.getNickname().equals(nickname))
                    receivers.add(player.getNickname());
        }
        model.addMessageToChat(new Message(content, nickname, receivers));

    }




    public Game getModel() {
        return model;
    }

    public List<Client> getViews(){
        return views;
    }
}