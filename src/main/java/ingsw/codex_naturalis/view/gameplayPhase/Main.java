package ingsw.codex_naturalis.view.gameplayPhase;

import ingsw.codex_naturalis.controller.gameplayPhase.GameplayController;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        GameplayUI view = new GameplayTextualUI("nickname", new ArrayList<>(List.of("Gianmarco", "Andrea", "Alessia", "Leonardo")));

        Game game = new Game(1, 3);
        Player player = new Player("nickname");
        player.setHand(new ArrayList<>(List.of(new PlayableCard(null, null, null, null, null), new PlayableCard(null, null, null, null, null))));
        game.addPlayer(player);
        GameplayController gameplayController = new GameplayController(game, view);

        view.addObserver(gameplayController);

        view.run();
    }
}
