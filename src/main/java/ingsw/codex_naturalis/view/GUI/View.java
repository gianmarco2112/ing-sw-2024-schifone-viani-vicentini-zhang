package ingsw.codex_naturalis.view.GUI;

import ingsw.codex_naturalis.server.GameControllerImpl;
import ingsw.codex_naturalis.common.enumerations.GameStatus;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.util.GameEvent;

import java.util.List;

public interface View extends Runnable{
    /**
     * Method called to show updated view
     * @param msg The GameView that represents the immutable version of the updated model
     * @param ev Event that caused the model's change
     */
    void update(Game.Immutable msg, GameStatus ev);
    void run();

    /**
     * Method called to stop the thread that handles the nickname choice
     */
    void endLoginPhase();
    void endLobbyPhase();

    /**
     * Returns the ID of the game the user is in
     */
    int getGameID();

    Game.Immutable getGameView();

    void setNickname(String nickname);

    void setAvailableGames(List<GameControllerImpl> availableGamesList);

    void setReconnecting(boolean reconnecting);

    void nicknameAlreadyUsed();
}
