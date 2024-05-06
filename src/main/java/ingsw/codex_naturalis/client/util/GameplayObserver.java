package ingsw.codex_naturalis.client.util;


import ingsw.codex_naturalis.common.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.common.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.common.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.common.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.common.events.gameplayPhase.DrawCardEvent;

public interface GameplayObserver {

    void ctsUpdateFlipCard(FlipCardEvent flipCardEvent);
    void ctsUpdatePlayCard(PlayCardEvent playCardEvent, int x, int y) throws NotYourTurnException;
    void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException;
    void ctsUpdateSendMessage(String receiver, String content);

}
