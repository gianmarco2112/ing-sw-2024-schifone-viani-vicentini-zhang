package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;

import javax.swing.*;
import java.util.List;

public class GameplayGraphicUI extends JFrame implements GameplayObserver {

    @Override
    public void updateFlipCard(FlipCard flipCard) {

    }

    @Override
    public void updatePlayCard(PlayCard playCard, int x, int y) throws NotYourTurnException {

    }

    @Override
    public void updateDrawCard(DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException {

    }

    @Override
    public void updateText(Message message, String content, List<String> receivers) {

    }
}
