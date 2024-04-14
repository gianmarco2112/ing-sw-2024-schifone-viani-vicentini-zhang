package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayObserver;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;
import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;

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

    public synchronized void deleteObservers() {
        obs.clear();
    }


    public void notifyFlipCard(FlipCard flipCard) {
        for (GameplayObserver o : obs){
            o.updateFlipCard(flipCard);
        }
    }

    public void notifyPlayCard(PlayCard playCard, int x, int y) {
        for (GameplayObserver o : obs){
            o.updatePlayCard(playCard, x, y);
        }
    }

    public void notifyDrawCard(DrawCard drawCard) {
        for (GameplayObserver o : obs){
            o.updateDrawCard(drawCard);
        }
    }

    public void notifyText(Message message, String content, List<String> receivers) {
        for (GameplayObserver o : obs){
            o.updateText(message, content, receivers);
        }
    }
}