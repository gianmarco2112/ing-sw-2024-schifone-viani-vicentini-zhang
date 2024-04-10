package ingsw.codex_naturalis.view.playing;

import ingsw.codex_naturalis.controller.ObserverController;
import ingsw.codex_naturalis.view.playing.commands.DrawCardCommand;
import ingsw.codex_naturalis.view.playing.commands.FlipCardCommand;
import ingsw.codex_naturalis.view.playing.commands.PlayCardCommand;
import ingsw.codex_naturalis.view.playing.commands.TextCommand;

import java.util.ArrayList;
import java.util.List;

public class ObservableView {

    private final List<ObserverController> obs;


    public ObservableView() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(ObserverController o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObserver(ObserverController o) {
        obs.remove(o);
    }


    public void notifyFlipCard(String nickname, FlipCardCommand flipCardCommand) {
        for (ObserverController o : obs){
            o.updateFlipCard(nickname, flipCardCommand);
        }
    }

    public void notifyPlayCard(String nickname, PlayCardCommand playCardCommand, int x, int y) {
        for (ObserverController o : obs){
            o.updatePlayCard(nickname, playCardCommand, x, y);
        }
    }

    public void notifyDrawCard(String nickname, DrawCardCommand drawCardCommand) {
        for (ObserverController o : obs){
            o.updateDrawCard(nickname, drawCardCommand);
        }
    }

    public void notifyText(String nickname, TextCommand textCommand, String content, List<String> receivers) {
        for (ObserverController o : obs){
            o.updateText(nickname, textCommand, content, receivers);
        }
    }
}