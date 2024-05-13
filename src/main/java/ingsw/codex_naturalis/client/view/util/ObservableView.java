package ingsw.codex_naturalis.client.view.util;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.events.DrawCardEvent;
import ingsw.codex_naturalis.common.events.InitialCardEvent;

import java.util.ArrayList;
import java.util.List;

public class ObservableView {

    private final List<ViewObserver> obs;


    public ObservableView() {
        obs = new ArrayList<>();
    }


    public synchronized void addObserver(ViewObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    public synchronized void deleteObservers() {
        obs.clear();
    }



    public void notifyNickname(String nickname) {
        for (ViewObserver o : obs){
            o.ctsUpdateNickname(nickname);
        }
    }

    public void notifyNewGame(int numOfPlayers) {
        for (ViewObserver o : obs){
            o.ctsUpdateNewGame(numOfPlayers);
        }
    }

    public void notifyGameToAccess(int gameID) {
        for (ViewObserver o : obs){
            o.ctsUpdateGameToAccess(gameID);
        }
    }

    public void notifyReady() {
        for (ViewObserver o : obs){
            o.ctsUpdateReady();
        }
    }

    public void notifyInitialCard(InitialCardEvent initialCardEvent) {
        for (ViewObserver o : obs){
            o.ctsUpdateInitialCard(initialCardEvent);
        }
    }

    public void notifyColor(Color color) {
        for (ViewObserver o : obs){
            o.ctsUpdateColor(color);
        }
    }

    public void notifyObjectiveCardChoice(int index) {
        for (ViewObserver o : obs){
            o.ctsUpdateObjectiveCardChoice(index);
        }
    }


    public void notifyFlipCard(int index) {
        for (ViewObserver o : obs){
            o.ctsUpdateFlipCard(index);
        }
    }

    public void notifyPlayCard(int index, int x, int y) {
        for (ViewObserver o : obs){
            o.ctsUpdatePlayCard(index, x, y);
        }
    }

    public void notifyDrawCard(DrawCardEvent drawCardEvent) {
        for (ViewObserver o : obs){
            o.ctsUpdateDrawCard(drawCardEvent);
        }
    }

    public void notifySendMessage(String receiver, String content) {
        for (ViewObserver o : obs){
            o.ctsUpdateSendMessage(receiver, content);
        }
    }


    public void notifyLeaveGame() {
        for (ViewObserver o : obs){
            o.updateLeaveGame();
        }
    }

    public void notifyGetGameController() {
        for (ViewObserver o : obs){
            o.updateGetGameController();
        }
    }
}
