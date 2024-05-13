package ingsw.codex_naturalis.client.view.gui;

import ingsw.codex_naturalis.client.view.UI;
import ingsw.codex_naturalis.client.view.util.UIObservableItem;
import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.server.model.util.GameEvent;

import java.util.List;

public class GraphicalUI implements UI {

    private final UIObservableItem uiObservableItem;

    public GraphicalUI(UIObservableItem uiObservableItem) {
        this.uiObservableItem = uiObservableItem;
    }


    @Override
    public void run() {

    }

    @Override
    public void reportError(String error) {

    }

    @Override
    public void setNickname(String nickname) {

    }

    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {

    }

    @Override
    public void updateGameID(int gameID) {

    }

    @Override
    public void allPlayersJoined() {

    }

    @Override
    public void updateSetup(ImmGame immGame, GameEvent gameEvent) {

    }

    @Override
    public void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent) {

    }

    @Override
    public void updateColor(Color color) {

    }

    @Override
    public void updateObjectiveCardChoice(ImmGame immGame) {

    }

    @Override
    public void endSetup(ImmGame game) {

    }

    @Override
    public void cardFlipped(ImmGame game) {

    }

    @Override
    public void cardPlayed(ImmGame immGame, String playerNicknameWhoUpdated) {

    }

    @Override
    public void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated) {

    }

    @Override
    public void turnChanged(String currentPlayer) {

    }

    @Override
    public void messageSent(ImmGame immGame) {

    }

    @Override
    public void twentyPointsReached(ImmGame immGame) {

    }

    @Override
    public void decksEmpty(ImmGame immGame) {

    }

    @Override
    public void gameEnded(String winner, List<String> players, List<Integer> points, List<ImmObjectiveCard> secretObjectiveCards) {

    }

    @Override
    public void gameCanceled() {

    }

    @Override
    public void gameLeft() {

    }

    @Override
    public void gameRejoined(ImmGame game) {

    }

    @Override
    public void updatePlayerInGameStatus(ImmGame immGame, String playerNickname, boolean inGame, boolean hasDisconnected) {

    }

    @Override
    public void gamePaused() {

    }

    @Override
    public void gameResumed() {

    }
}
