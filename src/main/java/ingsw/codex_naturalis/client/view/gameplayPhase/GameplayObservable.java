package ingsw.codex_naturalis.client.view.gameplayPhase;

import ingsw.codex_naturalis.client.util.GameplayObserver;
import ingsw.codex_naturalis.common.events.gameplayPhase.DrawCardEvent;
import ingsw.codex_naturalis.common.events.gameplayPhase.FlipCardEvent;
import ingsw.codex_naturalis.common.events.gameplayPhase.PlayCardEvent;

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


    public void notifyFlipCard(FlipCardEvent flipCardEvent) {
        for (GameplayObserver o : obs){
            o.ctsUpdateFlipCard(flipCardEvent);
        }
    }

    public void notifyPlayCard(PlayCardEvent playCardEvent, int x, int y) {
        for (GameplayObserver o : obs){
            o.ctsUpdatePlayCard(playCardEvent, x, y);
        }
    }

    public void notifyDrawCard(DrawCardEvent drawCardEvent) {
        for (GameplayObserver o : obs){
            o.ctsUpdateDrawCard(drawCardEvent);
        }
    }

    public void notifySendMessage(String receiver, String content) {
        for (GameplayObserver o : obs){
            o.ctsUpdateSendMessage(receiver, content);
        }
    }
}