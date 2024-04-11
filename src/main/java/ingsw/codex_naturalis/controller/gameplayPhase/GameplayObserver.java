package ingsw.codex_naturalis.controller.gameplayPhase;


import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourTurnStatusException;
import ingsw.codex_naturalis.view.gameplayPhase.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.FlipCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.PlayCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.TextCommand;

import java.util.List;

public interface GameplayObserver {

    void updateFlipCard(String nickname, FlipCardCommand flipCardCommand);
    void updatePlayCard(String nickname, PlayCardCommand playCardCommand, int x, int y) throws NotYourTurnException;
    void updateDrawCard(String nickname, DrawCardCommand drawCardCommand) throws NotYourTurnException, NotYourTurnStatusException;
    void updateText(String nickname, TextCommand textCommand, String content, List<String> receivers);

}
