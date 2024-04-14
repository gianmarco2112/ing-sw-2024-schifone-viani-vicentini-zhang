package ingsw.codex_naturalis.controller.gameplayPhase;


import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourTurnStatusException;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;

import java.util.List;

public interface GameplayObserver {

    void updateFlipCard(FlipCard flipCard);
    void updatePlayCard(PlayCard playCard, int x, int y) throws NotYourTurnException;
    void updateDrawCard(DrawCard drawCard) throws NotYourTurnException, NotYourTurnStatusException;
    void updateText(Message message, String content, List<String> receivers);

}
