package ingsw.codex_naturalis.controller.gameplayPhase;


import ingsw.codex_naturalis.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCardEvent;

public interface GameplayObserver {

    void ctsUpdateFlipCard(FlipCardEvent flipCardEvent);
    void ctsUpdatePlayCard(PlayCardEvent playCardEvent, int x, int y) throws NotYourTurnException;
    void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException;
    void ctsUpdateSendMessage(String receiver, String content);

}
