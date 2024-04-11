package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.view.gameplayPhase.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.FlipCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.PlayCardCommand;
import ingsw.codex_naturalis.view.gameplayPhase.commands.TextCommand;

import java.util.ArrayList;
import java.util.List;

public class GameplayObservable {

    private final List<GameplayObserver> obs;


    public GameplayObservable() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(GameplayObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(GameplayObserver o) {
        obs.remove(o);
    }


    public void notifyFlipCard(String nickname, FlipCardCommand flipCardCommand) {
        for (GameplayObserver o : obs){
            o.updateFlipCard(nickname, flipCardCommand);
        }
    }

    public void notifyPlayCard(String nickname, PlayCardCommand playCardCommand, int x, int y) {
        for (GameplayObserver o : obs){
            o.updatePlayCard(nickname, playCardCommand, x, y);
        }
    }

    public void notifyDrawCard(String nickname, DrawCardCommand drawCardCommand) {
        for (GameplayObserver o : obs){
            o.updateDrawCard(nickname, drawCardCommand);
        }
    }

    public void notifyText(String nickname, TextCommand textCommand, String content, List<String> receivers) {
        for (GameplayObserver o : obs){
            o.updateText(nickname, textCommand, content, receivers);
        }
    }
}