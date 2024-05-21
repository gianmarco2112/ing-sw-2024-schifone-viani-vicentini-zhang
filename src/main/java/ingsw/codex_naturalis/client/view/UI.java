package ingsw.codex_naturalis.client.view;

import ingsw.codex_naturalis.common.immutableModel.ImmGame;
import ingsw.codex_naturalis.common.immutableModel.ImmObjectiveCard;
import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.common.immutableModel.GameSpecs;
import ingsw.codex_naturalis.server.model.util.GameEvent;

import java.util.List;

/**
 * Interface implemented by the TUI and the GUI
 */
public interface UI {

    void run();


    void reportError(String error);

    void setNickname(String nickname);

    void updateGamesSpecs(List<GameSpecs> gamesSpecs);

    void updateGameID(int gameID);

    void allPlayersJoined();

    void updateSetup(ImmGame immGame, GameEvent gameEvent);


    void updateInitialCard(ImmGame game, InitialCardEvent initialCardEvent);

    void updateColor(Color color);

    void updateObjectiveCardChoice(ImmGame immGame);

    void endSetup(ImmGame game);

    void cardFlipped(ImmGame game);

    void cardPlayed(ImmGame immGame, String playerNicknameWhoUpdated);

    void cardDrawn(ImmGame immGame, String playerNicknameWhoUpdated);

    void turnChanged(String currentPlayer);

    void messageSent(ImmGame immGame);

    void twentyPointsReached(ImmGame immGame);

    void decksEmpty(ImmGame immGame);

    void gameEnded(String winner, List<String> players, List<Integer> points, List<ImmObjectiveCard> secretObjectiveCards);

    void gameCanceled();

    void gameLeft();

    void gameRejoined(ImmGame game);

    void updatePlayerInGameStatus(ImmGame immGame, String playerNickname,
                                         boolean inGame, boolean hasDisconnected);

    void gamePaused();

    void gameResumed();

    void playerIsReady(String playerNickname);
}
