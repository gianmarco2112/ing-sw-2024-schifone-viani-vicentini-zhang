package ingsw.codex_naturalis.client.view.util;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;
import ingsw.codex_naturalis.server.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.server.exceptions.NotYourTurnException;

public interface ViewObserver {

    void ctsUpdateNickname(String nickname);

    void ctsUpdateGameToAccess(int gameID);

    void ctsUpdateNewGame(int numOfPlayers);


    void ctsUpdateReady();

    void ctsUpdateInitialCard(InitialCardEvent initialCardEvent);

    void ctsUpdateColor(Color color);

    void ctsUpdateObjectiveCardChoice(int index);


    void ctsUpdateFlipCard(int index);

    void ctsUpdatePlayCard(int index, int x, int y) throws NotYourTurnException;

    void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException;

    void ctsUpdateSendMessage(String receiver, String content);


    void updateLeaveGame();

    void updateGetGameController();
}
