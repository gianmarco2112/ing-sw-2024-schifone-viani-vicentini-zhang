package ingsw.codex_naturalis.view.playing;

import ingsw.codex_naturalis.controller.Controller;
import ingsw.codex_naturalis.model.Game;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        TextualUI view = new TextualUI(new ArrayList<>(List.of("Gianmarco", "Andrea", "Alessia", "Leonardo")));

        Controller controller = new Controller(new Game(0, 4), view);

        view.addObserver(controller);

        view.run();
    }
}
