package ingsw.codex_naturalis.controller.gameplayPhase;


import ingsw.codex_naturalis.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCardEvent;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCardEvent;
import ingsw.codex_naturalis.events.gameplayPhase.Message;

import java.util.List;

public interface GameplayObserver {

    void ctsUpdateFlipCard(FlipCardEvent flipCardEvent);
    void ctsUpdatePlayCard(PlayCardEvent playCardEvent, int x, int y) throws NotYourTurnException;
    void ctsUpdateDrawCard(DrawCardEvent drawCardEvent) throws NotYourTurnException, NotYourDrawTurnStatusException;
    void ctsUpdateText(Message message, String content, List<String> receivers);

}
