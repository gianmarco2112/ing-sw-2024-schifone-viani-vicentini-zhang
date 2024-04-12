package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.model.observerObservable.Event;
import ingsw.codex_naturalis.model.observerObservable.GameView;

public class SetupTextualUI extends SetupUI {

    private String nickname;


    public SetupTextualUI(String nickname){
        this.nickname = nickname;
    }


    public void run() {

    }



    @Override
    public void update(GameView o, Event arg, String nickname) {
        switch (arg) {
        }
    }

        private void showScoreBoard() {
        }
}
